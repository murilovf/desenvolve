package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.dao.VacinaDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBVacina")
@ViewScoped
public class VacinaBean {

	private ArrayList<Vacina> itens;
	private ArrayList<Vacina> itensFiltrados;
	private Vacina vacina;
	private ArrayList<Bovino> comboBovinos;

	public ArrayList<Bovino> getComboBovinos() {
		return comboBovinos;
	}
	
	public void setComboBovinos(ArrayList<Bovino> comboBovinos) {
		this.comboBovinos = comboBovinos;
	}
	
	public ArrayList<Vacina> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Vacina> itens) {
		this.itens = itens;
	}

	public ArrayList<Vacina> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Vacina> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	@PostConstruct
	public void prepararPesquisa() {

		vacina = new Vacina();

		try {
			VacinaDAO dao = new VacinaDAO();
			itens = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void excluir() {

		try {
			VacinaDAO dao = new VacinaDAO();
			dao.excluir(vacina);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Vacina Removida com Sucesso");
		} catch (SQLException e) {
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
			VacinaDAO dao = new VacinaDAO();
			
			dao.editar(vacina);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Vacina editada com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
	public void atualizaEstado(){
		try {
			
			VacinaDAO dao = new VacinaDAO();
			dao.atualizaEstado(vacina);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Estado da vacina atualizado com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}


}
