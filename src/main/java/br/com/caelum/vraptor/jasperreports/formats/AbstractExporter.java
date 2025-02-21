package br.com.caelum.vraptor.jasperreports.formats;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.io.Flushables;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;

public abstract class AbstractExporter implements ExportFormat {
	
	protected Map<JRExporterParameter, Object> parameters = Maps.newHashMap();
	
	public AbstractExporter(){
		defaultParameters();
	}
	
	public ExportFormat configure(JRExporterParameter parameter, Object value) {
		parameters.put(parameter, value);
		return this;
	}
	
	public Map<JRExporterParameter, Object> getParameters(){
		return this.parameters;
	}
	
	protected void defaultParameters(){
		configure(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
	}
	
	protected abstract JRExporter setup();
	
	public boolean supportsBatchMode() {
		return true;
	}
	
	public byte[] toByteArray(List<JasperPrint> print) {
			
		try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			
			JRExporter exporter = setup();
			exporter.setParameters(getParameters());
			
			if (print.size() > 1)
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, print);
			else
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print.get(0));
			
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
			exporter.exportReport();
			Flushables.flushQuietly(output);
			return output.toByteArray();  
			
		} catch (JRException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {			
			parameters.clear();
		}
	}
	
}
