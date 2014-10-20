package edu.illinois.gitsvn.infra.collectors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.List;

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

import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.filters.blacklister.NonSourceCodeFileExtensionBlacklister;
import fr.labri.gumtree.actions.ActionGenerator;
import fr.labri.gumtree.actions.model.Action;
import fr.labri.gumtree.gen.jdt.JdtTreeGenerator;
import fr.labri.gumtree.matchers.Matcher;
import fr.labri.gumtree.matchers.MatcherFactories;
import fr.labri.gumtree.tree.Tree;

public class ASTNodeCollector extends DiffCountFilter implements DataCollector {
	
	private int data;
	private final String tempFolder;
	
	public ASTNodeCollector(String tempFolder) {
		this.tempFolder = tempFolder;
		
	}

	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		TreeWalk treeWalk = super.createTreeWalk(walker, commit);
		TreeFilter filter = AndTreeFilter.create(treeWalk.getFilter(), new NonSourceCodeFileExtensionBlacklister(NonSourceCodeFileExtensionBlacklister.JAVA_EXTENSIONS));
		treeWalk.setFilter(filter);
		return treeWalk;
	}
	
	@Override
	public boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		data = 0; //resetting the state for each commit
		for (DiffEntry diff : diffs) {
			ChangeType changeType = diff.getChangeType();
			String oldFileContent = "";
			String newFileContent = "";
			
			switch (changeType) {
			case MODIFY:
				AbbreviatedObjectId oldObject = diff.getId(Side.OLD);
				oldFileContent = BlobUtils.getContent(repository, oldObject.toObjectId());
			case ADD:
				AbbreviatedObjectId newObject = diff.getId(Side.NEW);
				newFileContent = BlobUtils.getContent(repository, newObject.toObjectId());
				break;
			}
			
			File oldContentFile = Paths.get(tempFolder,"A" + System.currentTimeMillis() + ".java").toFile();
			File newContentFile = Paths.get(tempFolder,"B"+ System.currentTimeMillis() + ".java").toFile();
			
			try {
				oldContentFile.createNewFile();
				newContentFile.createNewFile();
				Files.write(oldContentFile.toPath(), oldFileContent.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
				Files.write(newContentFile.toPath(), newFileContent.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
				
				JdtTreeGenerator treeGenerator = new JdtTreeGenerator();
				Tree oldTree = treeGenerator.fromFile(oldContentFile.getAbsolutePath());
				Tree newTree = treeGenerator.fromFile(newContentFile.getAbsolutePath());
				Matcher matcher = MatcherFactories.newMatcher(oldTree, newTree);
				matcher.match();
				ActionGenerator actionGenerator = new ActionGenerator(oldTree, newTree, matcher.getMappings());
				actionGenerator.generate();
				List<Action> actions = actionGenerator.getActions();
				data += actions.size();
			} catch (IOException e) {
				System.err.println("Error creating temporary file for the AST matcher.");
			}
			
			oldContentFile.delete();
			newContentFile.delete();
			
		}
		return true;
	}

	@Override
	public String name() {
		return "AST Nodes changed";
	}

	@Override
	public String getDataForCommit() {
		return "" + data;
	}

}
