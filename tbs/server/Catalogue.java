package tbs.server;

import tbs.server.IDDuplicateException;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.UUID;
import java.util.Iterator;

public abstract class Catalogue<T extends IDableEntity> implements Iterable<T>, Dumpable {

	private final NavigableMap<String,T> _items = new TreeMap<>();

	public void add(T item) throws TBSRequestException {
		String id = item.getID();

		if (_items.containsKey(id)) {
			throw new IDDuplicateException();
		}

		_items.put(id, item);
	}

	public boolean hasID(String id) {
		return _items.containsKey(id);
	}

	public T fromID(String id) throws TBSRequestException {
		if (id == null || id.length() == 0) {
			String message = createEmptyIDMessage();
			throw new TBSRequestException(message);
		}

		T item = _items.get(id);

		if (item == null) {
			String message = createNotFoundMessage(id);
			throw new TBSRequestException(message);
		}

		return item;
	}

	public List<String> toIDs() {
		return new ArrayList<String>(_items.keySet());
	}

	public Iterator<T> iterator() {
		return _items.values().iterator();
	}

	public int count() {
		return _items.size();
	}

	public void dump(Dump dump) {
		dump.add("Catalogue");
		dump.groupStart();
		for (T item : _items.values()) {
			item.dump(dump);
		}
		dump.groupEnd();
	}

	// To customise error messages
	protected abstract String createNotFoundMessage(String id);
	protected abstract String createEmptyIDMessage();

}
