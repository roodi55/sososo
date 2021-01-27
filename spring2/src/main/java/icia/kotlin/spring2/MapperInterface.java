package icia.kotlin.spring2;

import org.apache.ibatis.annotations.Select;

public interface MapperInterface {

	@Select("SELECT SYSDATE FROM DUAL")
	public String getDate();

	public String getDate2();
}
