
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/go")
public class Go extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");

		String pac = req.getParameter("package");
		String met = req.getParameter("method");

		PrintWriter out = res.getWriter();

		try {

			Class<?> cls = Class.forName(pac + ".Dao");
			Object obj = cls.newInstance();
			Method setFunc = cls.getMethod(met, Map.class);
			Enumeration<String> p = req.getParameterNames();
			List<String> list = new ArrayList<>();

			while (p.hasMoreElements()) {

				String value = (String) p.nextElement();// 调用nextElement方法获得元素

				list.add(value);

			}

			System.out.println(list);
			Map<String, String> param = new ConcurrentHashMap<String, String>();

			for (int i = 2; i < list.size(); i++) {

				String value = req.getParameter(list.get(i));
				param.put(list.get(i), value);

			}

			String result = setFunc.invoke(obj, param) + "";
			out.print(result);

		} catch (Exception e) {

			e.printStackTrace();
			out.println("有异常，请联系管理员，异常代码为" + e);
		}
	}
}