package com.blackcrystal.platform.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJdbcConnectTest {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.2.21:3306/test", "smarthome",
					"smarthome123");

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from user");

			while (rs.next()) {
				String str1 = rs.getString(1);
				String str2 = rs.getString(2);
				System.out.println(str1 + ":" + str2);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
