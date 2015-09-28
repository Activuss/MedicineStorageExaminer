package client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public final class PdfExtractor {
	
	public static String extract (InputStream inputStream) throws PDFExtractorException {
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser();
		try {
			pdfparser.parse(inputStream, handler, metadata, pcontext);			
		} catch (IOException | SAXException | TikaException e) {
			throw new PDFExtractorException("Can't extract data", e);
		}
		return handler.toString();
	}

}
