var data;
var array; 

function win(){
    if(window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
}

function file(){
    if(data.readyState==XMLHttpRequest.DONE && data.status==200){
        
        array = JSON.parse(this.response)
        start_page(JSON.parse(this.response));        
    }
}

data = win();
data.onreadystatechange = file;
data.open("GET","./menu/menu.json",true);
data.send();


function start_page(array){
    console.log(array);
    var mainul = document.getElementById("main_menu");

    //var no = array.members.length;   //넘버 번호

    array.forEach(function(a,b){
        //console.log(a.main_menu);
        html_li = document.createElement("li");
        html_li.append(a.main_menu); //대메뉴이름 추가
        html_ul = document.createElement("ul");
        html_li.append(html_ul); //li안에 새로운 ul추가(소메뉴부분)
        
            a.menu_list.forEach(function(a1,b1){
            html_ul.className = "menu_list"; //ul 안에 class생성
            html_li2 = document.createElement("li");
            

                a.menu_link.forEach(function(a2,b2){ //li안에 링크추가(소메뉴부분)
                console.log(a2);
                html_li2.innerHTML = "<a href="+a2+">"+a1+""; //소메뉴에 들어갈 내용을 li안에 추가 + 링크이동
                html_ul.append(html_li2) //생성된ul안에 li생성
                mainul.append(html_li);
                });

        });
    });

    

$(function(){
    $(".main_menu > li").mouseenter(function(){
        var node = $(this).index();
        console.log(node);
        $(".menu_list").eq(node).stop().slideDown();
    });

    $(".main_menu > li").mouseleave(function(){
        var node = $(this).index();
        console.log(node);
        $(".menu_list").eq(node).stop().slideUp();
    });

});

};//html생성 끝란