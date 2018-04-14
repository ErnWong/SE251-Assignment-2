package tbs.server.performance;

import tbs.server.Catalogue;
import tbs.server.Dump;

public class PerformanceCatalogue extends Catalogue<Performance> {

	@Override
	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Performance Catalogue");
	}

	@Override
	protected String createNotFoundMessage(String id) {
		return "Performance with performance ID `" + id + "` does not exist";
	}

	@Override
	protected String createEmptyIDMessage() {
		return "Peformance ID is empty";
	}

}
