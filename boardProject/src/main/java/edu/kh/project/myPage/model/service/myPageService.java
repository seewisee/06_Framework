package edu.kh.project.myPage.model.service;

import edu.kh.project.member.model.dto.Member;

public interface myPageService {

	int updateInfo(Member updateMember);

	/** 비밀번호 변경 서비스
	 * @param curreJntPw
	 * @param newPw
	 * @return
	 */
	int changePw(String currentPw, String newPw, int memberNo);

	/** 회원 탈퇴 서비스
	 * @param memberNo
	 * @return
	 */
	int secession(int memberNo, String memberPw);

}
