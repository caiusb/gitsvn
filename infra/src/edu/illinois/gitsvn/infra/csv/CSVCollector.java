package edu.illinois.gitsvn.infra.csv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class CSVCollector implements CSVWriter {
	
	public static final String PATH_TO_CSV_FILES = "../../results/";
	public static final String CSV_EXTENSION = ".csv";

	protected String filePath;
	protected List<List<String>> rows;
	protected List<String> headers;

	public CSVCollector(String fileName) {
		this.filePath = getPathToCSV(fileName);
		rows = new ArrayList<>();
		headers = new LinkedList<>();
	}

	public void addHeader(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getHeader() {
		return headers;
	}

	public void addRow(List<String> row) {
		rows.add(row);
	}

	public List<List<String>> getRows() {
		return rows;
	}
	
	protected String getPathToCSV(String projectName) {
		return PATH_TO_CSV_FILES + projectName + CSV_EXTENSION;
	}

}