package edu.illinois.gitsvn.analysis.launchers;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class RecoverableLauncher extends AnalysisLauncher {
	
	public static final String REPO_PATH_PROPERTY = "edu.illinois.gitsvn.repopath";
	public static final String COMMIT_IDS_PROPERTY = "edu.illinois.gitsvn.commitids";
	public static final String CUTOFF_PROPERTY = "edu.illinois.gitsvn.cutoff";
	
	private String repoPath;
	private List<String> commitIDs;
	private long cutoffTime;
	
	@Override
	public void run() throws Exception {
		ArrayList<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		populateWithConfigurations(configurations);
		
		loadRecoverPoint();
	}

	private void loadRecoverPoint() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		//magic loading goes here
	}
	
}
