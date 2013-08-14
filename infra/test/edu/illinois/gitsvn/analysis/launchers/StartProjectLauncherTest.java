package edu.illinois.gitsvn.analysis.launchers;

import java.util.ArrayList;

import org.gitective.tests.GitTestCase;
import org.junit.Test;

import edu.illinois.gitsvn.infra.AnalysisConfiguration;

public class StartProjectLauncherTest extends GitTestCase {

	@Test
	public void testUnwrap() throws Exception {
		System.setProperty(RecoverableLauncher.INIT_LINE,"../../somepath,test_project,123");
		
		StartProjectLauncher launcher = new StartProjectLauncher();
		ArrayList<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		launcher.populateWithConfigurations(configurations);
		assertEquals(1, configurations.size());
	}
}
