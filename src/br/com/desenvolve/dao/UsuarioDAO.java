package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.Usuario;
import br.com.desenvolve.factory.ConexaoFactory;



public class UsuarioDAO {
	
	public void salvar(Usuario u) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_usuarios ");
		sql.append("(usu_nome,usu_datanascimento,usu_email,usu_telefone,usu_login,usu_senha,usu_perfil,usu_funcao) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?)");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, u.getNome());
		comando.setDate(2, new java.sql.Date(u.getDatanascimento().getTime()));
		comando.setString(3, u.getEmail());
		comando.setString(4, u.getTelefone());
		comando.setString(5, u.getLogin());
		comando.setString(6, u.getSenha());
		comando.setInt(7, u.getPerfil());
		comando.setString(8, u.getFuncao());
		
		comando.executeUpdate();
	}
	
	public ArrayList<Usuario> listar() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT usu_codigo,usu_nome,usu_datanascimento,usu_email,usu_telefone,usu_login,usu_senha,usu_perfil,usu_funcao ");
		sql.append("FROM tbl_usuarios ");
		sql.append("ORDER BY usu_codigo ASC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		
		while(resultado.next()){
			Usuario a = new Usuario();
			a.setCodigo(resultado.getLong("usu_codigo"));
			a.setNome(resultado.getString("usu_nome"));
			a.setDatanascimento(resultado.getDate("usu_datanascimento"));
			a.setEmail(resultado.getString("usu_email"));
			a.setTelefone(resultado.getString("usu_telefone"));
			a.setLogin(resultado.getString("usu_login"));
			a.setSenha(resultado.getString("usu_senha"));
			a.setPerfil(resultado.getInt("usu_perfil"));
			a.setFuncao(resultado.getString("usu_funcao"));

			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	public Usuario verificarEmail (String email) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT usu_codigo,usu_nome,usu_datanascimento,usu_email,usu_telefone,usu_login,usu_senha,usu_perfil,usu_funcao ");
		sql.append("FROM tbl_usuarios ");
		sql.append("WHERE usu_email = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, email);
		
		ResultSet resultado = comando.executeQuery();
		
		Usuario retorno = null;
		
		if(resultado.next()){
			retorno = new Usuario();
			retorno.setCodigo(resultado.getLong("usu_codigo"));
			retorno.setNome(resultado.getString("usu_nome"));
			retorno.setDatanascimento(resultado.getDate("usu_datanascimento"));
			retorno.setEmail(resultado.getString("usu_email"));
			retorno.setTelefone(resultado.getString("usu_telefone"));
			retorno.setLogin(resultado.getString("usu_login"));
			retorno.setSenha(resultado.getString("usu_senha"));
			retorno.setPerfil(resultado.getInt("usu_perfil"));
			retorno.setFuncao(resultado.getString("usu_funcao"));
		}
		
		return retorno;
	}
	
	public Usuario autenticar (String login,String senha) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT usu_codigo,usu_nome,usu_datanascimento,usu_email,usu_telefone,usu_login,usu_senha,usu_perfil,usu_funcao ");
		sql.append("FROM tbl_usuarios ");
		sql.append("WHERE usu_login = ? and usu_senha = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, login);
		comando.setString(2, senha);
		
		ResultSet resultado = comando.executeQuery();
		
		Usuario retorno = null;
		
		if(resultado.next()){
			retorno = new Usuario();
			retorno.setCodigo(resultado.getLong("usu_codigo"));
			retorno.setNome(resultado.getString("usu_nome"));
			retorno.setDatanascimento(resultado.getDate("usu_datanascimento"));
			retorno.setEmail(resultado.getString("usu_email"));
			retorno.setTelefone(resultado.getString("usu_telefone"));
			retorno.setLogin(resultado.getString("usu_login"));
			retorno.setSenha(resultado.getString("usu_senha"));
			retorno.setPerfil(resultado.getInt("usu_perfil"));
			retorno.setFuncao(resultado.getString("usu_funcao"));
		}
		
		return retorno;
	}
	
	public Usuario buscarPorCodigo (Usuario usuario) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT usu_codigo,usu_nome,usu_datanascimento,usu_email,usu_telefone,usu_login,usu_senha,usu_perfil,usu_funcao ");
		sql.append("FROM tbl_usuarios ");
		sql.append("WHERE usu_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, usuario.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Usuario retorno = null;
		
		if(resultado.next()){
			retorno = new Usuario();
			retorno.setCodigo(resultado.getLong("usu_codigo"));
			retorno.setNome(resultado.getString("usu_nome"));
			retorno.setDatanascimento(resultado.getDate("usu_datanascimento"));
			retorno.setEmail(resultado.getString("usu_email"));
			retorno.setTelefone(resultado.getString("usu_telefone"));
			retorno.setLogin(resultado.getString("usu_login"));
			retorno.setSenha(resultado.getString("usu_senha"));
			retorno.setPerfil(resultado.getInt("usu_perfil"));
			retorno.setFuncao(resultado.getString("usu_funcao"));
		}
		
		return retorno;
	}
	
	public void excluir(Usuario a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_usuarios ");
		sql.append("WHERE usu_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void editar(Usuario a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_usuarios ");
		sql.append("SET usu_nome = ?, usu_datanascimento = ?, usu_email = ?, usu_telefone = ?, usu_login = ?, usu_senha = ?, usu_perfil = ?, usu_funcao = ? ");
		sql.append("WHERE usu_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, a.getNome());
		comando.setDate(2, new java.sql.Date(a.getDatanascimento().getTime()));
		comando.setString(3, a.getEmail());
		comando.setString(4, a.getTelefone());
		comando.setString(5, a.getLogin());
		comando.setString(6, a.getSenha());
		comando.setDouble(7, a.getPerfil());
		comando.setString(8, a.getFuncao());
		comando.setLong(9, a.getCodigo());

		
		comando.executeUpdate();
		
	}
}
