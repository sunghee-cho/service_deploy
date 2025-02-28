package react.ajax;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
public class ReactAjaxController {
	@CrossOrigin(origins="*")//시큐어코딩면 좋은 코드 아니다 
	@RequestMapping("/helloajax")
	//@ResponseBody
	public LoginDTO test() {
		return new LoginDTO("boot id", 1234);
	}
	
	@CrossOrigin(origins="http://localhost:4000")
	@GetMapping("/helloajaxparam")
	public LoginDTO test(String id, int pw) {
		return new LoginDTO(id, pw);
	}

    //ResponseBody - 자바객체--json변환
    //RequestBody - json--파라미터, 자바객체변환
	@CrossOrigin(origins="http://localhost:4000")
	@PostMapping("/helloajaxparam")
	public LoginDTO test(@RequestBody LoginDTO dto) {//json--DTO 변환
		System.out.println("post전달받음=" + dto.id +":" + dto.pw);
		return dto;
	}
	
	@CrossOrigin(origins="http://localhost:4000")
	@GetMapping("/helloajaxarray")
	public int[] testarray(int[] ids) {  
		//ids=1&ids=5&ids=9 기대-react QueryString -  배열형식 변경
		//ids[]=1&ids[]=5&ids[]=9  실제
		for(int i = 0; i < ids.length; i++) {
			ids[i] = ids[i]*10;
			System.out.println(ids[i]);
		}
		return ids;
	}

	@CrossOrigin(origins="http://localhost:4000")
	@PostMapping("/helloajaxobjectarray")
	public PlayerDTO testplayer(@RequestBody Map<String, Object> parameters) throws Exception {  
		String json = parameters.get("playerArray").toString();//
		ObjectMapper mapper = new ObjectMapper();
		
		//public PlayerDTO() 필요. 기본생성자 정의 필수
		List<PlayerDTO> playerList = mapper.readValue(json, new TypeReference<ArrayList<PlayerDTO>>() {});
		//3개
		for(PlayerDTO dto : playerList) {
			if(dto.player.equals("son")) {
				return new PlayerDTO(dto.player, dto.goal, "한국", "손흥민");//son
			}
		}
		return new PlayerDTO("알수없음", 0, "국적모름","이름모름");//messi, kane
	}

	//세션전달.origins은 "*"설정시 보안문제로 세션못받음. 요청하는 클라이언트 ip로 설정함.
	//allowCredentials = "true" 설정하면 세션 인증 활용 가능함.
	@CrossOrigin(origins="http://localhost:4000", allowCredentials = "true")
	@PostMapping("/loginsession")
	//@ResponseBody
	public LoginDTO test3(@RequestBody LoginDTO dto, HttpSession session) throws Exception {
		 System.out.println("post전달받음"+ dto.id+":"+dto.pw); 
		 session.setAttribute("sessiondto", dto);
		 return new LoginDTO("post-"+((LoginDTO)session.getAttribute("sessiondto")).getId(), ((LoginDTO)session.getAttribute("sessiondto")).getPw());//json자동변환(스프링부트내장)
	}
	 
	//세션확인
	//@CrossOrigin(origins="*") 
	@CrossOrigin(origins = "http://localhost:4000" , allowCredentials = "true" )
	@PostMapping("/loginsessionconfirm")
	//@ResponseBody
	public LoginDTO test4(HttpSession session, HttpServletResponse response) throws Exception {
	 LoginDTO dto = (LoginDTO)session.getAttribute("sessiondto");
	return dto;//json자동변환(스프링부트내장)
	}

	//세션소멸
	//@CrossOrigin(origins="*") 
	@CrossOrigin(origins = "http://localhost:4000" , allowCredentials = "true" )
	@PostMapping("/loginsessionremove")
	//@ResponseBody
	public LoginDTO test5(HttpSession session, HttpServletResponse response) throws Exception {
	LoginDTO dto = (LoginDTO)session.getAttribute("sessiondto");
	if(dto != null) {
		session.removeAttribute("sessiondto");
	}
	return (LoginDTO) session.getAttribute("sessiondto");//json자동변환(스프링부트내장)
	}	
	
}

class LoginDTO {
	String id;
	int pw;
	
	public LoginDTO() {	}
	
	public LoginDTO(String id, int pw) {
		this.id = id;
		this.pw = pw;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPw() {
		return pw;
	}
	public void setPw(int pw) {
		this.pw = pw;
	}
}//LoginDTO

class PlayerDTO{
	String player;
	int goal;
	String fullName;
	String nation;
	
	public PlayerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlayerDTO(String player, int goal, String fullName, String nation) {
		super();
		this.player = player;
		this.goal = goal;
		this.fullName = fullName;
		this.nation = nation;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
}

