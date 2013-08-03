package edu.illinois.gitsvn.analysis.launchers;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.illinois.gitsvn.analysis.SpecificCommitStartCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class RecoverableLauncher extends AnalysisLauncher {

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String recoveryLine = System.getProperty(RECOVERY_LINE);
		
		String[] split = recoveryLine.split(RECOVERY_LINE_SEPARATOR);
		String repoPath = split[0];
		String projectName = split[1];
		String commitIDsAsString = split[2];
		List<String> commitIDs = Arrays.asList(commitIDsAsString.split(COMMIT_SEPARATOR));
		int cutoffTime = Integer.parseInt(split[3]);
		
		configurations.add(new SpecificCommitStartCutoffAnalysis(repoPath, projectName, commitIDs, cutoffTime));
	}
	
	@Test
	public void test() throws Exception {
		run();
	}

}
