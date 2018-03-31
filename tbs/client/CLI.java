package tbs.client;

import tbs.server.TBSServer;
import tbs.server.TBSServerImpl;
import java.util.List;

public class CLI {
	public static void main(String[] args) {
		String path = "theatres1.csv";
		if (args.length > 0) {
			path = args[0]; // This allows a different file to be specified as an argument, but the default is theatres2.csv
		}
		TBSServer server = new TBSServerImpl();
		String result = server.initialise(path);
		System.out.println("Result from initialisation is {" + result + "}");  // Put in { } to make empty strings easier to see.
		server.dump(); // Implement dump() to print something useful here to determine whether your initialise method has worked.
		
		String artistID1 = server.addArtist("Ewan");
		System.out.println("Result from adding artist 'Ewan' is {" + artistID1 + "}");
		server.dump(); // Check that the server has been updated
		
		String actID1 = server.addAct("Lecture 3b: Making Objects", artistID1, 50); // this also checks that the artist ID is used properly
		System.out.println("Result from adding act to artist 'Ewan' is {" + actID1 + "}");
		server.dump();

    List<String> report1 = server.salesReport(actID1);
		System.out.println("Sales report for lecture: {" + report1 + "}");

    String perfID1 = server.schedulePerformance(actID1, "T1", "2018-03-31T16:00", "$1000", "$0");
		System.out.println("Result from scheduling performance for Lecture is {" + perfID1 + "}");
		server.dump();

    String ticketID1 = server.issueTicket(perfID1, 2, 2);
		System.out.println("Result from issuing ticket (2,2) for performance is {" + ticketID1 + "}");
		server.dump();
    String ticketID2 = server.issueTicket(perfID1, 2, 3);
		System.out.println("Result from issuing ticket (2,3) for performance is {" + ticketID2 + "}");
		server.dump();
    String ticketID3 = server.issueTicket(perfID1, 2, 3);
		System.out.println("Result from issuing ticket (2,3) for performance is {" + ticketID3 + "}");
		server.dump();

    List<String> report2 = server.salesReport(actID1);
		System.out.println("Sales report for lecture: {" + report2 + "}");

    List<String> seatsAvailable = server.seatsAvailable(perfID1);
		System.out.println("Seats available:");
    for (String line : seatsAvailable) {
      System.out.println(line);
    }
	}
}
