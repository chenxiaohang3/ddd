package a;

import java.util.*;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;

public class cipher extends JFrame implements MouseListener {

	// ������
	public static void main(String[] args) {
		Scanner sn = new Scanner(System.in);
		int n = sn.nextInt();
		String s = Integer.toBinaryString(n);
		n = s.split("1").length;
		System.out.print(n);
		new cipher("���ܽ���", 200, 200, 300, 200);

	}

	// ����ؼ�
	JTextField text1;
	JTextField text2;
	JButton button1;
	JButton button2;

	public cipher(String title, int x, int y, int width, int height) {

		// ���ÿؼ�
		add(new JLabel("ԭ��:"));
		text1 = new JTextField(10);
		add(text1);
		add(new JLabel("����:"));
		text2 = new JTextField(10);
		add(text2);
		button1 = new JButton("����");
		add(button1);
		button2 = new JButton("����");
		add(button2);

		// ���û���
		setTitle(title);
		setLocation(x, y);
		setSize(width, height);
		setResizable(false);
		// ���˲���.
		setLayout(new GridLayout(6, 1));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// �����¼�
		button1.addMouseListener(this);
		button2.addMouseListener(this);
	}

	// ����
	public static String encrypt(String clearText, int key) {
		char[] charArray = clearText.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			// ȡ��ÿһ����ĸ���������ƶ�4λ
			char c = (char) (charArray[i] + key);
			// �滻ԭ������
			charArray[i] = c;
		}
		return new String(charArray);
	}

	// ����
	static String decrypt(String cipherText, int key) {
		char[] charArray = cipherText.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			// ȡ����ĸ���ƶ�4λ
			char c = (char) (charArray[i] - key);
			charArray[i] = c;
		}
		return new String(charArray);
	}

	public void mouseClicked(MouseEvent e) {
		String s = "";
		if (e.getComponent().equals(button1)) {
			s = encrypt(text1.getText(), 4);
		}
		if (e.getComponent().equals(button2)) {
			s = decrypt(text2.getText(), 4);
		}
		text2.setText(s);
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}