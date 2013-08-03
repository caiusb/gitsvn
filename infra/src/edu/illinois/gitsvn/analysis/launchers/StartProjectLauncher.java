package edu.illinois.gitsvn.analysis.launchers;

import java.util.List;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class StartProjectLauncher extends AnalysisLauncher {

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		String initLine = System.getProperty(INIT_LINE);
		
		String[] split = initLine.split(INIT_LINE_SEPARATOR);
		String repoPath = split[0];
		String projectName = split[1];
		int cutoff = Integer.parseInt(split[2]);
		
		configurations.add(new CutoffGenericAnalysis(repoPath, projectName, cutoff));
	}

}
