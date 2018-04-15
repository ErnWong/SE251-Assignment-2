package tbs.client;

import tbs.server.TBSServer;
import tbs.server.TBSServerImpl;
import java.util.List;

public class Tests {

	private static int _passCount = 0;
	private static int _assertCount = 0;

	public static void main(String[] args) {
		testInitialise();
	}

	private static void testInitialise() {
		startTest("Initialise");

		TBSServer server;
		String result;

		comment("Initialising from theatres1 - original");
		server = new TBSServerImpl();

		result = server.initialise("theatres1.csv");
		assertEquals(result, "", "should have no errors");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[T1, T2, T3]", "should have added theatres correctly");

		comment("Initialising from theatres2 - interesting ids");
		server = new TBSServerImpl();

		result = server.initialise("theatres2.csv");
		assertEquals(result, "", "should have no errors");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[T1, Theatre2, abababa]", "should have added theatres correctly");

		comment("Initialising from theatres3 - empty");
		server = new TBSServerImpl();

		result = server.initialise("theatre3.csv");
		assertEquals(result, "", "should have no errors");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should have added theatres correctly");

		comment("Initialising from invalid1 - dup ids");
		server = new TBSServerImpl();

		result = server.initialise("invalid1.csv");
		assertIsError(result, "should return error");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should not have added theatres");

		comment("Initialising from invalid2 - bad first col");
		server = new TBSServerImpl();

		result = server.initialise("invalid2.csv");
		assertIsError(result, "should return error");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should not have added theatres");

		comment("Initialising from invalid3 - space instead of tab (aka missing tab)");
		server = new TBSServerImpl();

		result = server.initialise("invalid3.csv");
		assertIsError(result, "should return error");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should not have added theatres");

		comment("Initialising from invalid4 - NaN");
		server = new TBSServerImpl();

		result = server.initialise("invalid4.csv");
		assertIsError(result, "should return error");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should not have added theatres");

		comment("Initialising from invalid5 - Negative");
		server = new TBSServerImpl();

		result = server.initialise("invalid5.csv");
		assertIsError(result, "should return error");

		result = server.getTheatreIDs().toString();
		assertEquals(result, "[]", "should not have added theatres");

		endTest();
	}

	private static void comment(String message) {
		System.out.println(message);
	}

	private static void startTest(String name) {
		System.out.println("────────────────────────────────────────────────────────────────────────────────");
		System.out.println("Starting test: " + name);
	}

	private static void endTest() {
		System.out.println("Test ended. Passed " + _passCount + " / " + _assertCount);
	}

	private static void assertEquals(String a, String b, String message) {
		_assertCount++;
		boolean pass = a.equals(b);
		message += ":\n\t";
		message += pass ? "PASS" : "FAIL";
		System.out.println(message);
		if (pass) {
			_passCount++;
		} else {
			System.out.println("Expected:\t" + b);
			System.out.println("Got:\t" + a);
		}
	}

	private static void assertIsError(String res, String message) {
		_assertCount++;
		boolean pass = res.startsWith("ERROR");
		System.out.println(message);
		System.out.println("\tGot:\t" + res);
		System.out.println("\t" + (pass ? "PASS" : "FAIL"));
		if (pass) {
			_passCount++;
		} else {
			System.out.println("Expected error");
			System.out.println("Got:\t" + res);
		}
	}
	
}
