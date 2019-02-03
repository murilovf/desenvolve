package br.com.desenvolve.bean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.desenvolve.dao.AlimentoDAO;
import br.com.desenvolve.dao.AtividadeReprodutivaDAO;
import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaLiteraturaDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaPessoalDAO;
import br.com.desenvolve.dao.DietaDAO;
import br.com.desenvolve.dao.MedicamentoDAO;
import br.com.desenvolve.dao.MedidaDAO;
import br.com.desenvolve.dao.VacinaDAO;
import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.domain.AtividadeReprodutiva;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.domain.Dieta;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBBovinoIndividual")
@SessionScoped
public class BovinoIndividualBean {
	
	private ArrayList<Vacina> vacinas;
	private ArrayList<Vacina> vacinasFiltradas;
	private Vacina vacina;
	private ArrayList<AtividadeReprodutiva> atividades;
	private ArrayList<AtividadeReprodutiva> atividadesFiltradas;
	private AtividadeReprodutiva atividade;
	private String dataAtividade;
	private ArrayList<Medida> medidas;
	private ArrayList<Medida> medidasFiltradas;
	private Bovino bovino;
	private Double pesoAtual;
	private Double pesoInicial;
    private LineChartModel graficoPeso;
    private LineChartModel graficoAltura;
    private LineChartModel graficoCircunferencia;
    private ArrayList<ConfiguracaoMedidas> medidasLiterarias;
    private ArrayList<ConfiguracaoMedidas> medidasPessoais;
	private ArrayList<Medicamento> medicamentos;
	private ArrayList<Medicamento> medicamentosFiltradas;
	private Medicamento medicamento;
	private String dataVacina;
	private String dataMedicamentoInicial, dataMedicamentoFinal;
	private DefaultDiagramModel model;
	
