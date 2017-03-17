package spms.servlets;

import spms.controls.*;
import spms.vo.Member;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
			ServletContext sc = this.getServletContext();
			Map<String, Object> model = new HashMap<>();
			model.put("session", request.getSession());

			Controller pageController = (Controller) sc.getAttribute(servletPath);

			switch (servletPath) {
				case "/member/add.do":
					Optional.ofNullable(request.getParameter("email")).ifPresent(email ->
						model.put("member", new Member()
							.setEmail(email)
							.setName(request.getParameter("name"))
							.setPassword(request.getParameter("password"))));
					break;
				case "/member/update.do":
					Optional.ofNullable(request.getParameter("no")).ifPresent(no -> model.put("no", no));

					Optional.ofNullable(request.getParameter("email")).ifPresent(email ->
						model.put("member", new Member()
							.setNo(Integer.parseInt(request.getParameter("no")))
							.setEmail(email)
							.setName(request.getParameter("name"))));
					break;
				case "/member/delete.do":
					Optional.ofNullable(request.getParameter("no")).ifPresent(no -> model.put("no", no));
					break;
				case "/auth/login.do":
					Optional.ofNullable(request.getParameter("email")).ifPresent(email -> model.put("email", email));
					Optional.ofNullable(request.getParameter("password")).ifPresent(password -> model.put("password", password));
					break;
			}

			String viewUrl = pageController.execute(model);

			for (String key : model.keySet()) {
				request.setAttribute(key, model.get(key));
			}

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
