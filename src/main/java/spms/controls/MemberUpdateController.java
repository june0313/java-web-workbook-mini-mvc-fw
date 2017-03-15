package spms.controls;

import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class MemberUpdateController implements Controller {

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDao memberDao = (MemberDao) model.get("memberDao");

		if (model.get("member") == null) {
			Integer no = Integer.parseInt((String) model.get("no"));

			Member member = memberDao.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		}

		Member member = (Member) model.get("member");
		memberDao.update(member);

		return "redirect:list.do";
	}

}
