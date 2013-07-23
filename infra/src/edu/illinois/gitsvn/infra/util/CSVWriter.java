package edu.illinois.gitsvn.infra.util;

import java.util.List;

public interface CSVWriter {

	public abstract void addHeader(List<String> headers);

	public abstract List<String> getHeader();

	public abstract void addRow(List<String> row);

	public abstract List<List<String>> getRows();

}