package a;

import java.util.*;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.*;

public class cipher extends JFrame implements MouseListener {

	// 主函数
	public static void main(String[] args) {
		Scanner sn = new Scanner(System.in);
		int n = sn.nextInt();
		String s = Integer.toBinaryString(n);
		n = s.split("1").length;
		System.out.print(n);
		new cipher("加密解密", 200, 200, 300, 200);

	}

	// 定义控件
	JTextField text1;
	JTextField text2;
	JButton button1;
	JButton button2;

	public cipher(String title, int x, int y, int width, int height) {

		// 设置控件
		add(new JLabel("原文:"));
		text1 = new JTextField(10);
		add(text1);
		add(new JLabel("处理:"));
		text2 = new JTextField(10);
		add(text2);
		button1 = new JButton("加密");
		add(button1);
		button2 = new JButton("解密");
		add(button2);

		// 设置画板
		setTitle(title);
		setLocation(x, y);
		setSize(width, height);
		setResizable(false);
		// 加了布局.
		setLayout(new GridLayout(6, 1));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 设置事件
		button1.addMouseListener(this);
		button2.addMouseListener(this);
	}

	// 加密
	public static String encrypt(String clearText, int key) {
		char[] charArray = clearText.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			// 取出每一个字母，并向右移动4位
			char c = (char) (charArray[i] + key);
			// 替换原来数据
			charArray[i] = c;
		}
		return new String(charArray);
	}

	// 解密
	static String decrypt(String cipherText, int key) {
		char[] charArray = cipherText.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			// 取出字母左移动4位
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