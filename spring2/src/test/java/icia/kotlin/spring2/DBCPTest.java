package icia.kotlin.spring2;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //이 클래스 실행 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")//환경설정 
@Log4j
public class DBCPTest {
	@Setter(onMethod_= {@Autowired})
	private DataSource data;
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSession;
	@Setter(onMethod_= {@Autowired})
	private MapperInterface mapper;
	@Test
	public void connectTest() {
		try {
			SqlSession session = sqlSession.openSession();
			Connection connection  = data.getConnection();
			log.info(session);
			log.info(connection);
			log.info(mapper.getDate());
			log.info(mapper.getDate2());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
