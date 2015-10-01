package domain;

/**
 * Class represent one recognised aid entry in the clinic.
 */
public class Leftover {

	private int localId;
	private String name;
	private String measure;
	private double quantity;

	public Leftover(int localId, String name, String measure, double quantity) {
		this.localId = localId;
		this.name = name;
		this.quantity = quantity;
		this.measure = measure;
	}

	public int getLocalId() {
		return localId;
	}

	public String getName() {
		return name;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getMeasure() {
		return measure;
	}

	@Override
	public String toString() {
		return "Leftover [" + localId + " " + name + " " + measure + " " + quantity
				+ "]";
	}
}
