package br.com.desenvolve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.desenvolve.domain.Alerta;
import br.com.desenvolve.domain.AlertaErro;
import br.com.desenvolve.factory.ConexaoFactory;


public class AlertaErroDAO {
	
public ArrayList<AlertaErro> listarIndividual(Alerta b) throws SQLException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT err_codigo, ale_codigo, err_categoria, err_descricao ");
		sql.append("FROM tbl_alerta_erro ");
		sql.append("WHERE ale_codigo=? ");
		
		Connection conexao = ConexaoFactory.conectar();
		
		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		comando.setLong(1, b.getCodigo());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList<AlertaErro> lista = new ArrayList<AlertaErro>();
		
		while(resultado.next()){
			AlertaErro a = new AlertaErro();
			a.setCodigo(resultado.getLong("err_codigo"));
			a.setAlerta(b);
			a.setCategoria(resultado.getString("err_categoria"));
			a.setDescricao(resultado.getString("err_descricao"));

			
			lista.add(a);
		}
		
		return lista;
		
	}

}
