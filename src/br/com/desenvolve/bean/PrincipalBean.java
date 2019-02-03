package br.com.desenvolve.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import br.com.desenvolve.dao.AlertaDAO;
import br.com.desenvolve.dao.AlertaErroDAO;
import br.com.desenvolve.domain.Alerta;
import br.com.desenvolve.domain.AlertaErro;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBPrincipal")
@ViewScoped
public class PrincipalBean {
	
	private ArrayList<Alerta> alertas;
	private ArrayList<Alerta> alertasFiltrados;
	private ArrayList<AlertaErro> listadeerros;
	private Alerta alerta;
	
	
	
	public ArrayList<AlertaErro> getListadeerros() {
		return listadeerros;
	}

	public void setListadeerros(ArrayList<AlertaErro> listadeerros) {
		this.listadeerros = listadeerros;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public ArrayList<Alerta> getAlertas() {
		return alertas;
	}

	public void setAlertas(ArrayList<Alerta> alertas) {
		this.alertas = alertas;
	}

	public ArrayList<Alerta> getAlertasFiltrados() {
		return alertasFiltrados;
	}

	public void setAlertasFiltrados(ArrayList<Alerta> alertasFiltrados) {
		this.alertasFiltrados = alertasFiltrados;
	}
	
	@PostConstruct
	public void prepararPesquisa(){
		

		try {
			alerta = new Alerta();
			AlertaDAO dao = new AlertaDAO();		
			alertas = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void excluir(){
		
		try {
			AlertaDAO dao = new AlertaDAO();
			dao.excluir(alerta);
			alertas = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void carregarErros(Integer codigo){
		
		try {
			AlertaDAO dao = new AlertaDAO();
			alerta = dao.buscarPorCodigo(codigo);
			AlertaErroDAO daoe = new AlertaErroDAO();
			listadeerros = daoe.listarIndividual(alerta);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}

	public void rebanho() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./rebanho.xhtml");

	}
	
	public void alimento() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./alimentos.xhtml");

	}
	
	public void vacina() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./vacinas.xhtml");

	}	
	
	public void medicamento() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./medicamentos.xhtml");

	}	
	
	public void crescimento() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./crescimento.xhtml");

	}	
	
	public void relatorio() throws IOException {

		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("./relatorioBovino.xhtml");

	}		

}
