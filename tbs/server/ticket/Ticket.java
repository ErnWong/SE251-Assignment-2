package tbs.server.ticket;

import tbs.server.IDableEntity;
import tbs.server.Dump;

public class Ticket extends IDableEntity {

	private int _rowNumber;
	private int _seatNumber;

	public Ticket(int rowNumber, int seatNumber) {
		_rowNumber = rowNumber;
		_seatNumber = seatNumber;

		setIDPrefix("Ticket");
	}

	@Override
	public void dump(Dump dump) {
		dump.add("Ticket [r:" + _rowNumber +"|s:" + _seatNumber + "] <" + getID() + ">");
	}

}
