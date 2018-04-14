package tbs.server;

import java.util.UUID;

public abstract class IDableEntity implements Dumpable {

	private String _id = "<" + UUID.randomUUID().toString() + ">";
	private String _prefix = "";

	private boolean _idHasBeenRead = false;

	public String getID() {
		_idHasBeenRead = true;
		return _prefix + _id;
	}

	protected void setID(String newID) {
		if (_idHasBeenRead) {
			throw new IDMutationAfterUseException();
		}
		_id = newID;
	}

	protected void setIDPrefix(String newPrefix) {
		if (_idHasBeenRead) {
			throw new IDMutationAfterUseException();
		}
		_prefix = newPrefix;
	}

	public void dump(Dump dump) {
		dump.add("Id: " + _id);
	}

}
