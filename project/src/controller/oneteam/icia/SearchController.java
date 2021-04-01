package controller.oneteam.icia;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Action;
import search.services.oneteam.icia.Search;

@WebServlet({ "/List", "/Search", "/GoodsDetail","/Basket","/BasketCheck","/BasketDelete","/BasketOrderForm","/GoodsOrderForm",
				"/Snack","/Noodle","/Drink","/Frozen","/Fresh","/Menu"})
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {


		Action action = null;

		req.setCharacterEncoding("UTF-8");

		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);

		if (reqValue.equals("List") || reqValue.equals("Search") || reqValue.equals("GoodsDetail")
			|| reqValue.equals("Basket") || reqValue.equals("BasketCheck") || reqValue.equals("BasketDelete")
			|| reqValue.equals("BasketOrderForm") || reqValue.equals("GoodsOrderForm")	
			|| reqValue.equals("Snack") || reqValue.equals("Noodle") || reqValue.equals("Drink") 
			|| reqValue.equals("Frozen") || reqValue.equals("Fresh") ||reqValue.equals("Menu")) {

			if (req.getSession().getAttribute("accessInfo") != null) {

				Search sch = new Search();
				action = sch.entrance(req);
			}else {

				action = new Action();

				action.setActionType(false);
				action.setPage("login.jsp?" + this.setPara(reqValue, "gInfo", req));

			}
		}

		// 서버 응답
		if (action.isActionType()) {
			if (action.getMessage() != null) {
				res.sendRedirect(action.getPage() + "?message=" + URLEncoder.encode(action.getMessage(), "UTF-8"));
			} else {
				res.sendRedirect(action.getPage());
			}

		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(action.getPage());
			dispatcher.forward(req, res);
		}

	}

	private String setPara(String action, String paramName, HttpServletRequest req)
			throws UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();
		// action = Basket?null:1001:1000112345:10
		sb.append("action=" + URLEncoder.encode(action, "UTF-8") + "&");

		for (int index = 0; index < req.getParameterValues("gInfo").length; index++) {
			sb.append(paramName + "=");
			sb.append(URLEncoder.encode(req.getParameterValues("gInfo")[index], "UTF-8"));
			sb.append(index == req.getParameterValues("gInfo").length - 1 ? "" : "&");
		}
		sb.append("&opt=" + req.getParameter("opt"));
		return sb.toString();
	}

}
