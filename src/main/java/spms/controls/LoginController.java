package spms.controls;

import spms.dao.MemberDao;
import spms.vo.Member;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class LoginController implements Controller{

	private MemberDao memberDao;

	public LoginController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		HttpSession session = (HttpSession) model.get("session");

		String email = (String) model.get("email");
		String password = (String) model.get("password");

		if (email == null && password == null) {
			return "/auth/LogInForm.jsp";
		}

		Member member = memberDao.exist(email, password);

		if (member != null) {
			session.setAttribute("member", member);
			return "redirect:../member/list.do";
		}

		return "redirect:/auth/LogInFail.jsp";
	}

}
