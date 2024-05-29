package com.sellpro.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

@SuppressWarnings("unused")
public class Database {
	private Connection connection = null;
	static private Database instance = null;

	private Database() {		
		String host = "localhost";
		int port = 3306;
		String database = "SellPro";
		String user = "root";
		String password = "";

		try {
			//Driver driver = new Driver();
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    
			String url = "jdbc:mysql://" + host + ':' + String.valueOf(port) + '/' + database;
		    connection = DriverManager.getConnection(url, user, password);
		    System.out.println("Database connected successfully.");
		} catch (SQLException e) {
		    e.printStackTrace();
		} catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
		}
	}
	
	public ResultSet execSelect(String query) {
		Statement statement = exec(query);
		try {
			return statement.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Statement exec(String query) {
		PreparedStatement statement = prepare(query);
		try {
			statement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statement;
	}
	
	public PreparedStatement prepare(String query) {
		try {
			return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Database getInstance() {
		if (instance == null)
			instance = new Database();
		return instance;
	}
}
