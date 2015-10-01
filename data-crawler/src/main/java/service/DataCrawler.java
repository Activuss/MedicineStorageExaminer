package service;

import domain.DataCrawlerException;

public interface DataCrawler {
	void startCrawling(String siteUrl) throws DataCrawlerException;
}
