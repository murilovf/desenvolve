package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.dao.MedicamentoDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBMedicamento")
@ViewScoped
public class MedicamentoBean {

	private ArrayList<Medicamento> itens;
	private ArrayList<Medicamento> itensFiltrados;
	private Medicamento medicamento;
	private ArrayList<Bovino> comboBovinos;

	public ArrayList<Medicamento> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Medicamento> itens) {
		this.itens = itens;
	}

	public ArrayList<Medicamento> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Medicamento> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public ArrayList<Bovino> getComboBovinos() {
		return comboBovinos;
	}

	public void setComboBovinos(ArrayList<Bovino> comboBovinos) {
		this.comboBovinos = comboBovinos;
	}

	@PostConstruct
	public void prepararPesquisa() {

		medicamento = new Medicamento();

		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			itens = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void excluir() {

		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			dao.excluir(medicamento);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Medicamento Removido com Sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void prepararEditar(){
		try {
			BovinoDAO dao = new BovinoDAO();
			
			comboBovinos = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void editar(){
		try {
			MedicamentoDAO dao = new MedicamentoDAO();
			
			dao.editar(medicamento);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Medicamento editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}

}
