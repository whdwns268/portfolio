ajax();

function ajax() {
    var data;
    function win() {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        }
    }
    function fileopen() {
        if (data.readyState == XMLHttpRequest.DONE && data.status == 200) {
            movie(JSON.parse(this.response))
        }
    }
    data = win();
    data.onreadystatechange = fileopen;
    data.open("GET", "./json/new_program.json?v=1", true);
    data.send();
}

var html1 = "";
var html2 = "";
function movie(data) {
    console.log(data);
    data.forEach(function(a,b,c){
        //console.log(a);
        console.log(a.new_thumb);
        if (b <= 7){
            html1 += "<li title='" + a.new_pg + "'><img src='" + a.new_thumb + "'></li>"
            document.getElementById("vid1").innerHTML = html1;
        } 
        else if (b > 7){
            html2 += "<li title='" + a.new_pg + "'><img src='" + a.new_thumb + "'></li>"
            document.getElementById("vid2").innerHTML = html2;
        }
    });
}

function onairbtn() {
    location.href = "https://biz.skbroadband.com"
}

$(function(){
    $("#add").click(function(){
        $(".vid").fadeToggle(800);
    });

    // $(".main2_box2").click(function(){
    //     window.open("https://biz.skbroadband.com","width=500 height=50");
    // });


});