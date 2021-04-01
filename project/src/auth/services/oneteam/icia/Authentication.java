package auth.services.oneteam.icia;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.AuthBean;

public class Authentication {

	DataAccesObject dao;

	public Authentication() {

	}

	public Action entrance(HttpServletRequest req) throws UnsupportedEncodingException {

		Action action = null;

		String reqVal = req.getRequestURI().substring(req.getContextPath().length() + 1);

		switch (reqVal) {

		case "LogIn":
			action = this.loginCtl(req);
			break;
		case "LogOut":
			action = this.logOutCtl(req);
			break;
		case "MyPage":
			action = this.modifyInfo(req);
			break;
		case "MyPageForm":
			action = this.myPageCtl(req);
			break;
		}

		return action;

	}

	private Action myPageCtl(HttpServletRequest req) {

		Action action = new Action();
		ArrayList<AuthBean> memberInfo = null;
		boolean actionType = false;
		String page = "myPage.jsp";

		AuthBean auth = new AuthBean();
		auth.setmId((String) req.getSession().getAttribute("accessInfo"));
		auth.setAccessType(1);

		dao = new DataAccesObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		memberInfo = this.selectMember(auth);

		req.setAttribute("ID", memberInfo.get(0).getmId());
		req.setAttribute("NAME", memberInfo.get(0).getmName());
		req.setAttribute("PHONE", memberInfo.get(0).getPhone());

		dao.setAutoCommit(true);
		dao.closeConnection();

		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private ArrayList<AuthBean> selectMember(AuthBean auth) {

		return dao.selectMember(auth);
	}

	private Action loginCtl(HttpServletRequest req) throws UnsupportedEncodingException {

		Action action = new Action();
		ArrayList<AuthBean> memberInfo = null;
		boolean tran = false;
		boolean actionType = true;
		String page = "login.jsp";
		String message = "아이디나 패스워드가 올바르지 않습니다.";

		AuthBean auth = new AuthBean();
		auth.setmId(req.getParameterValues("accessInfo")[0]);
		auth.setmPassword(req.getParameterValues("accessInfo")[1]);
		auth.setAccessType(1);

		dao = new DataAccesObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		if (this.isMember(auth)) {
			if (this.isActive(auth)) {
				if (this.isPassword(auth)) {
					if (this.insAccessLog(auth)) {
						memberInfo = this.MemberInfo(auth);

						// HttpSession저장
						HttpSession session = req.getSession();
						session.setAttribute("accessInfo", memberInfo.get(0).getmId());

						tran = true;
						actionType = false;
						page = "List";
						message = "";

						if (req.getParameter("action").equals("Basket")) {
							req.setAttribute("gInfo", session.getAttribute("accessInfo"));
							page = "Basket?" + this.setPara("gInfo", req);

						} else if (req.getParameter("action").equals("Order")) {
							req.setAttribute("gInfo", session.getAttribute("accessInfo"));
							page = "Order?" + this.setPara("gInfo", req);
							// 전달된 값 : ID / GOCODE / SECODE / QUANTITY
						}

					} else {
						message = "네트워크가 불안정합니다. 다시 접속해주세요~";
					}
				}
			} else {
				message = "휴면 계정입니다. 활성화를 해주세요~";
			}
		}

		dao.setTransaction(tran);
		dao.setAutoCommit(true);
		dao.closeConnection();

		String mInfo = memberInfo.get(0).getmName() + "(" + memberInfo.get(0).getmId() + ")님은 "
				+ memberInfo.get(0).getAccessTime() + " 로그인 하셨습니다.";
		req.setAttribute("memberInfo", mInfo);

		action.setActionType(actionType);
		action.setPage(page);

		return action;

	}

	private Action logOutCtl(HttpServletRequest req) {
		Action action = new Action();
		String message = null;

		dao = new DataAccesObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		AuthBean auth = new AuthBean();
		HttpSession session = req.getSession();

		String name = (String) session.getAttribute("accessInfo");

		auth.setmId(name);
		auth.setAccessType(-1);

		if (session.getAttribute("accessInfo") != null) {

			if (this.insAccessLog(auth)) {
				message = "로그아웃성공";
			}

		} else {
			message = "이미 로그아웃이 되엇거나 로그인하지않앗습니다";
		}

		dao.setTransaction(true);
		dao.setAutoCommit(true);
		dao.closeConnection();

		session.removeAttribute("accessInfo");

		action.setActionType(true);
		action.setPage("index.jsp");
		action.setMessage(message);

		return action;

	}

	private Action modifyInfo(HttpServletRequest req) {

		Action action = new Action();
		boolean tran = false;
		boolean actionType = false;
		String page = "myPage.jsp";
		String message = "";

		dao = new DataAccesObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		AuthBean auth = new AuthBean();

		HttpSession session = req.getSession();
		String name = (String) session.getAttribute("accessInfo");

		auth.setmId(name);
		// auth.setmId(req.getParameterValues("updateInfo")[0]);
		auth.setmPassword(req.getParameterValues("updateInfo")[1]);
		auth.setmName(req.getParameterValues("updateInfo")[2]);
		auth.setPhone(req.getParameterValues("updateInfo")[3]);

		if (session.getAttribute("accessInfo") != null) {

			if (!this.isPhone(auth)) {

				if (this.modify(auth)) {
					tran = true;
					actionType = false;
					message = "정보가 수정되었습니다";
					page = "List";
				}

			} else {
				message = "다른 전화번호를 다시 입력해주세요";
				page = "MyPageForm";
			}
			;

		} else {
			message = "다시 시도해주세요 ";
		}

		dao.setAutoCommit(true);
		dao.setTransaction(tran);
		dao.closeConnection();

		req.setAttribute("msg", message);
		action.setActionType(actionType);
		action.setPage(page);
		action.setMessage(message);
		return action;
	}

	private boolean isPhone(AuthBean auth) {

		return this.convertToBoolean(dao.isPhone(auth));
	}

	private boolean isMember(AuthBean auth) {
		return this.convertToBoolean(dao.isMember(auth));
	}

	private boolean isActive(AuthBean auth) {

		return this.convertToBoolean(dao.isActive(auth));
	}

	private boolean isPassword(AuthBean auth) {

		return this.convertToBoolean(dao.isPassWord(auth));
	}

	private boolean insAccessLog(AuthBean auth) {

		return this.convertToBoolean(dao.insAccessLog(auth));
	}

	private boolean convertToBoolean(int data) {

		return (data == 1) ? true : false;
	}

	private ArrayList<AuthBean> MemberInfo(AuthBean auth) {

		return dao.MemberInfo(auth);
	}

	private boolean modify(AuthBean auth) {
		return this.convertToBoolean(dao.modify(auth));

	}

	private String setPara(String paramName, HttpServletRequest req) throws UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();
		// action = Basket?null:1001:1000112345:10

		for (int index = 0; index < req.getParameterValues("gInfo").length; index++) {
			sb.append(paramName + "=");
			sb.append(URLEncoder.encode(
					(index == 0) ? req.getAttribute(paramName).toString() : req.getParameterValues(paramName)[index],
					"UTF-8"));
			// sb.append(URLEncoder.encode(req.getParameterValues("gInfo")[index],
			// "UTF-8"));
			sb.append(index == req.getParameterValues("gInfo").length - 1 ? "" : "&");
		}
		sb.append("&opt=" + req.getParameter("opt"));
		return sb.toString();
	}

}
