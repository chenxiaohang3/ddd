package TMS;

import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TechInfoList extends JPanel {
	TechInfoList() {
		setSize(1202, 520);
		setLocation(0, 0);
		setLayout(new GridLayout(10, 1));
		List();
	}

	public void List() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/tms", "root", "123456");
			String sql = "select * from tech_inf limit 0,10";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				String s = rs.getString("tech_title").trim();
				if (s.length() > 20) {
					s = s.substring(0, 20) + ".....";
				}
				String name = rs.getString("tech_author");
				JPanel jp = new JPanel();
				jp.setLayout(null);
				add(jp);

				JLabel jl = new JLabel("<html><div style=\"padding-left:35px;\"><u>" + s + "</u></div></html>");
				jp.add(jl);
				JLabel jl2 = new JLabel(
						"<html><div style=\"padding-left:30px;float:left;\"><span style=\"color:red;\">????:&nbsp;&nbsp;&nbsp;&nbsp;</span>"
								+ name + "</div></html>");
				jp.add(jl2);
				String date = rs.getString("tech_date");
				JLabel jl3 = new JLabel("<html><div>" + date + "</div></html>");
				String body = rs.getString("tech_body");
				if (body.length() > 40) {
					body = body.substring(0, 35) + "......";
				}
				JLabel jl4 = new JLabel("<html><div style=\"line-height:30px;\">" + body + "</div></html>");
				jp.add(jl3);
				jp.add(jl4);
				jl.setBounds(0, 0, 350, 30);
				jl2.setBounds(350, 0, 120, 30);
				jl3.setBounds(500, 0, 100, 30);
				jl4.setBounds(630, 0, 500, 30);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
