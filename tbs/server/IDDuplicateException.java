package tbs.server;

public class IDDuplicateException extends RuntimeException {

	public IDDuplicateException() {
		super("An entity with given ID already exists.");
	}

}
