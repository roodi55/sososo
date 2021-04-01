package search.services.oneteam.icia;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Action;
import beans.GoodsBean;

public class Search {

	private DataAccessObject dao;
	int sumtotal = 0;

	public Search() {

	}

	public Action entrance(HttpServletRequest req) {
		Action action = null;
		String reqValue = req.getRequestURI().substring(req.getContextPath().length() + 1);

		switch (reqValue) {
		case "List":
			action = this.goodsList(req);
			break;
		case "GoodsDetail":
			action = this.goodsDetailCtl(req);
			break;
		case "Search":
			action = this.searchCtl(req);
			break;
		case "Basket":
			action = this.basketCtl(req);
			break;
		case "BasketCheck":
			action = this.basketListCtl(req);
			break;
		case "BasketDelete":
			action = this.basketDeleteCtl(req);
			break;
		case "BasketOrderForm":
			action = this.basketOrderCtl(req);
			break;
		case "GoodsOrderForm":
			action = this.GoodsorderFormCtl(req);
			break;
		case "Snack":
			action =this.tabCategories(req);
			break;
		case "Noodle":
			action =this.tabCategories(req);
			break;
		case "Drink":
			action =this.tabCategories(req);
			break;
		case "Frozen":
			action =this.tabCategories(req);
			break;
		case "Fresh":
			action =this.tabCategories(req);
			break;
		case "Menu":
			action = this.goodsList(req);
			break;

		}

		return action;

	}
	private Action tabCategories(HttpServletRequest req) {
		Action action = new Action();
		String page = "goods.jsp";
		boolean actionType = false;
		
		GoodsBean gb = new GoodsBean();
		gb.setStartGoCode(req.getParameter("number"));
		
		
		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);
		
		req.setAttribute("gList", this.makeGoodsList(this.selCategories(gb)));
		
		dao.setAutoCommit(true);
		dao.closeConnection();
		
		action.setActionType(actionType);
		action.setPage(page);
		
