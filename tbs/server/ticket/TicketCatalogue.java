package tbs.server.ticket;

import tbs.server.Catalogue;
import tbs.server.Dump;

public class TicketCatalogue extends Catalogue<Ticket> {

	@Override
	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Ticket Catalogue");
	}

	@Override
	protected String createNotFoundMessage(String id) {
		return "Ticket with ticket ID `" + id + "` does not exist";
	}

	@Override
	protected String createEmptyIDMessage() {
		return "Ticket ID is empty";
	}

}
