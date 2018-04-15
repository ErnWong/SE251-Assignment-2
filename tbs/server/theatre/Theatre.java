package tbs.server.theatre;

import tbs.server.IDableEntity;
import tbs.server.TBSRequestException;
import tbs.server.Dump;
import tbs.server.performance.PerformanceCatalogue;
import tbs.server.performance.Performance;

import java.util.List;
import java.util.ArrayList;

public class Theatre extends IDableEntity {

	private int _seatingDimension;
	private double _floorArea;
	private final PerformanceCatalogue _performances = new PerformanceCatalogue();

	public Theatre(String theatreID, int seatingDimension, double floorArea) {
		_seatingDimension = seatingDimension;
		_floorArea = floorArea;

		setID(theatreID);
	}

	public void addPerformance(Performance performance) throws TBSRequestException{
		_performances.add(performance);
	}

	public List<String> getPerformanceIDs() {
		return _performances.toIDs();
	}

	public Seating createSeating() {
		return new Seating(_seatingDimension);
	}

	@Override
	public void dump(Dump dump) {
		dump.add("Theatre <" + getID() +"> dim:" + _seatingDimension + ", area:" + _floorArea);
		dump.groupStart();
		for (String id : getPerformanceIDs()) {
			dump.add("Performance <" + id + ">");
		}
		dump.groupEnd();
	}

}
