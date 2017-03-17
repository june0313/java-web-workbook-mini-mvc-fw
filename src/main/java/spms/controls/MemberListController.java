package spms.controls;

import spms.dao.MemberDao;

import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class MemberListController implements Controller {

	private MemberDao memberDao;

	public MemberListController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}

}
