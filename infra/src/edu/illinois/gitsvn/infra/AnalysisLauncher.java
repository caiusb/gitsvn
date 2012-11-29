package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.gitsvn.analysis.CyclopsGroupAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTCoreAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTDebugAnalysis;
import edu.illinois.gitsvn.analysis.EclipseJDTUIAnalysis;
import edu.illinois.gitsvn.analysis.EclipsePlatform;
import edu.illinois.gitsvn.analysis.EclipsePlatformCommon;
import edu.illinois.gitsvn.analysis.EclipsePlatformDebug;
import edu.illinois.gitsvn.analysis.EclipsePlatformTeam;
import edu.illinois.gitsvn.analysis.EclipsePlatformText;
import edu.illinois.gitsvn.analysis.ThymeleafAnalysis;

public abstract class AnalysisLauncher {

	/**
	 * Starts the analysis.
	 * 
	 * @param args
	 *            a list of repositories to crawl.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		
		configurations.add(new CyclopsGroupAnalysis());
		configurations.add(new ThymeleafAnalysis());
		configurations.add(new EclipseJDTCoreAnalysis());
		configurations.add(new EclipseJDTDebugAnalysis());
		configurations.add(new EclipseJDTUIAnalysis());
		configurations.add(new EclipsePlatform());
		configurations.add(new EclipsePlatformTeam());
		configurations.add(new EclipsePlatformText());
		configurations.add(new EclipsePlatformDebug());
		configurations.add(new EclipsePlatformCommon());
		
		for (AnalysisConfiguration configuration : configurations) {
			configuration.run();
		}
	}
}