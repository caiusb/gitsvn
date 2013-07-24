package edu.illinois.gitsvn.infra.csv;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImmediateCSVWriterTest {
	
	private ImmediateCSVWriter writer;
	private String fileName = "tempfile";

	@Before
	public void setUp() {
		writer = new ImmediateCSVWriter(fileName);
	}
	
	@After
	public void tearDown() throws Exception {
		Files.delete(getTestFilePath());
	}

	private Path getTestFilePath() {
		return Paths.get(fileName);
	}
	
	private String getTestFileContents() throws IOException {
		return new String(Files.readAllBytes(getTestFilePath()));
	}
	
	@Test
	public void testOnlyHeader() throws Exception {
		addHeader();
		assertEquals("\"First column\",\"Second column\"\n", getTestFileContents());
	}

	private void addHeader() {
		writer.addHeader(Arrays.asList(new String[]{"First column", "Second column"}));
	}
	
	@Test
	public void testWithOneRow() throws Exception {
		addHeader();
		addRow();
		assertEquals("\"First column\",\"Second column\"\n\"1\",\"2\"\n", getTestFileContents());
	}

	private void addRow() {
		writer.addRow(Arrays.asList(new String[]{"1","2"}));
	}
	
	@Test
	public void testWithTwoRows() throws Exception {
		addHeader();
		addRow();
		addRow();
		assertEquals("\"First column\",\"Second column\"\n" + 
				"\"1\",\"2\"\n" + 
				"\"1\",\"2\"\n", getTestFileContents());
	}
}
