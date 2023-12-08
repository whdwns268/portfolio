agree_text();
function agree_text() {
    
    var opentext = new XMLHttpRequest();
    opentext.open("GET", "./agree1.txt", false);
    opentext.send();
    document.getElementById("txx1").innerText = opentext.response;

    var opentext = new XMLHttpRequest();
    opentext.open("GET", "./agree2.txt", false);
    opentext.send();
    document.getElementById("txx2").innerText = opentext.response;
};

//전체동의체크
var ckbox = document.getElementsByClassName("ckbox");
var ckea = ckbox.length;
console.log(ckea);

var k;
function allck(c){
    k = 0;
    while(k<ckea){
        ckbox[k].checked = c;
        k++;
    }
}

function agree_btn(){
    if(ship.m_agree1.checked==false){
        alert("이용약관에 동의하세요");
    }
    else if(ship.m_agree2.checked==false){
        alert("개인정보 수집에 동의 하세요");
    }
    else{
        ship.submit();
    }
}
