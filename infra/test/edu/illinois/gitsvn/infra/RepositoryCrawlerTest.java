package edu.illinois.gitsvn.infra;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

import edu.illinois.gitsvn.infra.collectors.CSVCommitPrinter;

public class RepositoryCrawlerTest extends GitTestCase{
	
	private RepositoryCrawler crawler;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		crawler = new RepositoryCrawler();
	}
	
	@Test
	public void testProducesCorrectOutput() throws Exception {
		add("test.java", "Some java program", "first");
		add("test2.java", "Some other java program", "second");
		mv("test.java", "test_rename.java");
		add("readme","A non-java file", "forth");
		
		PipelineCommitFilter pipeline = ConfigurationUtil.configureAnalysis();
		crawler.crawlRepo(Git.open(testRepo), pipeline);
		CSVCommitPrinter agregator = (CSVCommitPrinter) pipeline.getAgregator();
		List<List<String>> rows = agregator.getCSVWriter().getRows();
		assertEquals(3,rows.size());
	}
	
	@Test
	public void testStartNotAtTheBegining() throws Exception {
		RevCommit commit = add("file1.java","Smth","first");
		add("file2.java","Smth else", "second");
		PipelineCommitFilter pipeline = ConfigurationUtil.configureAnalysis();
		crawler.crawlRepo(Git.open(testRepo), pipeline, Arrays.asList(new String[]{ commit.getName() }));
		CSVCommitPrinter agregator = (CSVCommitPrinter) pipeline.getAgregator();
		int noOfRows = agregator.getCSVWriter().getRows().size();
		assertEquals(1, noOfRows);
	}
}
