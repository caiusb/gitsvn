package edu.illinois.gitsvn.infra.collectors;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.CommitUtils;
import org.gitective.tests.GitTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ASTNodeCollectorTest extends GitTestCase{
	
	private ASTNodeCollector collector;
	private Repository repository;
	private RevWalk walker;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		collector = new ASTNodeCollector();
		repository = Git.open(testRepo).getRepository();
		collector.setRepository(repository);
		walker = new RevWalk(repository);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDescription = workspace.getDescription();
		workspaceDescription.setAutoBuilding(false);
		workspace.setDescription(workspaceDescription);
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testOneAddCommit() throws Exception {
		RevCommit commit = add("Test.java", "public class Test{}");
		
		collector.include(walker, commit);
		assertEquals("AST Operations",collector.name());
		assertEquals("3",collector.getDataForCommit());
	}
	
	@Test
	public void testRenameCommit() throws Exception {
		add("Test.java", "public class Test{int x;}");
		RevCommit commit = add("Test.java", "public class Test{int y;}");
		
		collector.include(walker, commit);
		assertEquals("1",collector.getDataForCommit());
	}
	
	@Test
	@Ignore
	public void testProblemCommitInJDTCore() throws Exception {
		setCustomRepo("../../svnToGitRepos/eclipse.jdt.core");
		
		RevCommit problemCommit = CommitUtils.getCommit(repository, "968a44abcc71c95b90a66b2bfce384792751e997");
		collector.include(walker, problemCommit);
		assertEquals("10",collector.getDataForCommit());
	}

	private void setCustomRepo(String customRepo) throws IOException {
		repository = Git.open(new File(customRepo)).getRepository();
		collector.setRepository(repository);
		walker = new RevWalk(repository);
	}
	
	@Test
	public void testProblemCommitInJunit() throws Exception {
		setCustomRepo("../../svnToGitRepos/junit");
		
		RevCommit problemCommit = CommitUtils.getCommit(repository, "30f2b16525dabb477373be9ed3e76bb98b200806");
		collector.include(walker, problemCommit);
	}
	
}
