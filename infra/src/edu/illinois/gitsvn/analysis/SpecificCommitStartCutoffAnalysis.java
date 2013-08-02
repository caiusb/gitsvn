package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.RepositoryCrawler;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;

public class SpecificCommitStartCutoffAnalysis extends AnalysisConfiguration {

	private final String repoPath;
	private final String projectName;
	private final List<String> startCommitIDs;
	private final int cutoff;
	private RepositoryCrawler crawler;
	
	public SpecificCommitStartCutoffAnalysis(String repoPath, String projectName, List<String> startCommitIDs,
			int cutoff) {
		this.repoPath = repoPath;
		this.projectName = projectName;
		this.startCommitIDs = startCommitIDs;
		this.cutoff = cutoff;
		
		crawler = new RepositoryCrawler();
		pipeline = configurePipelineAnalysis();
	}

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(repoPath));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return projectName;
	}

	@Override
	public void run() {
		pipeline.addFilter(new CutofDetectorFilter(cutoff));
		crawler.crawlRepo(getGitRepo(), pipeline, startCommitIDs);
	}
}
