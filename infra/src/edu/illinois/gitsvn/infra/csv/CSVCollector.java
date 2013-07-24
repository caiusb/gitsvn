package edu.illinois.gitsvn.infra.csv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class CSVCollector implements CSVWriter {

	protected List<List<String>> rows;
	protected List<String> headers;

	public CSVCollector() {
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

}