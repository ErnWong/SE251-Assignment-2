package tbs.server.theatre;

import tbs.server.TBSRequestException;
import tbs.server.Dumpable;
import tbs.server.Dump;

import java.util.List;
import java.util.ArrayList;

public class Seating implements Dumpable {

	int _seatingDimension = 0;
	boolean[][] _isTaken;

	public Seating(int seatingDimension) {
		_seatingDimension = seatingDimension;
		_isTaken = new boolean[seatingDimension + 1][seatingDimension + 1];
	}

	public List<String> listAvailable() {
		ArrayList<String> available = new ArrayList<>();
		for (int rowNumber = 1; rowNumber <= _seatingDimension; rowNumber++) {
			for (int seatNumber = 1; seatNumber <= _seatingDimension; seatNumber++) {
				if (!_isTaken[rowNumber][seatNumber]) {
					available.add(rowNumber + "\t" + seatNumber);
				}
			}
		}
		return available;
	}

	public boolean isPremium(int rowNumber, int seatNumber) {
		return rowNumber <= _seatingDimension / 2;
	}

	public void occupy(int rowNumber, int seatNumber) throws TBSRequestException {
		if (rowNumber < 1 || rowNumber > _seatingDimension) {
			throw new TBSRequestException("Row number is invalid");
		}
		if (seatNumber < 1 || seatNumber > _seatingDimension) {
			throw new TBSRequestException("Seat number is invalid");
		}
		if (_isTaken[rowNumber][seatNumber]) {
			throw new TBSRequestException("Seat is already taken");
		}
		_isTaken[rowNumber][seatNumber] = true;
	}

	@Override
	public void dump(Dump dump) {
		dump.add("Seating");
		dump.groupStart();
		for (int rowNumber = 1; rowNumber <= _seatingDimension; rowNumber++) {
			String rowLine = "";
			for (int seatNumber = 1; seatNumber <= _seatingDimension; seatNumber++) {
				rowLine += _isTaken[rowNumber][seatNumber] ? "T " : "- ";
			}
			dump.add(rowLine);
		}
		dump.groupEnd();
	}

}
