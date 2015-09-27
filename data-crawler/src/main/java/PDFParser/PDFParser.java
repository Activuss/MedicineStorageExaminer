package PDFParser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioFormat.Encoding;

import org.apache.lucene.document.Document;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.lucene.LucenePDFDocument;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.persistence.util.COSObjectKey;
import org.apache.pdfbox.util.PDFStreamEngine;
import org.apache.pdfbox.util.PDFText2HTML;
import org.apache.pdfbox.util.PDFTextStripper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Hello world!
 *
 */
public class PDFParser {
	
	private static StringBuffer currentLine = new StringBuffer();
	private static int wordMarginLeft = 0;
    private static int wordCurrentWidth = 0;
	
	
    

	

	public static void main( String[] args )
    {
    	ClassLoader classLoader = PDFParser.class.getClassLoader();
    	String PDFFileName = "example.pdf";
        File PDFFile = new File(classLoader.getResource(PDFFileName).getFile());
        
        
        String pathToPdf = PDFFile.getAbsolutePath();
        String pathToImagemagick = "/usr/bin/convert";
        System.out.println(pathToPdf);
		float zoom = Float.parseFloat("1.0");
		
		try {
			Pdf2Json converter = new Pdf2Json(zoom, pathToImagemagick);
			converter.convert(pathToPdf);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        
        
        
        
    }
}


