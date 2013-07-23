package edu.illinois.gitsvn.infra.util;

import java.util.List;

public class CSVCollector {

	protected List<List<String>> rows;
	protected List<String> headers;

	public CSVCollector() {
		super();
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