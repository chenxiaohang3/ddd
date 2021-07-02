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
	// 可缓存线程池 - 建客户端处理线程池
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	// 可缓存线程池 - 建客户端处理线程池
	ExecutorService keepAliveThreadPool = Executors.newCachedThreadPool();

	/**
	 * 
	 * @param checkDelay
	 *            检查时间：单位毫秒
	 * @param keepAliveDelay
	 *            心跳时间：单位秒
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
		Thread t = new Thread(server);// 创建客户端处理线程
		t.start();// 启动线程
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("输入操作：");
			String op = scan.nextLine();
			if ("1".equals(op)) {
				for (String key : server.clientMap.keySet()) {
					System.out.println("key = " + key);
				}
				for (Client value : server.clientMap.values()) {
					System.out.println("Client = " + value.toString());
				}
			} else {
				System.out.println("输入地址");
				String ClientIp = scan.nextLine();
				System.out.println("输入地址是：" + ClientIp);
				System.out.println("输入消息");
				String message = scan.nextLine();
				System.out.println("输入消息：" + message);

				Client c = server.clientMap.get(ClientIp);
				if (c != null) {
					c.send(ClientIp, message);
				} else {
					System.out.println("该链接不存在！");
				}

			}

		}
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5678);// 创建一个Socket服务器，监听5566端口
			System.out.println("服务已启动。。。");
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

	// 添加客户端
	private void addclient(Socket s) {
		// 第一次收到客户端信息
		/*
		 * String message; try { BufferedReader in = new BufferedReader(new
		 * InputStreamReader( s.getInputStream())); message = in.readLine(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		Client c = new Client(s);// 创建客户端处理线程对象
		// clientMap.put(s.getInetAddress().toString().replace("/", ""), c);
		clientMap.put(System.currentTimeMillis() + "", c);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Thread t = new Thread(c);// 创建客户端处理线程
		// t.start();// 启动线程
		cachedThreadPool.execute(c);

		// 遍历
		/*
		 * for (String key : clientMap.keySet()) { System.out.println("key = " +
		 * key); } for (Client value : clientMap.values()) { System.out.println(
		 * "Client = " + value.toString()); }
		 */

	}

	class Client implements Runnable {
		Socket s = null;// 保存客户端Socket对象
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
					System.out.println(ClientIp + "连接不存在！");
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
			System.out.println(s.getInetAddress().toString().replace("/", "") + "连接已关闭！");
		}

	}

	class KeepAliveWatchDog implements Runnable {
		long lastSendTime;
		long checkDelay = 10;
		long keepAliveDelay = 10000;

		/**
		 * 
		 * @param checkDelay
		 *            检查时间
		 * @param keepAliveDelay
		 *            心跳时间
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
