package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.factory.ConexaoFactory;

public class VacinaDAO {
	
	public ArrayList<Vacina> listar() throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT v.vac_codigo, v.bov_codigo, b.bov_nome, v.vac_nome,v.vac_finalidade, v.vac_situacao, v.vac_data, v.vac_dataAplicacao ");
		sql.append("FROM tbl_vacinas AS v ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = v.bov_codigo) ");
		sql.append("ORDER BY v.vac_data DESC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Vacina> lista = new ArrayList<Vacina>();
		
		while(resultado.next()){
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Vacina v = new Vacina();
			v.setCodigo(resultado.getLong("vac_codigo"));
			v.setNome(resultado.getString("vac_nome"));
			v.setFinalidade(resultado.getString("vac_finalidade"));
			v.setSituacao(resultado.getInt("vac_situacao"));
			v.setDatavacina(resultado.getDate("vac_data"));
			v.setDataaplicacao(resultado.getDate("vac_dataAplicacao"));
			v.setBovino(b);
			
			lista.add(v);
		}
		
		return lista;
		
	}
	
	public void salvar(Vacina v) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_vacinas ");
		sql.append("(bov_codigo,vac_nome,vac_finalidade,vac_situacao,vac_data,vac_dataAplicacao) ");
		sql.append("VALUES (?,?,?,?,?,?) ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, v.getBovino().getCodigo());
		comando.setString(2, v.getNome());
		comando.setString(3, v.getFinalidade());
		comando.setInt(4, v.getSituacao()!=null ? v.getSituacao() : 0);
		comando.setDate(5, new java.sql.Date(v.getDatavacina().getTime()));
		comando.setDate(6, v.getDataaplicacao()!=null ? new java.sql.Date(v.getDataaplicacao().getTime()) : null);
		
		comando.executeUpdate();
		
	}	
	
	public void salvarParaTodos(ArrayList<Vacina> vacinas) throws SQLException{		
		
		for (int i = 0; i < vacinas.size(); i++) {
				
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO tbl_vacinas ");
				sql.append("(bov_codigo,vac_nome,vac_finalidade,vac_situacao,vac_data,vac_dataAplicacao) ");
				sql.append("VALUES (?,?,?,?,?,?) ");
				
				Connection conexao = ConexaoFactory.conectar();
				
				PreparedStatement comando = conexao.prepareStatement(sql.toString());
				
				comando.setLong(1, vacinas.get(i).getBovino().getCodigo());
				comando.setString(2, vacinas.get(i).getNome());
				comando.setString(3, vacinas.get(i).getFinalidade());
				comando.setInt(4, vacinas.get(i).getSituacao());
				comando.setDate(5, new java.sql.Date(vacinas.get(i).getDatavacina().getTime()));
				comando.setDate(6, vacinas.get(i).getDataaplicacao()!=null ? new java.sql.Date(vacinas.get(i).getDataaplicacao().getTime()) : null);
				
				comando.executeUpdate();			
			
		}
		
	}
	
	public void excluir(Vacina v) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_vacinas ");
		sql.append("WHERE vac_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, v.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public void excluirTodasIndividual(Bovino v) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_vacinas ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, v.getCodigo());
		
		comando.executeUpdate();
		
	}
	
	public ArrayList<Vacina> listarIndividual(Bovino b) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT vac_codigo, bov_codigo,vac_nome,vac_finalidade, vac_situacao, vac_data, vac_dataAplicacao ");
		sql.append("FROM tbl_vacinas ");
		sql.append("WHERE bov_codigo=? ");
		sql.append("ORDER BY vac_data DESC");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, b.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Vacina> lista = new ArrayList<Vacina>();
		
		while(resultado.next()){
			Vacina a = new Vacina();
			a.setCodigo(resultado.getLong("vac_codigo"));
			a.setBovino(b);
			a.setNome(resultado.getString("vac_nome"));
			a.setFinalidade(resultado.getString("vac_finalidade"));
			a.setSituacao(resultado.getInt("vac_situacao"));
			a.setDatavacina(resultado.getDate("vac_data"));
			a.setDataaplicacao(resultado.getDate("vac_dataAplicacao"));
			
			lista.add(a);
		}
		
		return lista;
		
	}
	
	public void editar(Vacina v) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_vacinas ");
		sql.append("SET vac_nome = ?, vac_finalidade = ?, vac_data = ?, bov_codigo = ?, vac_dataAplicacao = ? ");
		sql.append("WHERE vac_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setString(1, v.getNome());
		comando.setString(2, v.getFinalidade());
		comando.setDate(3, new java.sql.Date(v.getDatavacina().getTime()));
		comando.setLong(4, v.getBovino().getCodigo());
		comando.setDate(5, new java.sql.Date(v.getDataaplicacao().getTime()));
		comando.setLong(6, v.getCodigo());
		
		
		comando.executeUpdate();
	}
	
	public void atualizaEstado(Vacina v) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_vacinas ");
		sql.append("SET vac_situacao = ?, vac_dataAplicacao = ? ");
		sql.append("WHERE vac_codigo = ? ");
		
		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setInt(1, v.getSituacao() == 1 ? 0 : 1);
		comando.setDate(2, v.getDataaplicacao() == null ? new java.sql.Date(new Date().getTime()) : null);
		comando.setLong(3, v.getCodigo());
		
		comando.executeUpdate();
	}
	
	public static void main(String[] args) {
/*		Bovino b = new Bovino();
		b.setCodigo(6L);
		Vacina u1 = new Vacina();
		u1.setBovino(b);
		u1.setNome("Omep");
		u1.setSituacao(0);
		try {
			u1.setDatavacina(new SimpleDateFormat("dd/MM/yyyy").parse("01/04/1982"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		VacinaDAO fdao = new VacinaDAO();
		
		try {
			fdao.salvar(u1);
			System.out.println("vacina Salvo com Sucesso!");
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao tentar salvar a Vacina!");
			e.printStackTrace();
		}*/
		
		
		
/*		Bovino b = new Bovino();
		b.setCodigo(6L);
		
		VacinaDAO fdao = new VacinaDAO();
		try {
			ArrayList<Vacina> lista = fdao.listarIndividual(b);
			for (Vacina f : lista) {
				System.out.println("Resultado: "+f);
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro listar as vacinas!");
			e.printStackTrace();
		}*/
	}

}
