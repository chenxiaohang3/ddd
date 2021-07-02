package yk;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverAction;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.*;
import javax.swing.JTextArea;

public class Client {

	static ClientWindow cw;
	static ObjectOutputStream oos;
	JFrame jf = new JFrame();
	JButton add = new JButton("添加");
	JButton list = new JButton("刷新");
	JButton save = new JButton("保存");
	JFrame jp = new JFrame();
	JPanel back = new JPanel();
	JTextField jtIP = new JTextField();
	JLabel jlIP = new JLabel("IP");
	JLabel jlroomname = new JLabel("车间名字");
	JTextField jtroomname = new JTextField();
	JLabel jlcompany = new JLabel("公司名字");
	JTextField jtcompany = new JTextField();
	JLabel jlMark = new JLabel("备注");
	JTextField jtMark = new JTextField();
	JButton jbipsub = new JButton("提交");
	JLabel jlstove = new JLabel("炉号");
	JTextField jtstove = new JTextField();
	// 标识用来填写设备编号
	JLabel jlDevice_id = new JLabel("设备号");
	// 设备编号填写用的文本框
	JTextField jtDevice_id = new JTextField();
	// 列表
	int nlist = 300;
	JButton jllist[] = new JButton[nlist];
	int nindex = 0;
	JLabel jllistDevice[] = new JLabel[nlist];
	JLabel jllistroom[] = new JLabel[nlist];
	JLabel jllistcompany[] = new JLabel[nlist];
	JLabel jllistmark[] = new JLabel[nlist];
	// 详情
	static String ip = "";

