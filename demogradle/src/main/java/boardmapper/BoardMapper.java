package boardmapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BoardMapper {
	//sql id 와 동일 이름 메소드
	//<insert id="insertBoard" parameterType="boarddto"> 
	int insertBoard(BoardDTO dto);
	
	//<select id="pagingList" resultType="boarddto" parameterType="int">
	List<BoardDTO> pagingList(int[] array);
	
	//<select id="totalCount" resultType="int" >
	int totalCount();
	
	 //<update id="updateViewcount" parameterType="int">
	int updateViewcount(int seq);
	 
	 //<select id="getDetail" resultType="boarddto" parameterType="int">
	BoardDTO getDetail(int seq);
		
	int deleteBoard(int seq);	
	
	int updateBoard(BoardDTO dto);
}
