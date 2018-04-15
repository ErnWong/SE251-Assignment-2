package tbs.server.act;

import tbs.server.TBSRequestException;
import tbs.server.Dump;
import tbs.server.artist.Artist;
import tbs.server.performance.PerformanceCatalogue;
import tbs.server.performance.Performance;
import tbs.server.theatre.Theatre;
import tbs.server.IDableEntity;

import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class Act extends IDableEntity {

	private final Artist _artist;

	private String _title;
	private int _minutesDuration;
	private final PerformanceCatalogue _performances = new PerformanceCatalogue();

	public Act(String title, Artist artist, int minutesDuration) throws TBSRequestException {
		if (title == null || title.trim().isEmpty()) {
			throw new TBSRequestException("Act title is empty");
		}
		if (minutesDuration <= 0) {
			throw new TBSRequestException("Act has non-positive duration");
		}

		_title = title;
		_artist = artist;
		_minutesDuration = minutesDuration;

		setIDPrefix("Act");
	}

	public Performance performAt(Theatre theatre, String startTimeStr) throws TBSRequestException {
		Performance performance = new Performance(this, theatre, startTimeStr);
		_performances.add(performance);
		theatre.addPerformance(performance);
		return performance;
	}

	public List<String> getPerformanceIDs() {
		return _performances.toIDs();
	}

	public List<String> salesReport() {
		ArrayList<String> report = new ArrayList<>();

		for (Performance performance : _performances) {
			report.add(performance.generateSummary());
		}

		return report;
	}

	@Override
	public void dump(Dump dump) {
		String summary = "Act: \"" + _title + "\" ";
		summary += "<" + getID() + "> ";
		summary += "(" + _minutesDuration + " mins) ";
		summary += "by \"" + _artist.getName() + "\"";
		dump.add(summary);
		dump.groupStart();
		for (Performance performance : _performances) {
			dump.add("Performance <" + performance.getID() + ">");
		}
		dump.groupEnd();
	}

}
