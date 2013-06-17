package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorCollector;

public class CyclopsGroupAnalysis extends AnalysisConfiguration {

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File("../../projects/cyclopsgroup"));
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	protected PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter configuredAnalysis = super.configureAnalysis();
		configuredAnalysis.addDataCollector(new CutofDetectorCollector(1295380000));
		return configuredAnalysis;
	}

	@Override
	protected String getProjectName() {
		return "CyclopsGroup";
	}
}
