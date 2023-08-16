package edu.kh.project.myPage.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.myPageService;

@SessionAttributes({"loginMember"})
// 1) Model에 세팅된 값의 key와 {}의 작성된 값이 일치하면 session scope로 이동
// 2) Session으로 올려둔 값을 해당 클래스에서 얻어와 사용 가능하게 함
//		-> @SessionAttribute(key)로 사용 

@RequestMapping("/myPage") // /myPage로 시작하는 요청을 모두 받음
@Controller // 요청/응답 제어 클래스 + Bean 등록
public class myPageController {
	
	@Autowired // myPageService의 자식 myPageServiceImpl 의존성 주입(DI)
	private myPageService service;
	
	
	@GetMapping("/info")
	public String info() {
		// ViewResolver 설정 -> servlet-context.xml
		return "myPage/myPage-info";
	}
	
	@GetMapping("/profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	@GetMapping("/changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	
	@GetMapping("/secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	@PostMapping("/info")
	public String info(Member updateMember, String[] memberAddress
						, @SessionAttribute("loginMember") Member loginMember
						, RedirectAttributes ra){
		
		
		// ---------------- 매개변수 설명 ---------------------
		// Member updateMember : 수정할 닉네임, 전화번호 담긴 커맨드 객체
		// String[] memberAddress : name = "memberAddress"인 input 3개의 값(주소)
		
		// @SessionAttribute("loginMember) Member loginMember
		// : Session에서 얻어온 "loginMember"에 해당하는 객체를
		//		매개변수 Member loginMember에 저장
		
		// RedirectAttributes ra : 리다이렉트 시 값 전달용 객체(request)
		// ----------------------------------------------------
		
		
		// 주소 하나로 합치기(a^^^b^^^c^^^)
		String addr = String.join("^^^", memberAddress);
		updateMember.setMemberAddress(addr);
		
		// 로그인한 회원의 번호를 updateMember에 추가
		updateMember.setMemberNo(loginMember.getMemberNo());
		
		//DB에 회원 정보 수정(update) 서비스 호출
		int result = service.updateInfo(updateMember);
		
		
		String message;
		
		if(result > 0) {
			message = "회원정보가 수정되었습니다.";
			
			// session에 로그인된 회원 정보도 수정(동기화)
			loginMember.setMemberTel(updateMember.getMemberTel());
			loginMember.setMemberNickname(updateMember.getMemberNickname());
			loginMember.setMemberAddress(updateMember.getMemberAddress());
			
		}else {
			message = "회원정보 수정 실패.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:info"; // 상대경로 (/myPage/info GET 방식)
	}
	
	
	@PostMapping("/changePw")
	public String changePw(String currentPw, String newPw
					, @SessionAttribute("loginMember") Member loginMember
					, RedirectAttributes ra) {
		
		// 로그인한 회원 번호(DB에서 어떤 회원을 조회, 수정하는지 알아야 되니까)
		int memberNo = loginMember.getMemberNo();
		
		// 비밀번호 변경 서비스 호출
		int result = service.changePw(currentPw, newPw, memberNo);
		
		String path = "redirect:";
		String message = null;
		
		if(result > 0) { // 성공
			
			message = "비밀번호가 변경 되었습니다.";
			path += "info";
			
		}else {
			message = "현재 비밀번호가 일치하지 않습니다.";
			path += "changePw";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	// 회원 탈퇴
	@PostMapping("/secession")
	public String secession(@SessionAttribute("loginMember") Member loginMember
							, String memberPw , SessionStatus status
							, HttpServletResponse resp
							, RedirectAttributes ra) {
		
		// SessionStatus : 세션을 관리하는 객체
		//HttpServletResponse : 서버 -> 클라이언트 응답하는 방법 제공 객체
		//RedirectAttributes : 리다이렉으 시 request로 값을 전달하는 객체
		
		// 1. 로그인한 회원의 회원 번호 얻어오기
		int memberNo = loginMember.getMemberNo();
		// 2. 회원 탈퇴 서비스 호출
		int result = service.secession(memberNo, memberPw);
		// - 비밀번호가 일치하면 MEMBER_DEL_FL -> 'Y'f로 바꾸고 1 반환
		// - 비밀번호가 일치하지 않으면 -> 0 반환

		String path = "redirect:";
		String message = null;
		
		if (result > 0) {
			// 3. 탈퇴 성공 시
			// - 로그아웃
			// - message : 탈퇴 되었습니다.
			// - 메인 페이지로 리다이렉트
			// + 쿠키 삭제
			
			message = "탈퇴 되었습니다.";
			path += "/";
			
			status.setComplete();
			
			Cookie cookie = new Cookie("saveId", "")
					cookie.setMaxAge(0); // 0초 생존 -> 삭제
			cookie.setPath("/"); // 요청시 쿠키가 첨부되는 경로
			resp.addCookie(cookie); // 요청 객체를 통해서 클라이언트에게 전달
									// -> 클라이언트 컴퓨터에 파일로 생성
			
			
		} else {
			// 4. 탈퇴 실패 시
			// - message : 현재 비밀번호가 일치하지 않습니다.
			// - 회원 탈퇴 페이지로 리다이렉트
			
			message = "탈퇴 실패.";
			path += "secession";
		}
		
		ra.addFlashAttribute("message", message);

		return path;
	}

}
