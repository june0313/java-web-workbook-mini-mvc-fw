package spms.servlets;

import spms.dao.MemberDao;
import spms.vo.Member;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 프론트 컨트롤러 적용
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("viewUrl", "/auth/LogInForm.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletContext sc = this.getServletContext();
			MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");

			String email = request.getParameter("email");
			String password = request.getParameter("password");
			Member member = memberDao.exist(email, password);

			if (member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("member", member);
				request.setAttribute("viewUrl", "redirect:../member/list.do");
			} else {
				request.setAttribute("viewUrl", "/auth/LogInFail.jsp");
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
