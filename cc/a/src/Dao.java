
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;

public class Dao {
	public String test(Map m) {
		return list("select * from ");
	}

	public String TableList(Map m) {
		return list("select table_name from tables where table_schema='test'");
	}

	public String list(String sql) {
		try {
			String result = "";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/test", "root", "123456");
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
