package a;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {
			String s = "";
			Scanner sc = new Scanner(System.in);
			s = sc.next();
			int l = s.length();
			for (int i = 0; i < l; i++) {
				if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
					System.out.println(-1);
					return;
				}
			}
			if (l > 100) {
				return;
			}
			String indexStr = s.charAt(l - 1) + "";
			String L = "";
			L = sc.next();
			int Llen = L.length();
			for (int i = 0; i < Llen; i++) {
				if (L.charAt(i) >= 48 && L.charAt(i) <= 57) {
					System.out.println(-1);
					return;
				}
			}
			if (Llen > 500000) {
				return;
			}
			int my_l_index = 0;
			int my_s_index = 0;
			int if_success = 0;
			for (int j = my_l_index; j < Llen; j++) {
				if (my_s_index == l) {
					break;
				}
				char cs = s.charAt(my_s_index);
				char ls = L.charAt(j);
				if (cs == ls) {
					my_s_index++;
				}
				if ((l - my_s_index) >= (Llen - j)) {
					if (!s.substring(my_s_index).equals(L.substring(j))) {
						if_success = 1;
					}
					break;
				}
			}
			if (if_success == 1) {
				System.out.println(-1);
			} else {
				System.out.println(L.lastIndexOf(s.charAt(s.length() - 1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