		return action;
	}

	private Action GoodsorderFormCtl(HttpServletRequest req) {

		Action action = new Action();
		ArrayList<GoodsBean> gList;
		boolean actionType = false;
		String page = "orderpage.jsp";
		//String message = "주문페이지로 이동합니다";
		GoodsBean gb = new GoodsBean();

		gb.setmId(req.getParameterValues("gInfo")[0]);
		gb.setGoCode(req.getParameterValues("gInfo")[1]);
		gb.setSeCode(req.getParameterValues("gInfo")[2]);
		gb.setGoQty(Integer.parseInt(req.getParameter("opt")));

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		gList = this.orderlist(gb);

		req.setAttribute("orderInfo", this.makeOrderList(gList));

//		dao.setTransaction(isCommit);
		dao.setAutoCommit(true);
		dao.closeConnection();

		//req.setAttribute("msg", message);
		req.setAttribute("sumtotal", sumtotal);
		req.setAttribute("salePrice", sumtotal / 10);
		req.setAttribute("total", sumtotal - sumtotal / 10);
		req.setAttribute("orderList", req.getSession().getAttribute("accessInfo") + "," + gList.get(0).getGoCode() + ","
				+ gList.get(0).getSeCode() + "," + gList.get(0).getGoQty());
		req.setAttribute("confirm", "주문을 확정지으시겟습니까 ?");
		req.setAttribute("ID", req.getSession().getAttribute("accessInfo"));

		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private Action basketOrderCtl(HttpServletRequest req) {

		Action action = new Action();
		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();
		ArrayList<GoodsBean> goodsList = new ArrayList<GoodsBean>();
		boolean actionType = false;
		String page = "orderpage.jsp";
		String message = "주문페이지로 이동합니다";

		String[] gInfo = req.getParameter("gInfo").split(":");

		String od = req.getParameter("gInfo");

		for (int index = 0; index < gInfo.length; index++) {
			String[] goods = gInfo[index].split(",");
			GoodsBean gb = new GoodsBean();
			gb.setmId(goods[0]);
			gb.setGoCode(goods[1]);
			gb.setSeCode(goods[2]);
			gb.setGoQty(Integer.parseInt(goods[3]));
			gb.setState("I");
			gList.add(gb);
		}

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		for (int index = 0; index < gList.size(); index++) {
			goodsList.add(this.orderlist(gList.get(index)).get(0));
		}

		req.setAttribute("orderInfo", this.makeOrderList(goodsList));

		dao.setAutoCommit(true);
		dao.closeConnection();

		req.setAttribute("msg", message);
		req.setAttribute("sumtotal", sumtotal);
		req.setAttribute("salePrice", sumtotal / 10);
		req.setAttribute("total", sumtotal - sumtotal / 10);
		req.setAttribute("confirm", "장바구니의 주문을 확정지으시겟습니까 ?");
		req.setAttribute("orderList", od);
		req.setAttribute("ID", req.getSession().getAttribute("accessInfo"));

		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private Action goodsDetailCtl(HttpServletRequest req) {

		Action action = new Action();
		String page = "goodsDetail.jsp";
		boolean actionType = false;
		ArrayList<GoodsBean> goodsInfo;
		// req --> gb
		GoodsBean gb = new GoodsBean();
		gb.setGoCode(req.getParameterValues("code")[0]);
		gb.setSeCode(req.getParameterValues("code")[1]);

		dao = new DataAccessObject();
		dao.getConnection();

		goodsInfo = this.getDetail(gb);
		if (goodsInfo.size() != 1) {
			page = "goods.jsp";
			actionType = false;
			req.setAttribute("gList",
					this.makeGoodsList((gb.getWord() == null) ? this.searchGoods() : this.searchGoods(gb)));
			req.setAttribute("message", "죄송합니다. 품절상태입니다.");
		} else {
			req.setAttribute("goodsImage", "image/" + goodsInfo.get(0).getGoImage());
			req.setAttribute("item", goodsInfo.get(0).getGoName());
			req.setAttribute("price", goodsInfo.get(0).getGoPrice());
			req.setAttribute("gInfo", req.getSession().getAttribute("accessInfo") + ":" + goodsInfo.get(0).getGoCode()
					+ ":" + goodsInfo.get(0).getSeCode());
			req.setAttribute("detailImage", "image/" + goodsInfo.get(0).getGoBimage());
			req.setAttribute("seller", goodsInfo.get(0).getSeName());
		}
		dao.closeConnection();

		action.setActionType(actionType);
		action.setPage(page);

		return action;

	}

	private String makeOrderList(ArrayList<GoodsBean> gList) {

		StringBuffer sb = new StringBuffer();
		int goodsumPrice = 0;
		int count = 0;

		sb.append("<div id=\"table\">");
		sb.append("<h4>주문상품 정보  <span style=\"color: red; font-weight: bold;\">( 이미지를 크게보려면 이미지에 마우스를 올려보세요 ::: 상품상세정보를 보려면 해당 상품정보를 클릭하세요)</span></h4>");
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
			sb.append("<td><a onClick=\"moveGoods(\'" + gb.getGoCode() + "\',\'" + gb.getSeCode() + "\')\" />" + gb.getGoName() + "</a></td>");
			sb.append("<td>" + gb.getSeName() + "</td>");
			sb.append("<td>" + gb.getGoPrice() + "원</td>");
			sb.append("<td>" + gb.getGoQty() + "</td>");
			sb.append("<td>" + goodsumPrice + "원</td>");
			sb.append("</tr>");
			sumtotal += goodsumPrice;
		}

		sb.append("</table>");
		sb.append("</div>");

		return sb.toString();
	}

	private String makeGoodsList(ArrayList<GoodsBean> gList) {

		StringBuffer sb = new StringBuffer();

		int index = 0;

		for (GoodsBean goods : gList) {
			index++;

			if (index % 3 == 1) {

				sb.append("<div class=\"line\">");

			}

			sb.append("<div class=\"item\" onClick=\"goDetail(\'" + goods.getGoCode() + ":" + goods.getSeCode()
					+ "\')\"><input type=\"hidden\" name=\"pk\" value=\"" + goods.getGoCode() + ":" + goods.getSeCode()
					+ "\"/>");
			sb.append("<div class=\"item__top\"><img src=\"image/" + goods.getGoImage() + "\" /></div>");
			sb.append("<div class=\"item__bottom\"><div class=\"item-name\">" + goods.getGoName()
					+ "</div><div class=\"item-price\">" + goods.getGoPrice() + "원</div><div class=\"item-stock\">재고"
					+ goods.getGoStock() + "무료배송</div></div>");
			sb.append("</div>");

			if (index % 3 == 0) {

				sb.append("</div>");

			}

		}
		if (index % 3 != 0) {
			sb.append("</div>");
		}

		return sb.toString();
	}

	private Action goodsList(HttpServletRequest req) {


		Action action = new Action();
		String page = "goods.jsp";
		boolean actionType = false;

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		req.setAttribute("gList", this.makeGoodsList(this.searchGoods()));

		dao.setAutoCommit(true);
		dao.closeConnection();

		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private Action searchCtl(HttpServletRequest req) {
		Action action = new Action();
		String page = "goods.jsp";
		boolean actionType = false;

		GoodsBean gb = new GoodsBean();
		gb.setWord(req.getParameter("word"));

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		req.setAttribute("gList",
				this.makeGoodsList((gb.getWord().equals("")) ? this.searchGoods() : this.searchGoods(gb)));

		dao.setAutoCommit(true);
		dao.closeConnection();

		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private Action basketCtl(HttpServletRequest req) {
		Action action = new Action();
		GoodsBean gb = new GoodsBean();
		boolean isCommit = false;
		boolean actionType = false;
		String page = "GoodsDetail";
		String message = null;
		boolean messageType = false;
		// req --> bean

		gb.setmId(req.getParameterValues("gInfo")[0]);
		gb.setGoCode(req.getParameterValues("gInfo")[1]);
		gb.setSeCode(req.getParameterValues("gInfo")[2]);
		gb.setGoQty(Integer.parseInt(req.getParameter("opt")));
		// gb.setGoQty(Integer.parseInt(req.getParameterValues("gInfo")[3]));

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		// 장바구니 구현 --> goodsDetail.jsp
		if (this.isBasketGoods(gb)) {
			if (this.updBasket(gb)) {
				isCommit = true;
				page += "?code=" + gb.getGoCode() + "&code=" + gb.getSeCode();
				message = "수량이 변경되었습니다. 장바구니로 이동하시겠습니까?";
				messageType = true;
			} else {
				message = "다신 한 번 시도해주세요!";
			}
		} else {
			if (this.insBasket(gb)) {
				isCommit = true;
				page += "?code=" + gb.getGoCode() + "&code=" + gb.getSeCode();
				message = "상품이 장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?";
				messageType = true;
			} else {
				req.setAttribute("message", "다신 한 번 시도해주세요!");
			}
		}

		dao.setTransaction(isCommit);
		dao.setAutoCommit(true);
		dao.closeConnection();

		req.setAttribute("msg", message);
		req.setAttribute("mType", messageType);
		action.setActionType(actionType);
		action.setPage(page);
		return action;
	}

	private Action basketListCtl(HttpServletRequest req) {

		Action action = new Action();
		GoodsBean gb = new GoodsBean();
		boolean isCommit = false;
		boolean actionType = false;
		String page = "basketList.jsp";
		String tag = null;
		// req --> bean
		gb.setmId(req.getParameter("gInfo"));

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		tag = this.makeHtml(this.getBasketList(gb));

		// 세션 저장
		HttpSession session = req.getSession();
		session.setAttribute("accessInfo", gb.getmId());

		dao.setTransaction(isCommit);
		dao.setAutoCommit(true);
		dao.closeConnection();
		req.setAttribute("orderInfo", tag);

		action.setActionType(actionType);
		action.setPage(page);
		return action;
	}

	private Action basketDeleteCtl(HttpServletRequest req) {
		Action action = new Action();
		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();
		boolean isCommit = true;
		boolean actionType = false;
		String[] gInfo = req.getParameter("gInfo").split(":");
		String page = "BasketCheck?gInfo=" + gInfo[0].split(",")[0];
		String message = "선택하신상품이 성공적으로 삭제되었습니다";

		for (int index = 0; index < gInfo.length; index++) {
			GoodsBean gb = new GoodsBean();
			String[] goods = gInfo[index].split(",");
			gb.setmId(goods[0]);
			gb.setGoCode(goods[1]);
			gb.setSeCode(goods[2]);
			gList.add(gb);
		}

		dao = new DataAccessObject();
		dao.getConnection();
		dao.setAutoCommit(false);

		for (GoodsBean gb : gList) {
			if (this.delBasketGoods(gb)) {
				isCommit = true;
			} else {
				isCommit = false;
			}
		}

		dao.setTransaction(isCommit);
		dao.setAutoCommit(true);
		dao.closeConnection();

		req.setAttribute("message", message);
		action.setActionType(actionType);
		action.setPage(page);

		return action;
	}

	private boolean delBasketGoods(GoodsBean gb) {
		return convertToBoolean(dao.delBasketGoods(gb));
	}

	private boolean isBasketGoods(GoodsBean gb) {
		return convertToBoolean(dao.isBasketGoods(gb));
	}

	private boolean updBasket(GoodsBean gb) {
		return convertToBoolean(dao.isUpdateBasket(gb));
	}

	private boolean insBasket(GoodsBean gb) {
		return convertToBoolean(dao.insBasket(gb));
	}

	private String makeHtml(ArrayList<GoodsBean> gList) {
		StringBuffer sb = new StringBuffer();
		int totAmount = 0;
		int amount = 0;
		int count = 0;
		int index = 0;
		String gInfo = new String();
		String Info = new String();
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<th class=\"th1_size\"></th>");
		sb.append("<th></th>");
		sb.append("<th>상품명</th>");
		sb.append("<th>판매자</th>");
		sb.append("<th>가격</th>");
		sb.append("<th>수량</th>");
		sb.append("<th>금액</th>");
		sb.append("</tr>"); // -->고정정보
		for (GoodsBean gb : gList) {
			count++;
			index++;
			amount = gb.getGoQty() * gb.getGoPrice();
			// 수정
			Info = gb.getmId() + "," + gb.getGoCode() + "," + gb.getSeCode();
			totAmount += amount;
			sb.append("<tr>");
			sb.append("<td><input type=\"checkbox\" name=\"check\" class=\"check\" onChange=\"checkState(this)\"/></td>");
			sb.append("<td><img src=\"image/" + gb.getGoImage() +"\"/></td>");
			sb.append("<td>"+gb.getGoName());
			sb.append("<input type=\"button\" class=\"icon\" onClick=\"delgoods(\'" + Info + "\')\"/></td>");
			//sb.append("<td>" + gb.getGoName() + "</td>");
			sb.append("<td>" + gb.getSeName() + "</td>");
			sb.append("<td>" + gb.getGoPrice() + "원" + "</td>");
			sb.append("<td class=\"count\">");
			sb.append("<div class=\"btn\"><input type=\"button\" class=\"bu\" value=\"-\" onClick=\"alter(false," + index + ")\">");
			sb.append("<input type=\"text\" class=\"opt\" id=" + index + " name=\"opt\" value=" + gb.getGoQty() + " />");
			sb.append("<input type=\"button\" class=\"bu\" value=\"+\" onClick=\"alter(true," + index + ")\"></div>");
			sb.append("<input type=\"button\" class=\"bu2\" value=\"변경\" onClick=\"change(\'" + Info + "\',\'" + index + "\')\" />");
			sb.append("</td>");
			
			sb.append("<td onChange=\"price(\'"+gb.getGoPrice()+"\',\'"+index+"\')\">" + amount + "원 </td>");
			gInfo += (gb.getmId() + "," + gb.getGoCode() + "," + gb.getSeCode() + "," + gb.getGoQty()
					+ (count == gList.size() ? "" : ":"));
			// 수정 (delgoods(gInfo) -> delgoods(Info)
			sb.append("</tr>");
		}
		sb.append("<tr>");
		sb.append("<td><input type=\"checkbox\" id=\"Allcheck\" class=\"check\"  onClick=\"AllCheck()\">");
		sb.append("<input type=\"button\" class=\"dbtn\" value=\"삭제\" onClick=\"remove(\'" + gInfo + "\')\"/></td>");
		sb.append("<td colspan=\"2\"></td>");
		sb.append("<td colspan=\"3\" class=\"money\">상품 총 금액</td>");
		sb.append("<td >" + totAmount + "원" + "</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("<div class=\"submit\">");
		sb.append("<span id=\"button\" class=\"submit1\" onClick=\"movePage(true)\">쇼핑 계속하기</span>");
		sb.append("<input type=\"button\" value=\"주문하기\" class=\"submit2\" onClick=\"order(\'" + gInfo + "\')\"/>");
		sb.append("</div>");
		return sb.toString();
	}
	private ArrayList<GoodsBean> selCategories(GoodsBean gb){
		 return dao.selectGoods(gb);
		}

	private ArrayList<GoodsBean> getBasketList(GoodsBean gb) {
		return dao.getBasketList(gb);
	}

	private boolean convertToBoolean(int value) {
		return (value == 1) ? true : false;
	}

	private ArrayList<GoodsBean> getDetail(GoodsBean gb) {
		return dao.getDetail(gb);
	}

	private ArrayList<GoodsBean> searchGoods() {
		return dao.searchGoods();
	}

	private ArrayList<GoodsBean> searchGoods(GoodsBean gb) {

		return dao.searchGoods(gb);
	}

	private ArrayList<GoodsBean> orderlist(GoodsBean gb) {

		return dao.orderList(gb);
	}

}
