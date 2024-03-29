package tbs.server.performance;

import tbs.server.IDableEntity;
import tbs.server.TBSRequestException;
import tbs.server.Dump;
import tbs.server.act.Act;
import tbs.server.theatre.Theatre;
import tbs.server.theatre.Seating;
import tbs.server.ticket.TicketCatalogue;
import tbs.server.ticket.Ticket;

import java.util.List;

public class Performance extends IDableEntity {

	private final Act _act;
	private final Theatre _theatre;

	private final String _startTime;
	private int _pricePremium;
	private int _priceCheap;
	private int _salesReceipts = 0;

	private final TicketCatalogue _tickets = new TicketCatalogue();
	private final Seating _seating;

	public Performance(Act act, Theatre theatre, String startTimeStr) throws TBSRequestException{
		_act = act;
		_theatre = theatre;
		_seating = theatre.createSeating();
		_startTime = startTimeStr;

		validateStartTime(startTimeStr);

		setIDPrefix("Performance");
	}

	public void validateStartTime(String startTimeStr) throws TBSRequestException {
		// Check order of delimiters, group sizes, and digits
		if (!startTimeStr.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")) {
			throw new TimeWrongFormatException();
		}

		String[] groups = startTimeStr.split("[\\-T\\:]");

		try {

			int year = Integer.parseInt(groups[0]);
			int month = Integer.parseInt(groups[1]);
			int day = Integer.parseInt(groups[2]);
			int hour = Integer.parseInt(groups[3]);
			int minute = Integer.parseInt(groups[4]);

			if (month < 1 || month > 12) {
				throw new TimeWrongFormatException();
			}
			if (day < 1 || day > 31) {
				throw new TimeWrongFormatException();
			}
			// NOTE: hour of 24 is valid
			if (hour > 24) {
				throw new TimeWrongFormatException();
			}
			if (minute > 59) {
				throw new TimeWrongFormatException();
			}

		} catch (NumberFormatException e) {
			throw new TimeWrongFormatException();
		}
	}

	public void setPrices(String premiumPriceStr, String cheapSeatsStr) throws TBSRequestException {
		if (premiumPriceStr.charAt(0) != '$' || cheapSeatsStr.charAt(0) != '$') {
			throw new PriceWrongFormatException();
		}

		premiumPriceStr = premiumPriceStr.substring(1);
		cheapSeatsStr = cheapSeatsStr.substring(1);

		// Check that all characters are digits
		if (!premiumPriceStr.matches("^\\d+$") || !cheapSeatsStr.matches("^\\d+$")) {
			throw new PriceWrongFormatException();
		}

		try {

			_pricePremium = Integer.parseInt(premiumPriceStr);
			_priceCheap = Integer.parseInt(cheapSeatsStr);

		} catch (NumberFormatException e) {
			throw new PriceWrongFormatException();
		}
	}

	public String issueTicket(int rowNumber, int seatNumber) throws TBSRequestException{
		Ticket ticket = new Ticket(rowNumber, seatNumber);
		_seating.occupy(rowNumber, seatNumber);
		_tickets.add(ticket);

		if (_seating.isPremium(rowNumber, seatNumber)) {
			_salesReceipts += _pricePremium;
		} else {
			_salesReceipts += _priceCheap;
		}

		return ticket.getID();
	}

	public List<String> getTicketIDs() {
		return _tickets.toIDs();
	}

	public List<String> seatsAvailable() {
		return _seating.listAvailable();
	}

	public String generateSummary() {
		return String.format("%1$s\t%2$s\t%3$d\t$%4$d", getID(), _startTime, _tickets.count(), _salesReceipts);
	}

	@Override
	public void dump(Dump dump) {
		String summary = "Performance: ";
		summary += "<" + getID() + "> ";
		summary += "[starts " + _startTime + "] ";
		summary += "p:" + _pricePremium + " ";
		summary += "c:" + _priceCheap + " ";
		summary += "r:" + _salesReceipts;
		dump.add(summary);
		dump.groupStart();
		_tickets.dump(dump);
		_seating.dump(dump);
		dump.groupEnd();
	}

	private class PriceWrongFormatException extends TBSRequestException {
		public PriceWrongFormatException() {
			super("Price is in the wrong format");
		}
	}

	private class TimeWrongFormatException extends TBSRequestException {
		public TimeWrongFormatException() {
			super("Start time is in the wrong format");
		}
	}

}
