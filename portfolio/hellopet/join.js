var checkbtn = "";  //아이디 중복체크 유/무

//아이디 중복체크
function memberid_ck(){
    if(joinform.m_id.value==""){
        alert("아이디를 입력하세요");
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
                    joinform.m_id.readOnly = true;
                    checkbtn = "ok";
                }
                else if(call=="cancel"){
                    alert("해당 아이디는 사용하실 수 없습니다.");
                    joinform.m_id.value="";
                }
                else{
                    console.log("통신오류 발생!!");
                }
            }
        }
        sign = ajax();
        sign.onreadystatechange = postdata;
        sign.open("POST","./id_search.php",true);
        sign.setRequestHeader("content-type","application/x-www-form-urlencoded");
        sign.send("m_id="+joinform.m_id.value);
    }
}
//이메일 선택부분
function mail_input(m){
    //indexOf : -1 해당 없을 경우 그 외에 숫자는 해당 문자가 있음
    var m_usermail = joinform.m_usermail.value;
    var indexof = m_usermail.indexOf("@");
    var mselect = document.getElementById("mselect");
    if(m_usermail=="" || m==""){
        joinform.m_usermail.focus();
        mselect.value="";
        joinform.m_usermail.value = "";
    }
    else{
        if(indexof==-1){
            joinform.m_usermail.value = m_usermail + "@" + mselect.value;
        }
        else{
            var sp = m_usermail.split("@")[0];
            joinform.m_usermail.value = sp + "@" + mselect.value;
        }
    }
}

//회원가입 버튼
function logins(){
    if(checkbtn==""){
        alert("아이디 중복체크를 하셔야 합니다.");
    }
    else{
    joinform.submit();
    }
}

$(()=>{
    $("#jox").click(function(){
        $(".jj").css({
            "display":"none" 
        });
    

    $(".bkscreen").css({
        "opacity": "0",
        "display":"none"
        });
    });
});
function joina(){
    
}