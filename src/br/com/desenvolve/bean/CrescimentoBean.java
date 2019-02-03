package br.com.desenvolve.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaLiteraturaDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaPessoalDAO;
import br.com.desenvolve.dao.CrescimentoDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.domain.MedidaGrafico;

@ManagedBean(name = "MBCrescimento")
@ViewScoped
public class CrescimentoBean {
	
	private ArrayList<Bovino> bovinos;
	private ArrayList<Bovino> bovinosSelecionados;
	private ArrayList<MedidaGrafico> dadosGrafico;
	private Bovino bovino;
	private LineChartModel graficoPeso;
	private LineChartModel graficoAltura;
	private LineChartModel graficoCirunferencia;
	private String selecionados;
    private ArrayList<ConfiguracaoMedidas> medidasLiterarias;
    private ArrayList<ConfiguracaoMedidas> medidasPessoais;
	

	public ArrayList<ConfiguracaoMedidas> getMedidasLiterarias() {
		return medidasLiterarias;
	}
	public void setMedidasLiterarias(ArrayList<ConfiguracaoMedidas> medidasLiterarias) {
		this.medidasLiterarias = medidasLiterarias;
	}
	public ArrayList<ConfiguracaoMedidas> getMedidasPessoais() {
		return medidasPessoais;
	}
	public void setMedidasPessoais(ArrayList<ConfiguracaoMedidas> medidasPessoais) {
		this.medidasPessoais = medidasPessoais;
	}
	public LineChartModel getGraficoAltura() {
		return graficoAltura;
	}
	public void setGraficoAltura(LineChartModel graficoAltura) {
		this.graficoAltura = graficoAltura;
	}
	public LineChartModel getGraficoCirunferencia() {
		return graficoCirunferencia;
	}
	public void setGraficoCirunferencia(LineChartModel graficoCirunferencia) {
		this.graficoCirunferencia = graficoCirunferencia;
	}
	public LineChartModel getGraficoPeso() {
		return graficoPeso;
	}
	public void setGraficoPeso(LineChartModel graficoPeso) {
		this.graficoPeso = graficoPeso;
	}
	public ArrayList<MedidaGrafico> getDadosGrafico() {
		return dadosGrafico;
	}
	public void setDadosGrafico(ArrayList<MedidaGrafico> dadosGrafico) {
		this.dadosGrafico = dadosGrafico;
	}
	public ArrayList<Bovino> getBovinos() {
		return bovinos;
	}
	public void setBovinos(ArrayList<Bovino> bovinos) {
		this.bovinos = bovinos;
	}
	public ArrayList<Bovino> getBovinosSelecionados() {
		return bovinosSelecionados;
	}
	public void setBovinosSelecionados(ArrayList<Bovino> bovinosSelecionados) {
		this.bovinosSelecionados = bovinosSelecionados;
	}
	public Bovino getBovino() {
		return bovino;
	}
	public void setBovino(Bovino bovino) {
		this.bovino = bovino;
	}
	
