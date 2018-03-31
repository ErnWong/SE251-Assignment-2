package tbs.server;

import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class Artist extends IDableEntity {

  private String _name;
  private TreeSet<String> _actIDs = new TreeSet<>();

  public Artist(String name) {
    _name = name;
  }

  public Act createAct(String title, int minutesDuration) {
    Act act = new Act(title, this, minutesDuration);
    _actIDs.add(act.getID());
    return act;
  }

  public String getName() {
    return _name;
  }

  public List<String> getActIDs() {
    return new ArrayList<String>(_actIDs);
  }

  public void dump(Dump dump) {
    dump.add("Artist: \"" + _name + "\" <" + getID() + ">");
    dump.groupStart();
    for (String id : getActIDs()) {
      dump.add("Act <" + id + ">");
    }
    dump.groupEnd();
  }

}
