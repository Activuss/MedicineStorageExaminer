package service;

import domain.Medical;
import org.jsoup.nodes.Document;

import java.util.Map;

public interface SiteParser {
	Map<Medical, String> parse(Document htmlDocument);
}
