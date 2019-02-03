package br.com.desenvolve.bean;

import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import br.com.desenvolve.dao.UsuarioDAO;
import br.com.desenvolve.domain.Usuario;
import br.com.desenvolve.util.JSFUtil;

@ManagedBean(name = "MBAutenticacao")
@SessionScoped
public class AutenticacaoBean {
	
	private Usuario usuario;
	private Usuario usuarioLogado;
	private String senhaNova;	
	

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@PostConstruct
	public void prepararNovo(){
		usuario = new Usuario();
		
	}
	
	public String login(){

		try{
			UsuarioDAO dao = new UsuarioDAO();
			usuarioLogado = dao.autenticar(usuario.getLogin(), usuario.getSenha());
			
			if(usuarioLogado == null){
				JSFUtil.adicionarMensagemErro("Login e/ou senha incorretos");
				return "";				
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
		
		return "/pages/principal.xhtml?faces-redirect=true"; 

	}
	
	public String sair() throws IOException{
		
		usuarioLogado = new Usuario();
		return "/pages/login.xtml?faces-redirect=true";

	}	
	
	public void excluir() throws IOException{
		
		
		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.excluir(usuarioLogado);
			usuarioLogado = new Usuario();
			usuario = new Usuario();
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext(); ec.redirect("./login.xhtml");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void prepararSenha (){
		senhaNova = "";
	}
	
	public void editarSenha(){
		
		
		try {

			usuarioLogado.setSenha(senhaNova);
			UsuarioDAO dao = new UsuarioDAO();
			dao.editar(usuarioLogado);
			JSFUtil.adicionarMensagemSucesso("Senha Editada com Sucesso");
			usuarioLogado = dao.buscarPorCodigo(usuarioLogado);
			RequestContext request = RequestContext.getCurrentInstance();
			request.addCallbackParam("sucesso", true);

		} catch (SQLException e) {
			
			JSFUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}


	}
	
	public void editar(){
		
		
		try {
			UsuarioDAO dao = new UsuarioDAO();
			dao.editar(usuarioLogado);
			JSFUtil.adicionarMensagemSucesso("Usuário Editado com Sucesso");
			usuarioLogado = dao.buscarPorCodigo(usuarioLogado);

		} catch (SQLException e) {
			JSFUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}


	}
	
	public boolean temPermissoes(Integer permissao){
		
			if(usuarioLogado.getPerfil()==permissao){
				return true;
			}
			
			return false;
		
	}
	

}
