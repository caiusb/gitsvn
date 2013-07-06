package edu.illinois.gitsvn.infra.collectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.DiffEntry.Side;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.ui.internal.ide.misc.AndFileInfoMatcher;
import org.gitective.core.BlobUtils;
import org.gitective.core.filter.commit.DiffCountFilter;

import edu.illinois.codingtracker.operations.ast.ASTOperation;
import edu.illinois.codingtracker.tests.postprocessors.ast.AddDeleteUpdateInferencePostprocessor;
import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.filters.blacklister.NonSourceCodeFileExtensionBlacklister;

public class ASTNodeCollector extends DiffCountFilter implements DataCollector {
	
	private Map<RevCommit, Set<ASTOperation>> astOperations;
	private Set<ASTOperation> setOfOperationForLastCommit;
	
	public ASTNodeCollector() {
		astOperations = new HashMap<RevCommit, Set<ASTOperation>>();
	}

	@Override
	public String name() {
		return "AST Operations";
	}

	@Override
	public String getDataForCommit() {
//		if (setOfOperationForLastCommit == null)
//			return "0";
		return "" + setOfOperationForLastCommit.size();
	}
	
	@Override
	protected TreeWalk createTreeWalk(RevWalk walker, RevCommit commit) {
		// TODO Auto-generated method stub
		TreeWalk treeWalk = super.createTreeWalk(walker, commit);
		TreeFilter newFilter = AndTreeFilter.create(treeWalk.getFilter(), new NonSourceCodeFileExtensionBlacklister());
		treeWalk.setFilter(newFilter);
		return treeWalk;
		
	}
	
	@Override
	public boolean include(RevCommit commit, Collection<DiffEntry> diffs, int diffCount) {
		setOfOperationForLastCommit = new HashSet<ASTOperation>();
		for (DiffEntry diff : diffs) {
			ChangeType changeType = diff.getChangeType();
			String oldFileContent = "";
			
			switch (changeType) {
			case MODIFY:
				AbbreviatedObjectId oldObject = diff.getId(Side.OLD);
				oldFileContent = BlobUtils.getContent(repository, oldObject.toObjectId());
			case ADD:
				AbbreviatedObjectId newObject = diff.getId(Side.NEW);
		
				String newFileContent = BlobUtils.getContent(repository, newObject.toObjectId());
				try {
					Set<ASTOperation> diffAsASTNodeOperations = AddDeleteUpdateInferencePostprocessor
							.getDiffAsASTNodeOperations(oldFileContent, newFileContent);
					setOfOperationForLastCommit = diffAsASTNodeOperations;
					astOperations.put(commit, diffAsASTNodeOperations);
				} catch (CoreException e) {
				}
				break;
			default:
				break;
			}
		}
		return true;
	}
}