	//VARIAVEIS QUE FAZEM PARTE DA DIETA
	private ArrayList<Dieta> dietas;
	private ArrayList<Dieta> dietasFiltradas;
	private ArrayList<DietaItem> dietasItem;
	private ArrayList<DietaItem> dietasItemFiltradas;
	private Dieta dieta;
	private DietaItem dietaItem;
	private ArrayList<Alimento> alimentos;
	private ArrayList<Alimento> alimentosFiltrados;
	private Alimento alimento;
	private Double quantidadeAlimento;
	private Date horaAlimento;
	private DietaItem dietaItemSelecionado;
	private Double valorTotalDieta = 0.0;
	
	
	public ArrayList<AtividadeReprodutiva> getAtividades() {
		return atividades;
	}
	public void setAtividades(ArrayList<AtividadeReprodutiva> atividades) {
		this.atividades = atividades;
	}
	public ArrayList<AtividadeReprodutiva> getAtividadesFiltradas() {
		return atividadesFiltradas;
	}
	public void setAtividadesFiltradas(ArrayList<AtividadeReprodutiva> atividadesFiltradas) {
		this.atividadesFiltradas = atividadesFiltradas;
	}
	public AtividadeReprodutiva getAtividade() {
		return atividade;
	}
	public void setAtividade(AtividadeReprodutiva atividade) {
		this.atividade = atividade;
	}
	public String getDataAtividade() {
		return dataAtividade;
	}
	public void setDataAtividade(String dataAtividade) {
		this.dataAtividade = dataAtividade;
	}
	public DefaultDiagramModel getModel() {
		return model;
	}
	public void setModel(DefaultDiagramModel model) {
		this.model = model;
	}
	public String getDataMedicamentoInicial() {
		return dataMedicamentoInicial;
	}
	public void setDataMedicamentoInicial(String dataMedicamentoInicial) {
		this.dataMedicamentoInicial = dataMedicamentoInicial;
	}
	public String getDataMedicamentoFinal() {
		return dataMedicamentoFinal;
	}
	public void setDataMedicamentoFinal(String dataMedicamentoFinal) {
		this.dataMedicamentoFinal = dataMedicamentoFinal;
	}
	public String getDataVacina() {
		return dataVacina;
	}
	public void setDataVacina(String dataVacina) {
		this.dataVacina = dataVacina;
	}
	public ArrayList<Medicamento> getMedicamentos() {
		return medicamentos;
	}
	public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}
	public ArrayList<Medicamento> getMedicamentosFiltradas() {
		return medicamentosFiltradas;
	}
	public void setMedicamentosFiltradas(ArrayList<Medicamento> medicamentosFiltradas) {
		this.medicamentosFiltradas = medicamentosFiltradas;
	}
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
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
	public LineChartModel getGraficoCircunferencia() {
		return graficoCircunferencia;
	}
	public void setGraficoCircunferencia(LineChartModel graficoCircunferencia) {
		this.graficoCircunferencia = graficoCircunferencia;
	}
	public LineChartModel getGraficoPeso() {
		return graficoPeso;
	}
	public void setGraficoPeso(LineChartModel graficoPeso) {
		this.graficoPeso = graficoPeso;
	}
	public ArrayList<Medida> getMedidas() {
		return medidas;
	}
	public void setMedidas(ArrayList<Medida> medidas) {
		this.medidas = medidas;
	}
	public ArrayList<Medida> getMedidasFiltradas() {
		return medidasFiltradas;
	}
	public void setMedidasFiltradas(ArrayList<Medida> medidasFiltradas) {
		this.medidasFiltradas = medidasFiltradas;
	}
	public Bovino getBovino() {
		return bovino;
	}
	public void setBovino(Bovino bovino) {
		this.bovino = bovino;
	}
	public ArrayList<Vacina> getVacinas() {
		return vacinas;
	}
	public void setVacinas(ArrayList<Vacina> vacinas) {
		this.vacinas = vacinas;
	}
	public ArrayList<Vacina> getVacinasFiltradas() {
		return vacinasFiltradas;
	}
	public void setVacinasFiltradas(ArrayList<Vacina> vacinasFiltradas) {
		this.vacinasFiltradas = vacinasFiltradas;
	}
	public Vacina getVacina() {
		return vacina;
	}
	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}
	public ArrayList<Dieta> getDietas() {
		return dietas;
	}
	public void setDietas(ArrayList<Dieta> dietas) {
		this.dietas = dietas;
	}
	public ArrayList<Dieta> getDietasFiltradas() {
		return dietasFiltradas;
	}
	public void setDietasFiltradas(ArrayList<Dieta> dietasFiltradas) {
		this.dietasFiltradas = dietasFiltradas;
	}
	public ArrayList<DietaItem> getDietasItem() {
		return dietasItem;
	}
	public void setDietasItem(ArrayList<DietaItem> dietasItem) {
		this.dietasItem = dietasItem;
	}
	public ArrayList<DietaItem> getDietasItemFiltradas() {
		return dietasItemFiltradas;
	}
	public void setDietasItemFiltradas(ArrayList<DietaItem> dietasItemFiltradas) {
		this.dietasItemFiltradas = dietasItemFiltradas;
	}
	public Dieta getDieta() {
		return dieta;
	}
	public void setDieta(Dieta dieta) {
		this.dieta = dieta;
	}
	public DietaItem getDietaItem() {
		return dietaItem;
	}
	public void setDietaItem(DietaItem dietaItem) {
		this.dietaItem = dietaItem;
	}
	public ArrayList<Alimento> getAlimentos() {
		return alimentos;
	}
	public void setAlimentos(ArrayList<Alimento> alimentos) {
		this.alimentos = alimentos;
	}
	public ArrayList<Alimento> getAlimentosFiltrados() {
		return alimentosFiltrados;
	}
	public void setAlimentosFiltrados(ArrayList<Alimento> alimentosFiltrados) {
		this.alimentosFiltrados = alimentosFiltrados;
	}
	public Alimento getAlimento() {
		return alimento;
	}
	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}
	public Double getQuantidadeAlimento() {
		return quantidadeAlimento;
	}
	public void setQuantidadeAlimento(Double quantidadeAlimento) {
		this.quantidadeAlimento = quantidadeAlimento;
	}
	public DietaItem getDietaItemSelecionado() {
		return dietaItemSelecionado;
	}
	public void setDietaItemSelecionado(DietaItem dietaItemSelecionado) {
		this.dietaItemSelecionado = dietaItemSelecionado;
	}
	public Double getValorTotalDieta() {
		return valorTotalDieta;
	}
	public void setValorTotalDieta(Double valorTotalDieta) {
		this.valorTotalDieta = valorTotalDieta;
	}
	public Date getHoraAlimento() {
		return horaAlimento;
	}
	public void setHoraAlimento(Date horaAlimento) {
		this.horaAlimento = horaAlimento;
	}
	public Double getPesoAtual() {
		return pesoAtual;
	}
	public void setPesoInicial(Double pesoInicial) {
		this.pesoInicial = pesoInicial;
	}
	public Double getPesoInicial() {
		return pesoInicial;
	}
	public void setPesoAtual(Double pesoAtual) {
		this.pesoAtual = pesoAtual;
	}
	
	public void excluirVacinadoBovino(){

		try {
			VacinaDAO dao = new VacinaDAO();
			dao.excluir(vacina);		
			vacinas = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Vacina Removida com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void atualizaEstado(){
		try {
			VacinaDAO dao = new VacinaDAO();
			
			dao.atualizaEstado(vacina);
			
			vacinas = dao.listarIndividual(bovino);
			
			JSFUtil.adicionarMensagemSucesso("Estado da vacina atualizado com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
	public void excluirTodas(){

		try {
			VacinaDAO dao = new VacinaDAO();
			dao.excluirTodasIndividual(bovino);		
			vacinas = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Vacinas Removidas com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void editarVacinadoBovino(){

		try {
			VacinaDAO dao = new VacinaDAO();
			dao.editar(vacina);		
			vacinas = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Vacina Editada com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void prepararNovoVacina(){
		vacina = new Vacina();
		dataVacina = null;
	}
	
	
	public void novo() throws ParseException{
		boolean validaCampos = true;

		if (vacina.getNome() == null || vacina.getNome().equals("")){
			JSFUtil.adicionarMensagemErro("Nome da vacina deve ser informada");
			validaCampos = false;
		}

		if(dataVacina == null || dataVacina.equals("")){
			JSFUtil.adicionarMensagemErro("Data da vacina deve ser informada");
			validaCampos = false;
		}
		if(vacina.getSituacao() == null ){
			JSFUtil.adicionarMensagemErro("Situação da vacina deve ser informada");
			validaCampos = false;
		}
		
		if (validaCampos){
			try {
				VacinaDAO dao = new VacinaDAO();
				vacina.setBovino(bovino);
				vacina.setDatavacina(new SimpleDateFormat("dd/MM/yyyy").parse(dataVacina));
				
				if(vacina.getSituacao() == 1){
					vacina.setDataaplicacao(new Date());
				}
				dao.salvar(vacina);
				vacinas = dao.listarIndividual(bovino);
				JSFUtil.adicionarMensagemSucesso("Vacina Salva com Sucesso");
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}	
		}
	}
	
	//--------------- metodos da atividade reprodutiva
	
	
	public void prepararNovaAtividade(){
		atividade = new AtividadeReprodutiva();
		dataAtividade = null;
	}
	
	public void novaAtividade() throws ParseException{
		try {
			AtividadeReprodutivaDAO dao = new AtividadeReprodutivaDAO();
			atividade.setBovino(bovino);
			atividade.setDataAtividade(new SimpleDateFormat("dd/MM/yyyy").parse(dataAtividade));
			
			DateTime dtToday = new DateTime(atividade.getDataAtividade()); //pega data e hora atual
	        DateTime dtOther = new DateTime(DateTime.parse(bovino.getDatanascimento()+""));
	        
	        Duration dur = new Duration(dtOther, dtToday); //pega a duração da diferença dos dois
	        atividade.setIdade((int)dur.getStandardDays());
			dao.salvar(atividade);
			atividades = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Atividade Reprodutiva Salva com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void excluirAtividade(){

		try {
			AtividadeReprodutivaDAO dao = new AtividadeReprodutivaDAO();
			dao.excluir(atividade);		
			atividades = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Atividade Removida com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void editarAtividade(){

		try {
			AtividadeReprodutivaDAO dao = new AtividadeReprodutivaDAO();
			
			DateTime dtToday = new DateTime(atividade.getDataAtividade()); //pega data e hora atual
	        DateTime dtOther = new DateTime(DateTime.parse(bovino.getDatanascimento()+""));
	        Duration dur = new Duration(dtOther, dtToday); //pega a duração da diferença dos dois
	        atividade.setIdade((int)dur.getStandardDays());
			
			dao.editar(atividade);		
			atividades = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Atividade Reprodutiva Editada com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	
	//--------------------------Metodos do Medicamento
	
	public void prepararNovoMedicamento(){
		medicamento = new Medicamento();
		dataMedicamentoFinal = null;
		dataMedicamentoInicial = null;
	}
	
	public void novoMedicamento() throws ParseException{
		boolean validaCampos = true;

		if (medicamento.getNome() == null || medicamento.getNome().equals("")){
			JSFUtil.adicionarMensagemErro("Nome do medicamento deve ser informado");
			validaCampos = false;
		}

		if(medicamento.getPeso() == null){
			JSFUtil.adicionarMensagemErro("Peso do bovino deve ser informado");
			validaCampos = false;
		}
		
		if(medicamento.getTipo() == null || medicamento.getTipo().equals("")){
			JSFUtil.adicionarMensagemErro("Tipo do medicamento deve ser informado");
			validaCampos = false;
		}
		
		if (medicamento.getDiagnostico() == null || medicamento.getDiagnostico().equals("")){
			JSFUtil.adicionarMensagemErro("Diagnóstico deve ser informado");
			validaCampos = false;
		}
		
		if(medicamento.getDosagem() == null){
			JSFUtil.adicionarMensagemErro("Dosagem deve ser informada");
			validaCampos = false;
		}
		
		if(dataMedicamentoInicial == null || dataMedicamentoInicial.equals("")){
			JSFUtil.adicionarMensagemErro("Data inicial deve ser informada");
			validaCampos = false;
		}
		if(dataMedicamentoFinal == null || dataMedicamentoFinal.equals("")){
			JSFUtil.adicionarMensagemErro("Data final deve ser informada");
			validaCampos = false;
		}
		
		if(medicamento.getHora() == null){
			JSFUtil.adicionarMensagemErro("Horário do medicamento deve ser informada");
			validaCampos = false;
		}
		
		if (validaCampos){
			try {
				MedicamentoDAO dao = new MedicamentoDAO();
				medicamento.setBovino(bovino);
				medicamento.setDatainicio(new SimpleDateFormat("dd/MM/yyyy").parse(dataMedicamentoInicial));
				medicamento.setDatafinal(new SimpleDateFormat("dd/MM/yyyy").parse(dataMedicamentoFinal));
				dao.salvar(medicamento);
				medicamentos = dao.listarIndividual(bovino);
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
				JSFUtil.adicionarMensagemSucesso("Medicamento Salvo com Sucesso");
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}
		}
	}
	
	public void excluirTodosMedicamentos(){

		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			dao.excluirTodosIndividual(bovino);		
			medicamentos = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Medicamentos Removidos com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void excluirMedicamentoBovino(){

		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			dao.excluir(medicamento);		
			medicamentos = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Medicamento Removido com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void editarMedicamentoBovino(){

		boolean validaCampos = true;

		if (medicamento.getNome() == null || medicamento.getNome().equals("")){
			JSFUtil.adicionarMensagemErro("Nome do medicamento deve ser informado");
			validaCampos = false;
		}

		if(medicamento.getPeso() == null){
			JSFUtil.adicionarMensagemErro("Peso do bovino deve ser informado");
			validaCampos = false;
		}
		
		if(medicamento.getTipo() == null || medicamento.getTipo().equals("")){
			JSFUtil.adicionarMensagemErro("Tipo do medicamento deve ser informado");
			validaCampos = false;
		}
		
		if (medicamento.getDiagnostico() == null || medicamento.getDiagnostico().equals("")){
			JSFUtil.adicionarMensagemErro("Diagnóstico deve ser informado");
			validaCampos = false;
		}
		
		if(medicamento.getDosagem() == null){
			JSFUtil.adicionarMensagemErro("Dosagem deve ser informada");
			validaCampos = false;
		}
		
		if(medicamento.getDatainicio() == null){
			JSFUtil.adicionarMensagemErro("Data inicial deve ser informada");
			validaCampos = false;
		}
		if(medicamento.getDatafinal() == null){
			JSFUtil.adicionarMensagemErro("Data final deve ser informada");
			validaCampos = false;
		}
		
		if(medicamento.getHora() == null){
			JSFUtil.adicionarMensagemErro("Horário do medicamento deve ser informada");
			validaCampos = false;
		}
		
		MedicamentoDAO dao = new MedicamentoDAO();
			try {
				if (validaCampos){
					dao.editar(medicamento);		
					medicamentos = dao.listarIndividual(bovino);
					RequestContext request = RequestContext.getCurrentInstance();
					request.addCallbackParam("sucesso", true);
					JSFUtil.adicionarMensagemSucesso("Medicamento Editado com Sucesso");
				}else{
					medicamentos = dao.listarIndividual(bovino);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}			
		
	}
	
	//--------------------------Metodos da Dieta
	
	public void expandeItens(ToggleEvent event) throws SQLException{
		DietaDAO dao = new DietaDAO();
		Dieta d = new Dieta();
		d.setCodigo(((Dieta) event.getData()).getCodigo());
		
		dietasItem = dao.listarItensDieta(d);
	}	
	
	public void prepararNovaDieta(){
		dieta = new Dieta();
		dietaItem = new DietaItem();
		dietaItemSelecionado = new DietaItem();
		alimento = new Alimento();
		dietasItem = new ArrayList<DietaItem>();
		
		try {
			carregaListaAlimentos();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void carregaListaAlimentos() throws SQLException{
		AlimentoDAO dao = new AlimentoDAO();
		alimentos = dao.listar();	
	}
	
	public void adicionaItemDieta(){
		boolean validaCampos = true;

		if (quantidadeAlimento == null){
			JSFUtil.adicionarMensagemErro("Quantidade deve ser informada");
			validaCampos = false;
		}

		if(horaAlimento == null){
			JSFUtil.adicionarMensagemErro("Hora do alimento deve ser informada");
			validaCampos = false;
		}
		
		if(validaCampos){
			dietaItemSelecionado.setAlimento(alimento);
			dietaItemSelecionado.setQuantidade(quantidadeAlimento);
			dietaItemSelecionado.setHora(horaAlimento);
			dietaItemSelecionado.setValor(alimento.getPreco() * quantidadeAlimento);
			dietasItem.add(dietaItemSelecionado);
			valorTotalDieta += dietaItemSelecionado.getValor();
			
			dietaItemSelecionado = new DietaItem();
			alimento = new Alimento();
			quantidadeAlimento= null;
			horaAlimento = null;
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);
		}
	}
	
	public void excluirAlimentoDieta(){
		for (int i = 0; i < dietasItem.size(); i++) {
			if(dietaItem.getAlimento().getCodigo()==dietasItem.get(i).getAlimento().getCodigo()){
				dietasItem.remove(i);
			}
		}
		valorTotalDieta -= dietaItem.getValor();
		dietaItem = new DietaItem();
	}
	
	public void editarAlimentoDieta(){
		valorTotalDieta = 0.0;
		for (int i = 0; i < dietasItem.size(); i++) {
			if(dietaItem.getAlimento().getCodigo()==dietasItem.get(i).getAlimento().getCodigo()){
				dietasItem.get(i).setQuantidade(dietaItem.getQuantidade());
				dietasItem.get(i).setValor(dietasItem.get(i).getAlimento().getPreco() * dietaItem.getQuantidade());
				dietasItem.get(i).setHora(dietaItem.getHora());
			}
			valorTotalDieta += dietasItem.get(i).getValor();
		}
		dietaItem = new DietaItem();
	}
	
	public void salvarDieta(){
		String msg = "";
		try {
			if(validarCamposItemDieta()){
				DietaDAO dao = new DietaDAO();
				System.out.println(dieta);
				if(dieta.getCodigo() != null){
					dao.editarDieta(dieta, dietasItem, bovino, valorTotalDieta);
					msg = "Dieta Alterada com Sucesso";
				}else{
					dao.salvarDieta(dieta, dietasItem, bovino, valorTotalDieta);
					msg = "Dieta Salva com Sucesso";
				}
				
				dieta = new Dieta();
				dietasItem = new ArrayList<DietaItem>();
				valorTotalDieta = 0.0;
				
				DietaDAO dDAO = new DietaDAO();
				dietas = dDAO.listarIndividual(bovino);	
				
				voltar();
				
				JSFUtil.adicionarMensagemSucesso(msg);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void excluirDieta(){
		try {
			DietaDAO dao = new DietaDAO();
			dao.excluir(dieta);
			dietas = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Dieta Removida com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void prepararEditarDieta(){
		DietaDAO dao = new DietaDAO();
		try {
			carregaListaAlimentos();
			alimento = new Alimento();
			dietaItemSelecionado = new DietaItem();
			valorTotalDieta = dieta.getValorTotal();
			dietasItem = dao.listarItensDieta(dieta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect("./individualDietaItem.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean validarCamposItemDieta(){
		
		boolean validar = true;
		
		if(dietasItem.size()<=0){
			JSFUtil.adicionarMensagemErro("Deverá informar pelo menos um item para a dieta.");
			validar = false;
		}
		
		if(dieta.getNome().equals("") || dieta.getNome() == null){
			JSFUtil.adicionarMensagemErro("Nome da dieta deverá ser informado.");
			validar = false;
		}
		
		return validar;
			
	}
	
	public void clicou() throws ParseException{
		
		prepararNovaDieta();

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect("./individualDietaItem.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void voltar() throws ParseException{

		//FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selec", bovinosSelecionados);
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect("./individualDietas.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void excluirTodasDietas(){

		try {
			DietaDAO dao = new DietaDAO();
			dao.excluirTodasIndividual(bovino);		
			dietas = dao.listarIndividual(bovino);
			JSFUtil.adicionarMensagemSucesso("Dietas Removidas com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	//--------------------------Metodos do bovino
	
	
	public void editar(){

		try {
			BovinoDAO dao = new BovinoDAO();
			
			dao.editar(bovino);
			//itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Bovino Editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}

		
	}
	
	//--------------------------Metodos do Inicialização
	
	
	public void carregarCadastro() throws FileNotFoundException, IOException{
		
		try {

			String valor = JSFUtil.getParam("bovCodigo");

			if(valor!=null){
				Long codigo = Long.parseLong(valor);
				BovinoDAO dao = new BovinoDAO();

				Bovino selecionado = new Bovino();
				selecionado.setCodigo(codigo);

				bovino = dao.buscarPorCodigo(selecionado);
				pesoAtual = dao.buscarPeso(selecionado, "DESC");
				pesoInicial = dao.buscarPeso(selecionado, "ASC");
				
				//caso não tenha foto ele gera
				if(bovino.getFoto()==null){

					bovino.setFoto(bovino.getCodigo()+".png");
					
					dao.salvarQR(bovino);
					
					bovino = dao.buscarPorCodigo(selecionado);
					
				}
				
				DietaDAO dDAO = new DietaDAO();
				dietas = dDAO.listarIndividual(selecionado);	
				
				AtividadeReprodutivaDAO adao = new AtividadeReprodutivaDAO();
				atividades = adao.listarIndividual(selecionado);
				dataAtividade = null;
				
				
				
				VacinaDAO vDao = new VacinaDAO();
				vacinas = vDao.listarIndividual(selecionado);
				dataVacina = null;
				
				dataMedicamentoInicial = null;
				dataMedicamentoFinal = null;
				MedicamentoDAO meDao = new MedicamentoDAO();
				medicamentos = meDao.listarIndividual(selecionado);
				MedidaDAO mDao = new MedidaDAO();
				medidas = mDao.listarIndividual(selecionado);
				ConfiguracaoMedidaLiteraturaDAO lDao = new ConfiguracaoMedidaLiteraturaDAO();
				medidasLiterarias = lDao.listar();
				ConfiguracaoMedidaPessoalDAO pDao = new ConfiguracaoMedidaPessoalDAO();
				medidasPessoais = pDao.listar();
				criarGraficos();
				criaDiagrama();
				
			}
		} catch (SQLException ex) {

			JSFUtil.adicionarMensagemErro("Erro ao Tentar Obter os Dados do Bovino: " + ex.getMessage());

		}	

	}
	
    public void criaDiagrama() {
        model = new DefaultDiagramModel();
        model.setMaxConnections(-1);
         
        FlowChartConnector connector = new FlowChartConnector();
        connector.setPaintStyle("{strokeStyle:'#C7B097',lineWidth:3}");
        model.setDefaultConnector(connector);
         

         
        Element itemPai = new Element(bovino.getPai(), "5em", "2em");
        itemPai.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

         
        Element itemMae = new Element(bovino.getMae(), "45em", "2em");
        itemMae.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

         
        Element itemBovino = new Element(bovino.getNome(), "25em", "8em");
        itemBovino.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
        itemBovino.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
        itemBovino.setStyleClass("ui-diagram-fail");
                         
        model.addElement(itemPai);
        model.addElement(itemMae);
        model.addElement(itemBovino);
                 
        model.connect(createConnection(itemPai.getEndPoints().get(0), itemBovino.getEndPoints().get(0), "Pai"));
        model.connect(createConnection(itemMae.getEndPoints().get(0), itemBovino.getEndPoints().get(1), "Mãe"));
    }
     
    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));
         
        if(label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }
         
        return conn;
    }
	
    private void criarGraficos() {
        
		graficoPeso = initGraficoPeso();
		graficoPeso.setLegendPosition("e");
        Axis yAxis = graficoPeso.getAxis(AxisType.Y);
        Axis xAxis = graficoPeso.getAxis(AxisType.X);
        yAxis.setLabel("Peso(kg)");
        yAxis.setMin(0);
        yAxis.setMax(800);
        xAxis.setLabel("Dias");
        xAxis.setMin(0);
        yAxis.setTickFormat("%.2f");
        
        graficoAltura = initGraficoAltura();
        graficoAltura.setLegendPosition("e");
        Axis yAxis2 = graficoAltura.getAxis(AxisType.Y);
        Axis xAxis2 = graficoAltura.getAxis(AxisType.X);
        yAxis2.setLabel("Altura(cm)");
        yAxis2.setMin(0);
        yAxis2.setMax(200);
        xAxis2.setLabel("Dias");
        xAxis2.setMin(0);
        yAxis2.setTickFormat("%.2f");
        
        graficoCircunferencia = initGraficoCircunferencia();
        graficoCircunferencia.setLegendPosition("e");
        Axis yAxis3 = graficoCircunferencia.getAxis(AxisType.Y);
        Axis xAxis3 = graficoCircunferencia.getAxis(AxisType.X);
        yAxis3.setLabel("Circunferência Torácica(cm)");
        yAxis3.setMin(0);
        yAxis3.setMax(200);
        xAxis3.setLabel("Dias");
        xAxis3.setMin(0);
        yAxis3.setTickFormat("%.2f");
    }
    
	private LineChartModel initGraficoPeso() {
		LineChartModel model = new LineChartModel();

		//serie valores literais

		LineChartSeries literal = new LineChartSeries();
		literal.setLabel("Peso ideal da literatura");
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes()*30, medidasLiterarias.get(i).getPeso());

		}

		model.addSeries(literal);
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new LineChartSeries();
		produtor.setLabel("Peso ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes()*30, medidasPessoais.get(i).getPeso());

		}

		model.addSeries(produtor);	
		
		LineChartSeries real = new LineChartSeries();
		real.setLabel("Peso real do bezerro");
		for (int i = 0; i < medidas.size(); i++) {

			real.set(medidas.get(i).getDias(), medidas.get(i).getPeso());

		}

		model.addSeries(real);
		

		return model;
	}
	
	private LineChartModel initGraficoAltura() {
		LineChartModel model = new LineChartModel();

		
		LineChartSeries literal = new LineChartSeries();
		literal.setLabel("Altura ideal da literatura");
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes()*30, medidasLiterarias.get(i).getAltura());

		}

		model.addSeries(literal);
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new LineChartSeries();
		produtor.setLabel("Altura ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes()*30, medidasPessoais.get(i).getAltura());

		}

		model.addSeries(produtor);	
		
		LineChartSeries real = new LineChartSeries();
		real.setLabel("Altura real do bezerro");
		for (int i = 0; i < medidas.size(); i++) {

			real.set(medidas.get(i).getDias(), medidas.get(i).getAltura());

		}

		model.addSeries(real);
		

		return model;


	}
	
	private LineChartModel initGraficoCircunferencia() {
		LineChartModel model = new LineChartModel();

		
		//serie valores literais

		LineChartSeries literal = new LineChartSeries();
		literal.setLabel("Circunferência Torácica ideal da literatura");
		for (int i = 0; i < medidasLiterarias.size(); i++) {

			literal.set(medidasLiterarias.get(i).getMes()*30, medidasLiterarias.get(i).getCircunferencia());

		}

		model.addSeries(literal);
		
		//serie valores ideais do produtor

		LineChartSeries produtor = new LineChartSeries();
		produtor.setLabel("Circunferência Torácica ideal do produtor");
		for (int i = 0; i < medidasPessoais.size(); i++) {

			produtor.set(medidasPessoais.get(i).getMes()*30, medidasPessoais.get(i).getCircunferencia());

		}

		model.addSeries(produtor);	
		
		LineChartSeries real = new LineChartSeries();
		real.setLabel("Circunferêcia Torácica real do bezerro");
		for (int i = 0; i < medidas.size(); i++) {

			real.set(medidas.get(i).getDias(), medidas.get(i).getCircunferencia());

		}

		model.addSeries(real);
		

		return model;
	}	
	

}
