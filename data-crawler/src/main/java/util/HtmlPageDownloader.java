package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Returned HTML document of given http address.
 */
public class HtmlPageDownloader {

	public static Document download(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
}
