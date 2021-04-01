package auth.services.oneteam.icia;

import java.sql.SQLException;
import java.util.ArrayList;

import beans.AuthBean;

class DataAccesObject extends module.oneteam.icia.DataAccesObject {

	DataAccesObject() {

	}

	int isPhone(AuthBean auth) {
		int count = 0;

		String query = "SELECT COUNT(*) AS CNT FROM MM WHERE MM_PHONE= ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getPhone());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	ArrayList<AuthBean> selectMember(AuthBean auth) {

		ArrayList<AuthBean> member = new ArrayList<AuthBean>();

		String query = "SELECT MM_ID AS ID , MM_NAME AS NAME , MM_PHONE AS PHONE FROM MM WHERE MM_ID=?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				AuthBean at = new AuthBean();
				at.setmId(rs.getNString("ID"));
				at.setmName(rs.getNString("NAME"));
				at.setPhone(rs.getNString("PHONE"));
				member.add(at);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return member;
	}

	// 아이디 유무 확인 + 아이디 중복 체크
	int isMember(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;

		String query = "SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID = ?";
		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 현재 id 활성화 여부 확인
	int isActive(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;
		String query = "SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID = ? AND MM_STATE = ?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, "A");

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// userId + userPassword 일치여부
	int isPassWord(AuthBean auth) {
		this.pstatement = null;
		this.rs = null;
		int count = 0;
		String query = "SELECT COUNT(*) AS CNT FROM MM WHERE MM_ID = ? AND MM_PASSWORD = ?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, auth.getmPassword());

			this.rs = this.pstatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt("CNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// ACCESSLOG INS
	int insAccessLog(AuthBean auth) {
		this.pstatement = null;
		int count = 0;

		String dml = "INSERT INTO AL(AL_ID, AL_TIME, AL_TYPE) VALUES(?, DEFAULT, ?)";

		try {
			this.pstatement = this.connection.prepareStatement(dml);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setInt(2, auth.getAccessType());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 회원정보 추출 :: 회원아이디, 회원이름, 로그인시간
	ArrayList<AuthBean> MemberInfo(AuthBean auth) {
		ArrayList<AuthBean> memberList = new ArrayList<AuthBean>();

		this.pstatement = null;
		this.rs = null;
		String query = "SELECT * FROM MINFO WHERE ALID = ? AND ALTYPE = ?";

		try {
			this.pstatement = this.connection.prepareStatement(query);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setInt(2, auth.getAccessType());

			this.rs = this.pstatement.executeQuery();

			while (rs.next()) {

				AuthBean ab = new AuthBean();
				ab.setmId(rs.getNString("ALID"));
				ab.setmName(rs.getNString("MNAME"));
				ab.setAccessTime(rs.getNString("ALTIME"));

				memberList.add(ab);
			}
		} catch (SQLException e) {
		}

		return memberList;
	}

	int joinMember(AuthBean auth) {
		this.pstatement = null;
		int count = 0;

		String dml = "INSERT INTO MM(MM_ID, MM_PASSWORD, MM_NAME, MM_PHONE, MM_STATE) VALUES(?, ?, ?, ? , ?)";

		try {
			this.pstatement = this.connection.prepareStatement(dml);
			this.pstatement.setNString(1, auth.getmId());
			this.pstatement.setNString(2, auth.getmPassword());
			this.pstatement.setNString(3, auth.getmName());
			this.pstatement.setNString(4, auth.getPhone());
			this.pstatement.setNString(5, auth.getState());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	// 회원정보 수
	int modify(AuthBean auth) {
		this.pstatement = null;
		int count = 0;

		String dml = "UPDATE MM SET MM_PASSWORD=?,MM_NAME=?, MM_PHONE=?  WHERE MM_ID=?";
		;

		try {
			this.pstatement = this.connection.prepareStatement(dml);
			this.pstatement.setNString(1, auth.getmPassword());
			this.pstatement.setNString(2, auth.getmName());
			this.pstatement.setNString(3, auth.getPhone());
			this.pstatement.setNString(4, auth.getmId());

			count = this.pstatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

}
