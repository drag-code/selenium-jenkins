package com.main.selenium.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Hello world!
 *
 */
public class DBDataReader {

	private Connection connection;
	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/clovermania", "root", "R007.Eins");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public ResultSet getDataBy(List<String> columns) {
		ResultSet rows = null;
		try {
			Statement statement = connection.createStatement();
			rows = statement.executeQuery("SELECT " + String.join(", ", columns) + " FROM Product");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
}
