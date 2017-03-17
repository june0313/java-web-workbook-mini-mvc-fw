package spms.controls;

import spms.dao.MemberDao;

import java.util.Map;

/**
 * Created by wayne on 2017. 3. 15..
 *
 */
public class MemberDeleteController implements Controller {

	private MemberDao memberDao;

	public MemberDeleteController setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
		return this;
	}

	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Integer no = Integer.parseInt((String) model.get("no"));

		memberDao.delete(no);

		return "redirect:list.do";
	}

}
