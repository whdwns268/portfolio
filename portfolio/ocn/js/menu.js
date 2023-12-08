var data;
var array;
function win(){
    if(window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
}
function file(){
    if(data.readyState==XMLHttpRequest.DONE && data.status==200){
        array = JSON.parse(this.response);
        abc(JSON.parse(this.response));
    }
}
data = win();
data.onreadystatechange = file;
data.open("get","./json/menu.json",true);
data.send();

function abc(data){
    console.log(data);   
    var html_li="";
    var html_li2="";
    var html_ol="";
    var ulcss = document.getElementById("ulcss");
    data.forEach(function(a,b,c){
        //console.log(b);
        //console.log(data[b]["main_menu"]);
        html_li = document.createElement("li");
        html_li.className = "ulcss"+[b];
        html_li.append(data[b]["main_menu"]);
        ulcss.append(html_li);
        html_ol = document.createElement("ol");
        html_ol.className = "olcss";
        data[b]["menu_list"].forEach(function(a1,b1,c1){
        //console.log(a1);
        html_li2 = document.createElement("li");
        //console.log(data[b]["menu_link"]);
        html_li2.innerHTML = "<a href='"+data[b].menu_link[b1]+"'>"+a1+"</a>";
        html_ol.append(html_li2);
        html_li.append(html_ol);
        });
    });
}
$(function(){
       $(".ulcss0").mouseenter(function(){ 
        $(".ulcss0 > ol").stop().slideDown(500); 
       });
       $(".ulcss0").mouseleave(function(){ 
        $(".ulcss0 > ol").stop().slideUp(500); 
       });
       $(".ulcss1").mouseenter(function(){
        $(".ulcss1 > ol").stop().slideDown(500);
       });
       $(".ulcss1").mouseleave(function(){
        $(".ulcss1 > ol").stop().slideUp(500);
       });
       $(".ulcss2").mouseenter(function(){
        $(".ulcss2 > ol").stop().slideDown(500);
       });
       $(".ulcss2").mouseleave(function(){
        $(".ulcss2 > ol").stop().slideUp(500);
       });
       $(".ulcss3").mouseenter(function(){
        $(".ulcss3 > ol").stop().slideDown(500);
       });
       $(".ulcss3").mouseleave(function(){
        $(".ulcss3 > ol").stop().slideUp(500);
       });
       $(".ulcss4").mouseenter(function(){
        $(".ulcss4 > ol").stop().slideDown(500);
       });
       $(".ulcss4").mouseleave(function(){
        $(".ulcss4 > ol").stop().slideUp(500);
       });
});

function menulogo(){
    location.href="http://whdwns268.dothome.co.kr/portfolio/ocn/"; 
}
