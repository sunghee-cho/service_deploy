package boardmapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BoardMybatisController {
	
	@Autowired
	@Qualifier("boardMapperService")
	BoardService boardService;
	
	@RequestMapping("/")//http://localhost:9090/
	String start() {
		return "board/start";
	}
	
	@GetMapping("/boardwrite")//글쓰기폼
	String writeform() {
		return "board/writeform";
		//board테이블 첨부파일 포함. 
	}
	
	@PostMapping("/boardwrite")
	String writeprocess(BoardDTO dto) throws IOException{
		//dto 같은 이름 변수에 요청파라미터 이름 저장 (file1 - 제외)
		//title contents writer pw 요청파라미터 저장
		//seq= select max(seq)+1 from board  viewcount=0  writingtime= sysdate 
		//dto - service/serviceImpl - mapper - board-mapping.xml  - db
		
		//1>multifile1 c:ezwel/upload 파일 저장
		String savePath = "c:/ezwel/upload/";
		String newfilename1 = null;
		MultipartFile file1 = dto.getMultifile1();
		if(!file1.isEmpty()) {
			String originalfilename1 = file1.getOriginalFilename();
			String before1 = originalfilename1.substring(0, originalfilename1.indexOf("."));
			String ext1 = originalfilename1.substring(originalfilename1.indexOf("."));
			newfilename1 = before1 + "(" + UUID.randomUUID() + ")" + ext1;
			//서버내부 지정경로에 파일내용 저장
			file1.transferTo( new java.io.File(savePath +  newfilename1));
		}
		
		dto.setFile1(newfilename1);//업로드한 파일을 서버 저장에 이름 -- db insert
		
		//2>파일이름만+.... db 저장
		
		boardService.registerBoard(dto);
		return "board/start";
	}
	
	@RequestMapping("/boardlist")
	ModelAndView boardlist
	(@RequestParam(value="pagenum", required = false, defaultValue = "1") int pagenum) {//요청 - 파라미터 분석 - 서비스
		ModelAndView mv = new ModelAndView();
		List<BoardDTO> list = boardService.pagingList(pagenum);//현재페이지의 목록
		int total = boardService.totalCount();//게시물 전체 개수
		mv.addObject("boardlist", list);
		mv.addObject("total", total);
		mv.setViewName("board/list");
		return mv;
	}
	
	@RequestMapping("/boarddetail") //get+post 요청 가능
	ModelAndView boarddetail(int seq) {
		ModelAndView mv = new ModelAndView();
		BoardDTO dto = boardService.updateViewcountAndGetDetail(seq);
		mv.addObject("detailboard", dto);//데이터
		mv.setViewName("board/boarddetail");//뷰
		return mv;
	}
	
	//ajax요청처리
	@RequestMapping("/boarddelete")
	@ResponseBody
	String boarddelete(int seq) {
		int rows = boardService.deleteBoard(seq);
		//seq 글번호 primary key
		if(rows == 1) {
			return "{\"result\":\"삭제성공\"}";//데이터
		}
		else {
			return "{\"result\":\"삭제실패\"}";//데이터
		}
	}
	
	//ajax요청처리
	@RequestMapping("/boardupdate")
	@ResponseBody
	String boarddelete(BoardDTO dto) {//dto.xxx = 요청파라미터명 동일 자동 저장
		int rows = boardService.updateBoard(dto);
		//seq 글번호 primary key
		if(rows == 1) {
			return "{\"result\":\"수정성공\"}";
		}
		else {
			return "{\"result\":\"수정실패\"}";
		}
	}
	
	@GetMapping("/boarddownload")
	public void boarddownload(String filename, HttpServletResponse response) throws IOException {
		FileInputStream fin = new FileInputStream(new File( "c:/ezwel/upload/" + filename));
		
		filename = new String(filename.getBytes("utf-8") , "iso-8859-1" );

		response.setHeader("Content-Disposition", "attachment;filename=\"" +filename + "\"");

		OutputStream out = response.getOutputStream();
		FileCopyUtils.copy(fin,  out);
		fin.close();
		out.close();
	}
	
	@RequestMapping("/boardwriterinfo")
	@ResponseBody
	BoardWriterDTO boardwriterinfo(int seq) {
		BoardWriterDTO dto = boardService.getBoardWriterInfo(seq);
		return dto;
	}
	
	@RequestMapping("/memberandboardinfo")
	@ResponseBody
	MemberBoardDTO memberandboardinfo(String id) {
		MemberBoardDTO dto = boardService.getMemberAndBoardInfo(id);
		return dto;
	}
}









