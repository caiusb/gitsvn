package edu.illinois.gitsvn.infra;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.gitective.core.CommitFinder;

import edu.illinois.gitsvn.infra.filters.AnalysisFilter;

public class RepositoryCrawler {

	public void crawlRepo(String remoteRepoLoc,
			PipelineCommitFilter pipelineFilter) throws GitAPIException,
			InvalidRemoteException, TransportException {
		Git repo = cloneRepo(remoteRepoLoc);
		crawlRepo(repo, pipelineFilter);
	}

	public void crawlRepo(Git repo, PipelineCommitFilter pipelineFilter) {
		CommitFinder finder = createFinderAndSetRepo(repo, pipelineFilter);
		AnalysisFilter agregator = (AnalysisFilter) pipelineFilter.getAgregator();
		agregator.begin();
		finder.findInBranches();
		agregator.end();
	}

	private CommitFinder createFinderAndSetRepo(Git repo, PipelineCommitFilter pipelineFilter) {
		CommitFinder finder = new CommitFinder(repo.getRepository());
		finder.setFilter(pipelineFilter);
		pipelineFilter.setRepository(repo);
		return finder;
	}
	
	public void crawlRepo(Git repo, PipelineCommitFilter pipelineFilter, List<String> startCommitIDs) {
		CommitFinder finder = createFinderAndSetRepo(repo, pipelineFilter);
		AnalysisFilter agregator = (AnalysisFilter) pipelineFilter.getAgregator();
		agregator.begin();
		for (String commitID : startCommitIDs) {
			finder.findFrom(commitID);
		}
		agregator.end();
	}

	private Git cloneRepo(String remoteRepoLoc) throws GitAPIException,
			InvalidRemoteException, TransportException {
		String cloneDirName = "repos/clone" + System.nanoTime();
		File cloneDir = new File(cloneDirName);
		cloneDir.deleteOnExit();

		Git repo = Git.cloneRepository().setURI(remoteRepoLoc)
				.setDirectory(cloneDir).call();
		return repo;
	}
}
