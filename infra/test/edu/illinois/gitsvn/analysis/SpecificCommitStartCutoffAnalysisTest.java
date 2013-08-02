package edu.illinois.gitsvn.analysis;

import java.util.Arrays;

import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;
import edu.illinois.gitsvn.infra.csv.CSVWriter;

public class SpecificCommitStartCutoffAnalysisTest extends GitTestCase {

	@Before
	public void before() {
	}

	@Test
	public void testStartFrom2Branches() throws Exception {
		add("first.txt", "1");
		branch("other");
		add("second.txt", "1");
		add("second.txt", "2");
		RevCommit two = add("second.txt", "3");
		checkout("master");
		RevCommit one = add("first.txt", "2");

		SpecificCommitStartCutoffAnalysis analysis = new SpecificCommitStartCutoffAnalysis(testRepo.getAbsolutePath(),
				"Test", Arrays.asList(new String[] { one.name(), two.name(), }), 0);

		analysis.run();

		CSVCommitPrinter aggregator = (CSVCommitPrinter) analysis.getAggregator();
		CSVWriter writer = aggregator.getCSVWriter();
		assertEquals(3, writer.getRows()
				.size());
	}

}
