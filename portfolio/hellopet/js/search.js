$(function(){

    $('#sbtn').click(function(){
        $('.se_box').slideToggle();
    });

});


function se_search(){
    var se_text = document.getElementById("se_text");

    if(se_text.value==""){
        alert("검색어를 입력해 주세요");
        return false;
    }
    else{
        return;
        //혹은 f.submit(); 도 가능
    }
}