package boardmapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository //객체생성+db접근객체활용
@Mapper     //Mapper역할
public interface BoardWriterMapper {
	BoardWriterDTO getBoardWriterInfo(int seq);
	MemberBoardDTO getMemberAndBoardInfo(String id);
}