	Client() {
		// 列表
		for (int i = 0; i < nlist; i++) {
			int nx = i % 6;
			int ny = i / 6;
			// 公司名字
			jllistcompany[i] = new JLabel("公司111");
			jllistcompany[i].setBounds(180 * nx + 30, 150 * ny + 50, 160, 120);
			jf.add(jllistcompany[i]);
			jllistcompany[i].setVisible(false);
			// 车间
			jllistroom[i] = new JLabel("车间111");
			jllistroom[i].setVisible(false);
			jllistroom[i].setBounds(180 * nx + 30, 150 * ny + 80, 160, 120);
			jf.add(jllistroom[i]);
			// 设备设置
			jllistDevice[i] = new JLabel("设备111");
			jf.add(jllistDevice[i]);
			jllistDevice[i].setBounds(180 * nx + 30, 150 * ny + 110, 160, 120);
			jllistDevice[i].setVisible(false);
			// 备注
			jllistmark[i] = new JLabel("备注111");
			jf.add(jllistmark[i]);
			jllistmark[i].setBounds(180 * nx + 30, 150 * ny + 140, 160, 120);
			jllistmark[i].setVisible(false);
			// 连接按钮设置
			jllist[i] = new JButton("连接");
			jllist[i].setBounds(180 * nx + 30, 150 * ny + 220, 100, 30);
			jf.add(jllist[i]);
			jllist[i].setVisible(false);

			jllist[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						// 如果鼠标键盘线程开着，就关闭
						if (MyTCPClient.server != null) {
							MyTCPClient.server.close();
						}
						// 屏幕线程关闭.
						myScreenServer.close();
						if (cw != null) {
							cw.dispose();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					// 得到IP
					ip = e.getActionCommand();
					new Thread(new Runnable() {
						public void run() {
							gomousekey();
						}
					}).start();
					new Thread(new Runnable() {
						public void run() {
							goScreenView();
						}
					}).start();
				}
			});
		}
		// 添加页面
		// 提交用的按钮
		jp.add(jbipsub);
		jbipsub.setBounds(300, 300, 120, 30);
		// 备注的文本框
		jp.add(jtMark);
		jtMark.setBounds(100, 180, 320, 30);
		// 备注的标签
		jp.add(jlMark);
		jlMark.setBounds(10, 180, 120, 30);
		// 公司的文本框
		jp.add(jtcompany);
		jtcompany.setBounds(100, 10, 320, 30);
		// 公司的标签
		jp.add(jlcompany);
		jlcompany.setBounds(10, 10, 100, 30);
		// 车间的标签
		jp.add(jlroomname);
		jlroomname.setBounds(10, 60, 120, 30);
		// 车间的文本框
		jtroomname.setBounds(100, 60, 320, 30);
		// 添加页面
		jp.add(jtroomname);
		// 添加页面的背景色
		jp.setBackground(new Color(230, 230, 199));
		// IP填写的文本框
		jp.add(jtIP);
		jtIP.setBounds(100, 120, 320, 30);
		// 晶炉画板设置
		jp.setLayout(null);
		// 添加页面的大小
		jp.setSize(450, 384);
		// 添加页面的标题
		jp.setTitle("添加晶炉信息");
		// 设置添加页面居中
		jp.setLocationRelativeTo(null);
		// 设置添加页面大小不可更改
		jp.setResizable(false);
		// 设置设备编号的标识
		jp.add(jlDevice_id);
		jlDevice_id.setBounds(10, 240, 100, 30);
		// 设置设备的编号
		jp.add(jtDevice_id);
		// 设置设备编译的大小位置
		jtDevice_id.setBounds(100, 240, 320, 30);
		// 添加ip的标签
		jp.add(jlIP);
		// 添加页面显示
		jp.setVisible(true);
		// 设置IP的标签大小坐标
		jlIP.setBounds(10, 120, 120, 30);
		// 给管理系统设置标题
		jf.setTitle("浙江晶阳机电股份有限公司-单晶炉远程监控系统");
		// 给管理系统添加"添加"按钮
		jf.add(add);
		// 给管理系统添加"列表"按钮
		jf.add(list);
		// 设置列表按钮的大小位置
		list.setBounds(200, 20, 130, 40);
		// 设置添加按钮的大小位置
		add.setBounds(20, 20, 130, 40);
		// **********************************画布设置*********************************//
		// 设置管理系统的宽高
		jf.setSize(1700, 900);
		// 设置管理系统布局为自定义
		jf.setLayout(null);
		// 设置关闭系统页面可直接杀死程序，而没有留下进程。
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置管理系统的位置为垂直水平居中
		jf.setLocationRelativeTo(null);
		// 显示管理系统的页面面板
		jf.setVisible(true);

