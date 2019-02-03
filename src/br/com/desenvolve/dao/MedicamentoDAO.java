package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.factory.ConexaoFactory;

public class MedicamentoDAO {
	
	public void salvar(Medicamento v) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tbl_medicamentos ");
		sql.append("(bov_codigo,mec_nome,mec_peso,mec_dosagem,mec_data_inicio,mec_data_final,mec_hora,mec_tipo,mec_diagnostico,mec_observacao) ");
		sql.append("VALUES (?,?,?,?,?,?,?,?,?,?) ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		

		
		comando.setLong(1, v.getBovino().getCodigo());
		comando.setString(2, v.getNome());
		comando.setDouble(3, v.getPeso());
		comando.setDouble(4, v.getDosagem());
		comando.setDate(5, new java.sql.Date(v.getDatainicio().getTime()));
		comando.setDate(6, new java.sql.Date(v.getDatafinal().getTime()));
		comando.setTime(7, new Time(v.getHora().getTime()));
		comando.setString(8, v.getTipo());
		comando.setString(9, v.getDiagnostico());
		comando.setString(10, v.getObservacao() !=null ? v.getObservacao() : "");
		
		comando.executeUpdate();
		
		
	}

	public ArrayList<Medicamento> listar() throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m.mec_codigo, m.bov_codigo, b.bov_nome, m.mec_peso, m.mec_nome, m.mec_tipo, m.mec_dosagem, m.mec_data_inicio, m.mec_data_final, m.mec_hora, m.mec_diagnostico, m.mec_observacao ");
		sql.append("FROM tbl_medicamentos AS m ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = m.bov_codigo) ");
		sql.append("ORDER BY m.mec_codigo ASC");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Medicamento> lista = new ArrayList<Medicamento>();

		while (resultado.next()) {
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Medicamento m = new Medicamento();
			m.setCodigo(resultado.getLong("mec_codigo"));
			m.setNome(resultado.getString("mec_nome"));
			m.setPeso(resultado.getDouble("mec_peso"));
			m.setTipo(resultado.getString("mec_tipo"));
			m.setDosagem(resultado.getDouble("mec_dosagem"));
			m.setDatainicio(resultado.getDate("mec_data_inicio"));
			m.setDatafinal(resultado.getDate("mec_data_final"));
			m.setHora(resultado.getTime("mec_hora"));
			m.setDiagnostico(resultado.getString("mec_diagnostico"));
			m.setObservacao(resultado.getString("mec_observacao"));
			m.setBovino(b);

			lista.add(m);
		}

		return lista;

	}
	
	public void excluirTodosIndividual(Bovino v) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_medicamentos ");
		sql.append("WHERE bov_codigo = ?");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, v.getCodigo());
		
		comando.executeUpdate();
		
	}

	public void excluir(Medicamento m) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM tbl_medicamentos ");
		sql.append("WHERE mec_codigo = ?");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		comando.setLong(1, m.getCodigo());

		comando.executeUpdate();

	}

	public void editar(Medicamento m) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbl_medicamentos ");
		sql.append("SET mec_nome = ?, mec_peso = ?, mec_dosagem = ?, bov_codigo = ?, ");
		sql.append("mec_tipo = ?, mec_data_inicio = ?, mec_data_final = ?, mec_hora = ?, mec_diagnostico = ?, mec_observacao = ? ");
		sql.append("WHERE mec_codigo = ? ");

		Connection conexao = ConexaoFactory.conectar();
		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		comando.setString(1, m.getNome());
		comando.setDouble(2, m.getPeso());
		comando.setDouble(3, m.getDosagem());
		comando.setLong(4, m.getBovino().getCodigo());
		comando.setString(5, m.getTipo());
		comando.setDate(6, new java.sql.Date(m.getDatainicio().getTime()));
		comando.setDate(7, new java.sql.Date(m.getDatafinal().getTime()));
		comando.setTime(8, new Time(m.getHora().getTime()));
		comando.setString(9, m.getDiagnostico());
		comando.setString(10, m.getObservacao());
		comando.setLong(11, m.getCodigo());

		comando.executeUpdate();
	}
	
	public void salvarParaTodos(ArrayList<Medicamento> medicamentos) throws SQLException{		
		
		for (int i = 0; i < medicamentos.size(); i++) {
				
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO tbl_medicamentos ");
				sql.append("(bov_codigo,mec_nome,mec_peso,mec_dosagem,mec_data_inicio,mec_data_final,mec_hora,mec_tipo,mec_diagnostico,mec_observacao) ");
				sql.append("VALUES (?,?,?,?,?,?,?,?,?,?) ");
				
				Connection conexao = ConexaoFactory.conectar();
				
				PreparedStatement comando = conexao.prepareStatement(sql.toString());
				
				comando.setLong(1, medicamentos.get(i).getBovino().getCodigo());
				comando.setString(2, medicamentos.get(i).getNome());
				comando.setDouble(3, medicamentos.get(i).getPeso());
				comando.setDouble(4, medicamentos.get(i).getDosagem());
				comando.setDate(5, new java.sql.Date(medicamentos.get(i).getDatainicio().getTime()));
				comando.setDate(6, new java.sql.Date(medicamentos.get(i).getDatafinal().getTime()));
				comando.setTime(7, new Time(medicamentos.get(i).getHora().getTime()));
				comando.setString(8, medicamentos.get(i).getTipo());
				comando.setString(9, medicamentos.get(i).getDiagnostico());
				comando.setString(10, medicamentos.get(i).getObservacao() !=null ? medicamentos.get(i).getObservacao() : "");
				
				comando.executeUpdate();			
			
		}
		
	}
	
	public ArrayList<Medicamento> listarIndividual(Bovino b) throws SQLException{
		
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT mec_codigo,bov_codigo,mec_nome, mec_peso, mec_dosagem, mec_data_inicio, mec_data_final, mec_hora, mec_tipo, mec_diagnostico, mec_observacao ");
		sql.append("FROM tbl_medicamentos ");
		sql.append("WHERE bov_codigo=? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, b.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
		
		while(resultado.next()){
			Medicamento a = new Medicamento();
			
			a.setCodigo(resultado.getLong("mec_codigo"));
			a.setBovino(b);
			a.setNome(resultado.getString("mec_nome"));
			a.setPeso(resultado.getDouble("mec_peso"));
			a.setDosagem(resultado.getDouble("mec_dosagem"));
			a.setDatainicio(resultado.getDate("mec_data_inicio"));
			a.setDatafinal(resultado.getDate("mec_data_final"));
			a.setHora(resultado.getTime("mec_hora"));
			a.setTipo(resultado.getString("mec_tipo"));
			a.setDiagnostico(resultado.getString("mec_diagnostico"));
			a.setObservacao(resultado.getString("mec_observacao"));
			
			
			lista.add(a);
		}
		
		return lista;
		
	}

}
