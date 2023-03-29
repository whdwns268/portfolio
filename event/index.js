function corp(no){
    //공정거래위원회 API 서버와 동기화함
    window.open("http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+no,"","width=500 height=500");
}


/*
function ffsend(){
    var tiket = document.getElementById("tiket");
    
    if(tiket.value == ""){
        alert("티켓넘버를 입력하세요");
    } else{
        if(tiket.value.length != 8){
            alert("티켓은 8자리입니다.");
            console.log(tiket.length);
        }
        else{
            f.submit();
        } 

    }
} 

*/
function cgvgo(){
var cgvgo = document.getElementById("cgvgo");
location.href="http://www.cgv.co.kr";
}

function ffsend(){
    var nodes = document.getElementById("nodes");
    var nodeck = nodes.children;
    var inputss = nodes.nextSibling 
    console.log(inputss);
    if(nodeck[4].value == ""){
        alert("티켓넘버를 입력하세요");
    } else{
        if(tiket.value.length != 8){
            alert("티켓은 8자리입니다.");
            console.log(tiket.length);
        }
        else{
            f.submit();
        } 

    }

    
}