package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.primefaces.context.RequestContext;

import br.com.desenvolve.dao.AlertaDAO;
import br.com.desenvolve.dao.AlertaVlLiterarioDAO;
import br.com.desenvolve.dao.AlertaVlPessoalDAO;
import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaLiteraturaDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaPessoalDAO;
import br.com.desenvolve.dao.MedidaDAO;
import br.com.desenvolve.domain.Alerta;
import br.com.desenvolve.domain.AlertaErro;
import br.com.desenvolve.domain.AlertaVl;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBMedida")
@ViewScoped
public class MedidaBean {
	
	private ArrayList<Bovino> itensBovino;
	private ArrayList<Bovino> itensBovinoFiltrados;
	private ArrayList<Medida> itensMedida;
	private ArrayList<Medida> itensMedidaFiltrados;
	private AlertaVl valoresAlertaLiterario;
	private AlertaVl valoresAlertaPessoal;
	private Bovino bovino;
	private Medida medida;
	private ConfiguracaoMedidas medidaLiteraria;
	private ConfiguracaoMedidas medidaPessoal;
	private ArrayList<AlertaErro> listadeerros;
	private Long codigo;
	private boolean checadoNascimento;
	
	
	public boolean isChecadoNascimento() {
		return checadoNascimento;
	}
	public void setChecadoNascimento(boolean checadoNascimento) {
		this.checadoNascimento = checadoNascimento;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public AlertaVl getValoresAlertaPessoal() {
		return valoresAlertaPessoal;
	}
	public void setValoresAlertaPessoal(AlertaVl valoresAlertaPessoal) {
		this.valoresAlertaPessoal = valoresAlertaPessoal;
	}
	public ArrayList<AlertaErro> getListadeerros() {
		return listadeerros;
	}
	public void setListadeerros(ArrayList<AlertaErro> listadeerros) {
		this.listadeerros = listadeerros;
	}
	public ConfiguracaoMedidas getMedidaLiteraria() {
		return medidaLiteraria;
	}
	public void setMedidaLiteraria(ConfiguracaoMedidas medidaLiteraria) {
		this.medidaLiteraria = medidaLiteraria;
	}
	public ConfiguracaoMedidas getMedidaPessoal() {
		return medidaPessoal;
	}
	public void setMedidaPessoal(ConfiguracaoMedidas medidaPessoal) {
		this.medidaPessoal = medidaPessoal;
	}
	public AlertaVl getValoresAlertaLiterario() {
		return valoresAlertaLiterario;
	}
	public void setValoresAlertaLiterario(AlertaVl valoresAlertaLiterario) {
		this.valoresAlertaLiterario = valoresAlertaLiterario;
	}
	public Medida getMedida() {
		return medida;
	}
	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public ArrayList<Bovino> getItensBovino() {
		return itensBovino;
	}
	public void setItensBovino(ArrayList<Bovino> itensBovino) {
		this.itensBovino = itensBovino;
	}

	public ArrayList<Bovino> getItensBovinoFiltrados() {
		return itensBovinoFiltrados;
	}
	public void setItensBovinoFiltrados(ArrayList<Bovino> itensBovinoFiltrados) {
		this.itensBovinoFiltrados = itensBovinoFiltrados;
	}
	public Bovino getBovino() {
		return bovino;
	}
	public void setBovino(Bovino bovino) {
		this.bovino = bovino;
	}

	public ArrayList<Medida> getItensMedida() {
		return itensMedida;
	}
	public void setItensMedida(ArrayList<Medida> itensMedida) {
		this.itensMedida = itensMedida;
	}
	public ArrayList<Medida> getItensMedidaFiltrados() {
		return itensMedidaFiltrados;
	}
	public void setItensMedidaFiltrados(ArrayList<Medida> itensMedidaFiltrados) {
		this.itensMedidaFiltrados = itensMedidaFiltrados;
	}
	
	@PostConstruct
	public void prepararPesquisa(){

		try {
			medida = new Medida();
			BovinoDAO dao = new BovinoDAO();		
			itensBovino = dao.listarAtivos();
			MedidaDAO daom = new MedidaDAO();
			itensMedida = daom.listar();
			checadoNascimento = false;
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void excluir() {

		try {
			apagaAlertas();
			MedidaDAO dao = new MedidaDAO();
			dao.excluir(medida);
			itensMedida = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Medida Removida com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
		medida = new Medida();
	}
	
	public void editar(){
		boolean validaCampos = true;

		if (medida.getPeso() == null){
			JSFUtil.adicionarMensagemErro("Valor do peso deve ser informado");
			validaCampos = false;
		}

		if (medida.getAltura() == null){
			JSFUtil.adicionarMensagemErro("Valor da altura deve ser informada");
			validaCampos = false;
		}

		if (medida.getCircunferencia() == null){
			JSFUtil.adicionarMensagemErro("Valor da circunferência torácica deve ser informada");
			validaCampos = false;
		}
		
			try {
				if(validaCampos){
					MedidaDAO dao = new MedidaDAO();
					medida.setAlteracao(new Date());
					dao.editar(medida);
					itensMedida = dao.listar();
					AlertaVlLiterarioDAO daoA = new AlertaVlLiterarioDAO();
					valoresAlertaLiterario = daoA.listar();
					AlertaVlPessoalDAO daoPe = new AlertaVlPessoalDAO();
					valoresAlertaPessoal = daoPe.listar();
					ConfiguracaoMedidaLiteraturaDAO daoL = new ConfiguracaoMedidaLiteraturaDAO();
					medidaLiteraria = daoL.buscarMedidaLiteratura(medida.getMes());
					ConfiguracaoMedidaPessoalDAO daoP = new ConfiguracaoMedidaPessoalDAO();
					medidaPessoal = daoP.buscarMedidaPessoal(medida.getMes());
					listadeerros = new ArrayList<>();
					apagaAlertas();
					verificaAlertaEditar();
					JSFUtil.adicionarMensagemSucesso("Medida editada com Sucesso");
					RequestContext request = RequestContext.getCurrentInstance();
					request.addCallbackParam("sucesso", true);
					listadeerros.clear();
					medida = new Medida();
					medidaPessoal = new ConfiguracaoMedidas();
					medidaLiteraria = new ConfiguracaoMedidas();					
				}else{
					MedidaDAO dao = new MedidaDAO();
					itensMedida = dao.listar();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}
			
			
		
	}
	
	public void prepararNovo(){

		medida = new Medida();
		
	}
	
	public void apagaAlertas() throws SQLException{
		AlertaDAO dao = new AlertaDAO();
		Alerta alerta = dao.acharAlertaMedida(medida);
		if(alerta != null){
			dao.excluir(alerta);
		}
	}
	
	
	public void novo() throws Exception{
		boolean validaCampos = true;
		
		if (medida.getPeso() == null){
			JSFUtil.adicionarMensagemErro("Valor do peso deve ser informado");
			validaCampos = false;
		}
		
		if (medida.getAltura() == null){
			JSFUtil.adicionarMensagemErro("Valor da altura deve ser informada");
			validaCampos = false;
		}
		
		if (medida.getCircunferencia() == null){
			JSFUtil.adicionarMensagemErro("Valor da circunferência torácica deve ser informada");
			validaCampos = false;
		}
		
		if(validaCampos){
			try {
				
				if(checadoNascimento){
					
					medida.setMes(0);
					medida.setDias(1);
					medida.setDatamedicao(bovino.getDatanascimento());
					
				}else{
					
					DateTime dtToday = new DateTime(); //pega data e hora atual
					DateTime dtOther = new DateTime(DateTime.parse(bovino.getDatanascimento()+""));
					Duration dur = new Duration(dtOther, dtToday); //pega a duração da diferença dos dois
					medida.setDias((int)dur.getStandardDays());
					descobreMes();
					medida.setDatamedicao(new Date());
					
				}
				
				medida.setBovino(bovino);
				
				MedidaDAO dao = new MedidaDAO();
				codigo = dao.criar(medida);
				
				itensMedida = dao.listar();
				AlertaVlLiterarioDAO daoA = new AlertaVlLiterarioDAO();
				valoresAlertaLiterario = daoA.listar();
				AlertaVlPessoalDAO daoPe = new AlertaVlPessoalDAO();
				valoresAlertaPessoal = daoPe.listar();
				ConfiguracaoMedidaLiteraturaDAO daoL = new ConfiguracaoMedidaLiteraturaDAO();
				medidaLiteraria = daoL.buscarMedidaLiteratura(medida.getMes());
				ConfiguracaoMedidaPessoalDAO daoP = new ConfiguracaoMedidaPessoalDAO();
				medidaPessoal = daoP.buscarMedidaPessoal(medida.getMes());
				
				listadeerros = new ArrayList<>();
				verificaAlerta(); // verifica os valores do mes que foi inserido
				JSFUtil.adicionarMensagemSucesso("Medida Salva com Sucesso");
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}
			medida = new Medida();
			listadeerros.clear();
			medidaLiteraria = new ConfiguracaoMedidas();
			medidaPessoal = new ConfiguracaoMedidas();
			
		}
 
	}
	
	public void descobreMes(){
		
		if (medida.getDias()<=30) {
			medida.setMes(1);
		}
		if (medida.getDias()>30 && medida.getDias()<=60) {
			medida.setMes(2);
		}
		if (medida.getDias()>60 && medida.getDias()<=90) {
			medida.setMes(3);
		}
		if (medida.getDias()>90 && medida.getDias()<=120) {
			medida.setMes(4);
		}
		if (medida.getDias()>120 && medida.getDias()<=150) {
			medida.setMes(5);
		}
		if (medida.getDias()>150 && medida.getDias()<=180) {
			medida.setMes(6);
		}
		if (medida.getDias()>180 && medida.getDias()<=210) {
			medida.setMes(7);
		}
		if (medida.getDias()>210 && medida.getDias()<=240) {
			medida.setMes(8);
		}
		if (medida.getDias()>240 && medida.getDias()<=270) {
			medida.setMes(9);
		}
		if (medida.getDias()>270 && medida.getDias()<=300) {
			medida.setMes(10);
		}
		if (medida.getDias()>300 && medida.getDias()<=330) {
			medida.setMes(11);
		}
		if (medida.getDias()>330 && medida.getDias()<=360) {
			medida.setMes(12);
		}
		if (medida.getDias()>360 && medida.getDias()<=390) {
			medida.setMes(13);
		}
		if (medida.getDias()>390 && medida.getDias()<=420) {
			medida.setMes(14);
		}
		if (medida.getDias()>420 && medida.getDias()<=450) {
			medida.setMes(15);
		}
		if (medida.getDias()>450 && medida.getDias()<=480) {
			medida.setMes(16);
		}
		if (medida.getDias()>360 && medida.getDias()<=390) {
			medida.setMes(13);
		}
		if (medida.getDias()>390 && medida.getDias()<=420) {
			medida.setMes(14);
		}
		if (medida.getDias()>420 && medida.getDias()<=450) {
			medida.setMes(15);
		}
		if (medida.getDias()>450 && medida.getDias()<=480) {
			medida.setMes(16);
		}
		if (medida.getDias()>360 && medida.getDias()<=390) {
			medida.setMes(13);
		}
		if (medida.getDias()>390 && medida.getDias()<=420) {
			medida.setMes(14);
		}
		if (medida.getDias()>420 && medida.getDias()<=450) {
			medida.setMes(15);
		}
		if (medida.getDias()>450 && medida.getDias()<=480) {
			medida.setMes(16);
		}
		if (medida.getDias()>480 && medida.getDias()<=510) {
			medida.setMes(17);
		}
		if (medida.getDias()>510 && medida.getDias()<=540) {
			medida.setMes(18);
		}
		if (medida.getDias()>540 && medida.getDias()<=570) {
			medida.setMes(19);
		}
		if (medida.getDias()>600 && medida.getDias()<=630) {
			medida.setMes(20);
		}
		if (medida.getDias()>630 && medida.getDias()<=660) {
			medida.setMes(21);
		}
		if (medida.getDias()>660 && medida.getDias()<=690) {
			medida.setMes(22);
		}
		if (medida.getDias()>690 && medida.getDias()<=720) {
			medida.setMes(23);
		}
		if (medida.getDias()>720 && medida.getDias()<=750) {
			medida.setMes(24);
		}
		if (medida.getDias()>750 && medida.getDias()<=780) {
			medida.setMes(25);
		}
		if (medida.getDias()>780 && medida.getDias()<=810) {
			medida.setMes(26);
		}
		if (medida.getDias()>810 && medida.getDias()<=840) {
			medida.setMes(27);
		}
		if (medida.getDias()>840 && medida.getDias()<=870) {
			medida.setMes(28);
		}
		if (medida.getDias()>870 && medida.getDias()<=900) {
			medida.setMes(29);
		}
		if (medida.getDias()>900 && medida.getDias()<=930) {
			medida.setMes(30);
		}
		if (medida.getDias()>930 && medida.getDias()<=960) {
			medida.setMes(31);
		}
		if (medida.getDias()>960 && medida.getDias()<=990) {
			medida.setMes(31);
		}
	}
	
	public void verificaAlertaEditar(){
		
		int contadorLiterario = 0;
		int contadorPessoal = 0;
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		if(valoresAlertaLiterario.getAtivo() == 1){
		
			String sinalLiterarioPeso = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioPeso = "+";
			}else{
				sinalLiterarioPeso = "-";
			}
			
			double diferencaLiterariaPeso = Math.abs(medida.getPeso()-medidaLiteraria.getPeso());
			Double valorLiterarioPeso=Double.parseDouble(df.format(diferencaLiterariaPeso).replaceAll(",", "."));
			
			String sinalLiterarioAltura = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioAltura = "+";
			}else{
				sinalLiterarioAltura = "-";
			}
			
			double diferencaLiterariaAltura = Math.abs(medida.getAltura()-medidaLiteraria.getAltura());
			Double valorLiterarioAltura=Double.parseDouble(df.format(diferencaLiterariaAltura).replaceAll(",", "."));
			
			String sinalLiterarioCircunferencia = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioCircunferencia = "+";
			}else{
				sinalLiterarioCircunferencia = "-";
			}
			
			double diferencaLiterariaCircunferencia = Math.abs(medida.getCircunferencia()-medidaLiteraria.getCircunferencia());
			Double valorLiterarioCircunferencia=Double.parseDouble(df.format(diferencaLiterariaCircunferencia).replaceAll(",", "."));
			
			if(valorLiterarioPeso > valoresAlertaLiterario.getPeso() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro lp = new AlertaErro();
				lp.setCategoria("Literário");
				lp.setDescricao("O peso possui uma diferença de "+sinalLiterarioPeso+valorLiterarioPeso+" do valor configurado");
				listadeerros.add(lp);
			}
			if(valorLiterarioAltura > valoresAlertaLiterario.getAltura() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro la = new AlertaErro();
				la.setCategoria("Literário");
				la.setDescricao("A altura possui uma diferença de "+sinalLiterarioAltura+valorLiterarioAltura+" do valor configurado");
				listadeerros.add(la);
			}
			if(valorLiterarioCircunferencia > valoresAlertaLiterario.getCircunferencia() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro lc = new AlertaErro();
				lc.setCategoria("Literário");
				lc.setDescricao("A circunferência Torácica possui uma diferença de "+sinalLiterarioCircunferencia+valorLiterarioCircunferencia+" do valor configurado");
				listadeerros.add(lc);
			}
		
		}
		
		//----Pessoal-------------------------------------------------------------
		
		if(valoresAlertaPessoal.getAtivo() == 1){
		
			String sinalPessoalPeso = "";
			
			if((medida.getPeso()-medidaPessoal.getPeso()) > 0){
				sinalPessoalPeso = "+";
			}else{
				sinalPessoalPeso = "-";
			}
			
			double diferencaPessoalPeso = Math.abs(medida.getPeso()-medidaPessoal.getPeso());
			Double valorPessoalPeso=Double.parseDouble(df.format(diferencaPessoalPeso).replaceAll(",", "."));
			
			String sinalPessoalAltura = "";
			
			if((medida.getAltura()-medidaPessoal.getAltura()) > 0){
				sinalPessoalAltura = "+";
			}else{
				sinalPessoalAltura = "-";
			}
			
			double diferencaPessoalAltura = Math.abs(medida.getAltura()-medidaPessoal.getAltura());
			Double valorPessoalAltura=Double.parseDouble(df.format(diferencaPessoalAltura).replaceAll(",", "."));
			
			String sinalPessoalCircunferencia = "";
			
			if((medida.getCircunferencia()-medidaPessoal.getCircunferencia()) > 0){
				sinalPessoalCircunferencia = "+";
			}else{
				sinalPessoalCircunferencia = "-";
			}
			
			double diferencaPessoalCircunferencia = Math.abs(medida.getCircunferencia()-medidaPessoal.getCircunferencia());
			Double valorPessoalCircunferencia=Double.parseDouble(df.format(diferencaPessoalCircunferencia).replaceAll(",", "."));
					
			if(valorPessoalPeso > valoresAlertaPessoal.getPeso() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pp = new AlertaErro();
				pp.setCategoria("Pessoal");
				pp.setDescricao("O peso possui uma diferença de "+sinalPessoalPeso+valorPessoalPeso+" do valor configurado");
				listadeerros.add(pp);
			}
			if(valorPessoalAltura > valoresAlertaPessoal.getAltura() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pa = new AlertaErro();
				pa.setCategoria("Pessoal");
				pa.setDescricao("A altura possui uma diferença de "+sinalPessoalAltura+valorPessoalAltura+" do valor configurado");
				listadeerros.add(pa);
			}
			if(valorPessoalCircunferencia > valoresAlertaPessoal.getCircunferencia() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pc = new AlertaErro();
				pc.setCategoria("Pessoal");
				pc.setDescricao("A circunferência torácica possui uma diferença de "+sinalPessoalCircunferencia+valorPessoalCircunferencia+" do valor configurado");
				listadeerros.add(pc);
			}
		
		}
		//ele só vai cair em um tipo de alerta
		
		if(contadorLiterario>0){
			if(contadorPessoal>0){
				Alerta alerta = new Alerta();
				alerta.setBovino(medida.getBovino());
				alerta.setCategoria("Erro Padrão Pessoal e Literário");
				alerta.setData(new Date());
				alerta.setMedida(medida);
				AlertaDAO dao = new AlertaDAO();
				try {
					dao.criar(alerta, listadeerros);
				} catch (Exception e) {
					JSFUtil.adicionarMensagemErro(e.getMessage());
					e.printStackTrace();
				}
			}else{
				Alerta alerta = new Alerta();
				alerta.setBovino(medida.getBovino());
				alerta.setCategoria("Erro Padrão Literário");
				alerta.setData(new Date());
				alerta.setMedida(medida);
				AlertaDAO dao = new AlertaDAO();
				try {
					dao.criar(alerta, listadeerros);
				} catch (Exception e) {
					JSFUtil.adicionarMensagemErro(e.getMessage());
					e.printStackTrace();
				}
			}
		}else if (contadorPessoal>0){
			Alerta alerta = new Alerta();
			alerta.setBovino(medida.getBovino());
			alerta.setCategoria("Erro Padrão Pessoal");
			alerta.setData(new Date());
			alerta.setMedida(medida);
			AlertaDAO dao = new AlertaDAO();
			try {
				dao.criar(alerta, listadeerros);
			} catch (Exception e) {
				JSFUtil.adicionarMensagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
	public void verificaAlerta(){
		
		
		int contadorLiterario = 0;
		int contadorPessoal = 0;
		
		DecimalFormat df = new DecimalFormat("0.##");
		
		//----Literarios------------------------------------------------------------
		
		if(valoresAlertaLiterario.getAtivo() == 1){

			String sinalLiterarioPeso = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioPeso = "+";
			}else{
				sinalLiterarioPeso = "-";
			}
			 
			double diferencaLiterariaPeso = Math.abs(medida.getPeso()-medidaLiteraria.getPeso());
			Double valorLiterarioPeso=Double.parseDouble(df.format(diferencaLiterariaPeso).replaceAll(",", "."));
			
			String sinalLiterarioAltura = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioAltura = "+";
			}else{
				sinalLiterarioAltura = "-";
			}
			
			double diferencaLiterariaAltura = Math.abs(medida.getAltura()-medidaLiteraria.getAltura());
			Double valorLiterarioAltura=Double.parseDouble(df.format(diferencaLiterariaAltura).replaceAll(",", "."));
			
			String sinalLiterarioCircunferencia = "";
			
			if((medida.getPeso()-medidaLiteraria.getPeso()) > 0){
				sinalLiterarioCircunferencia = "+";
			}else{
				sinalLiterarioCircunferencia = "-";
			}
			
			double diferencaLiterariaCircunferencia = Math.abs(medida.getCircunferencia()-medidaLiteraria.getCircunferencia());
			Double valorLiterarioCircunferencia=Double.parseDouble(df.format(diferencaLiterariaCircunferencia).replaceAll(",", "."));
			
			if(valorLiterarioPeso > valoresAlertaLiterario.getPeso() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro lp = new AlertaErro();
				lp.setCategoria("Literário");
				lp.setDescricao("O peso possui uma diferença de "+sinalLiterarioPeso+valorLiterarioPeso+" do valor configurado");
				listadeerros.add(lp);
			}
			if(valorLiterarioAltura > valoresAlertaLiterario.getAltura() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro la = new AlertaErro();
				la.setCategoria("Literário");
				la.setDescricao("A altura possui uma diferença de "+sinalLiterarioAltura+valorLiterarioAltura+" do valor configurado");
				listadeerros.add(la);
			}
			if(valorLiterarioCircunferencia > valoresAlertaLiterario.getCircunferencia() ){
				contadorLiterario = contadorLiterario+1;
				AlertaErro lc = new AlertaErro();
				lc.setCategoria("Literário");
				lc.setDescricao("A circunferência Torácica possui uma diferença de "+sinalLiterarioCircunferencia+valorLiterarioCircunferencia+" do valor configurado");
				listadeerros.add(lc);
			}
		}
		
		//----Pessoal-------------------------------------------------------------
		
		if(valoresAlertaPessoal.getAtivo() == 1){
		
			String sinalPessoalPeso = "";
			
			if((medida.getPeso()-medidaPessoal.getPeso()) > 0){
				sinalPessoalPeso = "+";
			}else{
				sinalPessoalPeso = "-";
			}
			
			double diferencaPessoalPeso = Math.abs(medida.getPeso()-medidaPessoal.getPeso());
			Double valorPessoalPeso=Double.parseDouble(df.format(diferencaPessoalPeso).replaceAll(",", "."));
			
			String sinalPessoalAltura = "";
			
			if((medida.getAltura()-medidaPessoal.getAltura()) > 0){
				sinalPessoalAltura = "+";
			}else{
				sinalPessoalAltura = "-";
			}
			
			double diferencaPessoalAltura = Math.abs(medida.getAltura()-medidaPessoal.getAltura());
			Double valorPessoalAltura=Double.parseDouble(df.format(diferencaPessoalAltura).replaceAll(",", "."));
			
			String sinalPessoalCircunferencia = "";
			
			if((medida.getCircunferencia()-medidaPessoal.getCircunferencia()) > 0){
				sinalPessoalCircunferencia = "+";
			}else{
				sinalPessoalCircunferencia = "-";
			}
			
			double diferencaPessoalCircunferencia = Math.abs(medida.getCircunferencia()-medidaPessoal.getCircunferencia());
			Double valorPessoalCircunferencia=Double.parseDouble(df.format(diferencaPessoalCircunferencia).replaceAll(",", "."));
					
			if(valorPessoalPeso > valoresAlertaPessoal.getPeso() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pp = new AlertaErro();
				pp.setCategoria("Pessoal");
				pp.setDescricao("O peso possui uma diferença de "+sinalPessoalPeso+valorPessoalPeso+" do valor configurado");
				listadeerros.add(pp);
			}
			if(valorPessoalAltura > valoresAlertaPessoal.getAltura() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pa = new AlertaErro();
				pa.setCategoria("Pessoal");
				pa.setDescricao("A altura possui uma diferença de "+sinalPessoalAltura+valorPessoalAltura+" do valor configurado");
				listadeerros.add(pa);
			}
			if(valorPessoalCircunferencia > valoresAlertaPessoal.getCircunferencia() ){
				contadorPessoal = contadorPessoal+1;
				AlertaErro pc = new AlertaErro();
				pc.setCategoria("Pessoal");
				pc.setDescricao("A circunferência torácica possui uma diferença de "+sinalPessoalCircunferencia+valorPessoalCircunferencia+" do valor configurado");
				listadeerros.add(pc);
			}

		}
		//ele só vai cair em um tipo de alerta
		
		if(contadorLiterario>0){
			if(contadorPessoal>0){
				Alerta alerta = new Alerta();
				alerta.setBovino(bovino);
				alerta.setCategoria("Erro Padrão Pessoal e Literário");
				alerta.setData(new Date());
				Medida m = new Medida();
				m.setCodigo(codigo);
				alerta.setMedida(m);
				AlertaDAO dao = new AlertaDAO();
				try {
					dao.criar(alerta, listadeerros);
				} catch (Exception e) {
					JSFUtil.adicionarMensagemErro(e.getMessage());
					e.printStackTrace();
				}
			}else{
				Alerta alerta = new Alerta();
				alerta.setBovino(bovino);
				alerta.setCategoria("Erro Padrão Literário");
				alerta.setData(new Date());
				Medida m = new Medida();
				m.setCodigo(codigo);
				alerta.setMedida(m);
				AlertaDAO dao = new AlertaDAO();
				try {
					dao.criar(alerta, listadeerros);
				} catch (Exception e) {
					JSFUtil.adicionarMensagemErro(e.getMessage());
					e.printStackTrace();
				}
			}
		}else if (contadorPessoal>0){
			Alerta alerta = new Alerta();
			alerta.setBovino(bovino);
			alerta.setCategoria("Erro Padrão Pessoal");
			alerta.setData(new Date());
			Medida m = new Medida();
			m.setCodigo(codigo);
			alerta.setMedida(m);
			AlertaDAO dao = new AlertaDAO();
			try {
				dao.criar(alerta, listadeerros);
			} catch (Exception e) {
				JSFUtil.adicionarMensagemErro(e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		
	}

}
