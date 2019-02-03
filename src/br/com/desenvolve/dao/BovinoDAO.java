package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.factory.ConexaoFactory;

public class BovinoDAO {
	
	public void salvar(Bovino u) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_bovinos ");
		sql.append("(bov_nome,bov_data_nascimento,bov_raca,bov_pai,bov_mae,bov_origem) ");
		sql.append("VALUES (?,?,?,?,?,?)");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, u.getNome());
		comando.setDate(2, new java.sql.Date(u.getDatanascimento().getTime()));
		comando.setString(3, u.getRaca());
		comando.setString(4, u.getPai());
		comando.setString(5, u.getMae());
		comando.setString(6, u.getOrigem());

		
		comando.executeUpdate();
	}

	
	public ArrayList<Bovino> listar() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bov_codigo,bov_nome,bov_data_nascimento,bov_raca,bov_pai,bov_mae,bov_origem,bov_situacao,bov_destino ");
		sql.append("FROM tbl_bovinos ");
		sql.append("ORDER BY bov_situacao DESC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Bovino> lista = new ArrayList<Bovino>();
		
		while(resultado.next()){
			Bovino a = new Bovino();
			a.setCodigo(resultado.getLong("bov_codigo"));
			a.setNome(resultado.getString("bov_nome"));
			a.setDatanascimento(resultado.getDate("bov_data_nascimento"));
			a.setRaca(resultado.getString("bov_raca"));
			a.setPai(resultado.getString("bov_pai"));
			a.setMae(resultado.getString("bov_mae"));
			a.setOrigem(resultado.getString("bov_origem"));
			a.setSituacao(resultado.getInt("bov_situacao"));
			a.setDestino(resultado.getString("bov_destino"));
			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	public ArrayList<Bovino> listarAtivos() throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bov_codigo,bov_nome,bov_data_nascimento,bov_raca,bov_pai,bov_mae,bov_origem,bov_situacao,bov_destino ");
		sql.append("FROM tbl_bovinos ");
		sql.append("WHERE bov_situacao=1 ");
		sql.append("ORDER BY bov_situacao DESC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Bovino> lista = new ArrayList<Bovino>();
		
		while(resultado.next()){
			Bovino a = new Bovino();
			a.setCodigo(resultado.getLong("bov_codigo"));
			a.setNome(resultado.getString("bov_nome"));
			a.setDatanascimento(resultado.getDate("bov_data_nascimento"));
			a.setRaca(resultado.getString("bov_raca"));
			a.setPai(resultado.getString("bov_pai"));
			a.setMae(resultado.getString("bov_mae"));
			a.setOrigem(resultado.getString("bov_origem"));
			a.setSituacao(resultado.getInt("bov_situacao"));
			a.setDestino(resultado.getString("bov_destino"));
			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	public void excluir(Bovino a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_bovinos ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void ativar(Bovino a) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bovinos ");
		sql.append("SET bov_situacao = ?, bov_destino = ? ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setInt(1, 1);
		comando.setString(2, null);
		comando.setLong(3, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void inativar(Bovino a) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bovinos ");
		sql.append("SET bov_situacao = ?, bov_destino = ? ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setInt(1, 0);
		comando.setString(2, a.getDestino());
		comando.setLong(3, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void editar(Bovino a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bovinos ");
		sql.append("SET bov_nome = ?, bov_data_nascimento = ?, bov_raca = ?, bov_pai = ?, bov_mae = ?, bov_origem = ?, bov_situacao = ?, bov_destino = ? ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, a.getNome());
		comando.setDate(2, new java.sql.Date(a.getDatanascimento().getTime()));
		comando.setString(3, a.getRaca());
		comando.setString(4, a.getPai());
		comando.setString(5, a.getMae());
		comando.setString(6, a.getOrigem());
		comando.setInt(7, a.getSituacao());
		comando.setString(8, a.getDestino());
		comando.setLong(9, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void salvarQR(Bovino a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_bovinos ");
		sql.append("SET bov_qr = ? ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setString(1, a.getFoto());
		comando.setLong(2, a.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public Bovino buscarPorCodigo(Bovino a) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bov_codigo,bov_nome,bov_data_nascimento,bov_raca,bov_pai,bov_mae,bov_origem,bov_qr,bov_situacao,bov_destino ");
		sql.append("FROM tbl_bovinos ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Bovino retorno = null;
		
		if(resultado.next()){
			retorno = new Bovino();

			retorno.setCodigo(resultado.getLong("bov_codigo"));
			retorno.setNome(resultado.getString("bov_nome"));
			retorno.setDatanascimento(resultado.getDate("bov_data_nascimento"));
			retorno.setRaca(resultado.getString("bov_raca"));
			retorno.setPai(resultado.getString("bov_pai"));
			retorno.setMae(resultado.getString("bov_mae"));
			retorno.setOrigem(resultado.getString("bov_origem"));
			retorno.setFoto(resultado.getString("bov_qr"));
			retorno.setSituacao(resultado.getInt("bov_situacao"));
			retorno.setDestino(resultado.getString("bov_destino"));


		}
		
		return retorno;
		
	}
	
	public Double buscarPeso(Bovino a, String ordem) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m.med_peso ");
		sql.append("FROM tbl_bovinos AS b ");
		sql.append("LEFT OUTER JOIN tbl_medidas AS m ON (m.bov_codigo = b.bov_codigo) ");
		sql.append("WHERE b.bov_codigo = ? ");
		sql.append("ORDER BY m.med_dias " + ordem +  " LIMIT 1 ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, a.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		Double peso = 0.0;
		
		if(resultado.next()){
			peso = resultado.getDouble("med_peso");
		}
		
		return peso;
		
	}
	
	

}
