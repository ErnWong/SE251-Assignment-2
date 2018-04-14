package tbs.server.ticket;

import tbs.server.Catalogue;
import tbs.server.Dump;

public class TicketCatalogue extends Catalogue<Ticket> {

	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Ticket Catalogue");
	}

	protected String createNotFoundMessage(String id) {
		return "Ticket with ticket ID `" + id + "` does not exist";
	}

	protected String createEmptyIDMessage() {
		return "Ticket ID is empty";
	}

}
