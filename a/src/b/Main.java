package b;

import java.util.Scanner;

public class Main {
	// 判断是否是数字
	static boolean num_judge(String s) {
		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	static void gogo() {
		String s = "";
		Scanner sn = new Scanner(System.in);
		s = sn.next();
		int l = s.length();
		// 判断头尾是否规范
		if (s.charAt(0) != '[' || s.charAt(1) != '[') {
			System.out.println(0);
			return;
		}
		if (s.charAt(l - 1) != ']' || s.charAt(l - 2) != ']') {
			System.out.println(0);
			return;
		}
		// 去头尾，留下中间进行切割处理
		s = s.substring(1, l - 1);
		String arrs[] = null;
		int mylen = 0;
		try {
			// 判断是否有一本书.
			arrs = s.split("\\[");
			mylen = arrs.length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 一本书的情况下
		if (mylen <= 2) {
			String mstr = "[" + arrs[1];
			String myWH[] = mstr.split(",");
			int my_port_len = myWH.length;
			if (my_port_len <= 1) {
				System.out.println(0);
				return;
			}
			String ws = myWH[0].substring(1);
			if (!num_judge(ws)) {
				System.out.println(0);
				return;
			}
			String hs = myWH[1].substring(0, myWH[1].length() - 1);
			if (!num_judge(hs)) {
				System.out.println(0);
				return;
			}
			System.out.println(1);
			// 多于一本书的情况下
		} else {
			int wi[] = new int[mylen];
			int hi[] = new int[mylen];
			for (int i = 1; i < mylen; i++) {
				String mstr = "[" + arrs[i];
				char my_right = mstr.charAt(mstr.length() - 1);
				if (my_right != ',' && i < mylen - 1) {
					System.out.println(5);
					return;
				}
				String myWH[] = mstr.split(",");
				int my_port_len = myWH.length;
				if (my_port_len <= 1) {
					System.out.println(6);
					return;
				}
				String ws = myWH[0].substring(1);
				if (!num_judge(ws)) {
					System.out.println(7);
					return;
				}
				String hs = myWH[1].substring(0, myWH[1].length() - 1);
				if (!num_judge(hs)) {
					System.out.println(8);
					return;
				}
				wi[i] = Integer.parseInt(ws);
				hi[i] = Integer.parseInt(hs);
			}
		}
	}

	public static void main(String[] args) {
		gogo();

	}
}
