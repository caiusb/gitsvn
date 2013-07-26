package edu.illinois.gitsvn.infra.filters.blacklister;

import java.util.Arrays;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class TooManyChangesBlacklisterTest extends GitTestCase {

	private TooManyChangesBlacklister blacklister;
	private RevWalk revWalk;
	private String[] names;
	private String[] contents;

	@Before
	public void before() throws Exception {
		blacklister = new TooManyChangesBlacklister();
		revWalk = new RevWalk(Git.open(testRepo).getRepository());
	}

	@Test
	public void testOver50Added() throws Exception {
		RevCommit commit = addXFiles(51);

		boolean result = blacklister.include(revWalk, commit);
		assertFalse(result);
	}

	private RevCommit addXFiles(int noOfFiles) throws Exception {
		names = new String[noOfFiles];
		contents = new String[noOfFiles];

		for (int i = 0; i < noOfFiles; i++) {
			names[i] = i + ".java";
			contents[i] = i + "";
		}
		
		return add(Arrays.asList(names), Arrays.asList(contents));
	}

	@Test
	public void testOver50Edited() throws Exception {
		int noOfFiles = 51;
		addXFiles(noOfFiles);
		RevCommit commit = editXFiles(noOfFiles);
		
		
		boolean result = blacklister.include(revWalk, commit);
		assertFalse(result);
	}

	private RevCommit editXFiles(int noOfFiles) throws Exception {
		for (int i = 0; i < noOfFiles; i++) {
			names[i] = i + ".java";
			contents[i] = contents[i] + i;
		}
		
		return add(Arrays.asList(names), Arrays.asList(contents));
	}
	
	@Test
	public void testUnder50Added() throws Exception {
		RevCommit commit = addXFiles(49);
		boolean result = blacklister.include(revWalk, commit);
		assertTrue(result);
	}
	
	@Test
	public void testUnder50Edited() throws Exception {
		addXFiles(49);
		RevCommit commit = editXFiles(49);
		
		boolean result = blacklister.include(revWalk, commit);
		assertTrue(result);
		
	}

}
