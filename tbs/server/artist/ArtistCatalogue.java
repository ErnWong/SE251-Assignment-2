package tbs.server.artist;

import tbs.server.Catalogue;
import tbs.server.TBSRequestException;
import tbs.server.Dump;
import tbs.server.artist.Artist;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.HashSet;

public class ArtistCatalogue extends Catalogue<Artist> {

	private TreeSet<String> _names = new TreeSet<>();
	private HashSet<String> _namesInLowerCase = new HashSet<>();

	public void add(Artist artist) throws TBSRequestException {

		String name = artist.getName();
		String nameInLowerCase = name.toLowerCase();

		if (_namesInLowerCase.contains(nameInLowerCase)) {
			String message = createAlreadyExistsMessage(name);
			throw new TBSRequestException(message);
		}

		_names.add(name);
		_namesInLowerCase.add(nameInLowerCase);

		super.add(artist);
	}

	public List<String> toNames() {
		return new ArrayList<String>(_names);
	}

	public void dump(Dump dump) {
		super.dump(dump);
		dump.rewritePrevious("Artist Catalogue");
	}

	protected String createNotFoundMessage(String id) {
		return "Artist with artist ID `" + id + "` does not exist";
	}

	protected String createEmptyIDMessage() {
		return "Artist ID is empty";
	}

	protected String createAlreadyExistsMessage(String name) {
		return "Artist `" + name + "` already exist";
	}

}
