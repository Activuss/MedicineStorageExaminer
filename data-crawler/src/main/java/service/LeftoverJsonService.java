package service;

import com.google.gson.Gson;

import domain.Leftover;
import domain.Medical;

public final class LeftoverJsonService {
	
	private String json;
	
	
	public LeftoverJsonService(Leftover leftover) {
		Gson gson = new Gson();
		json = gson.toJson(leftover);
	}
	
	
	public LeftoverJsonService(Medical medical) {
		Gson gson = new Gson();
		json = gson.toJson(medical);
	}



	@Override
	public String toString() {
		return json;
	}
	
	
	
}
