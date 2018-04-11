package tbs.server;

import java.util.Collections;
import java.util.List;

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
