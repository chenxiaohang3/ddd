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
	String week[] = { "__����__����_", "��һ_��ԭ_��Ӣ_����_�տ�", "�ܶ�_��Ӣ_����_�տ�_�տ�", "����_JAVAWeb_˼��_�տ�_���", "����_���ݿ�_�տ�_ʵ��һ_ʵ���",
			"����_�տ�_����_�տ�_�տ�" };

	test() {
		for (int i = 0; i < week.length; i++) {
			JPanel jp = new JPanel();
			add(jp, new BorderLayout().CENTER);
			jp.setBounds(0, i * 100, 800, 20);
			jp.setLayout(new GridLayout(5, 1));
			String arr[] = week[i].split("_");
			for (int j = 0; j < arr.length; j++) {
				String s = arr[j];
				if (s.equals("�տ�")) {
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
		setTitle("java�γ̱�");
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