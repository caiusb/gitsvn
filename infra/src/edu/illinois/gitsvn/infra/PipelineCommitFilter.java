package edu.illinois.gitsvn.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.analysis.launchers.RecoverableLauncher;
import edu.illinois.gitsvn.infra.collectors.CollectorOperationException;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorFilter;
import edu.illinois.gitsvn.infra.csv.CSVCollector;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;

public class PipelineCommitFilter extends CommitFilter {

	private List<CommitFilter> filters = new ArrayList<CommitFilter>();
	private List<CommitFilter> collectors = new ArrayList<CommitFilter>();
	private AnalysisFilter dataAgregator;

	public void addFilter(CommitFilter filter) {
		filters.add(filter);
	}

	public void addDataCollector(CommitFilter collector) {
		if (!(collector instanceof DataCollector))
			throw new InvalidParameterException("The collector should be a DataCollector");
		
		collectors.add(collector);
	}

	public List<DataCollector> getAllCollectors() {
		List<DataCollector> returnedCollectors = new ArrayList<DataCollector>();
		for (CommitFilter collector : collectors) {
			returnedCollectors.add((DataCollector) collector);
		}

		return returnedCollectors;
	}

	public void setDataAgregator(AnalysisFilter agregator) {
		this.dataAgregator = agregator;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit)
			throws StopWalkException, MissingObjectException,
			IncorrectObjectTypeException, IOException {

		for (CommitFilter filter : filters) {
			boolean result = filter.include(walker, cmit);
			if (result == false)
				return false;
		}

		for (CommitFilter collector : collectors) {
			try {
				collector.include(walker, cmit); 
			} catch (CollectorOperationException e) {
				int cutoffTime = 0;
				for (CommitFilter c : collectors) {
					if (c instanceof CutofDetectorFilter) {
						cutoffTime = ((CutofDetectorFilter)c).getCutoff();
						break;
					}
				}
				String projectName = dataAgregator.getProjectName();
				String repoPath = repository.getWorkTree().getAbsolutePath();
				List<String> queue = walker.getQueue();
				String restoreString = getRestoreString(repoPath, projectName, queue, cutoffTime);
				saveRestorePoint(restoreString);
				throw e;
			}
		}

		dataAgregator.include(walker, cmit);

		return true;
	}
	
	protected String getRestoreString(String repoPath, String projectName, List<String> queue, int cutoffTime) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(repoPath);
		buffer.append(RecoverableLauncher.RECOVERY_LINE_SEPARATOR);
		buffer.append(projectName);
		buffer.append(RecoverableLauncher.RECOVERY_LINE_SEPARATOR);
		boolean first = true;
		for (String commit : queue) {
			if (!first) {
				buffer.append(RecoverableLauncher.COMMIT_SEPARATOR);
				first = false;
			}
			buffer.append(commit);
		}
		buffer.append(RecoverableLauncher.RECOVERY_LINE_SEPARATOR);
		buffer.append(cutoffTime);
		
		return buffer.toString();
	}

	private void saveRestorePoint(String recoverString) {
		try {
			Path restoreFile = Files.createFile(Paths.get(CSVCollector.PATH_TO_CSV_FILES + "restore_point.txt"));
			Files.write(restoreFile, recoverString.getBytes());
		} catch (IOException e) {
		}
	}

	public void setRepository(Git repo) {
		Repository repository = repo.getRepository();
		for (CommitFilter filter : filters) {
			filter.setRepository(repository);
		}
		
		for (CommitFilter collector: collectors) {
			collector.setRepository(repository);
		}
		
		dataAgregator.setRepository(repository);
	}

	public CommitFilter getAgregator() {
		return dataAgregator;
	}
}
