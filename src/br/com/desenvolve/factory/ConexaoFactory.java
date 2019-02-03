package br.com.desenvolve.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoFactory {

/*	private static final String USUARIO = "desenvolve_usr";
	private static final String SENHA = "ghYtu57&ghy51";
	private static final String URL = "jdbc:mysql://localhost:3306/db_desenvolve?autoReconnect=true&useSSL=false";*/
	
	private static final String USUARIO = "root";
	private static final String SENHA = "q1w2e3r4";
	private static final String URL = "jdbc:mysql://localhost:3306/db_desenvolve?autoReconnect=true&useSSL=false";
	
	public static Connection conectar() throws SQLException{
		
		 try {
			 Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
		return conexao;
	}
	
}
