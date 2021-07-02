package a620;

import javax.swing.JFrame;

public class client extends JFrame {
	client() {
		setSize(300, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		friendList();
	}

	public void friendList() {

	}

	public static void main(String[] args) {
		new client();
	}
}
