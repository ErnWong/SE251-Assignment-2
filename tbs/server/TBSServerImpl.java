package tbs.server;

import java.util.List;
import java.util.ArrayList;

public class TBSServerImpl implements TBSServer {

  TheatreCatalogue _theatres = new TheatreCatalogue();
  ArtistCatalogue _artists = new ArtistCatalogue();
  ActCatalogue _acts = new ActCatalogue();
  PerformanceCatalogue _performances = new PerformanceCatalogue();

  public String initialise(String path) {
    try {

      _theatres.addFromFile(path);

    } catch (TBSRequestException e) {
      return e.toResponse();
    }
    return "";
  }

  public List<String> getTheatreIDs() {
    return _theatres.toIDs();
  }

  public List<String> getArtistIDs() {
    return _artists.toIDs();
  }

  public List<String> getArtistNames() {
    return _artists.toNames();
  }

  public List<String> getActIDsForArtist(String artistID) {
    try {

      Artist artist = _artists.fromID(artistID);
      return artist.getActIDs();

    } catch (TBSRequestException e) {
      return e.toResponseList();
    }
  }

  public List<String> getPeformanceIDsForAct(String actID) {
    try {

      Act act = _acts.fromID(actID);
      return act.getPerformanceIDs();

    } catch (TBSRequestException e) {
      return e.toResponseList();
    }
  }

  public List<String> getTicketIDsForPerformance(String performanceID) {
    try {

      Performance performance = _performances.fromID(performanceID);
      return performance.getTicketIDs();

    } catch (TBSRequestException e) {
      return e.toResponseList();
    }
  }

  public String addArtist(String name) {
    try {

      Artist artist = new Artist(name);
      _artists.add(artist);
      return artist.getID();

    } catch (TBSRequestException e) {
      return e.toResponse();
    }
  }

  public String addAct(String title, String artistID, int minutesDuration) {
    try {

      Artist artist = _artists.fromID(artistID);
      Act act = artist.createAct(title, minutesDuration);
      _acts.add(act);
      return act.getID();

    } catch (TBSRequestException e) {
      return e.toResponse();
    }
  }

  public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
    try {

      Act act = _acts.fromID(actID);
      Theatre theatre = _theatres.fromID(theatreID);

      Performance performance = act.performAt(theatre);
      performance.setStartTime(startTimeStr);
      performance.setPrices(premiumPriceStr, cheapSeatsStr);

      _performances.add(performance);

      return performance.getID();

    } catch (TBSRequestException e) {
      return e.toResponse();
    }
  }

  public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
    try {

      Performance performance = _performances.fromID(performanceID);
      return performance.issueTicket(rowNumber, seatNumber);

    } catch (TBSRequestException e) {
      return e.toResponse();
    }
  }

  public List<String> seatsAvailable(String performanceID) {
    try {

      Performance performance = _performances.fromID(performanceID);
      return performance.seatsAvailable();

    } catch (TBSRequestException e) {
      return e.toResponseList();
    }
  }

  public List<String> salesReport(String actID) {
    try {

      Act act = _acts.fromID(actID);
      return act.salesReport();

    } catch (TBSRequestException e) {
      return e.toResponseList();
    }
  }

  public List<String> dump() {
    Dump dump = new Dump();
    _theatres.dump(dump);
    _artists.dump(dump);
    _acts.dump(dump);
    _performances.dump(dump);
    List<String> content = dump.render();
    for (String line : content) {
      System.out.println(line);
    }
    return content;
  }

}
