package br.com.desenvolve.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.MedidaGrafico;
import br.com.desenvolve.factory.ConexaoFactory;


public class CrescimentoDAO {
	
	public ArrayList<MedidaGrafico> listar(String selecionados) throws SQLException{
				
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT med_mes,sum(med_peso)/COUNT(med_mes) AS mediapeso, sum(med_altura)/COUNT(med_mes) AS mediaaltura,sum(med_circunferencia)/COUNT(med_mes) AS mediacircunferencia ");
		sql.append("FROM tbl_medidas ");
		sql.append("WHERE bov_codigo IN (" + selecionados + ") ");
		sql.append("GROUP BY med_mes");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<MedidaGrafico> lista = new ArrayList<MedidaGrafico>();
		
		while(resultado.next()){


			MedidaGrafico m = new MedidaGrafico();
	
			m.setMes(resultado.getInt("med_mes"));
			m.setPeso(resultado.getDouble("mediapeso"));
			m.setAltura(resultado.getDouble("mediaaltura"));
			m.setCircunferencia(resultado.getDouble("mediacircunferencia"));
			
			lista.add(m);
		}
		
		
		return lista;

		
	}

	
	public ArrayList<MedidaGrafico> listarTodos() throws SQLException{
		
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT med_mes,sum(med_peso)/COUNT(med_mes) AS mediapeso, sum(med_altura)/COUNT(med_mes) AS mediaaltura, sum(med_circunferencia)/COUNT(med_mes) AS mediacircunferencia ");
		sql.append("FROM tbl_medidas ");
		sql.append("GROUP BY med_mes");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<MedidaGrafico> lista = new ArrayList<MedidaGrafico>();
		
		while(resultado.next()){


			MedidaGrafico m = new MedidaGrafico();
	
			m.setMes(resultado.getInt("med_mes"));
			m.setPeso(resultado.getDouble("mediapeso"));
			m.setAltura(resultado.getDouble("mediaaltura"));
			m.setCircunferencia(resultado.getDouble("mediacircunferencia"));
			
			lista.add(m);
		}
		
		return lista;

		
	}
	
}
