package Park;

import java.net.URLEncoder;

import javax.swing.JFrame;

import gogo.Base64Util;
import gogo.FileUtil;
import gogo.HttpUtil;

public class Rec extends JFrame {
	public static String licensePlate() {
		// ����url
		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
		// String grl =
		// "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
		url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
		try {
			// �����ļ�·��
			String filePath = "D:/CC/b.png";
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");

			String param = "image=" + imgParam;

			// ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬
			// �ͻ��˿����л��棬���ں����»�ȡ��
			String accessToken = "24.6f1ac321aee9269975dee179e095c058.2592000.1622730539.282335-11631465";

			String result = HttpUtil.post(url, accessToken, param);
			System.out.println(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	Rec() {
		setSize(1000, 800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Rec();
	}
}
