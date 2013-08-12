package edu.illinois.gitsvn.analysis.launchers;

import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.analysis.SpecificCommitStartCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;

public class RecoverableLauncherTest extends GitTestCase {

	private String repoPath;
	private Git git;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repoPath = testRepo.getAbsolutePath();
		git = Git.open(testRepo);
		add("first.txt","first");
	}

	@Test
	public void testRestoreWithNoCommits() {
		System.setProperty(RecoverableLauncher.RECOVERY_LINE, repoPath + ";test;;0");

		ArrayList<AnalysisConfiguration> configurations = createLauncherAndGetConfigurations();
		AnalysisConfiguration configuration = configurations.get(0);
		assertTrue(configuration instanceof SpecificCommitStartCutoffAnalysis);
	}

	private ArrayList<AnalysisConfiguration> createLauncherAndGetConfigurations() {
		RecoverableLauncher launcher = new RecoverableLauncher();
		ArrayList<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		launcher.populateWithConfigurations(configurations);
		return configurations;
	}

	@Test
	public void testRestoreWithOneCommit() throws Exception {
		System.setProperty(RecoverableLauncher.RECOVERY_LINE, repoPath + ";test;"
				+ git.getRepository().getRef("master").getObjectId().getName() + ";0");

		ArrayList<AnalysisConfiguration> configurations = createLauncherAndGetConfigurations();
		AnalysisConfiguration configuration = configurations.get(0);
		assertTrue(configuration instanceof SpecificCommitStartCutoffAnalysis);
	}
}
