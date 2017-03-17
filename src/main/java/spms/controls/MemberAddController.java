package spms.controls;

import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class MemberAddController implements Controller {

	private MemberDao memberDao;

	public MemberAddController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			return "/member/MemberForm.jsp";
		}

		Member member = (Member) model.get("member");
		memberDao.insert(member);

		return "redirect:list.do";
	}

}
