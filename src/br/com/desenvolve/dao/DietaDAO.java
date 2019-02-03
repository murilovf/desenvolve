package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Dieta;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.factory.ConexaoFactory;

public class DietaDAO {
	
	public void salvarPraTodos(Dieta dieta, ArrayList<DietaItem> itensDieta, Double valorTotalDieta) throws SQLException{
		
		ArrayList<Bovino> listaBovino = new ArrayList<Bovino>();
		BovinoDAO dao = new BovinoDAO();
		listaBovino = dao.listar();
		
		for (int i = 0; i < listaBovino.size(); i++) {
			int idObjeto = 0;
			
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO tbl_dietas ");
			sql.append("(bov_codigo,die_nome,die_valor_total,die_data_inicial,die_data_final) ");
			sql.append("VALUES (?,?,?,?,?) ");
			
			Connection conexao = ConexaoFactory.conectar();
			
			PreparedStatement comando = conexao.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			
			comando.setLong(1, listaBovino.get(i).getCodigo());
			comando.setString(2, dieta.getNome());
			comando.setDouble(3, valorTotalDieta);
			comando.setDate(4, dieta.getDataInicial()!=null ? new java.sql.Date(dieta.getDataInicial().getTime()) : null);
			comando.setDate(5, dieta.getDataFinal()!=null ? new java.sql.Date(dieta.getDataFinal().getTime()) : null);
			
			comando.executeUpdate();
			
			ResultSet rs = comando.getGeneratedKeys();

			while(rs.next()){
				idObjeto = rs.getInt(1);
			}
			
			for (int j = 0; j < itensDieta.size(); j++) {
				  
				StringBuilder sql2 = new StringBuilder();
				sql2.append("INSERT INTO tbl_dietas_item ");
				sql2.append("(die_codigo,ali_codigo,dit_quantidade,dit_hora,dit_valor) ");
				sql2.append("VALUES (?,?,?,?,?) ");
				
				Connection conexao2 = ConexaoFactory.conectar();
				
				PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
				
				comando2.setLong(1, idObjeto);
				comando2.setLong(2, itensDieta.get(j).getAlimento().getCodigo());
				comando2.setDouble(3, itensDieta.get(j).getQuantidade());
				comando2.setTime(4, itensDieta.get(j).getHora() != null ? new Time(itensDieta.get(j).getHora().getTime()): null);
				comando2.setDouble(5, itensDieta.get(j).getValor());
				
				
				comando2.executeUpdate();
			
		  }
		}
		
//		StringBuilder sql3 = new StringBuilder();
//		sql3.append("DELETE FROM tbl_dietas");
//		
//		Connection conexao3 = ConexaoFactory.conectar();
//		
//		PreparedStatement comando3 = conexao3.prepareStatement(sql3.toString());
//		
//		comando3.executeUpdate();
		
	}
	
