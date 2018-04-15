package tbs.server;

import java.util.Collections;
import java.util.List;

/**
 * A generic exception to indicate a client error: ie a malformed or
 * insensible request. This checked exception is to be handled by the
 * TBSServer implementation so the message is returned back to the client.
 */
public class TBSRequestException extends Exception {

	public TBSRequestException(String message) {
		super(message);
	}

	public String toResponse() {
		return "ERROR " + getMessage();
	}

	public List<String> toResponseList() {
		return Collections.singletonList(toResponse());
	}

}
