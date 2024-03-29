package tbs.client;

import tbs.server.TBSServer;
import tbs.server.TBSServerImpl;
import java.util.List;

public class CLI {

	public static void main(String[] args) {
		custom();
		//simple();
		//grouped();
	}
	
	private static void simple() {
		System.out.println("SIMPLE This sequence adds 1 of everything");
		String path = "theatres1.csv";
		TBSServer server = new TBSServerImpl();
		String result = server.initialise(path);
		System.out.println("Result from initialise() is {" + result + "} (should be {})");
		
		List<String> actualTheatreIDs = server.getTheatreIDs();
		System.out.println("Result from getThreatreIDs() is " + actualTheatreIDs);	
		// Check initial state of server. Should be no artists
		System.out.println("Result from getArtistIDs() is " + server.getArtistIDs() + "(should be [])");
		System.out.println();
		
		// Artists
		String artistID1 = addArtist(server, "Artist 1");
		
		// Acts
		String actID1 = addAct(server, artistID1, "Act title 1", 90);
		
		// Performances
		String startTime = "2018-04-03T16:00";
		int seats = 7*7; // T1 has 7 rows and seats per row
		String perfID1 = schedulePerformance(server, actID1, "T1", startTime, seats, "$10", "$5");
		
		issueTicket(server, perfID1, 1, 1, 7*7-1);
		
		// sales
		List<String> salesReport = server.salesReport(actID1);
		System.out.println("Sales report for " + actID1 + " has " + salesReport.size() + " entry (expected 1)");
		checkSalesReport(salesReport, perfID1, startTime, 1, "$10");
		System.out.println();
		
		server.dump();

		System.out.println("---------------- completed ----------------------");
	}
	
	private static void grouped() {
		System.out.println("GROUPED This sequence adds multiple of everything but grouped together");
		String path = "theatres1.csv";
		TBSServer server = new TBSServerImpl();
		server.initialise(path);

		// Add multiple artists
		String artistID = addArtist(server, "Group Artist 1");
		artistID = addArtist(server, "Group Artist B");
		artistID = addArtist(server, "Another Group Artist");
		
		String actID = addAct(server, artistID, "Group Title First", 90);
		actID  = addAct(server, artistID, "A Group Title", 120);
		
		String perfID0 = schedulePerformance(server, actID, "T2", "2018-10-10T20:00", 10*10, "$15", "$7");
		perfID0 = schedulePerformance(server, actID, "T2", "2019-01-10T20:30", 10*10, "$10", "$7");
		perfID0 = schedulePerformance(server, actID, "T2", "2019-01-11T20:30", 10*10, "$12", "$3");
		String perfID = schedulePerformance(server, actID, "T2", "2019-01-13T19:00", 10*10, "$10", "$5");
		
		issueTicket(server, perfID, 1, 1, 10*10-1);
		issueTicket(server, perfID, 1, 2, 10*10-2);
		issueTicket(server, perfID, 1, 3, 10*10-3);
		issueTicket(server, perfID, 1, 4, 10*10-4);
		issueTicket(server, perfID, 5, 4, 10*10-5);
		issueTicket(server, perfID, 5, 5, 10*10-6);
		issueTicket(server, perfID, 8, 2, 10*10-7);
		issueTicket(server, perfID, 8, 3, 10*10-8);
		
		List<String> salesReport = server.salesReport(actID);
		System.out.println("Sales report for " + actID + " has " + salesReport.size() + " entry (expected 4)");
		checkSalesReport(salesReport, perfID0, "2019-01-11T20:30", 0, "$0");
		checkSalesReport(salesReport, perfID, "2019-01-13T19:00", 8, "$70");

		server.dump();
		
		System.out.println("---------------- completed ----------------------");
	}

	private static void custom() {
		System.out.println("CUSTOM");
		String path = "theatres1.csv";
		TBSServer server = new TBSServerImpl();
		server.initialise(path);

		// Add multiple artists
		String artistID = addArtist(server, "Group Artist 1");
		artistID = addArtist(server, "Group Artist B");
		artistID = addArtist(server, "Another Group Artist");
		
		String actID = addAct(server, artistID, "Group Title First", 90);
		actID  = addAct(server, artistID, "A Group Title", 120);
		
		String perfID0 = schedulePerformance(server, actID, "T2", "2018-10-10T20:00", 10*10, "$15", "$7");
		String perfID1 = schedulePerformance(server, actID, "T2", "2019-01-10T20:30", 10*10, "$10", "$7");
		String perfID2 = schedulePerformance(server, actID, "T2", "2019-01-11T20:30", 10*10, "$12", "$3");
		String perfID = schedulePerformance(server, actID, "T2", "2019-01-13T19:00", 10*10, "$10", "$5");
		
		issueTicket(server, perfID, 1, 1, 10*10-1);
		issueTicket(server, perfID, 1, 2, 10*10-2);
		issueTicket(server, perfID1, 1, 3, 10*10-3);
		issueTicket(server, perfID, 1, 4, 10*10-4);
		issueTicket(server, perfID2, 5, 4, 10*10-5);
		issueTicket(server, perfID, 5, 5, 10*10-6);
		issueTicket(server, perfID, 8, 2, 10*10-7);
		issueTicket(server, perfID, 8, 3, 10*10-8);
		
		List<String> salesReport = server.salesReport(actID);
		System.out.println("Sales report for " + actID + " has " + salesReport.size() + " entry (expected 4)");
		checkSalesReport(salesReport, perfID0, "2019-01-11T20:30", 0, "$0");
		checkSalesReport(salesReport, perfID, "2019-01-13T19:00", 8, "$70");

		server.dump();
		
		System.out.println("---------------- completed ----------------------");
	}
	
