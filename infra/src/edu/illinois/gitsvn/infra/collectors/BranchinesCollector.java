package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

public class BranchinesCollector extends CommitFilter {

	private int normalCommits = 0;
	private int mergeCommits = 0;

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		
		normalCommits++;
		if (cmit.getParentCount() > 1)
			mergeCommits++;
		
		
		return true;
	}
	
	public float getMaturity() {
		return (float)mergeCommits/(float)normalCommits;
	}

}
