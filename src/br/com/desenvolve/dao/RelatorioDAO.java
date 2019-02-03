package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.desenvolve.domain.Alimento;
import br.com.desenvolve.domain.Bovino;
import br.com.desenvolve.domain.Dieta;
import br.com.desenvolve.domain.DietaItem;
import br.com.desenvolve.domain.Medicamento;
import br.com.desenvolve.domain.Medida;
import br.com.desenvolve.domain.Vacina;
import br.com.desenvolve.factory.ConexaoFactory;

public class RelatorioDAO {

	public ArrayList<Bovino> listarBovino(Bovino bovinoFiltro, Date datanascimentoInicial, Date datanascimentoFinal,
			String ordem) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT bov_codigo,bov_nome,bov_data_nascimento,bov_raca,bov_origem ");
		sql.append("FROM tbl_bovinos ");
		sql.append("WHERE true ");

		if (bovinoFiltro.getNome().trim().length() > 0) {
			sql.append("AND bov_nome LIKE ('%" + bovinoFiltro.getNome() + "%') ");
		}

		if (bovinoFiltro.getOrigem().trim().length() > 0) {
			sql.append("AND bov_origem LIKE ('%" + bovinoFiltro.getOrigem() + "%') ");
		}
		
		if (bovinoFiltro.getSituacao() != 2) {
			sql.append("AND bov_situacao = " + bovinoFiltro.getSituacao() + " ");
		}

		if (bovinoFiltro.getRaca().trim().length() > 0) {
			sql.append("AND bov_raca LIKE ('%" + bovinoFiltro.getRaca() + "%') ");
		}

		if (datanascimentoInicial != null && datanascimentoFinal != null) {
			sql.append("AND bov_data_nascimento BETWEEN '" + new java.sql.Date(datanascimentoInicial.getTime())
					+ "' AND ");
			sql.append("'" + new java.sql.Date(datanascimentoFinal.getTime()) + "' ");
		}

		if (ordem.equals("nome")) {
			sql.append("ORDER BY bov_nome ASC");
		} else if (ordem.equals("origem")) {
			sql.append("ORDER BY bov_origem ASC");
		} else if (ordem.equals("raca")) {
			sql.append("ORDER BY bov_raca ASC");
		} else if (ordem.equals("data")) {
			sql.append("ORDER BY bov_data_nascimento ASC");
		} else {
			sql.append("ORDER BY bov_codigo ASC");
		}

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Bovino> lista = new ArrayList<Bovino>();

		while (resultado.next()) {
			Bovino a = new Bovino();
			a.setCodigo(resultado.getLong("bov_codigo"));
			a.setNome(resultado.getString("bov_nome"));
			a.setDatanascimento(resultado.getDate("bov_data_nascimento"));
			a.setRaca(resultado.getString("bov_raca"));
			a.setOrigem(resultado.getString("bov_origem"));

			lista.add(a);
		}

