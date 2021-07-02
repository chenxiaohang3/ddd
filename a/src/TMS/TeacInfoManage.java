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
	// ������
	JPanel jp_title = new JPanel();
	JLabel jl_user_name = new JLabel("���,����");
	JButton jb_exit = new JButton("�˳�");
	JPanel jp_jl_msg = new JPanel();
	JLabel jl_msg = new JLabel("<html><u style=\"color:red;\">����7.1�����ϼ���ί���¾���--�������ģ�����ʼ��</u></html>");
	// ��������
	JPanel jp_mainopera = new JPanel();
	// �����������-�˵�
	JPanel jp_menu = new JPanel();
	JLabel jb_tech_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>������Ϣ�鿴</h3></div></html>");
	JLabel jb_main_page = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>��ҳ</h3></div></html>");
	JLabel jb_person_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>������Ϣ����</h3></div></html>");
	JLabel jb_score_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>�ɼ�����</h3></div></html>");
	JLabel jb_course_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>�γ̹���</h3></div></html>");
	JLabel jb_exam_inf_manage = new JLabel(
			"<html><div style=\"width:150px;text-align:center;background:rgb(30,50,150);color:white;\"><h3>���Թ���</h3></div></html>");

	// ���������ұ�-��ϸ���ݴ���
	JPanel jp_opera = new JPanel();
	// ���������ұ�-��ϸ���ݴ���-������������ɾ�İ�ť
	JPanel jp_find_config = new JPanel();
	// ���������ұ�-��ϸ���ݴ���-�б�����
	JPanel jp_list_teac_inf = new JPanel();
	// ��Ȩ��
	JPanel jp_copyright = new JPanel();

	TeacInfoManage() {
		// ������
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
		// ��������
		add(jp_mainopera);
		jp_mainopera.setLayout(null);
		jp_mainopera.setBounds(0, 150, 1400, 670);
		// �����������-�˵�
		jp_mainopera.add(jp_menu);
		jp_menu.setBounds(0, 0, 180, 780);
		jp_menu.setLayout(new GridLayout(19, 1));
		jp_menu.add(jb_main_page);
		jp_menu.add(jb_tech_inf_manage);
		jp_menu.add(jb_person_inf_manage);
		jp_menu.add(jb_score_inf_manage);
		jp_menu.add(jb_course_inf_manage);
		jp_menu.add(jb_exam_inf_manage);
		// ���������ұ�-��ϸ���ݴ���
		jp_mainopera.add(jp_opera);
		jp_opera.setBounds(180, 0, 1210, 670);
		jp_opera.setLayout(null);
		// ���������ұ�-��ϸ���ݴ���-������������ɾ�İ�ť
		jp_opera.add(jp_find_config);
		jp_find_config.setBounds(0, 0, 1210, 60);
		// ���������ұ�-��ϸ���ݴ���-�б�����
		jp_opera.add(jp_list_teac_inf);
		jp_list_teac_inf.add(new TechInfoList());
		jp_list_teac_inf.setLayout(null);
		jp_list_teac_inf.setBounds(1, 60, 1210, 600);
		// ��Ȩ��
		add(jp_copyright);
		jp_copyright.setBounds(0, 820, 1400, 40);
		setResizable(false);
		setTitle("�������ϵͳ-������Ϣ(�б���Ϣ)");
		setSize(1400, 900);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		// ��ʼ������.
		title_msg_list();
		title_msg_gogo();
		// ��ť��ɫ
		title_jb_hover(jb_tech_inf_manage, "������Ϣ�鿴");
		title_jb_hover(jb_person_inf_manage, "������Ϣ����");
		title_jb_hover(jb_score_inf_manage, "�ɼ�����");
		title_jb_hover(jb_course_inf_manage, "�γ̹���");
		title_jb_hover(jb_exam_inf_manage, "���Թ���");
		setJBname();
	}

	// �����Ϣ����
	String title_msg_arr[];

	// ����������Ϣ����
	public void title_msg_list() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/tms", "root", "123456");
			String sql = "select count(tech_if_readed) count from tech_inf where tech_if_readed = 'δ��' group by tech_if_readed";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int n = 0;
			while (rs.next()) {
				n = rs.getInt("count");
			}
			title_msg_arr = new String[n];
			sql = "select tech_title from tech_inf where tech_if_readed = 'δ��'";
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