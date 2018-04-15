package tbs.server.theatre;

import tbs.server.Catalogue;
import tbs.server.TBSRequestException;
import tbs.server.IDDuplicateException;
import tbs.server.Dump;

import java.util.HashMap;
import java.util.Map;
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

			Map<String,Theatre> parsedTheatres = new HashMap<>();

			String line = reader.readLine();

			while (line != null) {
				Theatre parsed = theatreFromFileLine(line);

				if (parsedTheatres.containsKey(parsed.getID())) {
					throw new TBSRequestException("Theatre IDs not unique");
				}
				parsedTheatres.put(parsed.getID(), parsed);

				line = reader.readLine();
			}

			// Entire file is ok, so it is safe to add

			for (Theatre theatre : parsedTheatres.values()) {
				add(theatre);
			}

		} catch (FileNotFoundException e) {
			throw new TBSRequestException("Initialisation file does not exist");
		} catch (IOException e) {
			throw new TBSRequestException("Could not read initialisation file");
		}
	}

	private Theatre theatreFromFileLine(String line) throws TBSRequestException {

		String[] groups = line.split("\t");

		if (groups.length != 4) {
			throw new FileWrongFormatException("Not 4 columns");
		}
		if (!groups[0].equals("THEATRE")) {
			throw new FileWrongFormatException("Incorrect first column");
		}
		if (!groups[2].matches("^\\d+$")) {
			throw new FileWrongFormatException("Seat Dim. not an integer");
		}

		String theatreID = groups[1];
		int seatingDimension = 0;
		double floorArea = 0;

		try {

			seatingDimension = Integer.parseInt(groups[2]);
			floorArea = Double.parseDouble(groups[3]);

		} catch (NumberFormatException e) {
			throw new FileWrongFormatException("Seat Dim. or floor area not valid numbers");
		}

		if (seatingDimension < 1) {
			throw new TBSRequestException("Seating Dimension non-positive.");
		}

		if (floorArea < 0) {
			throw new TBSRequestException("Floor area negative");
		}

		if (hasID(theatreID)) {
			throw new TBSRequestException("A Theatre ID already exists.");
		}

		return new Theatre(theatreID, seatingDimension, floorArea);

	}

	@Override
	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Theatre Catalogue");
	}

	@Override
	protected String createNotFoundMessage(String id) {
		return "Theatre with theatre ID `" + id + "` does not exist";
	}

	@Override
	protected String createEmptyIDMessage() {
		return "Theatre ID is empty";
	}

	private class FileWrongFormatException extends TBSRequestException {

		public FileWrongFormatException(String message) {
			super("Initialisation file has incorrect format: " + message);
		}

	}

}
