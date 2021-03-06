package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class test extends JFrame {
	String week[] = { "__上午__下午_", "周一_组原_大英_大数_空课", "周二_大英_大数_空课_空课", "周三_JAVAWeb_思修_空课_班会", "周四_数据库_空课_实验一_实验二",
			"周五_空课_体育_空课_空课" };

	test() {
		for (int i = 0; i < week.length; i++) {
			JPanel jp = new JPanel();
			add(jp, new BorderLayout().CENTER);
			jp.setBounds(0, i * 100, 800, 20);
			jp.setLayout(new GridLayout(5, 1));
			String arr[] = week[i].split("_");
			for (int j = 0; j < arr.length; j++) {
				String s = arr[j];
				if (s.equals("空课")) {
					s = "";
				}
				JPanel port = new JPanel();
				port.add(new JPanel(), new BorderLayout().EAST);
				port.add(new JLabel(s), new BorderLayout().CENTER);
				port.add(new JPanel(), new BorderLayout().WEST);
				port.add(new JPanel(), new BorderLayout().SOUTH);
				port.add(new JPanel(), new BorderLayout().NORTH);
				Border br = BorderFactory.createLineBorder(new Color(35, 50, 156));
				port.setBorder(br);
				jp.add(port);
			}

		}
		setTitle("java课程表");
		setResizable(false);
		setSize(600, 400);
		setLayout(new GridLayout(1, 5));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] arg) {
		new test();
	}

}