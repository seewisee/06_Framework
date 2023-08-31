const insertBtn = document.getElementById("insertBtn");


if(insertBtn != null){
    // 글쓰기 버튼 클릭 시 
    insertBtn.addEventListener("click", () =>{
        // JS BOM 객체 중 location

        // location.href = "주소"
        // 해당 주소 요청 (GET 방식)

        // 2가지 방법
        //location.href = "/board2/" + location.pathname.split("/")[2];
        location.href = `/board2/${location.pathname.split("/")[2]}/insert`;
                        // /board2/1/insert
    })
}


// 검색창 이전 검색 기록을 남겨놓기
const boardSearch = document.querySelector("#boardSearch");
const searchQuery = document.querySelector("#searchQuery");
const searchKey = document.querySelector("#searchKey");

const options = document.querySelectorAll("#searchKey > option");

(()=>{

    const params = new URL(location.href).searchParams;

    const key = params.get("key"); // t,c,tc,w 중 하나
    const query = params.get("query"); // 검색어

    if (key != null) { // 검색을 했을 때
        searchQuery.value = query; // 검색어를 화면에 출력

        // option 태그를 하나씩 순차 접근해서 value가 key랑 같으면
        // selected 속성 추가
        for (let op of options) {
            if (op.value == key) {
                op.selected = true;
            }
        }
    }
})();

// 검색어 입력 없이 제출된 경우
boardSearch.addEventListener("submit", e=>{

    if(searchQuery.value.trim().length  == 0){ // 검색어 미입력 시
        e.preventDefault(); // form 기본 이벤트 제거

        // 해당 게시판 1페이지로 이동
        location.href = location.pathname;

    }
})



