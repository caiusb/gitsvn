package edu.illinois.gitsvn.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;

/**
 * Generic analysis that receives its repo dir and repo name by constructor. It
 * reuses the default analysis configuration
 * 
 * @author mihai
 * 
 */

public class GenericAnalysis extends AnalysisConfiguration {
	private String repoDir;
	private String projectName;
	private final String resultsFolder;
	private final String tempFolder;

	/**
	 * @deprecated Use {@link #GenericAnalysis(String,String,String, String)} instead
	 */
	public GenericAnalysis(String repoDir, String projectName) {
		this(repoDir, projectName, "../../results", "/Volumes/RAM Disk/");
	}

	public GenericAnalysis(String repoDir, String projectName, String resultsFolder, String tempFolder) {
		this.repoDir = repoDir;
		this.projectName = projectName;
		this.resultsFolder = resultsFolder;
		this.tempFolder = tempFolder;
	}

	@Override
	protected Git getGitRepo() {
		try {
			return Git.open(new File(repoDir));
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	protected String getProjectName() {
		return projectName;
	}
	
	@Override
	protected String getResultsFolder() {
		return resultsFolder;
	}
	
	@Override
	protected String getTemporaryFolder() {
		return tempFolder;
	}
}
