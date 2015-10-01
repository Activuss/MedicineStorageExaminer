package client;

import domain.DataCrawlerException;
import service.*;

public class Main {
	private final static String SITE_ADDRESS = "http://liky.odessa.ua";

	public static void main(String[] args) {
		DataCrawler dataCrawler = new DataCrawlerBean(new SiteParserBean(), new DataAnalyzerBean(),
				new MongoPersistenceService());
		try {
			dataCrawler.startCrawling(SITE_ADDRESS);
		} catch (DataCrawlerException e) {
			System.err.println("Program finished with error: " + e.getCause());
			System.exit(1);
		}
	}
}
