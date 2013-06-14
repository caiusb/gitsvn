package edu.illinois.gitsvn.infra.collectors;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class MaturityCollectorTest extends GitTestCase {
	
	private MaturityCollector filter;
	private RevWalk revWalk;
	private Repository repository;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		repository = Git.open(testRepo).getRepository();
		filter = new MaturityCollector();
		filter.setRepository(repository);
		revWalk = new RevWalk(repository);
	}
	
	@Test
	public void testNormalCommit() throws Exception {
		RevCommit cmit = add("f","content");
		
		boolean result = filter.include(revWalk, cmit);
		assertFalse(result);
		assertEquals(0, filter.getMaturity(), 0.01);
	}
	
	@Test
	public void testMergeCommit() throws Exception {
		add("f","content");
		branch("test-branch");
		checkout("test-branch");
		add("g","some other content");
		checkout("master");
		add("f","some different content");
		MergeResult mergeResult = merge("test-branch");
		ObjectId newHead = mergeResult.getNewHead();
		RevCommit mergeCommit = revWalk.parseCommit(newHead);
		
		boolean result = filter.include(revWalk, mergeCommit);
		assertFalse(result);
		assertEquals(1, filter.getMaturity(), 0.01);
	}

}
