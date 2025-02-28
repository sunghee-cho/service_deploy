package boardmapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardMapperService implements BoardService{
	@Autowired
	BoardMapper mapper;
	
	@Autowired
	BoardWriterMapper mapper2;
	
	@Override
	public String registerBoard(BoardDTO dto) {
		int result = mapper.insertBoard(dto);
		if(result == 1) return "글쓰기성공";
		else return "글쓰기실패";
	}

	@Override
	public List<BoardDTO> pagingList(int pagenum) {
		int cnt = 5;
		int start =  (pagenum-1)*cnt + 1; //1,4,7
		int end =  pagenum * cnt; //3,6,9
		int array[] = {start, end};
		return mapper.pagingList(array);
	}

	@Override
	public int totalCount() {
		return mapper.totalCount();
	}

	@Override
	public BoardDTO updateViewcountAndGetDetail(int seq) {
		mapper.updateViewcount(seq);
		return mapper.getDetail(seq);
	}

	@Override
	public int deleteBoard(int seq) {
		return mapper.deleteBoard(seq);
	}

	@Override
	public int updateBoard(BoardDTO dto) {
		return mapper.updateBoard(dto);
		
	}

	@Override
	public BoardWriterDTO getBoardWriterInfo(int seq) {
		return mapper2.getBoardWriterInfo(seq);
	}

	@Override
	public MemberBoardDTO getMemberAndBoardInfo(String id) {
		return mapper2.getMemberAndBoardInfo(id);
	}
	
	
	
	

}
