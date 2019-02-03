package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.factory.ConexaoFactory;

public class ConfiguracaoMedidaLiteraturaDAO {
	
	public ArrayList<ConfiguracaoMedidas> listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT lit_codigo, lit_mes, lit_peso, lit_altura, lit_circunferencia ");
		sql.append("FROM tbl_config_literatura");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<ConfiguracaoMedidas> lista = new ArrayList<ConfiguracaoMedidas>();
		
		while(resultado.next()){

			ConfiguracaoMedidas m = new ConfiguracaoMedidas();
			m.setCodigo(resultado.getLong("lit_codigo"));
			m.setMes(resultado.getInt("lit_mes"));
			m.setPeso(resultado.getDouble("lit_peso"));
			m.setAltura(resultado.getDouble("lit_altura"));
			m.setCircunferencia(resultado.getDouble("lit_circunferencia"));
			
			lista.add(m);
		}
		
		return lista;
		
	}
	
	public void editar(ConfiguracaoMedidas m) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_config_literatura ");
		sql.append("SET lit_mes = ?, lit_peso = ?, lit_altura = ?, lit_circunferencia = ? ");
		sql.append("WHERE lit_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setInt(1, m.getMes());
		comando.setDouble(2, m.getPeso());
		comando.setDouble(3, m.getAltura());
		comando.setDouble(4, m.getCircunferencia());
		comando.setLong(5, m.getCodigo());
		
		comando.executeUpdate();
	}
	
	public ConfiguracaoMedidas buscarMedidaLiteratura(Integer mes) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT lit_codigo,lit_mes,lit_peso,lit_altura,lit_circunferencia ");
		sql.append("FROM tbl_config_literatura ");
		sql.append("WHERE lit_mes = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, mes);
		
		ResultSet resultado = comando.executeQuery();
		
		ConfiguracaoMedidas retorno = null;
		
		if(resultado.next()){
			retorno = new ConfiguracaoMedidas();

			retorno.setCodigo(resultado.getLong("lit_codigo"));
			retorno.setMes(resultado.getInt("lit_mes"));
			retorno.setPeso(resultado.getDouble("lit_peso"));
			retorno.setAltura(resultado.getDouble("lit_altura"));
			retorno.setCircunferencia(resultado.getDouble("lit_circunferencia"));

		}
		
		return retorno;
		
	}
	
	

}
