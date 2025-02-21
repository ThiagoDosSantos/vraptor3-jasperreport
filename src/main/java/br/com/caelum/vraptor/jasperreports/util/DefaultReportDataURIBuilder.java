package br.com.caelum.vraptor.jasperreports.util;

import java.util.Base64;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.jasperreports.Report;
import br.com.caelum.vraptor.jasperreports.ReportFormatResolver;
import br.com.caelum.vraptor.jasperreports.exporter.ReportExporter;
import br.com.caelum.vraptor.jasperreports.formats.ExportFormat;

@Component
public class DefaultReportDataURIBuilder implements ReportDataURIBuilder {

	private final ReportExporter exporter;
	private final ReportFormatResolver resolver;

	public DefaultReportDataURIBuilder(ReportExporter exporter,
			ReportFormatResolver resolver) {
		this.exporter = exporter;
		this.resolver = resolver;
	}

	/**
	 * The data URIs have the following syntax:
	 * data:[<mimetype>][;charset][;base64],<data>
	 */
	public String build(Report report, ExportFormat format) {
		String charset = extractCharset(format).toLowerCase();
		byte[] content = exporter.export(report).to(format);
		StringBuilder URI = new StringBuilder("data:");
		URI.append(format.getContentType());
		URI.append(";charset=").append(charset);
		URI.append(";base64,").append(Base64.getEncoder().encodeToString(content));
		return URI.toString();
	}

	public String build(Report report) {
		return build(report, resolver.getExportFormat());
	}

	/**
     * Define um charset padrão UTF-8 para os relatórios exportados.
     */
    private String extractCharset(ExportFormat format) {
        // JasperReports 6.21.4 não usa mais JRExporterParameter
        // Portanto, assumimos UTF-8 como padrão
        return "UTF-8";
    }

}
