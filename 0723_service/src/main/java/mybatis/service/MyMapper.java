package mybatis.service;

public interface MyMapper {
	Object[] getList(); //emp와 dept 둘 다 사용하기 위해서
	
	Object[] search(String searchType, String searchValue);
}
