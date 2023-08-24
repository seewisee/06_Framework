package edu.kh.project.myPage.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.project.member.model.dto.Member;

@Repository // 저장소(DB)와 관련된 클래스 + Bean 등록(IOC, 스프링이 객체 관리)
public class myPageDAO {

	// 등록된 Bean 중 타입이 sqlSessionTemplate으로 일치하는 Bean을 주입 (DI)
	// -> root-context.xml에 <bean> 작성됨
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int updateInfo(Member updateMember) {
		
	//	return sqlSession.update("namespace.id", 전달할 값);
		return sqlSession.update("myPageMapper.updateInfo", updateMember);
		
	}

	/** 회원의 비밀번호 조회
	 * @param memberNo
	 * @return
	 */
	public String selectEncPw(int memberNo) {
		
		
		return sqlSession.selectOne("myPageMapper.selectEncPw", memberNo);
	}

	/** 비밀번호 변경
	 * @param newPw
	 * @param memberNo
	 * @return
	 */
	public int changePw(String newPw, int memberNo) {
		// Mybatis에서 sql 수행 시
		// 전달할 수 있는 파라미터는 딱 하나!
		// -> 여러 파라미터를 전달해야하는 경우
		//		map 또는 dto로 묶어서 전달
		
		Member member = new Member();
		member.setMemberNo(memberNo);
		member.setMemberPw(newPw);
		
		
		return sqlSession.update("myPageMapper.changePw", member);
	}

	/** 회원 탈퇴 
	 * @param memberNo
	 * @return
	 */
	public int secession(int memberNo) {
		// sqlSessionTemplate : 마이바티스 + DBCP + close 자동 + 트랜잭션처리
		return sqlSession.update("myPageMapper.secession", memberNo);
	}

	/** 프로필 이미지 수정
	 * @param loginMember
	 * @return
	 */
	public int updateProfile(Member loginMember) {
		return sqlSession.update("myPageMapper.updateProfileImage", loginMember);
	}
	
	
	
}
