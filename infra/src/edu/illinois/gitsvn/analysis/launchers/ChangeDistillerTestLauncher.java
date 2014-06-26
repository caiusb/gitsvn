package edu.illinois.gitsvn.analysis.launchers;

import edu.illinois.gitsvn.infra.SingleRepoLauncher;

public class ChangeDistillerTestLauncher {

	public static void main(String[] args) {
		try {
			new SingleRepoLauncher("/Users/caius/osu/COPE/clientRecorder").run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
