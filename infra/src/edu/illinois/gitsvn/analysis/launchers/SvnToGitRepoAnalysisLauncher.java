package edu.illinois.gitsvn.analysis.launchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

@RunWith(Parameterized.class)
public class SvnToGitRepoAnalysisLauncher extends AnalysisLauncher {

	private AnalysisLauncher launcher;

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
//		configurations.add(new CutoffGenericAnalysis("../../projects/junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/Arduino", "Arduino", 1284741802));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/cyclopsgroup", "CyclopsGroup", 1295380000));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.core", "JDTCore", 1316790694));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.debug", "JDTDebug", 1316538989));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.jdt.ui", "JDTUI", 1316608366));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform", "EclipsePlatform", 1317272400));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.common", "EclipsePlatformCommon", 1318741200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.debug", "EclipsePlatformDebug", 1316408400));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.team", "EclipsePlatformTeam", 1316581200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/eclipse.platform.text", "EclipsePlatformText", 1316149200));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/FFmpeg", "FFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/libreoffice", "LibreOffice", 1282256340));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/MPS", "MPS", 1277726186));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/prototype", "prototype", 1205935492));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/thymeleaf", "Thymeleaf", 1337530081));
		configurations.add(new CutoffGenericAnalysis("../../svnToGitRepos/upm-swing/", "UPM", 1287344636));

	}

//	public static void main(String[] args) throws Exception {
//		new SvnToGitRepoAnalysisLauncher().run();
//	}
//	
//	@Test
//	public void test() throws Exception {
//		new SvnToGitRepoAnalysisLauncher().run();
//	}
	
	public SvnToGitRepoAnalysisLauncher(final String path, final String name, final int cutoffTime) {
		launcher = new AnalysisLauncher() {
			
			@Override
			protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
				configurations.add(new CutoffGenericAnalysis(path, name, cutoffTime));
			}
		};
	}
	
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] {
				{"../../svnToGitRepos/junit", "JUnit", 1231818542},
				{"../../svnToGitRepos/Arduino", "Arduino", 1284741802},
				{"../../svnToGitRepos/cyclopsgroup", "CyclopsGroup", 1295380000},
				{"../../svnToGitRepos/eclipse.jdt.core", "JDTCore", 1316790694},
				{"../../svnToGitRepos/eclipse.jdt.debug", "JDTDebug", 1316538989},
				{"../../svnToGitRepos/eclipse.jdt.ui", "JDTUI", 1316608366},
				{"../../svnToGitRepos/eclipse.platform", "EclipsePlatform", 1317272400},
				{"../../svnToGitRepos/eclipse.platform.common", "EclipsePlatformCommon", 1318741200},
				{"../../svnToGitRepos/eclipse.platform.debug", "EclipsePlatformDebug", 1316408400},
				{"../../svnToGitRepos/eclipse.platform.team", "EclipsePlatformTeam", 1316581200},
				{"../../svnToGitRepos/eclipse.platform.text", "EclipsePlatformText", 1316149200},
				{"../../svnToGitRepos/FFmpeg", "FFmpeg", 1295258573},
				{"../../svnToGitRepos/libreoffice", "LibreOffice", 1282256340},
				{"../../svnToGitRepos/MPS", "MPS", 1277726186},
				{"../../svnToGitRepos/prototype", "prototype", 1205935492},
				{"../../svnToGitRepos/thymeleaf", "Thymeleaf", 1337530081},
				{"../../svnToGitRepos/upm-swing/", "UPM", 1287344636}
		};
		
		return Arrays.asList(data);
	}
	
	@Test
	public void test() throws Exception {
		launcher.run();
	}
}
