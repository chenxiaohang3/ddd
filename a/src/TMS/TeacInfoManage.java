package TMS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeacInfoManage extends JFrame {
	// 标题栏
	JPanel jp_title = new JPanel();
	JLabel jl_user_name = new JLabel("你好,张三");
	JButton jb_exit = new JButton("退出");
	JPanel jp_jl_msg = new JPanel();
	JLabel jl_msg = new JLabel("<html><u style=\"color:red;\">关于7.1建党上级党委最新精神--不忘初心，方得始终</u></html>");
	// 主操作区
	JPanel jp_mainopera = new JPanel();
	// 主操区的左边-菜单
	JPanel jp_menu = new JPanel();
	JLabel jb_tech_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>教务信息查看</h3></div></html>");
	JLabel jb_main_page = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>主页</h3></div></html>");
	JLabel jb_person_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>个人信息管理</h3></div></html>");
	JLabel jb_score_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>成绩管理</h3></div></html>");
	JLabel jb_course_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>课程管理</h3></div></html>");
	JLabel jb_exam_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>考试管理</h3></div></html>");

	// 主操区的右边-详细数据处理
	JPanel jp_opera = new JPanel();
	// 主操区的右边-详细数据处理-搜索条件，增删改按钮
	JPanel jp_find_config = new JPanel();
	// 主操区的右边-详细数据处理-列表数据
	JPanel jp_list_teac_inf = new JPanel();
	// 版权区
	JPanel jp_copyright = new JPanel();

	TeacInfoManage() {
		// 标题栏
		add(jp_title);
		jp_title.setBounds(0, 0, 1400, 40);
		jp_title.add(jl_user_name);
		jp_title.setLayout(null);
		jl_user_name.setBounds(1200, 0, 120, 40);
		jp_title.add(jb_exit);
		jb_exit.setBounds(1289, 5, 90, 30);
		jp_title.add(jl_msg);
		jp_title.add(jp_jl_msg);
		jp_jl_msg.setBounds(0, 0, 1170, 40);
		jp_jl_msg.add(jl_msg);
		// 主操作区
		add(jp_mainopera);
		jp_mainopera.setLayout(null);
		jp_mainopera.setBounds(0, 150, 1400, 670);
		// 主操区的左边-菜单
		jp_mainopera.add(jp_menu);
		jp_menu.setBounds(0, 0, 180, 780);
		jp_menu.setLayout(new GridLayout(19, 1));
		jp_menu.add(jb_main_page);
		jp_menu.add(jb_tech_inf_manage);
		jp_menu.add(jb_person_inf_manage);
		jp_menu.add(jb_score_inf_manage);
		jp_menu.add(jb_course_inf_manage);
		jp_menu.add(jb_exam_inf_manage);
		// 主操区的右边-详细数据处理
		jp_mainopera.add(jp_opera);
		jp_opera.setBounds(180, 0, 1210, 670);
		jp_opera.setLayout(null);
		// 主操区的右边-详细数据处理-搜索条件，增删改按钮
		jp_opera.add(jp_find_config);
		jp_find_config.setBounds(0, 0, 1210, 60);
		// 主操区的右边-详细数据处理-列表数据
		jp_opera.add(jp_list_teac_inf);
		jp_list_teac_inf.add(new TechInfoList());
		jp_list_teac_inf.setLayout(null);
		jp_list_teac_inf.setBounds(1, 60, 1210, 600);
		// 版权区
		add(jp_copyright);
		jp_copyright.setBounds(0, 820, 1400, 40);
		setResizable(false);
		setTitle("教务管理系统-教务信息(列表信息)");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		// 初始化函数.
		title_msg_list();
		title_msg_gogo();
		// 按钮变色
		title_jb_hover(jb_tech_inf_manage, "教务信息查看");
		title_jb_hover(jb_person_inf_manage, "个人信息管理");
		title_jb_hover(jb_score_inf_manage, "成绩管理");
		title_jb_hover(jb_course_inf_manage, "课程管理");
		title_jb_hover(jb_exam_inf_manage, "考试管理");
		setJBname();
	}

	// 存放消息标题
	String title_msg_arr[];

	// 标题栏的消息推送
	public void title_msg_list() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/tms", "root", "123456");
			String sql = "select count(tech_if_readed) count from tech_inf where tech_if_readed = '未读' group by tech_if_readed";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int n = 0;
			while (rs.next()) {
				n = rs.getInt("count");
			}
			title_msg_arr = new String[n];
			sql = "select tech_title from tech_inf where tech_if_readed = '未读'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int ii = 0;
			while (rs.next()) {
				title_msg_arr[ii] = rs.getString("tech_title").trim();
				ii++;
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int title_gogo = 0;
	int title_gogo_speed = -10;
	int title_msg_index = 0;

	public void title_msg_gogo() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				title_gogo++;
				int x = jp_jl_msg.getX();
				int y = jp_jl_msg.getY();
				if (title_gogo % 2 == 0) {
					if (title_gogo % 10 == 0) {
						title_gogo_speed++;
					}
					if (y > -20 && title_gogo_speed >= 1) {
						y -= title_gogo_speed;
					}
					if (y < -20) {
						y = 5;
						title_msg_index++;
						if (title_msg_index >= title_msg_arr.length) {
							title_msg_index = 0;
						}
						jl_msg.setText(
								"<html><u style=\"color:red;\">" + title_msg_arr[title_msg_index] + "</u></html>");
						title_gogo_speed = -25;
					}
				}
				jp_jl_msg.setLocation(x, y);
			}
		}, 0, 10);
	}

	public void title_jb_hover(JLabel jl, String txt) {
		jl.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				String name = e.getComponent().getName();
				if (name.substring(name.length() - 2, name.length()).equals("ok")) {
					jl.setText(
							"<html><div id=\"t1\" style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>"
									+ txt + "</h3></div></html>");
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				String name = e.getComponent().getName();
				if (name.substring(name.length() - 2, name.length()).equals("ok")) {
					jl.setText(
							"<html><div id=\"t1\" style=\"width:150px;text-align:center;background:red;color:rgb(11,255,255);\"><h3>"
									+ txt + "</h3></div></html>");
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				String name = e.getComponent().getName();
				switch (name) {
				case "jb_tech_inf":
					setJBname();
					e.getComponent().setName("jb_tech_inf_no");
					break;
				case "jb_person_inf":
					setJBname();
					e.getComponent().setName("jb_person_inf_no");
					break;
				}
			}
		});
	}

	public void setJBname() {
		jb_tech_inf_manage.setName("jb_tech_inf_ok");
		jb_person_inf_manage.setName("jb_person_inf_ok");
		jb_score_inf_manage.setName("jb_score_inf_ok");
		jb_course_inf_manage.setName("jb_course_inf_ok");
		jb_exam_inf_manage.setName("jb_exam_inf_ok");
	}

	public static void main(String[] args) {
		new TeacInfoManage();
	}
}
