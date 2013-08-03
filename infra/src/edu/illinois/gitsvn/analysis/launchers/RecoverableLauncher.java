package edu.illinois.gitsvn.analysis.launchers;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.illinois.gitsvn.analysis.SpecificCommitStartCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class RecoverableLauncher extends AnalysisLauncher {

	public static final String REPO_PATH_PROPERTY = "edu.illinois.gitsvn.repopath";
	public static final String PROJECT_NAME_PROPERTY = "edu.illinois.gitsvn.projectname";
	public static final String COMMIT_IDS_PROPERTY = "edu.illinois.gitsvn.commitids";
	public static final String CUTOFF_PROPERTY = "edu.illinois.gitsvn.cutoff";

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String recoveryLine = System.getProperty(RECOVERY_LINE);
		
		String[] split = recoveryLine.split(";");
		String repoPath = split[0];
		String projectName = split[1];
		String commitIDsAsString = split[2];
		List<String> commitIDs = Arrays.asList(commitIDsAsString.split(","));
		int cutoffTime = Integer.parseInt(split[3]);
		
		configurations.add(new SpecificCommitStartCutoffAnalysis(repoPath, projectName, commitIDs, cutoffTime));
	}
	
	@Test
	public void test() throws Exception {
		run();
	}

}
