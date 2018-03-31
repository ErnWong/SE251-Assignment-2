package tbs.server;

import java.util.Date;
import java.util.List;

public class Performance extends IDableEntity {

  private Act _act;
  private Theatre _theatre;

  private String _startTime;
  private int _pricePremium;
  private int _priceCheap;
  private int _salesReceipts = 0;

  private TicketCatalogue _tickets = new TicketCatalogue();
  private Seating _seating;

  public Performance(Act act, Theatre theatre) {
    _act = act;
    _theatre = theatre;
    _seating = theatre.createSeating();
  }

  public void setStartTime(String startTimeStr) throws TBSRequestException {
    if (!startTimeStr.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")) {
      throw new TBSRequestException("ANGERY");
    }

    String[] groups = startTimeStr.split("[\\-T\\:]");

    try {

      int year = Integer.parseInt(groups[0]);
      int month = Integer.parseInt(groups[1]);
      int day = Integer.parseInt(groups[2]);
      int hour = Integer.parseInt(groups[3]);
      int minute = Integer.parseInt(groups[4]);
      if(month < 1 || month > 12) {
        throw new TBSRequestException("ANGERY");
      }
      if(day < 1 || day > 31) {
        throw new TBSRequestException("ANGERY");
      }
      if(hour > 59) {
        throw new TBSRequestException("ANGERY");
      }
      if(minute > 59) {
        throw new TBSRequestException("ANGERY");
      }

    } catch (NumberFormatException e) {
      throw new TBSRequestException("ANGERY");
    }

    _startTime = startTimeStr;
  }

  public void setPrices(String premiumPriceStr, String cheapSeatsStr) throws TBSRequestException {
    if (premiumPriceStr.charAt(0) != '$' || cheapSeatsStr.charAt(0) != '$') {
      throw new PriceWrongFormatException();
    }

    premiumPriceStr = premiumPriceStr.substring(1);
    cheapSeatsStr = cheapSeatsStr.substring(1);

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
    _seating.occupy(rowNumber, seatNumber, ticket);
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
      super("Price has the wrong format");
    }

  }

}
