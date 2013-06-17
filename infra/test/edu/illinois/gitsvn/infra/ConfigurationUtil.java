package edu.illinois.gitsvn.infra;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.collectors.CutofDetectorCollector;
import edu.illinois.gitsvn.infra.collectors.diff.AllLineNumberFilter;
import edu.illinois.gitsvn.infra.collectors.diff.JavaLineNumberFilter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.blacklister.FileOperationBlacklister;

public class ConfigurationUtil {

	public static PipelineCommitFilter configureAnalysis() {
		PipelineCommitFilter analysisFilter = new PipelineCommitFilter();
		
		analysisFilter.addFilter(FileOperationBlacklister.getDeleteDiffFilter());
		analysisFilter.addFilter(FileOperationBlacklister.getRenameDiffFilter());
		
		analysisFilter.addDataCollector(new AllLineNumberFilter());
		analysisFilter.addDataCollector(new JavaLineNumberFilter());
		analysisFilter.addDataCollector(new CutofDetectorCollector(0));
		
		AnalysisFilter agregator = new CSVCommitPrinter(analysisFilter);
		analysisFilter.setDataAgregator(agregator);
		return analysisFilter;
	}

}
