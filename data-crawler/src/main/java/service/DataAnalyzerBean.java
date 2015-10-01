package service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class analyse provided text and extract data from table, if it found it.
 * Creates two lists of recognised and unrecognised table entries.
 */
public final class DataAnalyzerBean implements DataAnalyzer {

    public static final String RECOGNISED_KEY = "recognised";
    public static final String UNRECOGNISED_KEY = "unrecognised";
    public static final String REGEX_INCOMPLETE_ENTRY = "(\\s*)(\\d+)(\\W?)(\\s+)(.+)";
    public static final String REGEX_EOL = "\r\n";
    public static final String REGEX_PROPER_FIRST_ENTRY_PART = "(\\d+)(.+)(\\s+)";
    public static final String REGEX_PROPER_LAST_ENTRY_PART = "(\\W?)(\\s+)(.*)(\\d+)(\\s*)";

    private final String[] tableHeadRegex = {"(\\s*) (.*) [ ](.*) (.*)", "(\\s*) (.*) [ ](.*) (.*)"};
    private final String[] measures = {"амп", "шт", "фл", "табл", "уп", "кг", "пари"};

    public  Map<String, List<String>> analyze(String textFromPDF) {
        List<String> recognisedEntries = new ArrayList<>();
        List<String> unRecognisedEntries = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(textFromPDF, REGEX_EOL);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (isHead(token)) {
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (isTableLine(token)) {
                        recognisedEntries.add(token);
                    } else {
                        if (token.matches(REGEX_INCOMPLETE_ENTRY)) {
                            unRecognisedEntries.add(token);
                        }
                    }
                }
            }
        }

        Map<String, List<String>> analyzedData = new HashMap<>();
        analyzedData.put(RECOGNISED_KEY, recognisedEntries);
        analyzedData.put(UNRECOGNISED_KEY, unRecognisedEntries);
        return Collections.unmodifiableMap(analyzedData);
    }

    private boolean isHead(String token) {
        for (String aTableHeadRegex : tableHeadRegex) {
            Pattern pattern = Pattern.compile(aTableHeadRegex);
            Matcher matcher = pattern.matcher(token);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    private boolean isTableLine(String token) {
        for (String measure : measures) {
            if (token.matches(REGEX_PROPER_FIRST_ENTRY_PART + measure + REGEX_PROPER_LAST_ENTRY_PART)) {
                return true;
            }
        }
        return false;
    }
}
