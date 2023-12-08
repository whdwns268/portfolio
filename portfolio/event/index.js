function gopage(){
    location.href = "https://www.cgv.co.kr/";
}

function corp(no){
    window.open("http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+no,"","width=500 height=500");
}

function event1(){
    var crf = document.getElementById("crf");
    if(crf.event_user.value==""){
        alert("이름을 입력하세요");
    }   
    else if(crf.event_email.value==""){
        alert("이메일을 입력하세요");
    }
    else if(crf.event_tel.value==""){
        alert("휴대폰 번호를 입력하세요");
    }
    else if(crf.event_number.value==""){
        alert("티켓 번호를 입력하세요");
    }
    else if(crf.event_number.value.length<8){
        alert("티켓 번호는 8자리 숫자여야 합니다.");
    }
    else if(crf.event_check.checked==false){
        alert("이용약관에 대한 정보제공에 동의하셔야 합니다.");
    }
    else{
        crf.submit();
    }
}
function number_check(data){
    var msg="";
    if(isNaN(data)){
        msg="no";
    }
    else{
        msg="ok";
    }
    return msg;
}