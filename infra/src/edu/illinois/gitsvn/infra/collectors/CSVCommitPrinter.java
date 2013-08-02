package edu.illinois.gitsvn.infra.collectors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gitective.core.filter.commit.CommitFilter;

import edu.illinois.gitsvn.infra.DataCollector;
import edu.illinois.gitsvn.infra.PipelineCommitFilter;
import edu.illinois.gitsvn.infra.csv.CSVCollector;
import edu.illinois.gitsvn.infra.csv.CSVWriter;
import edu.illinois.gitsvn.infra.csv.ImmediateCSVWriter;
import edu.illinois.gitsvn.infra.filters.AnalysisFilter;
import edu.illinois.gitsvn.infra.filters.MetadataService;

//TODO refactor this class to be a filter composite
public class CSVCommitPrinter extends AnalysisFilter {
	
	public static final String PROJ_NAME_PROP = "ProjectName";

	private CSVCollector csv;
	private List<DataCollector> allCollectors;
	private final String projectName;
	
	public CSVCommitPrinter(PipelineCommitFilter filter) {
		super(filter);
	}
	
	@Override
	public void begin() {
		csv = new ImmediateCSVWriter(projectName,false);
		List<String> headerData = new ArrayList<>();
		
		allCollectors = filter.getAllCollectors();
		for (DataCollector collector : allCollectors)
			headerData.add(collector.name());
		
		csv.addHeader(headerData);
	}

	@Override
	public void end() {
		String projectName = MetadataService.getService().getInfo(PROJ_NAME_PROP);
		try {
			csv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CSVWriter getCSVWriter() {
		return csv;
	}

	@Override
	public boolean include(RevWalk walker, RevCommit cmit) throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
		List<String> data = new ArrayList<String>();
		
		for (DataCollector collector : allCollectors)
			data.add(collector.getDataForCommit());
		
		csv.addRow(data);
		
		return true;
	}

	@Override
	public CommitFilter setRepository(Repository repository) {
		CommitFilter ret = super.setRepository(repository);

		return ret;
	}
}
