package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.desenvolve.domain.Alerta;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.factory.ConexaoFactory;

public class MedidaDAO {
	
	public void editar(Medida m) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_medidas ");
		sql.append("SET bov_codigo = ?, med_mes = ?, med_dias = ?, med_peso = ?, med_altura = ?, med_circunferencia = ?, med_data = ?, med_alteracao = ? ");
		sql.append("WHERE med_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		System.out.println(m);
		comando.setLong(1, m.getBovino().getCodigo());
		comando.setInt(2, m.getMes());
		comando.setInt(3, m.getDias());
		comando.setDouble(4, m.getPeso());
		comando.setDouble(5, m.getAltura());
		comando.setDouble(6, m.getCircunferencia());
		comando.setDate(7, new java.sql.Date(m.getDatamedicao().getTime()));
		comando.setDate(8, new java.sql.Date(m.getAlteracao().getTime()));
		comando.setLong(9, m.getCodigo());
		
		comando.executeUpdate();
	}
	
	
	public Long criar(Medida m) throws Exception{
			
		Long idObjeto = (long) 0;
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement statement = conexao.prepareStatement("INSERT INTO " +
                "tbl_medidas " +
                "(bov_codigo, " +
                "med_mes, " +
                "med_dias, " +
                "med_peso, " +
                "med_altura, " +
                "med_circunferencia, " +
                "med_data) " +
                "VALUES (?,?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
		
		  statement.setLong(1, m.getBovino().getCodigo());
		  statement.setInt(2, m.getMes());
		  statement.setInt(3, m.getDias());
		  statement.setDouble(4, m.getPeso());
		  statement.setDouble(5, m.getAltura());
		  statement.setDouble(6, m.getCircunferencia());
		  statement.setDate(7, new java.sql.Date(m.getDatamedicao().getTime()));
		
		  statement.executeUpdate();
		  
		  ResultSet rs = statement.getGeneratedKeys();

		  while(rs.next()){
		   idObjeto = rs.getLong(1);
		  }
		  
		  return idObjeto;
		
	}
	
	public void excluir(Medida m) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ale_codigo ");
		sql.append("FROM tbl_alerta ");
		sql.append("WHERE med_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, m.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Alerta alerta = null;
		
		while(resultado.next()){
			alerta = new Alerta();
			alerta.setCodigo(resultado.getLong("ale_codigo"));
		
		}
		
		if(alerta != null){
			AlertaDAO daoAlerta = new AlertaDAO();
			daoAlerta.excluir(alerta);
		}
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM tbl_medidas ");
		sql2.append("WHERE med_codigo = ?");
		
		Connection conexao2 = ConexaoFactory.conectar();
		
		PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
		comando2.setLong(1, m.getCodigo());
		
		comando2.executeUpdate();
		
	}
	
	public ArrayList<Medida> listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m.med_codigo, m.med_mes, m.med_dias, m.bov_codigo, b.bov_nome, m.med_peso, m.med_altura,m.med_circunferencia, m.med_data,m.med_alteracao ");
		sql.append("FROM tbl_medidas AS m ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = m.bov_codigo) ");
		sql.append("ORDER BY m.med_data ASC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Medida> lista = new ArrayList<Medida>();
		
		while(resultado.next()){
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Medida m = new Medida();
			m.setCodigo(resultado.getLong("med_codigo"));
			m.setMes(resultado.getInt("med_mes"));
			m.setDias(resultado.getInt("med_dias"));
			m.setPeso(resultado.getDouble("med_peso"));
			m.setAltura(resultado.getDouble("med_altura"));
			m.setCircunferencia(resultado.getDouble("med_circunferencia"));
			m.setDatamedicao(resultado.getDate("med_data"));
			m.setAlteracao(resultado.getDate("med_alteracao"));
			m.setBovino(b);
			
			lista.add(m);
		}
		
		return lista;
		
	}
	
public ArrayList<Medida> listarIndividual(Bovino b) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT med_codigo, med_mes, med_dias, bov_codigo, med_peso, med_altura, med_circunferencia, med_data, med_alteracao ");
		sql.append("FROM tbl_medidas ");
		sql.append("WHERE bov_codigo=? ");
		sql.append("ORDER BY med_data ASC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, b.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Medida> lista = new ArrayList<Medida>();
		
		while(resultado.next()){
			Medida a = new Medida();
			a.setCodigo(resultado.getLong("med_codigo"));
			a.setMes(resultado.getInt("med_mes"));
			a.setDias(resultado.getInt("med_dias"));
			a.setBovino(b);
			a.setPeso(resultado.getDouble("med_peso"));
			a.setAltura(resultado.getDouble("med_altura"));
			a.setCircunferencia(resultado.getDouble("med_circunferencia"));
			a.setDatamedicao(resultado.getDate("med_data"));
			a.setAlteracao(resultado.getDate("med_alteracao"));
			
			lista.add(a);
		}
		
		return lista;
		
	}

}
