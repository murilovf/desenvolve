package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.desenvolve.dao.AlimentoDAO;
import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.util.JSFUtil;


@ManagedBean(name = "MBAlimento")
@ViewScoped
public class AlimentoBean {
	
	private ArrayList<Alimento> itens;
	private ArrayList<Alimento> itensFiltrados;
	private Alimento alimento;
	

	public ArrayList<Alimento> getItens() {
		return itens;
	}
	public void setItens(ArrayList<Alimento> itens) {
		this.itens = itens;
	}
	public ArrayList<Alimento> getItensFiltrados() {
		return itensFiltrados;
	}
	public void setItensFiltrados(ArrayList<Alimento> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}
	public Alimento getAlimento() {
		return alimento;
	}
	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}
	
	@PostConstruct
	public void prepararPesquisa(){
		
		alimento = new Alimento();

		try {
			AlimentoDAO dao = new AlimentoDAO();		
			itens = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	
	public void novo(){
		try {
			AlimentoDAO dao = new AlimentoDAO();
			dao.salvar(alimento);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Alimento Salvo com Sucesso");
			alimento = new Alimento();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	
	public void excluir(){

		try {
			AlimentoDAO dao = new AlimentoDAO();
			dao.excluir(alimento);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Alimento Removido com Sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void editar() {

		try {
			AlimentoDAO dao = new AlimentoDAO();
			dao.editar(alimento);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Alimento Editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}

		
	}
	
}
