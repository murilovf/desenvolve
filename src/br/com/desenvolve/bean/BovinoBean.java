package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import br.com.desenvolve.dao.BovinoDAO;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBBovino")
@ViewScoped
public class BovinoBean {

	private ArrayList<Bovino> itens;
	private ArrayList<Bovino> itensFiltrados;
	private Bovino bovino;
	private String data;
	private boolean inativo;
	private boolean verificar = true;
	
	public boolean isVerificar() {
		return verificar;
	}
	
	public void setVerificar(boolean verificar) {
		this.verificar = verificar;
	}

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ArrayList<Bovino> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Bovino> itens) {
		this.itens = itens;
	}

	public ArrayList<Bovino> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Bovino> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	public Bovino getBovino() {
		return bovino;
	}

	public void setBovino(Bovino bovino) {
		this.bovino = bovino;
	}

	@PostConstruct
	public void prepararPesquisa(){

		try {
			BovinoDAO dao = new BovinoDAO();		
			itens = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}				
	}
	
	public void prepararNovo(){
		bovino = new Bovino();
		data=null;
	}
	
	public void novo() throws ParseException{
		verificaCampos(bovino);
		
		if (verificar){
			try {
				BovinoDAO dao = new BovinoDAO();
				bovino.setDatanascimento(new SimpleDateFormat("dd/MM/yyyy").parse(data));
				dao.salvar(bovino);
				itens = dao.listar();
				JSFUtil.adicionarMensagemSucesso("Bovino Salvo com Sucesso");
				RequestContext request = RequestContext.getCurrentInstance();
				request.addCallbackParam("sucesso", true);
			} catch (SQLException e) {
				e.printStackTrace();
				JSFUtil.adicionarMensagemErro(e.getMessage());
			}
		}else{
			verificar = true;
		}
		
	}
	
	
	public void excluir(){

		try {
			BovinoDAO dao = new BovinoDAO();
			dao.excluir(bovino);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Bovino Removido com Sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void ativar(){

		try {
			BovinoDAO dao = new BovinoDAO();
			dao.ativar(bovino);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Bovino ativado com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void inativar(){

		try {
			BovinoDAO dao = new BovinoDAO();
			dao.inativar(bovino);
			itens = dao.listar();
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('tabelaBovinos').clearFilters()");
			JSFUtil.adicionarMensagemSucesso("Bovino inativado com Sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}	
	
	public void verificaCampos(Bovino bovino){
		if (bovino.getNome().equals("") || bovino.getNome() == null){
			JSFUtil.adicionarMensagemErro("Nome deve ser informado");
			verificar = false;
		}
		if (data.equals("")){
			JSFUtil.adicionarMensagemErro("Data de nascimento deve ser informada");
			verificar = false;
		}
		if (bovino.getRaca() == null || bovino.getRaca().equals("")){
			JSFUtil.adicionarMensagemErro("Raça deve ser informada");
			verificar = false;
		}
		if (bovino.getOrigem() == null || bovino.getOrigem().equals("")){
			JSFUtil.adicionarMensagemErro("Origem deve ser informada");
			verificar = false;
		}
		
	}
	
	
}
