package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

public interface Boardservice {

	/** 게시판 종류 목록 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

}
