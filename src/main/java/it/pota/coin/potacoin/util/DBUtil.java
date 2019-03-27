package it.pota.coin.potacoin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private final static String driver = "com.mysql.jdbc.Driver";
	private final static String db = "jdbc:mysql://localhost:3306/db_potacoin";
	private final static String user = "user=root";
	private final static String serverTimezone = "serverTimezone=UTC";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName (driver);
		return DriverManager.getConnection (db+"?"+user+"&"+serverTimezone); 
	}
}
