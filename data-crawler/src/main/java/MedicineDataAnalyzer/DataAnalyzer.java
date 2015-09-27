package MedicineDataAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.PDFExtractorException;
import client.PdfExtractor;
import client.PdfExtractorTestClient;



public final class DataAnalyzer {
	
	private final String[] tableHeadRegex = {"№(\\s*)Назва(.*)Одиниц[яі](.*)Кільк(.*)", 
													"№(\\s*)Назва лікарського засобу(\\s*)Одиниц[яі](\\s*)" };
	/*private final static String[] tableLineRegex = {"\\d(.*)(\\s+)(\\d+)", 
													"" };*/
	private final String[] measures = {"амп", "ампули", "кан", "кг", "кон", "канистра", "пар", "пари", "таб", 
										"уп", "упаковка", "фл", "флакони", "шт"};
	
	private List<String> analyzedData = new ArrayList<>();
	private List<String> unAnalyzedData = new ArrayList<>();

	public void analyze(String textFromPDF) {
		StringTokenizer tokenizer = new StringTokenizer(textFromPDF, "\r\n");
		//System.out.println(tokenizer.countTokens());
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			//System.out.println(token);
			if (isHead(token)) {
				//System.out.println(token + "isHead:" + true);
				while (tokenizer.hasMoreTokens()) {
					token = tokenizer.nextToken();
					if (isTableLine(token)) {
						//System.out.println(token + "isTableLine: " + true);
						analyzedData.add(token);
					} else {
						if (token.matches("(\\s*)(\\d+)(\\W?)(\\s+)(.+)")) {
							unAnalyzedData.add(token);
							//System.out.println(token + "isTableLine: " + false);
						} //else System.out.println(token + "is NOT TableLine: ");
					}
						
				}
				
			} //else System.out.println("isHead:" + false);
		}
	}

	private boolean isHead(String token) {
		for (int i = 0; i < tableHeadRegex.length; i++) {
			Pattern pattern = Pattern.compile(tableHeadRegex[i]);
			Matcher matcher = pattern.matcher(token);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	private boolean isTableLine(String token) {
		for (int i = 0; i < measures.length; i++) {
			if (token.matches("(\\d+)(.+)(\\s+)" + measures[i] + "(\\W?)(\\s+)(.*)(\\d+)(\\s*)")) {
				return true;
			}
		}
		return false;
	}

	public List<String> getAnalyzedData() {
		return analyzedData;
	}

	public List<String> getUnAnalyzedData() {
		return unAnalyzedData;
	}
	
	
	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader();
    	InputStream inputStream = null;
    	DataAnalyzer analyzer = null;
    	analyzer = new DataAnalyzer();
    	inputStream = new FileInputStream(new File(classLoader.getResource("balta_crl.pdf").getFile()));
		String textFromPDF = PdfExtractor.extract(inputStream);
		System.out.println(textFromPDF);
		analyzer.analyze(textFromPDF);
		System.out.println(analyzer.getAnalyzedData().size());
		/*for (String string : analyzer.getAnalyzedData()) {
			System.out.println(string);
		}*/
		
		/*String string = "№ Назва лікарського засобу Одиниця виміру Кількість* ";
		Pattern pattern = Pattern.compile(tableHeadRegex[0], Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(string);
		//System.out.println("CONTAINS: " + string.contains(tableHeadRegex[0]));
		System.out.println("MATCHER: " + matcher.matches());*/
		
		
		/*String string = "1.    L-лизин 0,1%-5,0 №10  амп  30";
		Pattern pattern = Pattern.compile(tableLineRegex[0]);
		Matcher matcher = pattern.matcher(string);
		System.out.println("MATCHER: " + matcher.matches());
		
		String[] splitted = "1.    L-лизин 0,1%-5,0 №10  амп  30".split("(\\s+)");
		System.out.println(splitted[0] + ": " + splitted[0].matches("(\\d+)(\\W+)"));
		System.out.println(splitted[splitted.length - 1] + ": " + splitted[splitted.length - 1].matches("(\\d+)"));
		
		String token = "1.    L-лизин 0,1%-5,0 №10  амп  30";
		for (int i = 0; i < measures.length; i++) {
			if (token.matches("(\\d+)(.+)(\\s+)" + measures[i] + "(\\s+)(.+)(\\d+)")) {
				System.out.println(true);
			} else System.out.println(false);
		}
		String  token = "42 сульфоканфокаїн 100 мг/мл 10%";
		System.out.println(token.matches("(\\s*)(\\d+)(\\W?)(\\s+)(.+)"));*/
	}

}
