package spms.controls;

import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class MemberUpdateController implements Controller, DataBinding {

	private MemberDao memberDao;

	public MemberUpdateController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member) model.get("member");

		if (member.getEmail() == null) {
			Integer no = (Integer) model.get("no");

			model.put("member", memberDao.selectOne(no));
			return "/member/MemberUpdateForm.jsp";
		}

		memberDao.update(member);

		return "redirect:list.do";
	}

	@Override
	public Object[] getDataBinders() {
		return new Object[] {
			"no", Integer.class,
			"member", Member.class
		};
	}
}