	private static String addArtist(TBSServer server, String artistName) {
		String artistID = server.addArtist(artistName);
		System.out.println("Added artist '" + artistName + "' got artist ID " + artistID);
		List<String> actualArtistIDs = server.getArtistIDs();
		System.out.println("Result from getArtistIDs is " + actualArtistIDs);
		System.out.println("Results from getArtistNames is " + server.getArtistNames());
	
		// Should be no acts yet for new artist
		System.out.println("Result from getActIDs() for artist " + artistID + " is " + server.getActIDsForArtist(artistID) + " (should be [])");
		System.out.println();
		return artistID;
	}
	
	private static String addAct(TBSServer server, String artistID, String actTitle, int duration) {
		String actID = server.addAct(actTitle,  artistID, duration);
		System.out.println("Added act title '" + actTitle + "' duration " + duration + " for artist " + artistID + " got act ID " + actID);
		System.out.println("Result from getActIDs() for artist " + artistID + " is " + server.getActIDsForArtist(artistID));
		// Should be no performances for new act
		System.out.println("Result from getPeformanceIDsForAct() for artist " + artistID + " is " + server.getPeformanceIDsForAct(actID) + " (should be [])");
		System.out.println();
		return actID;
	}
	
	private static String schedulePerformance(TBSServer server, String actID, String theatreID, String startTime, int numSeats, String premium, String cheap) {
		String perfID = server.schedulePerformance(actID, theatreID, startTime, premium, cheap);
		System.out.println("Added performance at " + startTime + ", premium=" + premium + ", cheap=" + cheap + " to act " + actID + " got performance ID " + perfID);
		System.out.println("Result from getPeformanceIDsForAct() for act " + actID + " is " + server.getPeformanceIDsForAct(actID));
		// Should be no tickets
		System.out.println("Result from getTicketIDsForPerformance() for " + perfID + " is " + server.getTicketIDsForPerformance(perfID) + " (should be [])");

		// There should be plenty of seats
		List<String> actualSeatsAvailable = server.seatsAvailable(perfID);
		System.out.println("Performance " + perfID + " has " + actualSeatsAvailable.size() + " seats available (expected " + numSeats + ")");
		System.out.println();
		
		return perfID;
	}
	
	private static String issueTicket(TBSServer server, String perfID, int row, int seat, int seatsAvailableAfter) {
		// Tickets
		String ticketID = server.issueTicket(perfID, row, seat);
		System.out.println("Issued ticket for performance " + perfID + " row=" + row + " seat=" + seat + " got ticket ID " + ticketID);
		List<String> actualTicketIDs = server.getTicketIDsForPerformance(perfID);
		System.out.println("Result from getTicketIDsForPerformance() for performance " + perfID + " is " + actualTicketIDs);
		List<String> actualSeatsAvailable = server.seatsAvailable(perfID);
		System.out.println("Performance " + perfID + " now has " + actualSeatsAvailable.size() + " seats available (should be " + seatsAvailableAfter + ")");
		checkSeatNotAvailable(actualSeatsAvailable, 1, 1);
		System.out.println();
		return ticketID;
	}
	private static void checkSeatNotAvailable(List<String> seatsAvailable, int row, int seat) {
		String record = row + "\t" + seat;
		for(String available: seatsAvailable) {
			if (record.equals(available)) {
				System.out.println("\tFAILURE seat row="+row+", seat="+seat+" is still available (but should not be)");
			}
		}
	}
	private static void checkSalesReport(List<String> salesReport, String perfID, String startTime, int ticketsSold, String receipts) {
		// Find the relevant report. Not the most elegant...
		String relevantReport = null;
		for(String perfReport: salesReport) {
			String[] bits = perfReport.split("\t");
			if (bits[0].equals(perfID)) {
				relevantReport = perfReport;
				break;
			}
		}
		if (relevantReport == null) {
			System.out.println("\tFAILURE cannot find report for " + perfID);
			return;
		}
		checkSalesReport(relevantReport, perfID, startTime, ticketsSold, receipts);
	}	
	private static void checkSalesReport(String report, String perfID, String startTime, int ticketsSold, String receipts) {
		System.out.print("Checking sales report format for performance ID " + perfID);
		boolean failure = false;
		String[] bits = report.split("\t");
		if (!perfID.equals(bits[0])) {
			System.out.println("\n\tFAILURE Performance ID does not match in sales report {" + report + "} expected " + perfID);
			failure=true;
		}
		if (!startTime.equals(bits[1])) {
			System.out.println("\n\tFAILURE Start time does not match in sales report {" + report + "} expected " + startTime);
			failure=true;
		}
		if (!(ticketsSold+"").equals(bits[2])) {
			System.out.println("\n\tFAILURE Tickets sold does not match in sales report {" + report + "} expected " + ticketsSold);
			failure=true;
		}
		if (!bits[3].startsWith(receipts)) { // Do it this way in case people don't add .00
			System.out.println("\n\tFAILUE Total Receipts do not match in sales report {" + report + "} expected " + receipts);
			failure=true;
		}
		if (!failure) {
			System.out.println(".....ok");
		}
	}

	private static void old(String[] args) {
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
