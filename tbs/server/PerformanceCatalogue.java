package tbs.server;

public class PerformanceCatalogue extends Catalogue<Performance> {

  public void dump(Dump dump) {
    super.dump(dump);
    dump.rewritePrevious("Performance Catalogue");
  }

  protected String createNotFoundMessage(String id) {
    return "Performance with performance ID `" + id + "` does not exist";
  }

  protected String createEmptyIDMessage() {
    return "Peformance ID is empty";
  }

}