		return lista;

	}

	public ArrayList<Vacina> listarVacinas(Vacina vacinaFiltro, Date dataVacinaInicial, Date dataVacinaFinal,
			boolean aplicado, boolean naoAplicado) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT v.vac_codigo, v.bov_codigo, b.bov_nome, v.vac_nome, vac_situacao, vac_data, vac_finalidade ");
		sql.append("FROM tbl_vacinas AS v ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = v.bov_codigo) ");
		sql.append("WHERE true ");

		if (vacinaFiltro.getNome().trim().length() > 0) {
			sql.append("AND v.vac_nome LIKE ('%" + vacinaFiltro.getNome() + "%') ");
		}
		
		if (vacinaFiltro.getFinalidade().trim().length() > 0) {
			sql.append("AND v.vac_finalidade LIKE ('%" + vacinaFiltro.getFinalidade() + "%') ");
		}

		if (vacinaFiltro.getBovino().getNome().trim().length() > 0) {
			sql.append("AND b.bov_nome LIKE ('%" + vacinaFiltro.getBovino().getNome() + "%') ");
		}

		if (dataVacinaInicial != null && dataVacinaFinal != null) {
			sql.append("AND vac_data BETWEEN '" + new java.sql.Date(dataVacinaInicial.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataVacinaFinal.getTime()) + "' ");
		}

		if (aplicado && naoAplicado) {
			sql.append(" ");
		} else if (aplicado) {
			sql.append("AND vac_situacao = 1 ");
		} else if (naoAplicado) {
			sql.append("AND vac_situacao = 0 ");
		}

		sql.append("ORDER BY v.bov_codigo, v.vac_data ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Vacina> lista = new ArrayList<Vacina>();

		while (resultado.next()) {
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Vacina v = new Vacina();
			v.setCodigo(resultado.getLong("vac_codigo"));
			v.setNome(resultado.getString("vac_nome"));
			v.setSituacao(resultado.getInt("vac_situacao"));
			v.setFinalidade(resultado.getString("vac_finalidade"));
			v.setDatavacina(resultado.getDate("vac_data"));
			v.setBovino(b);

			lista.add(v);
		}

		return lista;

	}

	public ArrayList<Medida> listarMedidas(Medida medidaFiltro, Date dataVacinaInicial, Date dataVacinaFinal,
			Double pesoInicial, Double pesoFinal, Double alturaInicial, Double alturaFinal,
			Double circunferenciaInicial, Double circunferenciaFinal) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT m.med_codigo, m.bov_codigo, b.bov_nome, m.med_peso, m.med_altura, m.med_circunferencia, m.med_data ");
		sql.append("FROM tbl_medidas AS m ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = m.bov_codigo) ");
		sql.append("WHERE true ");

		if (medidaFiltro.getBovino().getNome().trim().length() > 0) {
			sql.append("AND b.bov_nome LIKE ('%" + medidaFiltro.getBovino().getNome() + "%') ");
		}

		if (dataVacinaInicial != null && dataVacinaFinal != null) {
			sql.append("AND m.med_data BETWEEN '" + new java.sql.Date(dataVacinaInicial.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataVacinaFinal.getTime()) + "' ");
		}

		if (pesoInicial != null && pesoFinal != null) {
			sql.append("AND m.med_peso BETWEEN " + pesoInicial + " AND " + pesoFinal + " ");
		}

		if (alturaInicial != null && alturaFinal != null) {
			sql.append("AND m.med_altura BETWEEN " + alturaInicial + " AND " + alturaFinal + " ");
		}

		if (circunferenciaInicial != null && circunferenciaFinal != null) {
			sql.append(
					"AND m.med_circunferencia BETWEEN " + circunferenciaInicial + " AND " + circunferenciaFinal + " ");
		}

		sql.append("ORDER BY m.bov_codigo, m.med_data ASC ");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<Medida> lista = new ArrayList<Medida>();

		while (resultado.next()) {
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Medida m = new Medida();
			m.setCodigo(resultado.getLong("med_codigo"));
			m.setPeso(resultado.getDouble("med_peso"));
			m.setAltura(resultado.getDouble("med_altura"));
			m.setCircunferencia(resultado.getDouble("med_circunferencia"));
			m.setDatamedicao(resultado.getDate("med_data"));
			m.setBovino(b);

			lista.add(m);
		}

		return lista;

	}

	public ArrayList<Medicamento> listarMedicamentos(Medicamento medicamentoFiltro, Date dataMedicamentoInicial,
			Date dataMedicamentoFinal, Date dataMedicamentoInicial2, Date dataMedicamentoFinal2, Double dosagemInicial,
			Double dosagemFinal) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT m.mec_codigo, m.bov_codigo, b.bov_nome, m.mec_nome, m.mec_tipo, m.mec_dosagem, m.mec_data_inicio, m.mec_data_final, m.mec_observacao ");
		sql.append("FROM tbl_medicamentos AS m ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = m.bov_codigo) ");
		sql.append("WHERE true ");
		
		if (medicamentoFiltro.getNome().trim().length() > 0) {
			sql.append("AND m.mec_nome LIKE ('%" + medicamentoFiltro.getNome() + "%') ");
		}

		if (medicamentoFiltro.getBovino().getNome().trim().length() > 0) {
			sql.append("AND b.bov_nome LIKE ('%" + medicamentoFiltro.getBovino().getNome() + "%') ");
		}
		
		if (dataMedicamentoInicial != null && dataMedicamentoFinal != null) {
			sql.append("AND m.mec_data_inicio BETWEEN '" + new java.sql.Date(dataMedicamentoInicial.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataMedicamentoFinal.getTime()) + "' ");
		}
		
		if (dataMedicamentoInicial2 != null && dataMedicamentoFinal2 != null) {
			sql.append("AND m.mec_data_final BETWEEN '" + new java.sql.Date(dataMedicamentoInicial2.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataMedicamentoFinal2.getTime()) + "' ");
		}
		
		if (medicamentoFiltro.getTipo().trim().length() > 0) {
			sql.append("AND m.mec_tipo LIKE ('%" + medicamentoFiltro.getTipo() + "%') ");
		}
		
		if (dosagemInicial != null && dosagemFinal != null) {
			sql.append("AND m.mec_dosagem BETWEEN " + dosagemInicial + " AND " + dosagemFinal + " ");
		}
		
		sql.append("ORDER BY m.bov_codigo ASC");

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
			m.setTipo(resultado.getString("mec_tipo"));
			m.setDosagem(resultado.getDouble("mec_dosagem"));
			m.setDatainicio(resultado.getDate("mec_data_inicio"));
			m.setDatafinal(resultado.getDate("mec_data_final"));
			m.setObservacao(resultado.getString("mec_observacao"));
			m.setBovino(b);

			lista.add(m);
		}

		return lista;

	}
	
	public ArrayList<DietaItem> listarDietas(Dieta dietaFiltro, Alimento alimentoFiltro, Date dataDietaInicial,
			Date dataDietaFinal, Date dataDietaInicial2, Date dataDietaFinal2, Double valorInicial,
			Double valorFinal) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT d.die_codigo, d.bov_codigo, b.bov_nome, d.die_nome, d.die_valor_total, d.die_data_inicial, d.die_data_final, ");
		sql.append("i.dit_codigo, i.dit_quantidade, i.dit_valor, i.dit_hora, a.ali_codigo, a.ali_descricao, a.ali_preco ");
		sql.append("FROM tbl_dietas AS d ");
		sql.append("LEFT JOIN tbl_bovinos AS b ON (b.bov_codigo = d.bov_codigo) ");
		sql.append("LEFT JOIN tbl_dietas_item AS i ON (i.die_codigo = d.die_codigo) ");
		sql.append("LEFT JOIN tbl_alimentos AS a ON (a.ali_codigo = i.ali_codigo) ");
		sql.append("WHERE true ");
		
		if (dietaFiltro.getNome().trim().length() > 0) {
			sql.append("AND d.die_nome LIKE ('%" + dietaFiltro.getNome() + "%') ");
		}

		if (dietaFiltro.getBovino().getNome().trim().length() > 0) {
			sql.append("AND b.bov_nome LIKE ('%" + dietaFiltro.getBovino().getNome() + "%') ");
		}
		
		if (dataDietaInicial != null && dataDietaFinal != null) {
			sql.append("AND d.die_data_inicial BETWEEN '" + new java.sql.Date(dataDietaInicial.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataDietaFinal.getTime()) + "' ");
		}
		
		if (dataDietaInicial2 != null && dataDietaFinal2 != null) {
			sql.append("AND d.die_data_final BETWEEN '" + new java.sql.Date(dataDietaInicial2.getTime()) + "' AND ");
			sql.append("'" + new java.sql.Date(dataDietaFinal2.getTime()) + "' ");
		}
		
		if (alimentoFiltro.getDescricao().trim().length() > 0) {
			sql.append("AND a.ali_descricao LIKE ('%" + alimentoFiltro.getDescricao() + "%') ");
		}
		System.out.println(valorInicial);
		if (valorInicial != null && valorFinal != null) {
			sql.append("AND d.die_valor_total BETWEEN " + valorInicial + " AND " + valorFinal + " ");
		}
		
		sql.append("ORDER BY d.bov_codigo, d.die_codigo ASC");

		Connection conexao = ConexaoFactory.conectar();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());

		ResultSet resultado = comando.executeQuery();

		ArrayList<DietaItem> lista = new ArrayList<DietaItem>();

		while (resultado.next()) {
			Bovino b = new Bovino();
			b.setCodigo(resultado.getLong("bov_codigo"));
			b.setNome(resultado.getString("bov_nome"));

			Dieta d = new Dieta();
			d.setCodigo(resultado.getLong("die_codigo"));
			d.setNome(resultado.getString("die_nome"));
			d.setValorTotal(resultado.getDouble("die_valor_total"));
			d.setDataInicial(resultado.getDate("die_data_inicial"));
			d.setDataFinal(resultado.getDate("die_data_final"));
			d.setBovino(b);
			
			Alimento a = new Alimento();
			a.setCodigo(resultado.getLong("ali_codigo"));
			a.setDescricao(resultado.getString("ali_descricao"));
			a.setPreco(resultado.getDouble("ali_preco"));
			
			DietaItem i = new DietaItem();
			i.setCodigo(resultado.getLong("dit_codigo"));
			i.setQuantidade(resultado.getDouble("dit_quantidade"));
			i.setValor(resultado.getDouble("dit_valor"));
			i.setHora(resultado.getTime("dit_hora"));
			i.setAlimento(a);
			i.setDieta(d);

			lista.add(i);
		}

		return lista;

	}

}
