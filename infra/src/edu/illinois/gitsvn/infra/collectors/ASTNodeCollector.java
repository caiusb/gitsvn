package edu.illinois.gitsvn.infra.collectors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffEntry.Side;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.gitective.core.BlobUtils;
import org.gitective.core.filter.commit.DiffCountFilter;

import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ChangeDistiller.Language;
import ch.uzh.ifi.seal.changedistiller.ast.InvalidSyntaxException;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.filters.blacklister.NonSourceCodeFileExtensionBlacklister;

public class ASTNodeCollector extends DiffCountFilter implements DataCollector {

	private Set<SourceCodeChange> setOfOperationForLastCommit;

	@Override
	public String name() {
		return "AST Nodes Changed";
	}

	@Override
	public String getDataForCommit() {
		return setOfOperationForLastCommit.size() + "";
	}

	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk treeWalk = super.createTreeWalk(walker, commit);
		TreeFilter newFilter = AndTreeFilter.create(treeWalk.getFilter(), new NonSourceCodeFileExtensionBlacklister(
				NonSourceCodeFileExtensionBlacklister.JAVA_EXTENSIONS));
		treeWalk.setFilter(newFilter);
		return treeWalk;
	}

	@Override
	protected boolean include(RevCommit commit, java.util.Collection<DiffEntry> diffs, int diffCount) {
		setOfOperationForLastCommit = new HashSet<SourceCodeChange>();
		for (DiffEntry diff : diffs) {
			ChangeType changeType = diff.getChangeType();
			String oldFileContent = "";
			String newFileContent = "";
			String oldPath = "/dev/null";
			String newPath = "";

			switch (changeType) {
			case MODIFY:
				AbbreviatedObjectId oldObject = diff.getId(Side.OLD);
				oldPath = diff.getOldPath();
				oldFileContent = BlobUtils.getContent(repository, oldObject.toObjectId());
			case ADD:
				AbbreviatedObjectId newObject = diff.getId(Side.NEW);
				newPath = diff.getNewPath();
				newFileContent = BlobUtils.getContent(repository, newObject.toObjectId());
				break;
			default:
				break;
			}
			
			File oldContentFile = new File("A.java");
			File newContentFile = new File("B.java");
			
			try {
				oldContentFile.createNewFile();
				newContentFile.createNewFile();
				Files.write(oldContentFile.toPath(), oldFileContent.getBytes(), StandardOpenOption.WRITE);
				Files.write(newContentFile.toPath(), newFileContent.getBytes(), StandardOpenOption.WRITE);
			} catch (IOException e) {
			}
			
			FileDistiller fileDistiller = ChangeDistiller.createFileDistiller(Language.JAVA);
			
			try {
				fileDistiller.extractClassifiedSourceCodeChanges(oldContentFile, newContentFile);
			} catch (InvalidSyntaxException e) {
				if (e.getFileName().equals("A.java")) {
					System.out.println("Compilation error in old: " + oldPath);
					System.out.println(oldFileContent);
					System.out.println(e.getMessage());
				} else {
					System.out.println("Compilation error in new: " + newPath);
				}
				return true;
			}
			
			List<SourceCodeChange> sourceCodeChanges = fileDistiller.getSourceCodeChanges();
			for (SourceCodeChange sourceCodeChange : sourceCodeChanges) {
				setOfOperationForLastCommit.add(sourceCodeChange);
			}
			
			oldContentFile.delete();
			newContentFile.delete();
		}
		return true;
	}

}