	public ArrayList<Dieta> listarIndividual(Bovino b) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT die_codigo, bov_codigo, die_nome, die_valor_total,die_data_inicial,die_data_final ");
		sql.append("FROM tbl_dietas ");
		sql.append("WHERE bov_codigo=? ");
		sql.append("ORDER BY die_codigo DESC");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, b.getCodigo());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Dieta> lista = new ArrayList<Dieta>();

		while(resultado.next()){
			Dieta a = new Dieta();
			a.setCodigo(resultado.getLong("die_codigo"));
			a.setBovino(b);
			a.setNome(resultado.getString("die_nome"));
			a.setValorTotal(resultado.getDouble("die_valor_total"));
			a.setDataInicial(resultado.getDate("die_data_inicial"));
			a.setDataFinal(resultado.getDate("die_data_final"));

			lista.add(a);
		}

		return lista;

	}
	
	public ArrayList<DietaItem> listarItensDieta(Dieta di) throws SQLException{

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.dit_codigo, i.die_codigo, i.ali_codigo, a.ali_descricao, a.ali_preco, i.dit_hora, i.dit_quantidade, i.dit_valor ");
		sql.append("FROM tbl_dietas_item AS i ");
		sql.append("LEFT JOIN tbl_alimentos AS a ON (a.ali_codigo = i.ali_codigo) ");
		sql.append("WHERE i.die_codigo=? ");
		sql.append("ORDER BY i.die_codigo DESC");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setLong(1, di.getCodigo());

		ResultSet resultado = comando.executeQuery();

		ArrayList<DietaItem> lista = new ArrayList<DietaItem>();

		while(resultado.next()){
			DietaItem i = new DietaItem();
			i.setCodigo(resultado.getLong("dit_codigo"));
			Dieta d = new Dieta();
			d.setCodigo(resultado.getLong("die_codigo"));
			i.setDieta(d);
			Alimento a = new Alimento();
			a.setCodigo(resultado.getLong("ali_codigo"));
			a.setDescricao(resultado.getString("ali_descricao"));
			a.setPreco(resultado.getDouble("ali_preco"));
			i.setAlimento(a);			
			i.setHora(resultado.getTime("dit_hora"));
			i.setQuantidade(resultado.getDouble("dit_quantidade"));
			i.setValor(resultado.getDouble("dit_valor"));

			lista.add(i);
		}

		return lista;

	}
	
	public void salvarDieta(Dieta dieta, ArrayList<DietaItem> dietaItens, Bovino bovino, Double valorTotalDieta) throws SQLException{
		int idObjeto = 0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_dietas ");
		sql.append("(bov_codigo,die_nome,die_valor_total,die_data_inicial,die_data_final) ");
		sql.append("VALUES (?,?,?,?,?) ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
		
		comando.setLong(1, bovino.getCodigo());
		comando.setString(2, dieta.getNome());
		comando.setDouble(3, valorTotalDieta);
		comando.setDate(4, dieta.getDataInicial()!=null ? new java.sql.Date(dieta.getDataInicial().getTime()) : null);
		comando.setDate(5, dieta.getDataFinal()!=null ? new java.sql.Date(dieta.getDataFinal().getTime()) : null);
		
		comando.executeUpdate();
		
		ResultSet rs = comando.getGeneratedKeys();

		while(rs.next()){
			idObjeto = rs.getInt(1);
		}
		
		for (int i = 0; i < dietaItens.size(); i++) {
			  
			StringBuilder sql2 = new StringBuilder();
			sql2.append("INSERT INTO tbl_dietas_item ");
			sql2.append("(die_codigo,ali_codigo,dit_quantidade,dit_hora,dit_valor) ");
			sql2.append("VALUES (?,?,?,?,?) ");
			
			Connection conexao2 = ConexaoFactory.conectar();
			
			PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
			
			comando2.setLong(1, idObjeto);
			comando2.setLong(2, dietaItens.get(i).getAlimento().getCodigo());
			comando2.setDouble(3, dietaItens.get(i).getQuantidade());
			comando2.setTime(4, dietaItens.get(i).getHora() != null ? new Time(dietaItens.get(i).getHora().getTime()): null);
			comando2.setDouble(5, dietaItens.get(i).getValor());
			
			
			comando2.executeUpdate();
		
	  }
	}
	
	public void editarDieta(Dieta dieta, ArrayList<DietaItem> dietaItens, Bovino bovino, Double valorTotalDieta) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_dietas SET ");
		sql.append("die_nome = ?, ");
		sql.append("die_valor_total = ?, ");
		sql.append("die_data_inicial = ?, ");
		sql.append("die_data_final = ? ");		
		sql.append("WHERE die_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, dieta.getNome());
		comando.setDouble(2, valorTotalDieta);
		comando.setDate(3, dieta.getDataInicial()!=null ? new java.sql.Date(dieta.getDataInicial().getTime()) : null);
		comando.setDate(4, dieta.getDataFinal()!=null ? new java.sql.Date(dieta.getDataFinal().getTime()) : null);
		comando.setLong(5, dieta.getCodigo());
		
		System.out.println(dieta);
		
		comando.executeUpdate();
		
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM tbl_dietas_item ");
		sql2.append("WHERE die_codigo = ?");
		
		Connection conexao2 = ConexaoFactory.conectar();

		PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
		comando2.setLong(1, dieta.getCodigo());

		comando2.executeUpdate();
				
		for (int i = 0; i < dietaItens.size(); i++) {
			  
			StringBuilder sql3 = new StringBuilder();
			sql3.append("INSERT INTO tbl_dietas_item ");
			sql3.append("(die_codigo,ali_codigo,dit_quantidade,dit_hora,dit_valor) ");
			sql3.append("VALUES (?,?,?,?,?) ");
			
			Connection conexao3 = ConexaoFactory.conectar();
			
			PreparedStatement comando3 = conexao3.prepareStatement(sql3.toString());
			
			comando3.setLong(1, dieta.getCodigo());
			comando3.setLong(2, dietaItens.get(i).getAlimento().getCodigo());
			comando3.setDouble(3, dietaItens.get(i).getQuantidade());
			comando3.setTime(4, dietaItens.get(i).getHora() != null ? new Time(dietaItens.get(i).getHora().getTime()): null);
			comando3.setDouble(5, dietaItens.get(i).getValor());
			
			
			comando3.executeUpdate();
		
	  }
	}
	
	public void excluir(Dieta d) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_dietas_item ");
		sql.append("WHERE die_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, d.getCodigo());

		comando.executeUpdate();
		
		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE FROM tbl_dietas ");
		sql2.append("WHERE die_codigo = ?");

		Connection conexao2 = ConexaoFactory.conectar();

		PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
		comando2.setLong(1, d.getCodigo());

		comando2.executeUpdate();

	}
	
	public void excluirTodasIndividual(Bovino v) throws SQLException{

		StringBuilder sql2 = new StringBuilder();
		sql2.append("DELETE i.* FROM tbl_dietas_item AS i ");
		sql2.append("LEFT JOIN tbl_dietas AS d ON (d.die_codigo = i.die_codigo) ");
		sql2.append("WHERE d.bov_codigo = ?");

		Connection conexao2 = ConexaoFactory.conectar();

		PreparedStatement comando2 = conexao2.prepareStatement(sql2.toString());
		comando2.setLong(1, v.getCodigo());

		comando2.executeUpdate();
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_dietas ");
		sql.append("WHERE bov_codigo = ?");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, v.getCodigo());

		comando.executeUpdate();

	}
	
			

}
