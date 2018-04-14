package tbs.server.theatre;

import tbs.server.Catalogue;
import tbs.server.TBSRequestException;
import tbs.server.Dump;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TheatreCatalogue extends Catalogue<Theatre> {

	public void addFromFile(String pathStr) throws TBSRequestException {

		if (pathStr == null || pathStr.isEmpty()) {
			throw new TBSRequestException("File path is empty");
		}

		Path path = FileSystems.getDefault().getPath(pathStr);

		try (BufferedReader reader = Files.newBufferedReader(path)) {

			String line = reader.readLine();

			while (line != null) {
				addFromFileLine(line);
				line = reader.readLine();
			}

		} catch (FileNotFoundException e) {
			throw new TBSRequestException("Initialisation file does not exist");
		} catch (IOException e) {
			throw new TBSRequestException("Could not read initialisation file");
		}
	}

	private void addFromFileLine(String line) throws TBSRequestException {

		String[] groups = line.split("\t");

		if (groups.length != 4) {
			throw new FileWrongFormatException();
		}
		if (!groups[0].equals("THEATRE")) {
			throw new FileWrongFormatException();
		}
		if (!groups[2].matches("^\\d+$")) {
			throw new FileWrongFormatException();
		}

		String theatreID = groups[1];
		int seatingDimension = 0;
		double floorArea = 0;

		try {

			seatingDimension = Integer.parseInt(groups[2]);
			floorArea = Double.parseDouble(groups[3]);

		} catch (NumberFormatException e) {
			throw new FileWrongFormatException();
		}

		add(new Theatre(theatreID, seatingDimension, floorArea));

	}
	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Theatre Catalogue");
	}

	protected String createNotFoundMessage(String id) {
		return "Theatre with theatre ID `" + id + "` does not exist";
	}

	protected String createEmptyIDMessage() {
		return "Theatre ID is empty";
	}

	private class FileWrongFormatException extends TBSRequestException {

		public FileWrongFormatException() {
			super("Initialisation file has incorrect format");
		}

	}

}
