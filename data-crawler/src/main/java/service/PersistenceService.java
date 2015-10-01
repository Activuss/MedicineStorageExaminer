package service;

public interface PersistenceService {
	
	public void addMedical(String medicalJson);
	public String getMedical(String location, String medicalName);
}
