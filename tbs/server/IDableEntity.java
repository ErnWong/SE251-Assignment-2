package tbs.server;

import java.util.UUID;

public abstract class IDableEntity implements Dumpable {

  protected String _id = UUID.randomUUID().toString().substring(0,8);

  public String getID() {
    return _id;
  }

  public void dump(Dump dump) {
    dump.add("Id: " + _id);
  }

}
