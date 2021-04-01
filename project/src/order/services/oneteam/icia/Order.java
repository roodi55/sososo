package order.services.oneteam.icia;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.Action;
import beans.GoodsBean;

public class Order {

	DataAccessObject dao;

	public Order() {

	}

	public Action entrance(HttpServletRequest req) {

		Action action = null;

		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);

		switch (reqValue) {

		case "Order":
			action = this.orderCtl(req);
			break;

		}

		return action;
	}

	private Action orderCtl(HttpServletRequest req) {

		ArrayList<GoodsBean> odList = new ArrayList<GoodsBean>();
		ArrayList<GoodsBean> goodsList = new ArrayList<GoodsBean>();
		dao = new DataAccessObject();
		Action action = new Action();
		boolean isCommit = false;
		boolean actionType = false;
		String page = "res.jsp";
		String message = null;
		String msg = null;

		String[] gInfo = req.getParameter("orderInfo").split(":");

		for (int index = 0; index < gInfo.length; index++) {
			String[] goods = gInfo[index].split(",");
			GoodsBean gb = new GoodsBean();
			gb.setmId(goods[0]);
			gb.setGoCode(goods[1]);
			gb.setSeCode(goods[2]);
			gb.setGoQty(Integer.parseInt(goods[3]));
			gb.setState("I");
			odList.add(gb);
		}


		dao.getConnection();
		dao.setAutoCommit(false);

		String orDate = null;
		if (this.insorder(odList.get(0))) {
			orDate = this.date(odList.get(0));
			odList.get(0).setDate(orDate);
			for (GoodsBean goob : odList) {
				goob.setDate(orDate);
				if (this.insordetail(goob)) {
					message = "주문이 정상적으로 처리되었습니다 ";
					goob.setState("C");
					isCommit = true;
					if (this.deleteBasket(goob)) {
					}
				}
			} if(this.updateOrderDetail(odList.get(0)));
		} else {
			message = "결제도중 오류가발생하였습니다 . 다시 처음부터 시도해주세요";
		}

		for (int index = 0; index < odList.size(); index++) {
			goodsList.add(this.orderlist(odList.get(index)).get(0));
		}

		dao.setTransaction(isCommit);
		dao.setAutoCommit(true);
		dao.closeConnection();

		req.setAttribute("msg", message);
		req.setAttribute("message", msg);
		req.setAttribute("result", this.makeOrderList(goodsList));

		action.setActionType(actionType);
		action.setPage(page);

		return action;

	}

	private String makeOrderList(ArrayList<GoodsBean> gList) {

		StringBuffer sb = new StringBuffer();
		int goodsumPrice = 0;
		int count = 0;
		int total = 0;

		sb.append("<div id=\"table\">");
		sb.append("<h4> <span style=\"color:red; font-weight:bold;\">구매내역</span></h4>");
		sb.append("<table class=\"table\">");
		sb.append("<tr>");
		sb.append("<th>이미지</th>");
		sb.append("<th>NO</th>");
		sb.append("<th>상품정보</th>");
		sb.append("<th>판매자</th>");
		sb.append("<th>상품 가격</th>");
		sb.append("<th>구매 수량</th>");
		sb.append("<th>총 가격</th>");
		sb.append("</tr>");

		for (GoodsBean gb : gList) {
			count++;
			goodsumPrice = (gb.getGoQty() * gb.getGoPrice());
			sb.append("<tr>");
			sb.append("<td><img src=\"image/" + gb.getGoImage()
					+ "\" onmouseenter=\"zoomIn(event)\" onmouseleave=\"zoomOut(event)\"></td>");
			sb.append("<td>" + count + "</td>");
			sb.append("<td><a onClick=\"moveGoods(\'" + gb.getGoCode() + "\',\'" + gb.getSeCode() + "\')\" />"
					+ gb.getGoName() + "</a></td>");
			sb.append("<td>" + gb.getSeName() + "</td>");
			sb.append("<td>" + gb.getGoPrice() + "원</td>");
			sb.append("<td>" + gb.getGoQty() + "</td>");
			sb.append("<td>" + goodsumPrice + "원</td>");
			sb.append("</tr>");
			total += goodsumPrice;
		}
		sb.append("<tr>");
		sb.append("<td colspan=\"7\" style=\"color:red; font-weight:bold;\">구매 총액은 " + total + " 원입니다</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</div>");

		return sb.toString();
	}

	private ArrayList<GoodsBean> orderlist(GoodsBean gb) {

		return dao.orderList(gb);
	}

	private boolean updateOrderDetail(GoodsBean goob) {

		return this.convertToBoolean(dao.updateOrderDetail(goob));
	}

	private boolean deleteBasket(GoodsBean goob) {

		return this.convertToBoolean(dao.deleteBasket(goob));
	}

	private boolean insorder(GoodsBean gb) {
		return this.convertToBoolean(dao.insOrders(gb));
	}

	private boolean insordetail(GoodsBean gb) {
		return this.convertToBoolean(dao.insOrderDetail(gb));
	}

	private String date(GoodsBean gb) {
		return dao.date(gb);
	}

	private boolean convertToBoolean(int data) {
		return data == 1 ? true : false;
	}

}
