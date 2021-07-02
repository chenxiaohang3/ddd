package home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;

import com.github.sarxos.webcam.WebcamPanel;

import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Client {

	public static void main(String[] args) throws InterruptedException {

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		JFrame window = new JFrame("Test webcam panel");
		window.add(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);

		final JButton button = new JButton("拍照");
		window.add(panel, BorderLayout.CENTER);
		window.add(button, BorderLayout.SOUTH);
		window.setResizable(true);
		window.pack();
		window.setVisible(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				button.setEnabled(false); // 设置按钮不可点击

				// 实现拍照保存-------start
				String fileName = "D://" + System.currentTimeMillis(); // 保存路径即图片名称（不用加后缀）
				WebcamUtils.capture(webcam, fileName, ImageUtils.FORMAT_PNG);
				byte b[] = WebcamUtils.getImageBytes(webcam, ImageUtils.FORMAT_PNG);
				System.out.println(b.length);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "拍照成功");
						button.setEnabled(true); // 设置按钮可点击

						return;
					}
				});
				// 实现拍照保存-------end
			}
		});
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					String ip = "8.136.233.37";
					Socket myScreenServer = new Socket(ip, 9099);
					DataOutputStream dos = new DataOutputStream(myScreenServer.getOutputStream());
					byte b[] = WebcamUtils.getImageBytes(webcam, ImageUtils.FORMAT_JPG);
					System.out.println(b.length);
					ByteArrayInputStream in = new ByteArrayInputStream(b); // 将b作为输入流；
					dos.writeInt(b.length);
					dos.write(b);
					dos.flush();
					in.close();
					dos.close();
					myScreenServer.close();
				} catch (Exception e) {
					System.out.println(e.toString() + "fd");
				}
			}
		}, 0, 40);
	}
}