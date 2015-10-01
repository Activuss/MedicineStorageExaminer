package service;

import domain.DataCrawlerException;
import domain.Leftover;
import domain.Medical;
import domain.PDFExtractorException;
import org.jsoup.nodes.Document;
import util.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCrawlerBean implements DataCrawler {
    private SiteParser parserService;
    private DataAnalyzer analyzer;

    public DataCrawlerBean(SiteParser parserService, DataAnalyzer analyzer) {
        this.parserService = parserService;
        this.analyzer = analyzer;
    }

    @Override
    public void startCrawling(String siteUrl) throws DataCrawlerException {
        List<Medical> clinics = new ArrayList<>();
        List<String> unParsableLinks = new ArrayList<>();
        try {
            Document htmlPage = HtmlPageDownloader.download(siteUrl);
            Map<Medical, String> medicalPdfLinks = parserService.parse(htmlPage);

            for (Medical medical : medicalPdfLinks.keySet()) {
                String pdfUrl = medicalPdfLinks.get(medical);

                InputStream fileInputStream = PdfDownloader.downloadAndGetAsStream(pdfUrl);

                if (fileInputStream == null) {
                    continue;
                }

                String textFromPDF = TextExtractorFromPdf.extract(fileInputStream);

                Map<String, List<String>> analyzedEntries = analyzer.analyze(textFromPDF);
                List<String> recognisedAids = analyzedEntries.get(DataAnalyzerBean.RECOGNISED_KEY);
                List<String> unrecognisedAids = analyzedEntries.get(DataAnalyzerBean.UNRECOGNISED_KEY);

                medical.setUnrecodnisedEntries(unrecognisedAids);

                if (!recognisedAids.isEmpty()) {
                    for (String recognised : recognisedAids) {
                        Leftover leftover = LeftOverBuilder.build(recognised);
                        medical.getLeftovers().add(leftover);
                    }
                } else {
                    unParsableLinks.add(pdfUrl);
                }
                clinics.add(medical);
                System.out.println(Jsonificator.medicalToJson(medical));
            }
        } catch (IOException e) {
            throw new DataCrawlerException("Some IO error", e);
        } catch (PDFExtractorException e) {
            throw new DataCrawlerException("Error during pdf processing", e);
        }
    }

}
