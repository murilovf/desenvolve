package br.com.desenvolve.util;

import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.domain.Medicamento;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class RelatorioUtil
{
	public RelatorioUtil() {}

	public static void gerarRelatorioBovino(ArrayList<Bovino> lista, String subtitulo){
		try{
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("subtitulo", subtitulo);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			HttpServletResponse response = (HttpServletResponse)ec.getResponse();

			ec.responseReset();
			ec.setResponseContentType("application/pdf");

			InputStream arq = ec.getResourceAsStream("/resources/reports/relatorioBovino.jrxml");

			JasperReport report = JasperCompileManager.compileReport(arq);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));

			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			exporter.exportReport();

			FacesContext.getCurrentInstance().responseComplete();

		}catch (JRException e){

			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void gerarRelatorioVacinasPorBovino(ArrayList<br.com.desenvolve.domain.Vacina> lista, String subtitulo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("subtitulo", subtitulo);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			HttpServletResponse response = (HttpServletResponse)ec.getResponse();

			ec.responseReset();
			ec.setResponseContentType("application/pdf");

			InputStream arq = ec.getResourceAsStream("/resources/reports/relatorioVacinasPorBovino.jrxml");

			JasperReport report = JasperCompileManager.compileReport(arq);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			exporter.exportReport();

			FacesContext.getCurrentInstance().responseComplete();
		} catch (JRException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void gerarRelatorioMedidas(ArrayList<br.com.desenvolve.domain.Medida> lista, String subtitulo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("subtitulo", subtitulo);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			HttpServletResponse response = (HttpServletResponse)ec.getResponse();

			ec.responseReset();
			ec.setResponseContentType("application/pdf");

			InputStream arq = ec.getResourceAsStream("/resources/reports/relatorioMedidas.jrxml");

			JasperReport report = JasperCompileManager.compileReport(arq);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			exporter.exportReport();

			FacesContext.getCurrentInstance().responseComplete();
		}catch (JRException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void gerarRelatorioMedicamentosPorBovino(ArrayList<Medicamento> lista, String subtitulo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("subtitulo", subtitulo);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			HttpServletResponse response = (HttpServletResponse)ec.getResponse();

			ec.responseReset();
			ec.setResponseContentType("application/pdf");

			InputStream arq = ec.getResourceAsStream("/resources/reports/relatorioMedicamentosPorBovino.jrxml");

			JasperReport report = JasperCompileManager.compileReport(arq);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			exporter.exportReport();

			FacesContext.getCurrentInstance().responseComplete();
		}catch (JRException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void gerarRelatorioDietasPorBovino(ArrayList<DietaItem> lista, String subtitulo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("subtitulo", subtitulo);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			HttpServletResponse response = (HttpServletResponse)ec.getResponse();

			ec.responseReset();
			ec.setResponseContentType("application/pdf");

			InputStream arq = ec.getResourceAsStream("/resources/reports/relatorioDietasPorBovino.jrxml");

			JasperReport report = JasperCompileManager.compileReport(arq);

			JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(lista));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));

			exporter.exportReport();

			FacesContext.getCurrentInstance().responseComplete();
		}catch (JRException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
