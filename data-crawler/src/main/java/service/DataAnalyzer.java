package service;

import java.util.List;
import java.util.Map;

public interface DataAnalyzer {
	Map<String, List<String>> analyze(String textFromPDF);
}
