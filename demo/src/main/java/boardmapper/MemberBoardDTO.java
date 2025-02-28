package boardmapper;

import java.util.List;

public class MemberBoardDTO {
	String id;
	int pw;
	String name, phone, email, regdate;
	List<BoardDTO> boarddtolist;
	
	public List<BoardDTO> getBoarddtolist() {
		return boarddtolist;
	}

	public void setBoarddtolist(List<BoardDTO> boarddtolist) {
		this.boarddtolist = boarddtolist;
	}

	MemberBoardDTO(){} 
	
	MemberBoardDTO(String id, int pw){ //생성자추가 
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

}
