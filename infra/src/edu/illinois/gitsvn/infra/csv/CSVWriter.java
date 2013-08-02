package edu.illinois.gitsvn.infra.csv;

import java.io.IOException;
import java.util.List;

public interface CSVWriter {

	public abstract void addHeader(List<String> headers);

	public abstract List<String> getHeader();

	public abstract void addRow(List<String> row);

	public abstract List<List<String>> getRows();
	
	public abstract void close() throws IOException;

}