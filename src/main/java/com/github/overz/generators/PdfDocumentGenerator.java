package com.github.overz.generators;

import com.github.overz.errors.DocumentGenerationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfDocumentGenerator implements DocumentGenerator {
	private final ITextRenderer renderer = new ITextRenderer();

	@Override
	public String generate(final String content) {
		Assert.notNull(content, "Content for generate document should not be null");
		log.info("Generating base64 pdf");
		try {
//			Generate locally...
//			var os = new FileOutputStream(System.currentTimeMillis() + ".pdf");
			var os = new ByteArrayOutputStream();
			renderer.setDocumentFromString(content.trim());
			renderer.layout();
			renderer.setPDFVersion('7');
			renderer.createPDF(os);
			return Base64.getEncoder().encodeToString(os.toByteArray());
		} catch (Exception e) {
			throw new DocumentGenerationException("Error generating PDF from: \n" + content, e);
		}
	}
}
