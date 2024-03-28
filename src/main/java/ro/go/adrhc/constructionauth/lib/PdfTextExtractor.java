package ro.go.adrhc.constructionauth.lib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PdfTextExtractor {
    private final URLContentReader urlContentReader;

    public Optional<String> extractText(String url) {
        return urlContentReader
                .readBytes(url)
                .flatMap(this::loadPDF)
                .flatMap(this::extractText);
    }

    public Optional<String> extractText(File pdfFile) {
        return this.loadPDF(pdfFile).flatMap(this::extractText);
    }

    private Optional<String> extractText(PDDocument pdDocument) {
        try {
            return Optional.of(new PDFTextStripper().getText(pdDocument));
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return Optional.empty();
    }

    private Optional<PDDocument> loadPDF(File file) {
        try {
            return Optional.of(Loader.loadPDF(file));
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return Optional.empty();
    }

    private Optional<PDDocument> loadPDF(byte[] bytes) {
        try {
            return Optional.of(Loader.loadPDF(bytes));
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        }
        return Optional.empty();
    }
}
