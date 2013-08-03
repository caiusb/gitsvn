package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Class responsible for running the analyses
 * Subclasses should implement {@link populateWithConfigurations} in order to provide analysis configurations
 * @author mihai
 *
 */
public abstract class AnalysisLauncher {
	
	public static String INIT_LINE = "edu.illinois.gitsvn.initline";
	public static String RECOVERY_LINE = "edu.illinois.gitsvn.recoveryline";

	/**
	 * Starts the analysis. 
	 * @throws Exception
	 */
	public void run() throws Exception {
		List<AnalysisConfiguration> configurations = new ArrayList<AnalysisConfiguration>();
		
		populateWithConfigurations(configurations);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDescription = workspace.getDescription();
		workspaceDescription.setAutoBuilding(false);
		workspace.setDescription(workspaceDescription);
		
		long before = System.nanoTime();
		runSerial(configurations);
		long after = System.nanoTime();
		
		System.out.println((after - before) / 1000000);
	}

	protected abstract void populateWithConfigurations(List<AnalysisConfiguration> configurations);

	private void runSerial(List<AnalysisConfiguration> configurations) {
		for (int i = 0; i < configurations.size(); i++) {
			System.out.println("\n" + (i + 1) + " / " + configurations.size());
			AnalysisConfiguration configuration = configurations.remove(i);
			configuration.run();
		}
	}
	
	private void runParallel(List<AnalysisConfiguration> configurations) {
		ForkJoinPool pool = new ForkJoinPool();
		List<Callable<Void>> list = new ArrayList<>();
		
		for (final AnalysisConfiguration analysisConfiguration : configurations) {
			list.add(new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					analysisConfiguration.run();
					return null;
				}
			});
		}
		
		List<Future<Void>> tasks = pool.invokeAll(list);
		for (Future<Void> task : tasks) {
			try {
				task.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
	}
}
