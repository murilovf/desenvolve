package br.com.desenvolve.bean;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.desenvolve.dao.UsuarioDAO;
import br.com.desenvolve.domain.Usuario;
import br.com.desenvolve.util.JSFUtil;


@ManagedBean(name = "MBUsuario")
@ViewScoped

public class UsuarioBean {
	
	private Usuario usuario;
	private ArrayList<Usuario> itens;
	private ArrayList<Usuario> itensFiltrados;
	private String data;
	
	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public ArrayList<Usuario> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Usuario> itens) {
		this.itens = itens;
	}

	public ArrayList<Usuario> getItensFiltrados() {
		return itensFiltrados;
	}

	public void setItensFiltrados(ArrayList<Usuario> itensFiltrados) {
		this.itensFiltrados = itensFiltrados;
	}

	@PostConstruct
	public void prepararNovo(){
		usuario = new Usuario();
		data = new String();
		
		try {
			UsuarioDAO dao = new UsuarioDAO();
			itens = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
	public void excluir(){

		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.excluir(usuario);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Usuário Removido com Sucesso");
			usuario = new Usuario();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		

	}
	
	public void editar(){
		try {
			UsuarioDAO dao = new UsuarioDAO();
			
			dao.editar(usuario);
			
			itens = dao.listar();
			
			JSFUtil.adicionarMensagemSucesso("Usuário editado com Sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	
	public void novo() throws ParseException{
		try {
			UsuarioDAO dao = new UsuarioDAO();
			//colocar aquele filtro só e não calendario
			usuario.setDatanascimento(new SimpleDateFormat("dd/MM/yyyy").parse(data));
			dao.salvar(usuario);
			itens = dao.listar();
			JSFUtil.adicionarMensagemSucesso("Usuário Salvo com Sucesso");
			usuario = new Usuario();
			data= new String();
		} catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	

}
