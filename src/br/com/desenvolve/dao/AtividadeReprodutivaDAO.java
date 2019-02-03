package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.AtividadeReprodutiva;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.factory.ConexaoFactory;

public class AtividadeReprodutivaDAO {
	
	public void salvar(AtividadeReprodutiva a) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_ativ_repro ");
		sql.append("(bov_codigo,ativ_data,ativ_idade,ativ_descricao) ");
		sql.append("VALUES (?,?,?,?) ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, a.getBovino().getCodigo());
		comando.setDate(2, new java.sql.Date(a.getDataAtividade().getTime()));
		comando.setInt(3, a.getIdade());
		comando.setString(4, a.getDescricao());
		
		comando.executeUpdate();
		
	}
	
	public void excluir(AtividadeReprodutiva a) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_ativ_repro ");
		sql.append("WHERE ativ_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public ArrayList<AtividadeReprodutiva> listarIndividual(Bovino b) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ativ_codigo, bov_codigo,ativ_data,ativ_idade,ativ_descricao ");
		sql.append("FROM tbl_ativ_repro ");
		sql.append("WHERE bov_codigo=? ");
		sql.append("ORDER BY ativ_data DESC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, b.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<AtividadeReprodutiva> lista = new ArrayList<AtividadeReprodutiva>();
		
		while(resultado.next()){
			AtividadeReprodutiva a = new AtividadeReprodutiva();
			a.setCodigo(resultado.getLong("ativ_codigo"));
			a.setBovino(b);
			a.setDataAtividade(resultado.getDate("ativ_data"));
			a.setIdade(resultado.getInt("ativ_idade"));
			a.setDescricao(resultado.getString("ativ_descricao"));
			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	public void editar(AtividadeReprodutiva a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_ativ_repro ");
		sql.append("SET bov_codigo = ?, ativ_data = ?, ativ_idade = ?, ativ_descricao = ? ");
		sql.append("WHERE ativ_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, a.getBovino().getCodigo());
		comando.setDate(2, new java.sql.Date(a.getDataAtividade().getTime()));
		comando.setInt(3, a.getIdade());
		comando.setString(4, a.getDescricao());
		comando.setLong(5, a.getCodigo());

		
		
		comando.executeUpdate();
	}

}
