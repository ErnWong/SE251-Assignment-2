package tbs.server.theatre;

import tbs.server.IDableEntity;
import tbs.server.Dump;

import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class Theatre extends IDableEntity {

	private int _seatingDimension;
	private double _floorArea;
	private TreeSet<String> _performanceIDs = new TreeSet<>();

	public Theatre(String theatreID, int seatingDimension, double floorArea) {
		_seatingDimension = seatingDimension;
		_floorArea = floorArea;

		setID(theatreID);
	}

	public void addPerformanceID(String performanceID) {
		_performanceIDs.add(performanceID);
	}

	public List<String> getPerformanceIDs() {
		return new ArrayList<String>(_performanceIDs);
	}

	public Seating createSeating() {
		return new Seating(_seatingDimension);
	}

	public void dump(Dump dump) {
		dump.add("Theatre <" + getID() +"> dim:" + _seatingDimension + ", area:" + _floorArea);
		dump.groupStart();
		for (String id : getPerformanceIDs()) {
			dump.add("Performance <" + id + ">");
		}
		dump.groupEnd();
	}

}
