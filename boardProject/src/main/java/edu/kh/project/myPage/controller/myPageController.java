package edu.kh.project.myPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
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
	

	
}
