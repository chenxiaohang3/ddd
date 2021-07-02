package yk;

//屏幕截取器和发送器，这里需要拿到socket的out流
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 用来将图片数据发送
 * 
 * @author 哑元
 *
 */
public class ImageThread implements Runnable {
	DataOutputStream dos = null; // 数据输出流

	public ImageThread(DataOutputStream dos) {
		this.dos = dos;
	}

	@Override
	public void run() {
		try {
			Robot robot = new Robot();
			// 截取整个屏幕
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rec = new Rectangle(dimension);
			BufferedImage image;
			BufferedImage tag;
			byte imageBytes[] = null;
			int k = 1;
			while (true) {
				image = robot.createScreenCapture(rec);
				// System.out.println(image.getWidth());
				// System.out.println(image.getHeight());
				int w = 1700;
				int h = 920;
				// w = image.getWidth();
				// h = image.getHeight();
				// System.out.println(w);
				// System.out.println(1920 / w);
				// h = h * (1200 / w);
				if (k == 1) {
					tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					// 缩放图片
					tag.getGraphics().drawImage(image, 0, 0, w, h, null);
					imageBytes = getImageBytes(tag);
				} else {
					imageBytes = getImageBytes(image);
				}
				dos.writeInt(imageBytes.length);
				dos.write(imageBytes);
				dos.flush();
				Thread.sleep(4); // 线程睡眠
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (dos != null)
					dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param 需要压缩的图片
	 * @return 压缩后的byte数组
	 * @throws IOException
	 * @throws ImageFormatException
	 */
	public byte[] getImageBytes(BufferedImage image) throws ImageFormatException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 压缩器压缩，先拿到存放到byte输出流中
		JPEGImageEncoder jpegd = JPEGCodec.createJPEGEncoder(baos);
		// 将iamge压缩
		jpegd.encode(image);
		// 转换成byte数组
		return baos.toByteArray();
	}
}