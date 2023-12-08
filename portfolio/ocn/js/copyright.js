function corp(no){
    window.open("http://www.ftc.go.kr/bizCommPop.do?wrkr_no="+no,"","width=500 height=500");
}

$(function (){
    $.ajax({
        url: "./json/notice.json",
        cashe: false,
        type: "GET",
        dataType: "JSON",

        success: function (data2) {
            $.fn.notice(data2);
        },
        error: function () {
            console.log("통신오류")
        }
    });
    $.fn.notice = function (data2) {
        var li ;
        console.log(data2);
        $.each(data2.notice, function(a,b){

            li = `<li>`+b[0]+`</li>`
            //console.log(b[0]);
            $("#notice3").append(li);
        });
        
        var ea2 = $("#notice3 > li").length;
        //console.log(ea2); 5

        var liheight = $("#notice3 > li").height()
         //console.log(liheight);  45

        var totalheight = ea2 * liheight;
         //console.log(totalheight); 
        var roltime;
        $.fn.move = function () {
            $("#notice3").stop().animate({
                "top": -liheight + "px"
            }, 500, function () {
                var nextli = $("#notice3 > li").eq(0).clone();
                $("#notice3 > li").eq(0).remove();
                $("#notice3").append(li);
                $("#notice3").css({
                    "top": 0
                });
            });
            rolltime = setTimeout($.fn.move, 6000);
        }

        rolltime = setTimeout($.fn.move, 6000);
    }
    $("#corp1").click(function () {
        $("#ul1").fadeToggle(400);

    });
    $("#corp2").click(function () {
        $("#ul2").fadeToggle(400);
    });

    $.ajax({
        url: "./json/pullmenu.json",
        cashe: false,
        type: "GET",
        dataType: "JSON",
        
        success: function (pulldata) {
            $.fn.pull(pulldata);            
        },
        error: function () {
            console.log("통신오류")
        }
    });
    
    $.fn.pull = function (pulldatas) {
         //console.log(pulldatas[0]);
        var li1 ;
        var li2 ;

        $.each(pulldatas[0].brand, function(a,b){
            li1 = `<li>`+b+`</li>`
  
            //console.log(b);
            $("#ul1").append(li1)
        })

        $.each(pulldatas[0].subsidi, function(a2,b2){
            li2 = `<li>`+b2+`</li>`
           
             console.log(b2);
            $("#ul2").append(li2)
        })
    };
});