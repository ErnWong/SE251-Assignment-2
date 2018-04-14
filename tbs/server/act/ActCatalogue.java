package tbs.server.act;

import tbs.server.Catalogue;
import tbs.server.Dump;

public class ActCatalogue extends Catalogue<Act> {

	@Override
	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Act Catalogue");
	}

	@Override
	protected String createNotFoundMessage(String id) {
		return "Act with Act ID `" + id + "` does not exist";
	}

	@Override
	protected String createEmptyIDMessage() {
		return "Act ID is empty";
	}

}
