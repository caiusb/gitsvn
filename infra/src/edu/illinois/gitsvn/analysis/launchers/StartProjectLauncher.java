package edu.illinois.gitsvn.analysis.launchers;

import java.util.List;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class StartProjectLauncher extends AnalysisLauncher {

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String repoPath = System.getProperty(RecoverableLauncher.REPO_PATH_PROPERTY);
		String projectName = System.getProperty(RecoverableLauncher.PROJECT_NAME_PROPERTY);
		int cutoff = Integer.parseInt(System.getProperty(RecoverableLauncher.CUTOFF_PROPERTY));
		
		configurations.add(new CutoffGenericAnalysis(repoPath, projectName, cutoff));
	}

}
