
package edu.illinois.gitsvn.analysis.launchers;

import java.util.List;

import edu.illinois.gitsvn.analysis.CutoffGenericAnalysis;
import edu.illinois.gitsvn.analysis.GitSvnCutoffAnalysis;
import edu.illinois.gitsvn.infra.AnalysisConfiguration;
import edu.illinois.gitsvn.infra.AnalysisLauncher;

public class SvnToGitRepoAnalysisLauncher extends AnalysisLauncher {
	
	private static String pathToRepos = "../../svnToGitRepos/";

	@Override
	protected void populateWithConfigurations(List<AnalysisConfiguration> configurations) {
//		configurations.add(new CutoffGenericAnalysis("../../projects/junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "junit", "JUnit", 1231818542));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "Arduino", "Arduino", 1284741802));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "cyclopsgroup", "CyclopsGroup", 1295380000));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.jdt.core", "JDTCore", 1316790694));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.jdt.debug", "JDTDebug", 1316538989));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.jdt.ui", "JDTUI", 1316608366));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.platform", "EclipsePlatform", 1317272400));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.platform.common", "EclipsePlatformCommon", 1318741200));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.platform.debug", "EclipsePlatformDebug", 1316408400));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.platform.team", "EclipsePlatformTeam", 1316581200));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "eclipse.platform.text", "EclipsePlatformText", 1316149200));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "FFmpeg", "FFmpeg", 1295258573));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "libreoffice", "LibreOffice", 1282256340));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "MPS", "MPS", 1277726186));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "prototype", "prototype", 1205935492));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "thymeleaf", "Thymeleaf", 1337530081));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "upm-swing/", "UPM", 1287344636));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "astrid/", "astrid", 1273138392));
		

		configurations.add(new CutoffGenericAnalysis(pathToRepos + "clojure/", "clojure", 1229436656));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "spring-framework/", "spring-framework", 1266882776));
		configurations.add(new CutoffGenericAnalysis(pathToRepos + "yuicompressor/", "yuicompressor", 1232056073));


		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "cassandra", "cassandra"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "Essentials", "Essentials"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "k-9", "k-9"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "liferay-plugins", "liferay-plugins"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "liferay-portal", "liferay-portal"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "twitter4j", "twitter4j"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "gradle", "gradle"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "hibernate-orm", "hibernate-orm"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "jenkins", "jenkins"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "grails-core", "grails-core"));
		configurations.add(new GitSvnCutoffAnalysis(pathToRepos + "groovy-core", "groovy-core"));
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: <cmd> <path to repos> <result-folder> <temp-folder>");
			return;
		}
		pathToRepos = args[0];
		String resultsFolder = args[1];
		String tempFolder = args[2];
		
		new SvnToGitRepoAnalysisLauncher().run();
	}
}
