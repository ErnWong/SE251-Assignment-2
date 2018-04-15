package tbs.server.artist;

import tbs.server.IDableEntity;
import tbs.server.TBSRequestException;
import tbs.server.Dump;
import tbs.server.act.Act;
import tbs.server.act.ActCatalogue;

import java.util.List;
import java.util.ArrayList;

public class Artist extends IDableEntity {

	private String _name;
	private final ActCatalogue _acts = new ActCatalogue();

	public Artist(String name) throws TBSRequestException {
		if (name == null || name.trim().isEmpty()) {
			throw new TBSRequestException("Artist name is empty");
		}
		_name = name;

		setIDPrefix("Artist");
	}

	public Act createAct(String title, int minutesDuration) throws TBSRequestException {
		Act act = new Act(title, this, minutesDuration);
		_acts.add(act);
		return act;
	}

	public String getName() {
		return _name;
	}

	public List<String> getActIDs() {
		return _acts.toIDs();
	}

	@Override
	public void dump(Dump dump) {
		dump.add("Artist: \"" + _name + "\" <" + getID() + ">");
		dump.groupStart();
		for (String id : getActIDs()) {
			dump.add("Act <" + id + ">");
		}
		dump.groupEnd();
	}

}
