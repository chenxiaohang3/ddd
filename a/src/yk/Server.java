package yk;

//server主程
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务端
 * 
 * @author 哑元
 *
 */
public class Server {
	// 用户连接数
	static ServerSocket server;
	static int n = 0;
	static Robot robot = null;
	static ServerSocket dodo;

	public static void go() {
		try {
			robot = new Robot();
			server = new ServerSocket(8088);
			dodo = new ServerSocket(7077);
			while (true) {
				System.out.println("服务器已经正常启动");
				Socket socket = server.accept();// 等待接收请求,阻塞方法
				System.out.println("有客户端连接");
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				// 将客户端与服务器端链接的输出流交个ImageThread处理
				ImageThread imageThread = new ImageThread(dos);
				new Thread(imageThread).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Point() {
		try {

		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	public static void Key() {
		try {
			DatagramSocket socket = new DatagramSocket(7777);
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			socket.receive(packet);
			byte[] arr = packet.getData();
			int len = packet.getLength();
			String s = new String(arr, 0, len);
			System.out.println("s=" + s);
			// robot.mouseMove(x, y);
		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(KeyEvent.VK_1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				MyTCPServer.gogo();
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				go();
			}
		}).start();

	}
}

class MyTCPServer implements Runnable {

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

	public static void gogo() {
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
		Client c = new Client(s);// 创建客户端处理线程对象
		clientMap.put(System.currentTimeMillis() + "", c);

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cachedThreadPool.execute(c);

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

		String str = null;

		@Override
		public void run() {

			while (true) {
				try {

					str = in.readLine();
					if (str.length() < 4 && str.length() > 1) {
						new Robot().keyPress(Integer.parseInt(str));
						new Robot().delay(3);
						new Robot().keyRelease(Integer.parseInt(str));
					}
					if ((str = in.readLine()) != null) {
						switch (str.substring(0, 5)) {
						case "point":
							int ss = Integer.parseInt(str.split("&")[1]);
							System.out.println(ss);
							new Robot().mousePress((16 / (int) (Math.pow(2, ss - 1))));
							new Robot().delay(3);
							new Robot().mouseRelease(16 / (int) (Math.pow(2, ss - 1)));
							break;
						case "imove":
							try {
								Dimension dim = null;
								String s[] = str.split("&");
								dim = Toolkit.getDefaultToolkit().getScreenSize();
								int x = (int) (Double.parseDouble(s[0].split("_")[1]) * dim.width);
								int y = (int) (Double.parseDouble(s[1].split("_")[1]) * dim.height);
								// System.out.println(s.getInetAddress().toString().replace("/",
								// "") + ":" + str);
								new Robot().mouseMove(x, y);
							} catch (Exception e) {
								System.out.println(343434);
								System.out.println(e.toString());
							}
							break;
						case "mykey":
							// System.out.println(str.split("&")[1]);
							String string = str.split("&")[1];
							new Robot().keyPress(Integer.parseInt(string));
							new Robot().delay(3);
							new Robot().keyRelease(Integer.parseInt(string));
							switch (string) {
							case "20":
								new Robot().keyPress(20);

								break;
							}
							break;
						case "wheel":
							String sss = str.split("&")[1];
							System.out.println("ss=" + sss);
							if ((sss + "").equals("1")) {
								new Robot().mouseWheel(2);
							} else {
								new Robot().mouseWheel(-2);
							}
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
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
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clientMap.remove(s.getInetAddress().toString().replace("/", ""));
			try {
				Server.server.close();
			} catch (Exception e) {
				System.out.println(e.toString() + "000");
			}
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