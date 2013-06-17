package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorCollector;

public class EclipsePlatformCommon extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(
					"../../projects/eclipse.platform.common"));
		} catch (IOException e) {
		}

		return null;
	}

	@Override
	protected String getProjectName() {
		return "EclipsePlatformCommon";
	}

	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysis = super.configureAnalysis();
		analysis.addDataCollector(new CutofDetectorCollector(1318741200));
		return analysis;
	}

}
