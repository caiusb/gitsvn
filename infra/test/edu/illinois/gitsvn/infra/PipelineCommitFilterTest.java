package edu.illinois.gitsvn.infra;

import java.util.ArrayList;
import java.util.Arrays;

import org.gitective.tests.GitTestCase;
import org.junit.Before;
import org.junit.Test;

public class PipelineCommitFilterTest extends GitTestCase {

	private PipelineCommitFilter filter;

	@Before
	public void before() {
		filter = new PipelineCommitFilter();
	}
	
	@Test
	public void generateStringForNoCommits() {
		String string = filter.getRestoreString("/test", "TestProject", new ArrayList<String>(), 123);
		assertEquals("/test;TestProject;;123",string);
	}
	
	@Test
	public void generateStringForOneCommit() {
		String string = filter.getRestoreString("/test", "TestProject", Arrays.asList(new String[]{ "abc" }), 123);
		assertEquals("/test;TestProject;abc;123", string);
	}

}
