var uri = window.location.search;
var checkbtn = ""; //아이디 중복체크 유/무

if(uri==""){
    alert("올바른 접근이 아닙니다.");
    history.go(-1);
}
else{
    var agree2 = uri.split("&m_agree2=");
    join.m_agree2.value = agree2[1];
    var agree1 = agree2[0].split("?m_agree1=");
    join.m_agree1.value = agree1[1];
}
//아이디 중복체크
var ckid = /^[a-zA-Z0-9]{5,}$/
var ckname = /^[a-zA-Z가-힣]{2,}$/;
var ckemail = /^[a-zA-Z0-9+_-]{1,}@{1}[a-zA-Z0-9.-]{1,}.{1}[a-zA-Z0-9-.]{1,}$/;
var ckaddr = /^[a-zA-Z0-9가-힣]{1,}$/;
var ckphone = /^0{1}[0-9]{8,10}$/;
function memberid_ck(){
    if (join.m_userid.value == "") {
        alert("아이디를 입력하세요.");
    }
    else if(join.m_userid.value.length<5){
        alert("아이디는 최소 5자 이상입니다.");
    }
    else if(!ckid.test(join.m_userid.value)){
        alert("아이디는 영문과 숫자만 입력 가능합니다.");
    }
    else{
        var sign;
        function ajax(){
            if(window.XMLHttpRequest){
                return new XMLHttpRequest();
            }
        }
        function postdata(){
            if(sign.readyState==XMLHttpRequest.DONE && sign.status==200){
                var call = this.response;
                if(call=="ok"){
                    alert("사용가능한 아이디 입니다.");
                    join.m_userid.readOnly = true;
                    checkbtn = "ok";
                    //console.log(!ckid.test(join.m_userid.value))
                }
                else if(call =="cancel"){
                    alert("해당 아이디는 사용하실 수 없습니다.");
                    join.m_userid.value="";
                }
                else{
                    console.log("통신오류 발생!!");
                }
            }
        }

        sign = ajax();
        sign.onreadystatechange = postdata;
        sign.open("POST","./id_check.php",true);
        sign.setRequestHeader("content-type","application/x-www-form-urlencoded");
        sign.send("m_userid="+join.m_userid.value);
    }
}
//이메일 선택부분
function mail_input(m){
    //console.log(m)
    var m_usermail = join.m_usermail.value;
    var mselect = document.getElementById("mselect");
    var indexof = m_usermail.indexOf("@");
    if(m_usermail == "" || m==""){
        join.m_usermail.focus();
        mselect.value = "";
        join.m_usermail.value = "";
    }
    else{
        if(indexof == -1){
            join.m_usermail.value = m_usermail + "@" + mselect.value;
        }
        else{
            var sp = m_usermail.split("@")[0];
            join.m_usermail.value = sp + "@" + mselect.value;
        }
    }
}


//회원가입 버튼
function member_fn() {
    if (join.m_userid.value == ""){
        alert("아이디를 입력하세요");
    }
    else if (checkbtn == "") {
        alert("아이디 중복체크를 하셔야 합니다.");
    }
    else if(join.m_userpw.value == ""){
        alert("패스워드를 입력하세요.");
        join.m_userpw.focus();
    }
    else if(join.m_userpw.value.length < 6){
        alert("패스워드는 최소 6자 이상입니다.");
    }
    else if(join.checkpw.value == "" || join.checkpw.value != join.m_userpw.value){
        alert("동일한 패스워드를 입력하세요.");
    }
    else if(join.m_usernm.value == "" || join.m_usernm.value.length < 2 ){
        alert("고객명을 입력하세요.");
    }
    else if(join.m_usermail.value == ""){
        alert("이메일을 입력하세요.");
    }
    else if(join.m_usertel.value == ""){
        alert("연락처를 입력하세요.");
    }
    else if(!ckphone.test(join.m_usertel.value)){
        alert("정확한 전화번호를 입력하세요.");
    }
    else if(join.m_post.value == ""){
        alert("주소를 입력하세요.");
    }
    else if(join.m_addr.value == ""){
        alert("상세주소를 입력하세요.");
    }
    else {
        join.submit();
    }
}

//우편번호 검색
$(function(){
    $("#addpost").postcodifyPopUp();
});

function canc(){
    alert("회원가입을 취소하셨습니다.");
    location.href = "http://ser2070.dothome.co.kr/portfolio/ocn/index.html";
}