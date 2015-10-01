package domain;

@SuppressWarnings("serial")
public class DataCrawlerException extends Exception {

	public DataCrawlerException(String customMessage, Exception customException) {
		super(customMessage, customException);
	}
}

