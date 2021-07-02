package a620;

public class fff {
	static int n = 0;

	public static void gogo() {
		n++;
		System.out.println(n);
		gogo();
	}

	public static void main(String[] args) {
		gogo();
	}
}
