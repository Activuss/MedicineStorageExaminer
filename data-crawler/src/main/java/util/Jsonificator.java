package util;

import com.google.gson.Gson;
import domain.Leftover;
import domain.Medical;

public class Jsonificator {

	public static String leftoverToJson(Leftover leftover) {
		Gson gson = new Gson();
		return gson.toJson(leftover);
	}

	public static String medicalToJson(Medical medical) {
		Gson gson = new Gson();
		return gson.toJson(medical);
	}

}
