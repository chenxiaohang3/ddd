package a620;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTCPServer implements Runnable {

	Map<String, Client> clientMap = new ConcurrentHashMap<String, Client>();
	// �ɻ����̳߳� - ���ͻ��˴����̳߳�
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	// �ɻ����̳߳� - ���ͻ��˴����̳߳�
	ExecutorService keepAliveThreadPool = Executors.newCachedThreadPool();

	/**
	 * 
	 * @param checkDelay
	 *            ���ʱ�䣺��λ����
	 * @param keepAliveDelay
	 *            ����ʱ�䣺��λ��
	 */
	public MyTCPServer(long checkDelay, long keepAliveDelay) {
		keepAliveThreadPool.execute(new KeepAliveWatchDog(checkDelay, keepAliveDelay * 1000));
	}

	public MyTCPServer() {
		keepAliveThreadPool.execute((new KeepAliveWatchDog(10, 15 * 1000)));
	}

	public MyTCPServer(boolean keepAlive) {
		if (keepAlive) {
			// keepAliveThreadPool.execute((new Thread(new KeepAliveWatchDog(10,
			// 10 * 1000))));
			keepAliveThreadPool.execute((new KeepAliveWatchDog(10, 10 * 1000)));
		}
	}

	public static void main(String[] args) {
		MyTCPServer server = new MyTCPServer(true);
		Thread t = new Thread(server);// �����ͻ��˴����߳�
		t.start();// �����߳�
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("���������");
			String op = scan.nextLine();
			if ("1".equals(op)) {
				for (String key : server.clientMap.keySet()) {
					System.out.println("key = " + key);
				}
				for (Client value : server.clientMap.values()) {
					System.out.println("Client = " + value.toString());
				}
			} else {
				System.out.println("�����ַ");
				String ClientIp = scan.nextLine();
				System.out.println("�����ַ�ǣ�" + ClientIp);
				System.out.println("������Ϣ");
				String message = scan.nextLine();
				System.out.println("������Ϣ��" + message);

				Client c = server.clientMap.get(ClientIp);
				if (c != null) {
					c.send(ClientIp, message);
				} else {
					System.out.println("�����Ӳ����ڣ�");
				}

			}

		}
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5678);// ����һ��Socket������������5566�˿�
			System.out.println("����������������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket client = null;
			try {
				client = ss.accept();
				addclient(client);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// ��ӿͻ���
	private void addclient(Socket s) {
		// ��һ���յ��ͻ�����Ϣ
		/*
		 * String message; try { BufferedReader in = new BufferedReader(new
		 * InputStreamReader( s.getInputStream())); message = in.readLine(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		Client c = new Client(s);// �����ͻ��˴����̶߳���
		// clientMap.put(s.getInetAddress().toString().replace("/", ""), c);
		clientMap.put(System.currentTimeMillis() + "", c);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Thread t = new Thread(c);// �����ͻ��˴����߳�
		// t.start();// �����߳�
		cachedThreadPool.execute(c);

		// ����
		/*
		 * for (String key : clientMap.keySet()) { System.out.println("key = " +
		 * key); } for (Client value : clientMap.values()) { System.out.println(
		 * "Client = " + value.toString()); }
		 */

	}

	class Client implements Runnable {
		Socket s = null;// ����ͻ���Socket����
		BufferedReader in;
		PrintWriter out;
		String toClient;
		String ClientIp;
		boolean send = false;

		Client(Socket s) {
			this.s = s;
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = new PrintWriter(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

		void send(String ClientIp, String message) {
			this.ClientIp = ClientIp;
			this.toClient = message;
			send = true;
			if (send) {
				Client c = clientMap.get(ClientIp);
				if (c == null) {
					System.out.println(ClientIp + "���Ӳ����ڣ�");
				}

				try {
					c.send(toClient);
				} catch (Exception e) {
					e.printStackTrace();
					c.close();
				}

			}
			send = false;
		}

		@Override
		public void run() {

			while (true) {
				try {
					String str = null;
					// str = in.readLine();
					if ((str = in.readLine()) != null) {
						System.out.println(s.getInetAddress().toString().replace("/", "") + ":" + str);
					}
				} catch (Exception e) {
					// e.printStackTrace();
					close();
					break;
				}

				/*
				 * if (send) { Client c = clientMap.get(ClientIp);
				 * c.send(toClient); } send = false;
				 */
			}
		}

		public void send(String str) {
			out.println(str);
			if (!"KeepAlive".equals(str))
				System.out.println("send:" + str);
			out.flush();

		}

		public void close() {
			try {
				s.close();
				in.close();
				;
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clientMap.remove(s.getInetAddress().toString().replace("/", ""));
			System.out.println(s.getInetAddress().toString().replace("/", "") + "�����ѹرգ�");
		}

	}

	class KeepAliveWatchDog implements Runnable {
		long lastSendTime;
		long checkDelay = 10;
		long keepAliveDelay = 10000;

		/**
		 * 
		 * @param checkDelay
		 *            ���ʱ��
		 * @param keepAliveDelay
		 *            ����ʱ��
		 */
		public KeepAliveWatchDog(long checkDelay, long keepAliveDelay) {
			this.checkDelay = checkDelay;
			this.keepAliveDelay = keepAliveDelay;
		}

		@Override
		public void run() {
			while (true) {
				if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
					for (String ClientIp : clientMap.keySet()) {
						Client c = clientMap.get(ClientIp);
						c.send("KeepAlive");
					}
					lastSendTime = System.currentTimeMillis();
				} else {
					try {
						Thread.sleep(checkDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();

					}
				}
			}
		}
	}

}
