package edu.illinois.gitsvn.infra.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ImmediateCSVWriter extends CSVCollector {

	private Path csvFilePath;
	private final boolean deleteIfExists;

	/**
	 * @deprecated Use {@link #ImmediateCSVWriter(String,boolean)} instead
	 */
	public ImmediateCSVWriter(String csvFile) {
		this(csvFile, true);
	}

	public ImmediateCSVWriter(String csvFile, boolean deleteIfExists) {
		this.deleteIfExists = deleteIfExists;
		csvFilePath = Paths.get(csvFile);
		if (deleteIfExists) {
			try {
				Files.deleteIfExists(csvFilePath);
				Files.createFile(csvFilePath);
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void addHeader(List<String> headers) {
		super.addHeader(headers);
		if (deleteIfExists) {
			byte[] bytes = getBytesForList(headers);
			try {
				Files.write(csvFilePath, bytes);
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void addRow(List<String> row) {
		super.addRow(row);
		byte[] bytes = getBytesForList(row);
		try {
			Files.write(csvFilePath, bytes, StandardOpenOption.APPEND);
		} catch (IOException e) {
		}
	}

	private byte[] getBytesForList(List<String> listOfThings) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String header : listOfThings) {
			stringBuffer.append("\"");
			stringBuffer.append(header);
			stringBuffer.append("\"");
			stringBuffer.append(",");
		}
		stringBuffer.deleteCharAt(stringBuffer.lastIndexOf(","));
		stringBuffer.append("\n");

		byte[] bytes = stringBuffer.toString().getBytes();
		return bytes;
	}

	@Override
	public void close() throws IOException {
		// nothing
	}

}
