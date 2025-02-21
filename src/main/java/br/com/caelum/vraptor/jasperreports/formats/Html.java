package br.com.caelum.vraptor.jasperreports.formats;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.jasperreports.ReportPathResolver;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;

/**
 * Exportador de relatórios para HTML compatível com JasperReports 6.21.4
 * Mantendo a assinatura do método setup() retornando JRExporter
 * 
 * @author William Pivotto
 */
@Component
public class Html extends AbstractExporter {

    private final ReportPathResolver resolver;

    public Html(ReportPathResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getExtension() {
        return "html";
    }

    @Override
    public JRExporter setup() {
        HtmlExporter exporter = new HtmlExporter();

        // Configuração do relatório HTML
        SimpleHtmlReportConfiguration reportConfig = new SimpleHtmlReportConfiguration();
        reportConfig.setWhitePageBackground(false);
        reportConfig.setRemoveEmptySpaceBetweenRows(true);

        // Configuração do exportador
        SimpleHtmlExporterConfiguration exportConfig = new SimpleHtmlExporterConfiguration();
        exportConfig.setBetweenPagesHtml("<br/>");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        return exporter;
    }
}
