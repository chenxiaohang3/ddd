package a;

public class c {
	static int n = 0;
	static int a1[] = new int[n];
	static int b1[] = new int[n];
	static int q[] = { 1, 3, 3, 3, 7, 7, 11, 11, 15, 17, 19 };

	public static void main(String[] args) {
		for (int i = 0; i < q.length; i++) {
			int if_new_num = 0;
			int my_index = 0;
			for (int j = 0; j < a1.length; j++) {
				if (a1[j] == q[i]) {
					if_new_num = 1;
					my_index = j;
					break;
				}
			}
			if (if_new_num == 0) {
				n++;
				int t1[] = a1;
				a1 = new int[n];
				a1[n - 1] = q[i];
				int t2[] = b1;
				b1 = new int[n];
				b1[n - 1] = 1;
				for (int k = 0; k < t1.length; k++) {
					a1[k] = t1[k];
					b1[k] = t2[k];
				}
			}
			if (if_new_num == 1) {
				b1[my_index]++;
			}
		}
		for (int i = 0; i < a1.length; i++) {
			if (b1[i] == 1) {
				System.out.println(a1[i]);
			}
		}
	}
}
