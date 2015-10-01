package util;

import domain.Leftover;

public class LeftOverBuilder {
	public static Leftover build(String recognisedString) {
		String[] parts = recognisedString.split(" ");
		return new Leftover(getId(parts), getName(parts), getMeasure(parts), getQuantity(parts));
	}

	private static int getId(String[] splittedRecognisedString) {
		int localId = 0;
		if (splittedRecognisedString[0].matches("(\\d+)(\\W*)")) {
			localId = Integer.parseInt(splittedRecognisedString[0].trim().split("\\W")[0]);
		}
		return localId;
	}

	private static double getQuantity(String[] splittedRecognisedString) {
		double quantity = -1;
		if (splittedRecognisedString[splittedRecognisedString.length - 1].matches("(\\d+)")) {
			quantity = Double.parseDouble(splittedRecognisedString[splittedRecognisedString.length - 1].trim());
		} else {
			if (splittedRecognisedString[splittedRecognisedString.length - 1].matches("(\\d+)(\\W)(\\d+)")) {
				String[] splittedQuantity = splittedRecognisedString[splittedRecognisedString.length - 1].trim().split(",");
				String dottedQuantity = splittedQuantity[0] + "." + splittedQuantity[1];
				quantity = Double.parseDouble(dottedQuantity.trim());
			}
		}
		return quantity;
	}

	private static String getName(String[] splittedRecognisedString) {
		StringBuilder name = new StringBuilder();
		for (int i = 1; i < splittedRecognisedString.length - 2; i++) {
			if (!splittedRecognisedString[i].isEmpty() && !splittedRecognisedString[i].matches("(\\s+)")) {
				name.append(splittedRecognisedString[i].trim() + " ");
			}
		}
		return name.toString().trim();
	}

	private static String getMeasure(String[] splittedRecognisedString) {
		return splittedRecognisedString[splittedRecognisedString.length - 2].trim();
	}
}
