package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.desenvolve.dao.AlertaVlLiterarioDAO;
import br.com.desenvolve.dao.AlertaVlPessoalDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaLiteraturaDAO;
import br.com.desenvolve.dao.ConfiguracaoMedidaPessoalDAO;
import br.com.desenvolve.domain.AlertaVl;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.util.JSFUtil;


@ManagedBean(name = "MBConfiguracao")
@ViewScoped
public class ConfiguracaoBean {

	private ArrayList<ConfiguracaoMedidas> itensConfigLiteratura;
	private ArrayList<ConfiguracaoMedidas> itensConfigLiteraturaFiltrados;
	private ArrayList<ConfiguracaoMedidas> itensConfigPessoal;
	private ArrayList<ConfiguracaoMedidas> itensConfigPessoalFiltrados;
	private AlertaVl valoresAlertaLiterario; //valores de alerta literarios
	private AlertaVl valoresAlertaPessoal; //valores de alerta pessoal
	private ConfiguracaoMedidas medidaLiteraria;
	private ConfiguracaoMedidas medidaPessoal;
	private boolean checadoPessoal = false;
	private boolean checadoliterario = false;
	
	
	public boolean isChecadoPessoal() {
		return checadoPessoal;
	}
	public void setChecadoPessoal(boolean checadoPessoal) {
		this.checadoPessoal = checadoPessoal;
	}
	public boolean isChecadoliterario() {
		return checadoliterario;
	}
	public void setChecadoliterario(boolean checadoliterario) {
		this.checadoliterario = checadoliterario;
	}
	public AlertaVl getValoresAlertaPessoal() {
		return valoresAlertaPessoal;
	}
	public void setValoresAlertaPessoal(AlertaVl valoresAlertaPessoal) {
		this.valoresAlertaPessoal = valoresAlertaPessoal;
	}
	public ArrayList<ConfiguracaoMedidas> getItensConfigLiteratura() {
		return itensConfigLiteratura;
	}
	public void setItensConfigLiteratura(ArrayList<ConfiguracaoMedidas> itensConfigLiteratura) {
		this.itensConfigLiteratura = itensConfigLiteratura;
	}
	public AlertaVl getValoresAlertaLiterario() {
		return valoresAlertaLiterario;
	}
	public void setValoresAlertaLiterario(AlertaVl valoresAlertaLiterario) {
		this.valoresAlertaLiterario = valoresAlertaLiterario;
	}
	public ArrayList<ConfiguracaoMedidas> getItensConfigLiteraturaFiltrados() {
		return itensConfigLiteraturaFiltrados;
	}
	public void setItensConfigLiteraturaFiltrados(ArrayList<ConfiguracaoMedidas> itensConfigLiteraturaFiltrados) {
		this.itensConfigLiteraturaFiltrados = itensConfigLiteraturaFiltrados;
	}
	public ArrayList<ConfiguracaoMedidas> getItensConfigPessoal() {
		return itensConfigPessoal;
	}
	public void setItensConfigPessoal(ArrayList<ConfiguracaoMedidas> itensConfigPessoal) {
		this.itensConfigPessoal = itensConfigPessoal;
	}
	public ArrayList<ConfiguracaoMedidas> getItensConfigPessoalFiltrados() {
		return itensConfigPessoalFiltrados;
	}
	public void setItensConfigPessoalFiltrados(ArrayList<ConfiguracaoMedidas> itensConfigPessoalFiltrados) {
		this.itensConfigPessoalFiltrados = itensConfigPessoalFiltrados;
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
	
	@PostConstruct
	public void prepararTela(){

		try {
			ConfiguracaoMedidaLiteraturaDAO daoL = new ConfiguracaoMedidaLiteraturaDAO();		
			itensConfigLiteratura = daoL.listar();
			ConfiguracaoMedidaPessoalDAO daoP = new ConfiguracaoMedidaPessoalDAO();
			itensConfigPessoal = daoP.listar();
			AlertaVlLiterarioDAO daoA = new AlertaVlLiterarioDAO();
			valoresAlertaLiterario = daoA.listar();
			AlertaVlPessoalDAO daoPe = new AlertaVlPessoalDAO();
			valoresAlertaPessoal = daoPe.listar();

			if(valoresAlertaLiterario.getAtivo() == 1){
				checadoliterario = true;
			}
			if(valoresAlertaPessoal.getAtivo() == 1){
				checadoPessoal = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void editarLiteratura(){
		try {
			ConfiguracaoMedidaLiteraturaDAO dao = new ConfiguracaoMedidaLiteraturaDAO();
			dao.editar(medidaLiteraria);
			
			itensConfigLiteratura = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Medida editada com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
		medidaLiteraria = new ConfiguracaoMedidas();
		
	}
	
	
	public void editarPessoal(){
		try {
			ConfiguracaoMedidaPessoalDAO dao = new ConfiguracaoMedidaPessoalDAO();
			dao.editar(medidaPessoal);
			
			itensConfigPessoal = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Medida editada com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
		medidaPessoal= new ConfiguracaoMedidas();
		
	}
	
	public void editarValoresLiterarios(){
		try {
			AlertaVlLiterarioDAO dao = new AlertaVlLiterarioDAO();
			if(checadoliterario){
				valoresAlertaLiterario.setAtivo(1);
			}else{
				valoresAlertaLiterario.setAtivo(0);
			}
			dao.editar(valoresAlertaLiterario);
			
			valoresAlertaLiterario = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Valor Literário editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
	public void editarValoresPessoais(){
		try {
			AlertaVlPessoalDAO dao = new AlertaVlPessoalDAO();
			if(checadoPessoal){
				valoresAlertaPessoal.setAtivo(1);
			}else{
				valoresAlertaPessoal.setAtivo(0);
			}
			dao.editar(valoresAlertaPessoal);
			
			valoresAlertaPessoal = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Valor pessoal editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
}
