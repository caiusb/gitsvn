package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import edu.illinois.gitsvn.analysis.GenericAnalysis;

public class SingleRepoLauncher extends AnalysisLauncher {
	
	private String repositoryDir;
	private final String resultsFolder;
	private final String tempFolder;

	public SingleRepoLauncher(String repositoryDir, String resultsFolder, String tempFolder) {
		this.repositoryDir = repositoryDir;
		this.resultsFolder = resultsFolder;
		this.tempFolder = tempFolder;
	}
	
	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
		
		File f = new File(this.repositoryDir);
		if(f.exists()) {
			configurations.add(new GenericAnalysis(f.getAbsolutePath(), f.getName(), resultsFolder, tempFolder));
		} else {
			System.out.println("Repository " + this.repositoryDir + " does not exist");
		}
	}
}