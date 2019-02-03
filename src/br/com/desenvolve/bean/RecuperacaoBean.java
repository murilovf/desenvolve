package br.com.desenvolve.bean;

import java.sql.SQLException;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.desenvolve.dao.UsuarioDAO;
import br.com.desenvolve.domain.Usuario;
import br.com.desenvolve.util.JSFUtil;


@ManagedBean(name = "MBRecuperacao")
@ViewScoped
public class RecuperacaoBean {
	
	private Usuario usuarioRec;
	private String emailDigitado;


	public String getEmailDigitado() {
		return emailDigitado;
	}

	public void setEmailDigitado(String emailDigitado) {
		this.emailDigitado = emailDigitado;
	}

	public Usuario getUsuarioRec() {
		return usuarioRec;
	}

	public void setUsuarioRec(Usuario usuarioRec) {
		this.usuarioRec = usuarioRec;
	}

	public String recuperar() throws EmailException{
		

		try {
			if(emailDigitado == ""){
				JSFUtil.adicionarMensagemErro("Campo vazio! Digite um e-mail válido");
				return "";
			}
			
			UsuarioDAO dao = new UsuarioDAO();
			usuarioRec = dao.verificarEmail(emailDigitado);
			
			if(usuarioRec == null){
				JSFUtil.adicionarMensagemErro("E-mail não cadastrado");
				return "";
			}
			
			
			SimpleEmail email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com"); // o servidor SMTP para envio do e-mail
			email.setAuthentication("desenvolve.uni@gmail.com", "desenvolveunisul");
			email.setSSLOnConnect(true);
			email.setFrom("desenvolve.uni@gmail.com","Desenvolve");
			email.setSubject("Recuperação de Conta"); // assunto do e-mail
			email.setMsg("Olá "+ usuarioRec.getNome()+"!"+"\n\n"+
						"Recebemos uma solicitação de recuperação de senha."+"\n"+
						" Caso você não tenha solicitado, ignore essa mensagem!"+"\n\n"+
						"Login: "+usuarioRec.getLogin()+"\n"+"Senha: "+usuarioRec.getSenha()+"\n\n"+
						"Atenciosamente,"+"\n"+"Equipe Desenvolve."); //conteudo do e-mail	
			email.addTo(usuarioRec.getEmail());
			email.send(); //envia o e-mail
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSFUtil.adicionarMensagemSucesso("Os dados de recuperação foram enviados para o email cadastrado");
		return "/pages/login.xtml";
		


		

	}
	
}
