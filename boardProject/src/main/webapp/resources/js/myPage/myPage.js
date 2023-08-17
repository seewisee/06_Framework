const memberNickname = document.getElementById("memberNickname");
const memberTel = document.getElementById("memberTel");
const updateInfo = document.getElementById("updateInfo");

// 내 정보 수정 form 태그가 존재할 때 (내 정보 페이지)
if(updateInfo != null){

    // 전역변수로 수정 전 닉네임/전화번호를 저장
    initNickname = memberNickname.value;
    initTel = memberTel.value;

    // 닉네임 유효성 검사
    memberNickname.addEventListener("input", ()=>{

        // 변경 전 닉네임과 입력 값이 같을 경우
        if(initNickname == memberNickname.value){
            memberNickname.removeAttribute("style");
            return;
        }

        // 정규 표현식으로 유효성 검사
        const regEx = /^[가-힣\w\d]{2,10}$/;

        if(regEx.test(memberNickname.value)){
            memberNickname.style.color = "green";
        }else{
            memberNickname.style.color ="red";
        }

    });


    // 전화번호 유효성 검사
     memberTel.addEventListener("input", ()=>{

        // 변경 전 전화번호와 입력 값이 같을 경우
        if(initTel == memberTel.value){
            memberTel.removeAttribute("style");
            return;
        }

        // 정규 표현식으로 유효성 검사
        const regEx = /^(070|02|0[1-9]{1}[0-9]{1})[0-9]{3,4}[0-9]{4}$/;

        if(regEx.test(memberTel.value)){
            memberTel.style.color = "green";
        }else{
            memberTel.style.color ="red";
        }

    });

    // 익명함수
    // form 태그 제출 시 유효하지 않은 값이 있으면 제출 X
    document.getElementById("updateInfo").addEventListener("submit", e=>{

        // 닉네임이 유효하지 않을 경우
        if(memberNickname.style.color == "red"){
            alert("닉네임이 유효하지 않습니다.")
            // 포커스 이동
            memberNickname.focus();
            // 기본 이벤트 제거
            e.preventDefault();
            return;
        }

         // 닉네임이 유효하지 않을 경우
         if(memberTel.style.color == "red"){
            alert("전화번호가 유효하지 않습니다.")
            // 포커스 이동
            memberTel.focus();
            // 기본 이벤트 제거
            e.preventDefault();
            return;
        }


    })

} // if문 end


// 비밀번호 변경 제출 시
const changePwFrm = document.getElementById("changePwFrm");
const currentPw = document.getElementById("currentPw");
const newPw = document.getElementById("newPw");
const newPwConfirm = document.getElementById("newPwConfirm");

// 비밀번호 변경 페이지인 경우
if(changePwFrm != null){

    changePwFrm.addEventListener("submit", e =>{
        
        // 현재 비밀번호 미작성 시
        if(currentPw.value.trim() == ""){
            alert("현재 비밀번호를 입력해주세요.");
            currentPw.focus();
            e.preventDefault();
            currentPw.value = "";
            return;
        }
    
        // 비밀번호 유효성 검사
        const regEx = /^[a-zA-Z0-9\!\@\#\-\_]{6,20}$/;

        if(!regEx.test(newPw.value)){
            alert("새 비밀번호가 유효하지 않습니다.")
            newPw.focus();
            e.preventDefault();
            return;
        }
        

        if(newPw.value != newPwConfirm.value){
            alert("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            e.preventDefault();
            return;
        }
    })
}    

// 회원 탈퇴 페이지인 경우
const secessionFrm = document.getElementById("secessionFrm");
const memberPw = document.getElementById("memberPw");

if(secessionFrm != null){
    secessionFrm.addEventListener("submit", e=>{
        
        // 비밀번호 미작성
        if(memberPw.value.trim() == ""){
            alert("비밀번호를 작성해주세요.");
            memberPw.focus();
            e.preventDefault();
            return;
        }
        // 동의 체크가 되지 않은 경우
        if(!agree.checked){
            alert("약관 동의해주세요.");
            e.preventDefault();
            return;
        }
        // 취소 클릭 시 제출 안되게
        if(!confirm("정말 탈퇴하시겠습니까?")){
            e.preventDefault();
            return;
        }
    })    
}    














