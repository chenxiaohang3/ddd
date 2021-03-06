package STMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class Dao {
	// 测试用例列表展示
	public String test(Map m) {
		return list_a("select * from test_main_inf");
	}

	public String list_a(String sql) {
		try {
			String result = "";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/stms", "root", "123456");
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rd = rs.getMetaData();
			int l = rd.getColumnCount();
			while (rs.next()) {
				String s = "";
				for (int i = 1; i <= l; i++) {
					s = s + ",\"" + rd.getColumnName(i) + "\":\"" + rs.getString(rd.getColumnName(i)) + "\"";
				}
				s = ",{" + s.substring(1) + "}";
				result = result + s;
			}
			rs.close();
			ps.close();
			conn.close();
			if (result.length() > 0) {
				result = "[" + result.substring(1) + "]";
			}
			System.out.println(result);
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return "666";
	}
}
