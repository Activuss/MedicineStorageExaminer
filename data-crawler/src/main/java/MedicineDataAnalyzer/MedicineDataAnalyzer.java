package MedicineDataAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import client.PdfExtractorTestClient;
import domain.Leftover;

public class MedicineDataAnalyzer {
	
	public final static String[] fileNames = {"ananiev_cpsmd.pdf", 
												"ananiev_crl.pdf",
												"arciz_cpsmd.pdf",
												"arciz_crl.pdf"}; 
	public final static String[] dateRegexes = {"(.*)на ((_*)\\d{2}(_*).\\d{2}.\\d{4}) року(.*)",
												"(.*)від(\\s*)«(\\s*)(_*)\\d{2}(_*)(\\s*)»(\\s*)(_*)\\d{2}(_*)(\\s*)\\d{4}(\\s*)р(\\s*)"};
	public final static String checkInfoKey = "Інформація про залишки лікарських засобів та виробів медичного призначення на складі";
	private static boolean containsInfo = false;
	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		/*Pattern p = Pattern.compile(dateRegexes[1]);
		Matcher m = p.matcher("№ _______  від   « ___07__»______09________2015 р "); 
		System.out.println(m.matches());*/
		List<Leftover> leftovers = new ArrayList<>();
		ClassLoader classLoader = PdfExtractorTestClient.class.getClassLoader();
		for (int i = 0; i < fileNames.length; i++) {
			containsInfo = false;
			System.out.println(fileNames[i]);
			File PDFFile = new File(classLoader.getResource(fileNames[i]).getFile());
			String textFromPDF = extractPDFToText(PDFFile);
			StringTokenizer tokenizer = new StringTokenizer(textFromPDF, "\n");
			//System.out.println(tokenizer.countTokens());
			Date date = null;
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				int containsCreatingDate = containsCreatingDate(token, dateRegexes);
				//System.out.println(containsCreatingDate + "||" + token);
				if (containsCreatingDate >= 0) {						
					//System.out.println(containsCreatingDate + "||" + token);
					try {
						date = parseCreatingDate(token, dateRegexes[containsCreatingDate]);
						//System.out.println(date);
						
					} catch (DataCrawlerException e) {
						
						e.printStackTrace();
					}
				}
				if (token.contains(checkInfoKey)) {
					System.out.println("File has info about medicine leftovers \r\n" + token);
					containsInfo  = true;
				} 
				
				if (containsInfo && date != null) {
					System.out.println(date);
					if (isTableRow(token)) {
						
					}
				} 
				

				
				
				
			}
			if (containsInfo) {
				System.out.println(date);
			} 
			System.out.println("-----------------------------------------------------");
		}
		
		
		
		
	}
	
	private static boolean isTableRow(String token) {
		
		return false;
	}

	private static Date parseCreatingDate(String token, String regex) throws DataCrawlerException {
		int beginIndex = regex.indexOf("\\d{2}");
		int endIndex = regex.indexOf("\\d{4}") + "\\d{4}".length();
		String dateRegex = regex.substring(beginIndex, endIndex);
		//System.out.println(dateRegex);
		String replacement = "";
		String[] splitted = token.split(dateRegex);
		String creatingDate = token;
		for (int i = 0; i < splitted.length; i++) {
			//System.out.println(creatingDate + "[][]" + splitted[i]);
			creatingDate = creatingDate.replace(splitted[i], " ");
		}
		//System.out.println(creatingDate.trim());
		creatingDate = creatingDate.replaceAll("(\\D)", "");
		creatingDate = creatingDate.substring(0, 2) + "." +
						creatingDate.substring(2, 4) + "." +
						creatingDate.substring(4);
		//System.out.println("final: " + creatingDate);
		
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
		Date date = null;
		try {
			//System.out.println(creatingDate);
			date = dateFormat.parse(creatingDate);
			//System.out.println(date);
		} catch (ParseException e) {
			throw new DataCrawlerException("Creating date parsing error for: " + token, e);
		}
		return date;
	}

	private static int containsCreatingDate(String token, String[] dateregexes) {
		for (int i = 0; i < dateregexes.length; i++) {	
			//System.out.println(token + "\r\n" + dateregexes[i]);
			Pattern p = Pattern.compile(dateRegexes[i]);
			Matcher m = p.matcher(token);  			
			if (m.matches()) {
				//System.out.println(token + "|| m: " + m.regionStart());
				return i;
			} 
		}
		
		return -1;
	}

	private static String extractPDFToText(File PDFFile) {
		BodyContentHandler handler = new BodyContentHandler();

		Metadata metadata = new Metadata();
      
		try (FileInputStream inputstream = new FileInputStream(PDFFile)) {
			ParseContext pcontext = new ParseContext();

			//parsing the document using PDF parser
			PDFParser pdfparser = new PDFParser();
			pdfparser.parse(inputstream, handler, metadata, pcontext);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
      return handler.toString();
	}

}