		// gogo(ip);
		list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				list();
				JOptionPane.showMessageDialog(null, "数据更新完毕");
			}
		});
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jp.setVisible(true);
			}
		});
		jbipsub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = jtIP.getText();
				String roomname = jtroomname.getText();
				String company = jtcompany.getText();
				String device_id = jtDevice_id.getText();
				String Mark = jtMark.getText();
				if (ip.length() < 1) {
					JOptionPane.showMessageDialog(null, "ip不能为空");
					return;
				}
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/icsms", "root",
							"123456");
					String sql = "insert into pc_info(ip,company_name,room_name,device_id,remark) values(?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					// 填写IP地址
					ps.setString(1, ip);
					// 填写公司名字
					ps.setString(2, company);
					// 填写车间名字
					ps.setString(3, roomname);
					// 填写设备编号
					ps.setString(4, device_id);
					// 填写备注内容
					ps.setString(5, Mark);
					ps.executeUpdate();
					ps.close();
					conn.close();
					JOptionPane.showMessageDialog(null, "操作完毕");
					list();
				} catch (Exception ee) {
					System.out.println(ee.toString());
				}
				jp.setVisible(false);
			}
		});
		list();
	}

	public static void main(String[] args) {
		new Client();
	}

	public void list() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://8.136.233.37:3306/icsms", "root", "123456");
			String sql = "select * from pc_info where status = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			int n = 0;
			while (rs.next()) {
				jllist[n].setActionCommand(rs.getString("ip"));
				jllist[n].setVisible(true);
				jllistDevice[n].setVisible(true);
				jllistroom[n].setVisible(true);
				jllistmark[n].setVisible(true);
				jllistcompany[n].setVisible(true);
				n++;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	static Socket myScreenServer;

	public static void myScreenView() {

		try {
			myScreenServer = new Socket(ip, 8088);
			DataInputStream dis = new DataInputStream(myScreenServer.getInputStream());
			oos = new ObjectOutputStream(myScreenServer.getOutputStream());
			byte[] imageBytes;
			while (true) {
				imageBytes = new byte[dis.readInt()]; // 先拿到传过来的数组长度
				dis.readFully(imageBytes); // 所有的数据存放到byte中
				cw.repainImage(imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void gomousekey() {
		new MyTCPClient();
	}

	public static void goScreenView() {
		cw = new ClientWindow(oos);
		cw.setSize(1720, 925);
		cw.setResizable(false);
		cw.setLocationRelativeTo(null);
		cw.setVisible(true);
		myScreenView();
	}
}

class ClientWindow extends JFrame {

	private ObjectOutputStream oos;
	private JLabel label;
	private JPanel jp;

	// 重写背景图片方法
	public void repainImage(byte[] imageBytes) {
		label.setIcon(new ImageIcon(imageBytes));
		this.jp = jp;
		this.repaint();
	}

	String mx = "";
	String my = "";
	int k = ' ';
	String if_set_val = "";

	public ClientWindow() {
		super(" ");
	}

	public ClientWindow(ObjectOutputStream oos) {
		this.oos = oos;
		label = new JLabel();
		JPanel p = new JPanel();
		p.add(label);
		p.setBounds(0, 0, 1900, 920);
		p.setLayout(null);
		label.setBounds(0, 0, 1900, 920);
		JScrollPane scroll = new JScrollPane(p);// 给p面板添加滚动条
		this.add(scroll);

		// this.setLayout(null);
		this.setVisible(true);
		label.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					MyTCPClient.out.println("wheel&2");
				} else {
					MyTCPClient.out.println("wheel&1");
				}
				MyTCPClient.out.flush();
			}
		});
		label.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				MyTCPClient.out.println("point&" + e.getButton());
				MyTCPClient.out.println("point&" + e.getButton());
				MyTCPClient.out.flush();
			}
		});

		label.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

				mx = e.getX() + "";
				my = e.getY() + "";
				mx = (Double.parseDouble(mx + ".0") / 1700.00) + "";
				my = (Double.parseDouble(my + ".0") / 920.00) + "";
				String s = "imovepointx_" + mx + "&pointy_" + my;
				MyTCPClient.out.println(s);
				MyTCPClient.out.flush();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// 把得到的字符传过去.
				k = e.getKeyCode();
				if_set_val = "set";
			}
		});
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				// 这个是键盘事件。就是远控的时候，我这边输什么，那边也显示什么.
				// 用mykey加&加输入的值，服务端那边是用&来切割，我们统一命令
				// 前缀都为五个字符，切割的时候，&后面的值为我们需要的值.
				// 这个set是说明我要开始传值了。
				if (if_set_val == "set") {
					MyTCPClient.out.println("mykey&" + k);
					MyTCPClient.out.println("mykey&" + k);
					MyTCPClient.out.flush();
					// 这个if_set_val是传值结束了.
					if_set_val = "finish";
				}
			}
		}, 0, 4);// 间隔4毫秒是为了值传得更为迅速.
	}

	public void sendEvent(InputEvent event) {
		try {
			oos.writeObject(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

// 鼠标键盘类
class MyTCPClient extends JFrame {

	static Socket server;
	static PrintWriter out;

	public MyTCPClient() {
		try {
			server = new Socket(Client.ip, 5678);
			out = new PrintWriter(server.getOutputStream());
		} catch (Exception e) {

		}
	}
}
