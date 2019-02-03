package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.ConfiguracaoMedidas;
import br.com.desenvolve.factory.ConexaoFactory;

public class ConfiguracaoMedidaPessoalDAO {
	
	public ArrayList<ConfiguracaoMedidas> listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pes_codigo, pes_mes, pes_peso, pes_altura, pes_circunferencia ");
		sql.append("FROM tbl_config_pessoal");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<ConfiguracaoMedidas> lista = new ArrayList<ConfiguracaoMedidas>();
		
		while(resultado.next()){

			ConfiguracaoMedidas m = new ConfiguracaoMedidas();
			m.setCodigo(resultado.getLong("pes_codigo"));
			m.setMes(resultado.getInt("pes_mes"));
			m.setPeso(resultado.getDouble("pes_peso"));
			m.setAltura(resultado.getDouble("pes_altura"));
			m.setCircunferencia(resultado.getDouble("pes_circunferencia"));
			
			lista.add(m);
		}
		
		return lista;
		
	}
	
	public void editar(ConfiguracaoMedidas m) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_config_pessoal ");
		sql.append("SET pes_mes = ?, pes_peso = ?, pes_altura = ?, pes_circunferencia = ? ");
		sql.append("WHERE pes_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setInt(1, m.getMes());
		comando.setDouble(2, m.getPeso());
		comando.setDouble(3, m.getAltura());
		comando.setDouble(4, m.getCircunferencia());
		comando.setLong(5, m.getCodigo());
		
		comando.executeUpdate();
	}
	
	public ConfiguracaoMedidas buscarMedidaPessoal(Integer mes) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pes_codigo,pes_mes,pes_peso,pes_altura,pes_circunferencia ");
		sql.append("FROM tbl_config_pessoal ");
		sql.append("WHERE pes_mes = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, mes);
		
		ResultSet resultado = comando.executeQuery();
		
		ConfiguracaoMedidas retorno = null;
		
		if(resultado.next()){
			retorno = new ConfiguracaoMedidas();

			retorno.setCodigo(resultado.getLong("pes_codigo"));
			retorno.setMes(resultado.getInt("pes_mes"));
			retorno.setPeso(resultado.getDouble("pes_peso"));
			retorno.setAltura(resultado.getDouble("pes_altura"));
			retorno.setCircunferencia(resultado.getDouble("pes_circunferencia"));

		}
		
		return retorno;
		
	}

}
