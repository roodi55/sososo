package search.services.oneteam.icia;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.GoodsBean;

public class DataAccessObject extends module.oneteam.icia.DataAccesObject {

	DataAccessObject() {

	}

	// 상품코드 앞자리 검색
	ArrayList<GoodsBean> selectGoods(GoodsBean gb) {

		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();

		String query = "SELECT GOCODE , GONAME, SECODE, PRICE, STOCK, LIMAGE FROM"
				+ " GOODSINFO WHERE GOCODE LIKE ? || '%' ";

		try {

			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getStartGoCode());
			this.rs = this.pstatement.executeQuery();

			while (rs.next()) {

				GoodsBean goods = new GoodsBean();
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("PRICE"));
				goods.setGoStock(rs.getInt("STOCK"));
				goods.setGoImage(rs.getNString("LIMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				gList.add(goods);

			}

		} catch (Exception e) {
		}

		return gList;
	}

	// 상세정보 검색
	ArrayList<GoodsBean> getDetail(GoodsBean gb) {
		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();
		String query = "SELECT GOCODE, GONAME, SECODE, PRICE, STOCK, " + "LIMAGE, BIMAGE, SENAME " + "FROM GOODSINFO "
				+ "WHERE GOCODE=? AND SECODE=?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getGoCode());
			this.pstatement.setNString(2, gb.getSeCode());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				GoodsBean goods = new GoodsBean();
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("PRICE"));
				goods.setGoStock(rs.getInt("STOCK"));
				goods.setGoImage(rs.getNString("LIMAGE"));
				goods.setGoBimage(rs.getNString("BIMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				goods.setSeName(rs.getNString("SENAME"));
				gList.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gList;
	}

	ArrayList<GoodsBean> searchGoods() {

		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();

		String query = "SELECT GOCODE , GONAME, SECODE, PRICE, STOCK, LIMAGE FROM GOODSINFO";

		try {

			this.statement = this.connection.createStatement();
			this.rs = this.statement.executeQuery(query);

			while (rs.next()) {
				GoodsBean goods = new GoodsBean();
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("PRICE"));
				goods.setGoStock(rs.getInt("STOCK"));
				goods.setGoImage(rs.getNString("LIMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				gList.add(goods);

			}

		} catch (Exception e) {
		}

		return gList;
	}

	// 단어검색 상품조회
	ArrayList<GoodsBean> searchGoods(GoodsBean gb) {

		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();

		String query = "SELECT GOCODE , GONAME, SECODE, PRICE, STOCK, LIMAGE FROM"
				+ " GOODSINFO WHERE SEARCH LIKE '%'||?||'%'";

		try {

			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getWord());
			this.rs = this.pstatement.executeQuery();

			while (rs.next()) {

				GoodsBean goods = new GoodsBean();
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("PRICE"));
				goods.setGoStock(rs.getInt("STOCK"));
				goods.setGoImage(rs.getNString("LIMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				gList.add(goods);

			}

		} catch (Exception e) {
		}


		return gList;
	}

	ArrayList<GoodsBean> getBasketList(GoodsBean gb) {
		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();
		String query = "SELECT * FROM BL WHERE MMID = ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				GoodsBean goods = new GoodsBean();
				goods.setmId(rs.getNString("MMID"));
				goods.setMname(rs.getNString("MNAME"));
				goods.setGoCode(rs.getNString("GOCODE"));
				goods.setGoName(rs.getNString("GONAME"));
				goods.setGoPrice(rs.getInt("GOPRICE"));
				goods.setGoQty(rs.getInt("QTY"));
				goods.setGoImage(rs.getNString("GOIMAGE"));
				goods.setSeCode(rs.getNString("SECODE"));
				goods.setSeName(rs.getNString("SENAME"));
				gList.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gList;
	}

	// 삭제
	int delBasketGoods(GoodsBean gb) {
		int count = 0;
		String query = "DELETE FROM BA WHERE BA_MMID=? AND BA_SAGOCODE=? AND BA_SASECODE=?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getGoCode());
			this.pstatement.setNString(3, gb.getSeCode());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 기 등록된 상품 검색
	int isBasketGoods(GoodsBean gb) {
		int count = 0;
		String query = "SELECT COUNT(*) AS CNT FROM BA WHERE BA_MMID = ? " + "AND BA_SAGOCODE = ? AND BA_SASECODE = ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getGoCode());
			this.pstatement.setNString(3, gb.getSeCode());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 장바구니 업데이트
	int isUpdateBasket(GoodsBean gb) {
		int count = 0;
		String query = "UPDATE BA SET BA_QUANTITY = ? WHERE BA_MMID = ? AND BA_SAGOCODE = ? AND BA_SASECODE = ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setInt(1, gb.getGoQty());
			this.pstatement.setNString(2, gb.getmId());
			this.pstatement.setNString(3, gb.getGoCode());
			this.pstatement.setNString(4, gb.getSeCode());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 장바구니 입력
	int insBasket(GoodsBean gb) {
		int count = 0;
		String query = "INSERT INTO BA(BA_MMID, BA_SAGOCODE, BA_SASECODE, BA_QUANTITY) " + "VALUES(?, ?, ?, ?)";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getGoCode());
			this.pstatement.setNString(3, gb.getSeCode());
			this.pstatement.setInt(4, gb.getGoQty());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 결제화면 ~
	ArrayList<GoodsBean> orderList(GoodsBean gb) {

		ArrayList<GoodsBean> gList = new ArrayList<GoodsBean>();

		int qty = gb.getGoQty();

		String query = "SELECT * FROM GINFO WHERE GOCODE=? AND SECODE=?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getGoCode());
			this.pstatement.setNString(2, gb.getSeCode());

			this.rs = this.pstatement.executeQuery();

			while (rs.next()) {
				
				GoodsBean god = new GoodsBean();
				god.setGoCode(rs.getNString("GOCODE"));
				god.setGoName(rs.getNString("GONAME"));
				god.setSeCode(rs.getNString("SECODE"));
				god.setSeName(rs.getNString("SENAME"));
				god.setGoPrice(rs.getInt("PRICE"));
				god.setGoImage(rs.getNString("LIMAGE"));
				god.setGoQty(qty);
				god.setState("I");
				gList.add(god);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gList;
	}

}
