package job51;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class aa {
	/**
	 * ���öԷ��ӿڷ���
	 * 
	 * @param path
	 *            �Է���������ṩ��·��
	 * @param data
	 *            ��Է�����������͵����ݣ����������¸��Է�����JSON�����öԷ�����
	 */
	public static void interfaceUtil(String path, String data) {
		try {
			URL url = new URL(path);
			// �򿪺�url֮�������
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			PrintWriter out = null;

			/** ����URLConnection�Ĳ�������ͨ����������****start ***/

			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			/** ����URLConnection�Ĳ�������ͨ����������****end ***/

			// �����Ƿ���httpUrlConnection����������Ƿ��httpUrlConnection���룬���ⷢ��post�����������������
			// ��õ�Http�����޷���get��post��get������Ի�ȡ��̬ҳ�棬Ҳ���԰Ѳ�������URL�ִ����棬���ݸ�servlet��
			// post��get�� ��֮ͬ������post�Ĳ������Ƿ���URL�ִ����棬���Ƿ���http����������ڡ�
			conn.setDoOutput(true);
			conn.setDoInput(true);

			conn.setRequestMethod("GET");// GET��POST����ȫ��д
			/** GET��������*****start */
			/**
			 * ���ֻ�Ƿ���GET��ʽ����ʹ��connet����������Զ����Դ֮���ʵ�����Ӽ��ɣ�
			 * �������POST��ʽ��������Ҫ��ȡURLConnectionʵ����Ӧ����������������������
			 */
			conn.connect();

			/** GET��������*****end */

			/*** POST��������****start */

			/*
			 * out = new
			 * PrintWriter(conn.getOutputStream());//��ȡURLConnection�����Ӧ�������
			 * 
			 * out.print(data);//�����������������
			 * 
			 * out.flush();//��������
			 */
			/*** POST��������****end */

			// ��ȡURLConnection�����Ӧ��������
			InputStream is = conn.getInputStream();
			// ����һ���ַ�������
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while ((str = br.readLine()) != null) {
				str = new String(str.getBytes(), "UTF-8");// ���������������
				System.out.println(str);
			}
			// �ر���
			is.close();
			// �Ͽ����ӣ����д�ϣ�disconnect���ڵײ�tcp socket���ӿ���ʱ���жϡ�������ڱ������߳�ʹ�þͲ��жϡ�
			// �̶����̵߳Ļ��������disconnect�����ӻ����ֱ࣬���շ�������Ϣ��д��disconnect������һЩ��
			conn.disconnect();
			System.out.println("��������");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		interfaceUtil(
				"https://search.51job.com/list/020000,000000,0000,00,9,99,java,2,1.html?lang=c&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&ord_field=0&dibiaoid=0&line=&welfare=",
				"");// get����

		/*
		 * interfaceUtil(
		 * "http://172.83.28.221:7001/NSRTRegistration/test/add.do",
		 * "id=8888888&name=99999999");
		 */// post����
	}

}
