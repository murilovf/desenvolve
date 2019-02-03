package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.desenvolve.domain.AlertaVl;
import br.com.desenvolve.factory.ConexaoFactory;

public class AlertaVlLiterarioDAO {
	
	public AlertaVl listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT avll_codigo, avll_peso, avll_altura, avll_circunferencia, avll_ativo ");
		sql.append("FROM tbl_alerta_lit_vl ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		AlertaVl m = null;
		
		while(resultado.next()){
			
			m = new AlertaVl();
			m.setCodigo(resultado.getLong("avll_codigo"));
			m.setPeso(resultado.getDouble("avll_peso"));
			m.setAltura(resultado.getDouble("avll_altura"));
			m.setCircunferencia(resultado.getDouble("avll_circunferencia"));
			m.setAtivo(resultado.getInt("avll_ativo"));
			
		}
		
		return m;
		
	}
	
	public void editar(AlertaVl a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_alerta_lit_vl ");
		sql.append("SET avll_peso = ?, avll_altura = ?, avll_circunferencia = ?, avll_ativo = ? ");
		sql.append("WHERE avll_codigo = ?");
		
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
