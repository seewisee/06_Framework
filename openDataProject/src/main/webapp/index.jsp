<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<title>Insert title here</title>
</head>
<body>

	<h2>실시간 대기오염 정보</h2>

	지역 :
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
	</select>

	<button id="btn1">해당 지역 대기오염 정보</button>
	<br>
	<br>

	<table border="1" id="result1">
		<thead>
			<tr>
				<th>측정소명</th>

<th>측정일시</th>

<th>통합대기환경수치</th>

<th>미세먼지농도</th>

<th>일산화탄소농도</th>

<th>이산화질소농도</th>

<th>아황산가스농도</th>

<th>오존농도</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>

	<script>
		$(function() {
			$("#btn1").click(function() {

			// Json 형식으로 응답 받을 때
		
				$.ajax({
					url : "air",
					data : {
						location : $("#location").val()
					},
					success : function(data) {
						//console.log(data);
						console.log(data.response.body.items);
						
						const itemArr = data.response.body.items;
						
						let value = "";
						for(let item in itemArr){
							console.log(item);
							value += "<tr>"
										+ "<td>" + item.stationName + "</td>"
										+ "<td>" + item.dataTime + "</td>"
										+ "<td>" + item.khaiValue + "</td>"
										+ "<td>" + item.pm10Value + "</td>"
										+ "<td>" + item.coValue + "</td>"
										+ "<td>" + item.no2Value + "</td>"
										+ "<td>" + item.so2Value + "</td>"
										+ "<td>" + item.o3Value + "</td>"
										+"</tr>"
						}
						
						$("#result1 > tbody").html(value);
					},
					error : function() {
						console.log("통신 실패");
					}

				})
				
				// -------------------------------------------------------------------------
				// 응답 데이터를 xml 형식으로 받을 때
/* 			 $.ajax({
					url : "air",
					data : {location : $("#location").val()},
					success : function(result){
						//console.log(result);
						
						// $('요소명').find(매개변수)
						// - 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용
						// - html, xml은 같은 markup language이기 때문에 사용 가능하다.
						//console.log($(result).find("item"))
						
						// xml형식의 응답데이터를 받았을 때
						// 1. 넘겨받은 데이터를 $() 제이쿼리화 시킨 후
						//		응답데이터 안에 실제 데이터가 담겨있는 요소 선택
						const itemArr = $(result).find("item");
						
						// 2. 반복문을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
						let value;
						itemArr.each(function(index, item){
							//console.log(item);
							//console.log($(item).find("stationName").text())
							
							value += "<tr>"
								+ "<td>" + $(item).find("stationName").text() + "</td>"
								+ "<td>" + $(item).find("dataTime").text() + "</td>"
								+ "<td>" + $(item).find("khaiValue").text() + "</td>"
								+ "<td>" + $(item).find("pm10Value").text() + "</td>"
								+ "<td>" + $(item).find("coValue").text() + "</td>"
								+ "<td>" + $(item).find("so2Value").text() + "</td>"
								+ "<td>" + $(item).find("no2Value").text() + "</td>"
								+ "<td>" + $(item).find("o3Value").text() + "</td>"
								+"</tr>"
						})
						
						// 3. 동적으로 만들어낸 요소를 화면에 출력
						$("#result1 > tbody").html(value);
					},
					error : function(){
						console.log("통신 실패");
					}
					
					
				})
 */
			})
		})
	</script> 

</body>


</html>