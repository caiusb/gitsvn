package edu.illinois.gitsvn.infra.filters.blacklister;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;
import org.gitective.core.filter.commit.DiffFileCountFilter;

public class TooManyChangesBlacklister extends CommitFilter {

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {
		DiffFileCountFilter diffFileCountFilter = new DiffFileCountFilter();
		diffFileCountFilter.include(walker, cmit);
		long editedFiles = diffFileCountFilter.getEdited();
		long addedFiles = diffFileCountFilter.getAdded();
		
		if (editedFiles > 50)
			return false;
		if (addedFiles > 50)
			return false;
		
		return true;
	}
	
}
