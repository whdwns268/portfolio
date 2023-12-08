function lgsv(sv){
     if(sv==false){
                localStorage.setItem("userid",null);
            };
        };

function log(no){
    switch (no){
        case 1 :
            if(logform.m_id.value==""||logform.m_pw.value==""){
                alert("아이디 및 비밀번호를 입력해 주세요")
            }
            else{                 
                if(document.getElementById("lgc").checked == true){
                    localStorage.setItem("userid",logform.m_id.value);
                }
                logform.submit();            
                logcnt = 1;                
                document.getElementById("lgsp").style.display = "none";
                document.getElementById("bkscreen").style.opacity = "0";
                document.getElementById("bkscreen").style.display = "none";
            }
            ;
        break;
        case 2 :
            document.getElementById("lgsp").style.display = "none";
            document.getElementById("jj").style.display = "block";
            ;
        break;
        case 3 :
            alert("서비스 준비중"); 
        break;
        case 4 :
            document.getElementById("lgsp").style.display = "none";
            document.getElementById("bkscreen").style.opacity = "0";
            document.getElementById("bkscreen").style.display = "none";
        break;
    }
}


var saveid = localStorage.getItem("userid");        
        if(saveid != "null"){
            logform.m_id.value = saveid;
            document.getElementById("lgc").checked = true;
        }
        