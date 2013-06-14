package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;

public class MaturityCollector extends CommitFilter implements DataCollector {

	private int mergeCommits;
	private int noOfGitCommits;

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		if (cmit.isGitCommit()) {
			noOfGitCommits++;
			int parentCount = cmit.getParentCount();
			if (parentCount > 1)
				mergeCommits++;
		} 
		return false;
	}
	
	public float getMaturity() {
		return (float)mergeCommits/(float)noOfGitCommits;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "Maturity thing";
	}

	@Override
	public String getDataForCommit() {
		return "";
	}

}
