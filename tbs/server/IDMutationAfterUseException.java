package tbs.server;

public class IDMutationAfterUseException extends RuntimeException {

	public IDMutationAfterUseException() {
		super("Attempted to modify ID after ID has been used - violating immutability.");
	}

}
