package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OpenAPIController {

	// 발급 받은 인증키 변수처리
	public static final String SERVICEKEY = "IZRJwMDfjqaqPb5QdQ8BYMY1kSLG2oUkyxYtO%2BsjpGm6%2FFWLy4sXhzG5A2p6K4cHF5ecTdZPdkMTLYXJCOkFOA%3D%3D";
	
	// json형식으로 대기오염 OpenAPI 활용하기

	@ResponseBody
	//@RequestMapping(value = "air", produces = "application/json; charset=UTF-8")
	public String airMethod(String location) throws IOException  {
		
		// openAPI 서버로 요청하고자 하는 url 작성
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY; // 서비스키 추가
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); // 지역명 추가(한글이 들어가면 인코딩 처리 해야함)
		url += "&returnType=json"; // 리턴 타입
		url += "&numOfRows=2"; // 결과 개수
		
		// 1. 작성된 url 정보를 넣어 URL 객체 생성
		URL requestUrl = new URL(url);
		
		// 2. 생성된 URL 객체로 URLConnection 생성
		HttpURLConnection urlConn = (HttpURLConnection) requestUrl.openConnection();

		// 3. 요청 시 필요한 Header 설정
		urlConn.setRequestMethod("GET");

		// 4. 해당 OpenAPI 서버로 요청 후 입력 스트림을 통해서 응답데이터 읽어오기
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

		String responseText = "";
		String line;
		while ((line = br.readLine()) != null) {
			responseText += line;
		}

		// 5. 다 사용한 스트림 반납 및 연결 해제
		br.close();
		urlConn.disconnect();

		return responseText;
	}
	
	
	// xml 형식으로 대기오염 OpenApi 활용하기
	//@RequestMapping( value = "air", produces = "text/xml; charset=UTF-8")
	@ResponseBody
	public String airPollution(String location) throws IOException {

		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + SERVICEKEY; // 서비스키 추가
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8"); // 지역명 추가(한글이 들어가면 인코딩 처리 해야함)
		url += "&returnType=xml"; // 리턴 타입
		url += "&numOfRows=20"; // 결과 개수

		URL requestUrl = new URL(url);

		HttpURLConnection urlConn = (HttpURLConnection)requestUrl.openConnection(); 
		
		urlConn.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine())!=null) {
			responseText += line;
		}
	
		br.close();
		urlConn.disconnect();
		System.out.println(responseText);

		return responseText;
	}
	
	
	// xml 형식으로 지진 해일 대피소 OpenApi 활용하기
	//@RequestMapping( value = "shelter", produces = "text/xml; charset=UTF-8")
	@ResponseBody
	public String shelterList() throws IOException {
		
		String url = "http://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
		
		url += "?serviceKey=" + SERVICEKEY; // 서비스키 추가
		url += "&pageNo=1"; 
		url += "&type=xml"; // 리턴 타입
		url += "&numOfRows=20"; // 결과 개수
		
		URL requestUrl = new URL(url);

		HttpURLConnection urlConn = (HttpURLConnection)requestUrl.openConnection(); 
		
		urlConn.setRequestMethod("GET");

		BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		
		String responseText = "";
		String line;
		
		while((line = br.readLine())!=null) {
			responseText += line;
		}
	
		br.close();
		urlConn.disconnect();
		System.out.println(responseText);

		
		return responseText;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
}
