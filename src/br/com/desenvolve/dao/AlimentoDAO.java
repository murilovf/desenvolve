package br.com.desenvolve.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.factory.ConexaoFactory;


public class AlimentoDAO {

	public void salvar(Alimento a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_alimentos ");
		sql.append("(ali_descricao,ali_categoria,ali_ms,ali_pb,ali_ee,ali_mm,ali_fnd,ali_fda,ali_ndt,ali_preco) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?)");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		
		comando.setString(1, a.getDescricao());
		comando.setString(2, a.getCategoria());
		comando.setDouble(3, a.getMs() != null ? a.getMs() : 0);
		comando.setDouble(4, a.getPb() != null ? a.getPb() : 0);
		comando.setDouble(5, a.getEe() != null ? a.getEe() : 0);
		comando.setDouble(6, a.getMm() != null ? a.getMm() : 0);
		comando.setDouble(7, a.getFnd() != null ? a.getFnd() : 0);
		comando.setDouble(8, a.getFda() != null ? a.getFda() : 0);
		comando.setDouble(9, a.getNdt() != null ? a.getNdt() : 0);
		comando.setDouble(10, a.getPreco() != null ? a.getPreco() : 0);
		
		comando.executeUpdate();
	}
	
	public void excluir(Alimento a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_alimentos ");
		sql.append("WHERE ali_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void editar(Alimento a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_alimentos ");
		sql.append("SET ali_descricao = ?,ali_categoria = ?,ali_ms = ?, ali_pb = ?, ali_ee = ?, ali_mm = ?, ali_fnd = ?, ali_fda = ?,ali_ndt = ?, ali_preco = ? ");
		sql.append("WHERE ali_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, a.getDescricao());
		comando.setString(2, a.getCategoria());
		comando.setDouble(3, a.getMs() != null ? a.getMs() : 0);
		comando.setDouble(4, a.getPb() != null ? a.getPb() : 0);
		comando.setDouble(5, a.getEe() != null ? a.getEe() : 0);
		comando.setDouble(6, a.getMm() != null ? a.getMm() : 0);
		comando.setDouble(7, a.getFnd() != null ? a.getFnd() : 0);
		comando.setDouble(8, a.getFda() != null ? a.getFda() : 0);
		comando.setDouble(9, a.getNdt() != null ? a.getNdt() : 0);
		comando.setDouble(10, a.getPreco() != null ? a.getPreco() : 0);

		comando.setLong(11, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public Alimento buscarPorCodigo(Alimento a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ali_codigo,ali_descricao,ali_categoria,ali_ms,ali_pb,ali_ee,ali_mm,ali_fnd,ali_fda,ali_ndt,ali_preco ");
		sql.append("FROM tbl_alimentos ");
		sql.append("WHERE ali_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Alimento retorno = null;
		
		if(resultado.next()){
			retorno = new Alimento();

			retorno.setCodigo(resultado.getLong("ali_codigo"));
			retorno.setDescricao(resultado.getString("ali_descricao"));
			retorno.setCategoria(resultado.getString("ali_categoria"));
			retorno.setMs(resultado.getDouble("ali_ms"));
			retorno.setPb(resultado.getDouble("ali_pb"));
			retorno.setEe(resultado.getDouble("ali_ee"));
			retorno.setMm(resultado.getDouble("ali_mm"));
			retorno.setFnd(resultado.getDouble("ali_fnd"));
			retorno.setFda(resultado.getDouble("ali_fda"));
			retorno.setNdt(resultado.getDouble("ali_ndt"));
			retorno.setPreco(resultado.getDouble("ali_preco"));
		}
		
		return retorno;
		
	}
	
	public ArrayList<Alimento> listar() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ali_codigo,ali_descricao,ali_categoria,ali_ms,ali_pb,ali_ee,ali_mm,ali_fnd,ali_fda,ali_ndt,ali_preco ");
		sql.append("FROM tbl_alimentos ");
		sql.append("ORDER BY ali_codigo ASC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Alimento> lista = new ArrayList<Alimento>();
		
		while(resultado.next()){
			Alimento a = new Alimento();
			a.setCodigo(resultado.getLong("ali_codigo"));
			a.setDescricao(resultado.getString("ali_descricao"));
			a.setCategoria(resultado.getString("ali_categoria"));
			a.setMs(resultado.getDouble("ali_ms"));
			a.setPb(resultado.getDouble("ali_pb"));
			a.setEe(resultado.getDouble("ali_ee"));
			a.setMm(resultado.getDouble("ali_mm"));
			a.setFnd(resultado.getDouble("ali_fnd"));
			a.setFda(resultado.getDouble("ali_fda"));
			a.setNdt(resultado.getDouble("ali_ndt"));
			a.setPreco(resultado.getDouble("ali_preco"));
			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	
}
