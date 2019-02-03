package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.desenvolve.domain.AlertaVl;
import br.com.desenvolve.factory.ConexaoFactory;

public class AlertaVlPessoalDAO {
	
	public AlertaVl listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT avlp_codigo, avlp_peso, avlp_altura, avlp_circunferencia, avlp_ativo ");
		sql.append("FROM tbl_alerta_pes_vl ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		AlertaVl m = null;
		
		while(resultado.next()){
			
			m = new AlertaVl();

			m.setCodigo(resultado.getLong("avlp_codigo"));
			m.setPeso(resultado.getDouble("avlp_peso"));
			m.setAltura(resultado.getDouble("avlp_altura"));
			m.setCircunferencia(resultado.getDouble("avlp_circunferencia"));
			m.setAtivo(resultado.getInt("avlp_ativo"));
			
		}
		
		return m;
		
	}
	
	public void editar(AlertaVl a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_alerta_pes_vl ");
		sql.append("SET avlp_peso = ?, avlp_altura = ?, avlp_circunferencia = ?, avlp_ativo = ? ");
		sql.append("WHERE avlp_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setDouble(1, a.getPeso());
		comando.setDouble(2, a.getAltura());
		comando.setDouble(3, a.getCircunferencia());
		comando.setInt(4, a.getAtivo());
		comando.setLong(5, a.getCodigo());
		
		comando.executeUpdate();
		
	}


}
