package gxr.servlet;

import gxr.bean.RegisteTable;
import gxr.bean.User;
import gxr.tools.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUserServlet extends HttpServlet {

	/**
	 * 医生查看已申请挂号的病人的servlet
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		Data data = new Data();
		data.connect();
		List<RegisteTable> registeTables = data.checkUsers(id);
		for (int i = 0; i < registeTables.size(); i++) {
			User user = data.getUserInfo(registeTables.get(i).getUserId());
			registeTables.get(i).setUser(user);
		}
		data.closeSql();
		StringBuffer buffer = new StringBuffer("{\"regist_tables\":[");
		for (int i = 0; i < registeTables.size(); i++) {
			buffer.append("{");
			RegisteTable registeTable = registeTables.get(i);
			buffer.append("\"id\":\"" + registeTable.getId() + "\",");
			buffer.append("\"userId\":\"" + registeTable.getUserId() + "\",");
			buffer.append("\"time\":\"" + registeTable.getTime() + "\",");
			User user = registeTable.getUser();
			buffer.append("\"name\":\"" + user.getName() + "\",");
			buffer.append("\"phone\":\"" + user.getPhone() + "\",");
			buffer.append("\"age\":\"" + user.getAge() + "\",");
			buffer.append("\"sex\":\"" + user.getSex() + "\"");
			if (i == registeTables.size() - 1) {
				buffer.append("}");
			} else {
				buffer.append("},");
			}
		}
		buffer.append("]}");
		PrintWriter out = response.getWriter();
		out.print(buffer);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
