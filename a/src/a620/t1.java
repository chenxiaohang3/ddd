package a620;

import java.util.Scanner;

public class t1 {
	public static void test() {
		for (;;) {
			System.out.println("���������:");
			int n = new Scanner(System.in).nextInt();
			if (n % 4 == 0 && n % 100 != 0 || (n % 400 == 0)) {
				System.out.println(n + "������.");
			} else {
				System.out.println(n + "��������");
			}
		}

	}

	public static void main(String[] args) {
		test();
	}
}
