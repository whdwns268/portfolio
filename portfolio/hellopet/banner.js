$(function(){
    var node= 0; //노드초기값
    var timer = ""; //타이머 변수
    var ea = $('#ba_div > img').length;

    $.fn.banner = function(){
      
    $('#ba_div > img').eq(node).fadeOut(800);
    if(node >= ea){//배너갯수보다 같거나 클 경우 노드번호를 초기화
    node=0;
    };
    node++;
    $('#ba_div > img').eq(node).fadeIn(400);


        timer = setTimeout($.fn.banner,10000); //5초 후에 다시 확장함수를 로드 함
    }
    
    timer=setTimeout($.fn.banner,10000);
});