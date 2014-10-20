package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.DirectoryAnalysisLauncher;
import edu.illinois.gitsvn.infra.SingleRepoLauncher;

public class PureReposAnalysis {

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: <cmd> <path to repos> <result-folder> <temp-folder>");
			return;
		}
		String repoRoot = args[0];
		String resultsFolder = args[1];
		String tempFolder = args[2];
		if(args.length > 3) {
			for(int i=2; i<args.length; i++) {
				new SingleRepoLauncher(args[i], resultsFolder, tempFolder).run();
			}
		} else {
			 new DirectoryAnalysisLauncher(repoRoot, resultsFolder, tempFolder).run();
		}
	}
}
