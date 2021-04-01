package order.services.oneteam.icia;

import java.sql.SQLException;
import java.util.ArrayList;

//import com.sun.xml.internal.ws.api.server.Module;

import beans.GoodsBean;

public class DataAccessObject extends module.oneteam.icia.DataAccesObject {

	public DataAccessObject() {

	}
	
	// 해당날자에 주문한 모든상품에 대한 업데이트
	int updateOrderDetail(GoodsBean gb) {
		int count = 0;
		String query = "UPDATE OD SET OD_STATE=? WHERE OD_ORMMID=? AND OD_ORDATE=TO_DATE(?,'YYYYMMDDHH24MISS')";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getState());
			this.pstatement.setNString(2, gb.getmId());
			this.pstatement.setNString(3, gb.getDate());

			count = this.pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	
	int deleteBasket(GoodsBean gb) {
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

	String date(GoodsBean gb) {
		String data = null;
		// String query = "SELECT * FROM MAXTIME";
		String query = "SELECT TO_CHAR(MAX(OR_DATE),'YYYYMMDDHH24MISS') AS ORDERTIME FROM \"OR\" WHERE OR_MMID=? AND OR_STATE=?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getState());
			this.rs = this.pstatement.executeQuery();

			while (rs.next()) {

				data = rs.getNString("ORDERTIME");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return data;
	}

	int insOrders(GoodsBean gb) {
		int count = 0;

		String query = "INSERT INTO \"OR\"(OR_MMID,OR_DATE,OR_STATE)" + "VALUES(?,DEFAULT,?)";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getState());

			count = this.pstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	int insOrderDetail(GoodsBean gb) {

		int count = 0;

		String query = "INSERT INTO OD(OD_ORMMID,OD_ORDATE,OD_SAGOCODE,OD_QUANTITY,"
				+ "OD_STATE, OD_SASECODE) VALUES(?,TO_DATE(?,'YYYYMMDDHH24MISS'),?,?,?,?)";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, gb.getmId());
			this.pstatement.setNString(2, gb.getDate());
			this.pstatement.setNString(3, gb.getGoCode());
			this.pstatement.setInt(4, gb.getGoQty());
			this.pstatement.setNString(5, gb.getState());
			this.pstatement.setNString(6, gb.getSeCode());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return count;
	}
	
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