	private LineChartModel initGraficoPeso() {
		
		LineChartModel model = new LineChartModel();
		
        LineChartSeries literal = new LineChartSeries();
        literal.setLabel("Peso ideal da literatura");
        
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes(), medidasLiterarias.get(i).getPeso());

		}

		model.addSeries(literal);
		
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new  LineChartSeries();
		produtor.setLabel("Peso ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes(), medidasPessoais.get(i).getPeso());

		}

		model.addSeries(produtor);
		
		LineChartSeries real = new  LineChartSeries();
		real.setLabel("Média de peso real do bezerro");
		for (int i = 0; i < dadosGrafico.size(); i++) {
			real.set(dadosGrafico.get(i).getMes(), dadosGrafico.get(i).getPeso());

		}
				

		model.addSeries(real);

		return model;
	}
	
	private LineChartModel initGraficoCircunferencia() {
		
		LineChartModel model = new LineChartModel();


		
		//serie valores literais

		LineChartSeries literal = new  LineChartSeries();
		literal.setLabel("Circunferência Torácica ideal da literatura");
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes(), medidasLiterarias.get(i).getCircunferencia());

		}

		model.addSeries(literal);
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new  LineChartSeries();
		produtor.setLabel("Circunferência Torácica ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes(), medidasPessoais.get(i).getCircunferencia());

		}

		model.addSeries(produtor);
		
		LineChartSeries real = new  LineChartSeries();
		real.setLabel("Média da circunferência torácica real do bezerro");
		for (int i = 0; i < dadosGrafico.size(); i++) {
			real.set(dadosGrafico.get(i).getMes(), dadosGrafico.get(i).getCircunferencia());

		}
				

		model.addSeries(real);

		return model;
	}
	
	private LineChartModel initGraficoAltura() {
		
		LineChartModel model = new LineChartModel();
		
		LineChartSeries literal = new  LineChartSeries();
		literal.setLabel("Altura ideal da literatura");
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes(), medidasLiterarias.get(i).getAltura());

		}

		model.addSeries(literal);
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new  LineChartSeries();
		produtor.setLabel("Altura ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes(), medidasPessoais.get(i).getAltura());

		}

		model.addSeries(produtor);	
		
		LineChartSeries real = new  LineChartSeries();
		real.setLabel("Média da altura real do bezerro");
		for (int i = 0; i < dadosGrafico.size(); i++) {
			real.set(dadosGrafico.get(i).getMes(), dadosGrafico.get(i).getAltura());

		}
				

		model.addSeries(real);

		return model;
	}	
	
	public void carregarGrafico() throws SQLException{
		BovinoDAO dao = new BovinoDAO();
		bovinos = dao.listar();
		ConfiguracaoMedidaLiteraturaDAO lDao = new ConfiguracaoMedidaLiteraturaDAO();
		medidasLiterarias = lDao.listar();
		ConfiguracaoMedidaPessoalDAO pDao = new ConfiguracaoMedidaPessoalDAO();
		medidasPessoais = pDao.listar();
		criarGraficos();
	}
	
	
	public void criarGraficos(){
		CrescimentoDAO dao = new CrescimentoDAO();
		try {
			dadosGrafico = dao.listarTodos();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
		
		graficoPeso = initGraficoPeso();
		graficoPeso.setLegendPosition("e");
        Axis yAxis4 = graficoPeso.getAxis(AxisType.Y);
        Axis xAxis4 = graficoPeso.getAxis(AxisType.X);
        yAxis4.setLabel("Peso(kg)");
        yAxis4.setMin(0);
        yAxis4.setMax(800);
        xAxis4.setLabel("Mês");
        xAxis4.setMin(0);
        yAxis4.setTickFormat("%.2f");
        
        
        
        graficoAltura = initGraficoAltura();
        graficoAltura.setLegendPosition("e");
        Axis yAxis2 = graficoAltura.getAxis(AxisType.Y);
        Axis xAxis2 = graficoAltura.getAxis(AxisType.X);
        yAxis2.setLabel("Altura(cm)");
        yAxis2.setMin(0);
        yAxis2.setMax(200);
        xAxis2.setLabel("Mês");
        xAxis2.setMin(0);
        yAxis2.setTickFormat("%.2f");
        
        graficoCirunferencia = initGraficoCircunferencia();
        graficoCirunferencia.setLegendPosition("e");
        Axis yAxis3 = graficoCirunferencia.getAxis(AxisType.Y);
        Axis xAxis3 = graficoCirunferencia.getAxis(AxisType.X);
        yAxis3.setLabel("Circunferência Torácica(cm)");
        yAxis3.setMin(0);
        yAxis3.setMax(200);
        xAxis3.setLabel("Mês");
        xAxis3.setMin(0);
        yAxis3.setTickFormat("%.2f");
        
	}
	
	public void gerarPersonalizado(){
		
		selecionados = "";
		
		for (int i = 0; i < bovinosSelecionados.size(); i++) {
			selecionados+=bovinosSelecionados.get(i).getCodigo()+",";
		}
		
		if (selecionados.length() > 0) {
		    selecionados = selecionados.substring (0, selecionados.length() - 1);
		} 
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selec", selecionados);
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect("./graficosPersonalizados.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	

}
