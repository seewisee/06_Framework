package edu.kh.project.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository // DB 연결 의미 + bean 등록
public class AjaxDAO {
	
	@Autowired // bean 중에서 타입이 같은 객체를 DI(의존성 주입)
	private SqlSessionTemplate sqlSession;

	/** 이메일로 닉네임 조회
	 * @param email
	 * @return
	 */
	public String selectNickname(String email) {
		return sqlSession.selectOne("ajaxMapper.selectNickname", email);
	}

	/** 닉네임으로 전화번호 조회하기
	 * @param inputNickname
	 * @return
	 */
	public String selectMemberTel(String nickname) {
		return sqlSession.selectOne("ajaxMapper.selectMemberTel", nickname);
	}

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	public int dupCheck(String email) {
		return sqlSession.selectOne("ajaxMapper.dupCheck", email);
	}

	public int checkNickname(String nickname) {
		return sqlSession.selectOne("ajaxMapper.checkNickname", nickname);
	}
	

}
