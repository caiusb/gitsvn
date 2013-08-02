package edu.illinois.gitsvn.analysis.launchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.illinois.gitsvn.analysis.SpecificCommitStartCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class RecoverableLauncher extends AnalysisLauncher {

	public static final String REPO_PATH_PROPERTY = "edu.illinois.gitsvn.repopath";
	public static final String PROJECT_NAME_PROPERTY = "edu.illinois.gitsvn.projectname";
	public static final String COMMIT_IDS_PROPERTY = "edu.illinois.gitsvn.commitids";
	public static final String CUTOFF_PROPERTY = "edu.illinois.gitsvn.cutoff";

	@Override
	public void run() throws Exception {
		ArrayList<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();

		populateWithConfigurations(configurations);
	}

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String repoPath = System.getProperty(REPO_PATH_PROPERTY);
		String projectName = System.getProperty(PROJECT_NAME_PROPERTY);
		List<String> commitIDs = Arrays.asList(System.getProperty(COMMIT_IDS_PROPERTY).split(";"));
		int cutoffTime = Integer.parseInt(System.getProperty(CUTOFF_PROPERTY));

		configurations.add(new SpecificCommitStartCutoffAnalysis(repoPath, projectName, commitIDs, cutoffTime));
	}

}
