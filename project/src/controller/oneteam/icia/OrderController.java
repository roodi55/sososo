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
import order.services.oneteam.icia.Order;

@WebServlet({ "/OrderForm", "/Order" })
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		Action action = null;

		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);

		if (req.getSession().getAttribute("accessInfo") != null) {

			if (reqValue.equals("OrderForm") || reqValue.equals("Order")) {

				Order order = new Order();
				action = order.entrance(req);

			}
		} else {

			action = new Action();

			action.setActionType(false);
			action.setPage("login.jsp?" + this.setPara(reqValue, "gInfo", req));

		}

		// 클라이언트에게 전송
		if (action.isActionType()) {
			res.sendRedirect(action.getPage());
		} else {
			RequestDispatcher dis = req.getRequestDispatcher(action.getPage());
			dis.forward(req, res);
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
