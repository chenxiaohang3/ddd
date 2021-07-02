package gogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

public class HttpRequest {
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��ȡȨ��token
	 * 
	 * @return ����ʾ���� { "access_token":
	 *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
	 *         "expires_in": 2592000 }
	 */
	public static String getAuth() {
		// ������ȡ�� API Key ����Ϊ��ע���
		String clientId = "�ٶ���Ӧ�õ�AK";
		// ������ȡ�� Secret Key ����Ϊ��ע���
		String clientSecret = "�ٶ���Ӧ�õ�SK";
		return getAuth(clientId, clientSecret);
	}

	/**
	 * ��ȡAPI����token ��token��һ������Ч�ڣ���Ҫ���й�����ʧЧʱ�����»�ȡ.
	 * 
	 * @param ak
	 *            - �ٶ��ƹ�����ȡ�� API Key
	 * @param sk
	 *            - �ٶ��ƹ�����ȡ�� Securet Key
	 * @return assess_token ʾ����
	 *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
	 */
	public static String getAuth(String ak, String sk) {
		// ��ȡtoken��ַ
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_typeΪ�̶�����
				+ "grant_type=client_credentials"
				// 2. ������ȡ�� API Key
				+ "&client_id=eqbaZhYUrT9zR2bVvRH8bCOf"
				// 3. ������ȡ�� Secret Key
				+ "&client_secret=pvGXoFudOZFQgVXG78YkC57W11F66xVS";
		try {
			URL realUrl = new URL(getAccessTokenUrl);
			// �򿪺�URL֮�������
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = connection.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// ���� BufferedReader����������ȡURL����Ӧ
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			/**
			 * ���ؽ��ʾ��
			 */
			System.out.println("result:" + result);
			return result;
		} catch (Exception e) {
			System.out.printf("��ȡtokenʧ�ܣ�");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��Ҫ��ʾ���������蹤���� FileUtil,Base64Util,HttpUtil,GsonUtils���
	 * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	 * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	 * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	 * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3 ����
	 */
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

	public static void main(String[] args) {
		// getAuth();
		licensePlate();
		// getAuth("", "");
	}
}
