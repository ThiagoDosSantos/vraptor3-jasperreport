package br.com.caelum.vraptor.jasperreports.formats;

import br.com.caelum.vraptor.ioc.Component;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.type.HtmlSizeUnitEnum;

/**
 * Exportador de relatórios para XHTML compatível com JasperReports 6.21.4
 * Mantendo a assinatura do método setup() retornando JRExporter
 * 
 * @author William Pivotto
 */
@Component
public class Xhtml extends AbstractExporter {

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String getExtension() {
        return "xhtml";
    }

    @Override
    public JRExporter setup() {
        HtmlExporter exporter = new HtmlExporter();

        // Configuração do relatório XHTML
        SimpleHtmlReportConfiguration reportConfig = new SimpleHtmlReportConfiguration();
        reportConfig.setWhitePageBackground(false);
        reportConfig.setRemoveEmptySpaceBetweenRows(true);
        reportConfig.setSizeUnit(HtmlSizeUnitEnum.POINT); // Define unidade de tamanho

        // Configuração da exportação
        SimpleHtmlExporterConfiguration exportConfig = new SimpleHtmlExporterConfiguration();
        exportConfig.setBetweenPagesHtml("<br/>"); // Insere espaçamento entre páginas

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        return exporter;
    }
}
