package util;

import domain.DataCrawlerException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfDownloader {
	private static String BASE_PATH;

	static {
		String tempDirPath = FileUtils.getTempDirectoryPath();
		BASE_PATH = tempDirPath + "medicalPdf";

	}

	public static FileInputStream downloadAndGetAsStream(String url) throws DataCrawlerException {
		try {
			File baseDir = new File(BASE_PATH);
			if (!baseDir.exists()) {
				FileUtils.forceMkdir(new File(BASE_PATH));
			}

			String pdfName = FilenameUtils.getBaseName(url) + "." + FilenameUtils.getExtension(url);
			String pathToPdfFile = BASE_PATH + File.separator + pdfName;

			Path path = Paths.get(pathToPdfFile);

			if (Files.notExists(path)) {
				return null;
			}

			File pdfFile = new File(pathToPdfFile);

			URL link = new URL(url);
			FileUtils.copyURLToFile(link, pdfFile);

			return FileUtils.openInputStream(pdfFile);

		} catch (MalformedURLException e) {
			throw new DataCrawlerException("Wrong url: " + url, e);
		} catch (IOException e) {
			throw new DataCrawlerException("Some IO error", e);
		}
	}
}
