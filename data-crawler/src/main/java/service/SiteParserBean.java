package service;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import domain.Medical;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service for parsing given html document. The aim - name and location of clinic and url to
 * the pdf document with all aids content for each clinic.
 */
public class SiteParserBean implements SiteParser {

	public static final String TABLE_ROW_SELECTOR = "#main > div > div > table > tbody > tr ";
	public static final String ANCHOR_SELECTOR = "a";
	public static final String URL_SELECTOR = "href";
	public static final String LINK_ROW_SIZE = "19";
	public static final String NAME_ROW_SIZE = "48";
	public static final String LOCATION_ROW_SIZE = "31";

	public Map<Medical, String> parse(Document htmlDocument) {
		Preconditions.checkNotNull(htmlDocument, "HTML document is not specified!");

		Map<Medical, String> links = new LinkedHashMap<>();

		Elements rows = htmlDocument.select(TABLE_ROW_SELECTOR);

		for (Element row : rows) {
			Element linkElem = getElementFromRow(row, LINK_ROW_SIZE);
			Element nameElem = getElementFromRow(row, NAME_ROW_SIZE);
			Element locationElem = getElementFromRow(row, LOCATION_ROW_SIZE);

			if (linkElem == null || nameElem == null || locationElem == null) {
				continue;
			}

			String link = linkElem.select(ANCHOR_SELECTOR).attr(URL_SELECTOR);

			if (Strings.isNullOrEmpty(link)) {
				continue;
			}

			links.put(new Medical(locationElem.text(), nameElem.text()), link);
		}
		return Collections.unmodifiableMap(links);
	}

	private Element getElementFromRow(Element row, String attrPatrametr) {
		return row.select("td[width=" + attrPatrametr + "%] > p > span").last();
	}
}
