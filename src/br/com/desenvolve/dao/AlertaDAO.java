package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import br.com.desenvolve.domain.Alerta;
import br.com.desenvolve.domain.AlertaErro;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.factory.ConexaoFactory;

public class AlertaDAO {
	
	public ArrayList<Alerta> listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.ale_codigo, a.bov_codigo, b.bov_nome, a.ale_data, a.ale_categoria ");
		sql.append("FROM tbl_alerta AS a ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = a.bov_codigo) ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Alerta> lista = new ArrayList<Alerta>();
		
		while(resultado.next()){
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Alerta m = new Alerta();
			m.setCodigo(resultado.getLong("ale_codigo"));
			m.setData(resultado.getDate("ale_data"));
			m.setCategoria(resultado.getString("ale_categoria"));

			m.setBovino(b);
			
			lista.add(m);
		}
		
		return lista;
		
	}
	
	public Alerta acharAlertaMedida(Medida m) throws SQLException{
				
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ale_codigo, med_codigo ");
		sql.append("FROM tbl_alerta ");
		sql.append("WHERE med_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, m.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Alerta retorno = null;
		
		if(resultado.next()){
			retorno = new Alerta();
			retorno.setCodigo(resultado.getLong("ale_codigo"));
			Medida medida = new Medida();
			medida.setCodigo(resultado.getLong("med_codigo"));
			retorno.setMedida(m);
		}
		
		return retorno;
		
	}
	
	public void excluir(Alerta a) throws SQLException{
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM tbl_alerta_erro ");
		sql2.append("WHERE ale_codigo = ?");
		
		Connection conexao2 = ConexaoFactory.conectar();
		
		PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
		comando2.setLong(1, a.getCodigo());
		
		comando2.executeUpdate();
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_alerta ");
		sql.append("WHERE ale_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	
	public void criar(Alerta alerta,ArrayList<AlertaErro> listaerros) throws Exception{
		
		int idObjeto = 0;
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement statement = conexao.prepareStatement("INSERT INTO " +
                "tbl_alerta " +
                "(bov_codigo, " +
                "med_codigo, " +
                "ale_data, " +
                "ale_categoria) " +
                "VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
		
		  statement.setLong(1, alerta.getBovino().getCodigo());
		  statement.setLong(2, alerta.getMedida().getCodigo());
		  statement.setDate(3, new java.sql.Date(alerta.getData().getTime()));
		  statement.setString(4, alerta.getCategoria());
		
		  statement.executeUpdate();
		  
		  ResultSet rs = statement.getGeneratedKeys();

		  while(rs.next()){
		   idObjeto = rs.getInt(1);
		  }
		  
		  for (int i = 0; i < listaerros.size(); i++) {
			  
				StringBuilder sql2 = new StringBuilder();
				sql2.append("INSERT INTO tbl_alerta_erro ");
				sql2.append("(ale_codigo,err_categoria,err_descricao) ");
				sql2.append("VALUES (?,?,?) ");
				
				Connection conexao2 = ConexaoFactory.conectar();
				
				PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
				
				comando2.setLong(1, idObjeto);
				comando2.setString(2, listaerros.get(i).getCategoria());
				comando2.setString(3, listaerros.get(i).getDescricao());
				
				
				comando2.executeUpdate();
			
		  }
		
	}
	
	public Alerta buscarPorCodigo (int codigo) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.ale_codigo, a.bov_codigo, b.bov_nome, a.ale_data, a.ale_categoria, a.med_codigo, m.med_mes, m.med_peso, m.med_altura,m.med_circunferencia ");
		sql.append("FROM tbl_alerta a, tbl_medidas m, tbl_bovinos b ");
		sql.append("WHERE a.bov_codigo = b.bov_codigo AND a.med_codigo = m.med_codigo AND a.ale_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, codigo);
		
		ResultSet resultado = comando.executeQuery();
		
		Alerta retorno = null;
		
		if(resultado.next()){
			retorno = new Alerta();
			retorno.setCodigo(resultado.getLong("ale_codigo"));
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));
			retorno.setBovino(b);
			Medida m = new Medida();
			m.setCodigo(resultado.getLong("med_codigo"));
			m.setMes(resultado.getInt("med_mes"));
			m.setPeso(resultado.getDouble("med_peso"));
			m.setAltura(resultado.getDouble("med_altura"));
			m.setCircunferencia(resultado.getDouble("med_circunferencia"));
			retorno.setMedida(m);
			retorno.setData(resultado.getDate("ale_data"));
			retorno.setCategoria(resultado.getString("ale_categoria"));
		}
		
		return retorno;
		
	}
	
	public static void main(String[] args) {
		Alerta ale = new Alerta();
		Bovino b = new Bovino();
		b.setCodigo(1L);
		ale.setBovino(b);
		ale.setCategoria("pessoal");
		try {
			ale.setData(new SimpleDateFormat("dd/MM/yyyy").parse("01/04/1982"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<AlertaErro> listadeerros = new ArrayList<>();
		AlertaErro erro = new AlertaErro();
		erro.setCategoria("pessoal");
		erro.setDescricao("se distanciou 0,2");
		listadeerros.add(erro);
		AlertaErro erro2 = new AlertaErro();
		erro2.setCategoria("literario");
		erro2.setDescricao("se distanciou 0,5");
		listadeerros.add(erro2);
		AlertaDAO dao = new AlertaDAO();
		try {
			dao.criar(ale,listadeerros);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
