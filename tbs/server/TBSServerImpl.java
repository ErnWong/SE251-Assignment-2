package tbs.server;

import java.util.List;
import java.util.Vector;

public class TBSServerImpl implements TBSServer {

  TBSList<Theatres> _theatres = new TBSList<>();
  TBSNamedList<Artist> _artists = new TBSNamedList<>();
  TBSList<Act> _acts = new TBSList<>();
  TBSList<Performance> _performances = new TBSList<>();

  public String initialise(String path) {
    _theatres.initialise(path);
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
    return _artists.fromID(artistID).getActs().toIDs();
  }

  public List<String> getPeformanceIDsForAct(String actID) {
    return _acts.fromID(actID).getPerformances().toIDs();
  }

  public List<String> getTicketIDsForPerformance(String performanceID) {
    return _performances.fromID(performanceID).getTickets().toIDs();
  }

  public String addArtist(String name) {
    Artist artist = new Artist(name);
    return _artists.add(artist);
  }
  //public String addArtist(String name) {
  //  return _artists.add(name);
  //}

  public String addAct(String title, String artistID, int minutesDuration) {
    Artist artist = _artists.fromID(artistID);
    Act act = new Act(title, artist, minutesDuration);
    return _acts.add(act);
  }
  //public String addAct(String title, String artistID, int minutesDuration) {
  //  Artist artist = _artists.fromID(artistID);
  //  return _acts.add(title, artist, minutesDuration);
  //}

  public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
    Act act = _acts.fromID(actID);
    Theatre theatre = _theatres.fromID(theatreID);
    Performance performance = new Performance(act, theatre, startTimeStr, premiumPriceStr, cheapSeatsStr);
    return _performances.add(performance);
  }
  //public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {
  //  Act act = _acts.fromID(actID);
  //  Theatre theatre = _theatres.fromID(theatreID);
  //  return _performances.add(act, theatre, startTimeStr, premiumPriceStr, cheapSeatsStr);
  //}

  public String issueTicket(String performanceID, int rowNumber, int seatNumber) {
    return _performances.fromID(performanceID).issueTicket(rowNumber, seatNumber);
  }

  public List<String> seatsAvailable(String performanceID) {
    return _performances.fromID(performanceID).seatsAvailable();
  }

  public List<String> salesReport(String actID) {
    return _acts.fromID(actID).salesReport();
  }

  public List<String> dump() {
  }

}
