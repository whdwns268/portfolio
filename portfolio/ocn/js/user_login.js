function logins() {

    if (loginf.user_id.value == "") {
        alert("아이디를 입력하세요");
        return false;
    }
    else if (loginf.user_pw.value == "") {
        alert("패스워드를 입력하세요");
        return false;
    }
    else {
        var idck = document.getElementById("idck");
        if(idck.checked==true){
            localStorage.setItem("userid",loginf.user_id.value);
            return;
        }
    }
}

function idckbox(z){
   if(z==false){
    localStorage.setItem("userid",null);
   }
}

var saveid = localStorage.getItem("userid");
    if(saveid!="null"){
        loginf.user_id.value = saveid;
        document.getElementById("idck").checked = true;
    }

function memberin(){
    location.href = "./membership.html";
}