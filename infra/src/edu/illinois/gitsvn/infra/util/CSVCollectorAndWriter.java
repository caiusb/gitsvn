package edu.illinois.gitsvn.infra.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Builds a CSV row by row.
 * 
 * Does not ensure equal number of columns. 
 * 
 * @author mihai
 *
 */
public class CSVCollectorAndWriter extends CSVCollector implements CSVWriter {
	
	public CSVCollectorAndWriter() {
		rows = new ArrayList<>();
		headers = new LinkedList<>();
	}

	/**
	 * Dump the contents to the provided path. If a file already at the existing path is deleted.
	 * 
	 * The object states remains unaffected.
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void dumpToFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);

		Files.deleteIfExists(path);
		Files.createFile(path);

		String content = constructCSV();

		Files.write(path, content.toString().getBytes());
	}
	
	private String getCSVString(){
		return constructCSV();
	}

	private String constructCSV() {
		StringBuffer content = new StringBuffer();

		String line = getLine(headers);
		appendNewLineToBuffer(content, line);
		

		for (List<String> row : rows) {
			line = getLine(row);
			
			appendNewLineToBuffer(content, line);
		}
		
		return content.substring(0, content.length() -1).toString();
	}

	private void appendNewLineToBuffer(StringBuffer buffer, String line) {
		buffer.append(line);
		buffer.append("\n");
	}

	private String getLine(List<String> line) {
		StringBuffer sb = new StringBuffer();

		for (String element : line) {
			sb.append("\"");
			sb.append(element);
			sb.append("\"");
			sb.append(",");
		}

		return sb.substring(0, sb.length() - 1);
	}
}
