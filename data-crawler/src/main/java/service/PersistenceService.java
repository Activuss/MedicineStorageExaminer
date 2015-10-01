package service;

import java.util.List;

public interface PersistenceService {

	void addMedical(String medicalJson);

	String getMedical(String location, String medicalName);

	List<String> getAllMedical();
}
