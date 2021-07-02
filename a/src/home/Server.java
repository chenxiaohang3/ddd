package home;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	static ServerSocket PcPictoServer;
	static ServerSocket PictoAndroid;
	static byte ib[];

	public static void OutPicStream() throws IOException {
		try {
			PcPictoServer = new ServerSocket(8088);
			while (true) {
				System.out.println("8088" + "start...");
				Socket socket = PcPictoServer.accept();// 等待接收请求,阻塞方法
				System.out.println("" + socket.getInetAddress() + new Date());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeInt(ib.length);
				System.out.println("nnnnnnnnnnnnnnnnn" + ib.length);
				dos.write(ib);
				dos.flush();
			}
		} catch (Exception e) {
			PcPictoServer.close();
			OutPicStream();
		}
	}

	public static void getPicInstream() throws IOException {
		try {
			PictoAndroid = new ServerSocket(9099);
			System.out.println("9099" + "start...");
			Socket socket = PictoAndroid.accept();
			System.out.println("" + socket.getInetAddress() + new Date());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			while (true) {
				ib = new byte[dis.readInt()];
				System.out.println("****************************" + ib.length);
				dis.readFully(ib);
			}

		} catch (Exception e) {
			// System.out.println(e.toString());
			PictoAndroid.close();
			getPicInstream();
			// e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					OutPicStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					getPicInstream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
}