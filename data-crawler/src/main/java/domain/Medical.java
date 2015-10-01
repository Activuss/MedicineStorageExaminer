package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Class reflects one clinic on specific location and all aids in it.
 */
public class Medical {

	private String location;
	private String name;
	private List<Leftover> leftovers;
	private List<String> unrecodnisedEntries;

	public Medical(String location, String name) {
		this.location = location;
		this.name = name;
		this.leftovers = new ArrayList<>();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Leftover> getLeftovers() {
		return leftovers;
	}

	public void setLeftovers(List<Leftover> leftovers) {
		this.leftovers = leftovers;
	}

	public List<String> getUnrecodnisedEntries() {
		return unrecodnisedEntries;
	}

	public void setUnrecodnisedEntries(List<String> unrecodnisedEntries) {
		this.unrecodnisedEntries = unrecodnisedEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medical other = (Medical) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
