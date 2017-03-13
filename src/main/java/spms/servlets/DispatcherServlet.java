package spms.servlets;

import spms.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by wayne on 2017. 3. 11..
 */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String servletPath = request.getServletPath();

		try {
			String pageControllerPath;
			switch (servletPath) {
				case "/member/list.do":
					pageControllerPath = "/member/list";
					break;
				case "/member/add.do":
					pageControllerPath = "/member/add";
					Optional.ofNullable(request.getParameter("email")).ifPresent(email ->
						request.setAttribute("member", new Member()
							.setEmail(email)
							.setName(request.getParameter("name"))
							.setPassword(request.getParameter("password"))));
					break;
				case "/member/update.do":
					pageControllerPath = "/member/update";
					Optional.ofNullable(request.getParameter("email")).ifPresent(email ->
						request.setAttribute("member", new Member()
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setEmail(email)
							.setName(request.getParameter("name"))));
					break;
				case "/member/delete.do":
					pageControllerPath = "/member/delete";
					break;
				case "/auth/login.do":
					pageControllerPath = "/auth/login";
					break;
				case "/auth/logout.do":
					pageControllerPath = "/auth/logout";
					break;
				default:
					pageControllerPath = "/";
			}

			request.getRequestDispatcher(pageControllerPath).include(request, response);

			String viewUrl = (String) request.getAttribute("viewUrl");
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			}

			request.getRequestDispatcher(viewUrl).include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			request.getRequestDispatcher("/Error.jsp").forward(request, response);
		}
	}

}
