var bannerdata;
var banner; 

function win(){
    if(window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
}

function file(){
    if(bannerdata.readyState==XMLHttpRequest.DONE && bannerdata.status==200){
        
        banner = JSON.parse(this.response)
        //banner_page(JSON.parse(this.response));        
        $.fn.banner_page(banner)
    }
}

bannerdata = win();
bannerdata.onreadystatechange = file;
bannerdata.open("GET","./banner_json/banner.json",true);
bannerdata.send();


$.fn.banner_page = function(banner){
    var nodenum = 0
    $.each(banner["banners"],function(a1,a2){ // 3개
        console.log(a1);
        //배너이미지부분
        let li1 = "<li class='banner"+nodenum+"'></li>";
        nodenum++;
        $(".banner_ulcss").append(li1)
         //디스크부분
         let li2 = "<li></li>";
         $("#banner_btn").append(li2)
        $(Object.keys(banner["banners"][0])).each(function(b1,b2){            
        if(b1==0){
        $(".banner_ulcss>li").eq(a1).attr("data",banner["banners"][a1][1]);
        $(".banner_ulcss>li").eq(a1).append("<img src = "+(banner["banners"][a1][b2])+">");
        };


       
        });
 
    });

    $(document).on("click",".banner_ulcss > li",function(){
        var node = $(this).index();
        var url = $(".banner_ulcss > li").eq(node).attr("data");
        console.log(url);
    });

            var timer;
            var count = 0;
            var ea = $(".banner_ulcss>li").length;
            var width = $(".banner_ulcss>li").width();
            $(".banner_ulcss").css({"width":(width*ea)+"px"});

            $.fn.move = function(){
                clearTimeout(timer);

                $(".banner_ulcss").stop().animate({
                    "left":-width+"px"
                },function(){
                    var cp = $(".banner_ulcss>li").eq(0).clone();
                    $(".banner_ulcss>li").eq(0).remove();
                    $(".banner_ulcss").append(cp);
                    $(".banner_ulcss").css({
                        "left":"0px"
                    })
                });
                count++;
                if(count>=ea){
                    count = 0;
                }

                $("#banner_btn > li").css({
                    "background-color":"rgba(255, 255, 255, 0.5)"
                });

                $("#banner_btn > li").eq(count).css({
                    "background-color":"white"
                });

                

               timer = setTimeout($.fn.move,5000);
            }
            timer = setTimeout($.fn.move,5000);

            //전역변수
            var ea = $("#ban1>li").length;
            var banner_width = $("#ban1>li").width();
            var node = 0;
            var timer = "";



            $("#banner_btn > li").click(function(){
                node = $(this).index();
                $("#banner_btn > li").css({    
                    "background-color":"rgba(255, 255, 255, 0.5)"
                });
                $(this).css({    
                    "background-color":"white"
                });

                $.fn.mybanner(node);
            });

            $.fn.mybanner = function(i){
            var classname =".banner"+i;
            var nos = Math.ceil($(classname).offset().left);
            var pnos = Math.ceil($(".banner_ulcss").offset().left);
            var total_left = nos - pnos;
            var now_node = Number(String(total_left).substr(0,1));  
            $(".banner_ulcss").stop().animate({
                "left":-total_left+"px"
            },800,function(){
                var z = 0;
                while(z < now_node){
                var cp = $(".banner_ulcss > li").eq(0).clone();
                $(".banner_ulcss > li").eq(0).remove();
                $(".banner_ulcss").append(cp);
                z++;
                }  
                $(".banner_ulcss").css({
                    "left":"0px"
                });
            });
            };



            //이전, 다음 버튼
        $("#preview").click(function(){
            node--;
            var last = ea - 1;
            if(node < 0){
                node = last;
            }
            
            var z = $(".banner_ulcss>li").eq(last).clone();
            $(".banner_ulcss>li").eq(last).remove();
            $(".banner_ulcss").prepend(z);
            $(".banner_ulcss").css({
                "left":-banner_width+"px"
            });
            $(".banner_ulcss").stop().animate({
                "left":"0px"
            },800);
            
            $(".banner_btn > li").css({
                "background-color":"rgba(255, 255, 255, 0.5)"
            });
            $(".banner_btn > li").eq(node).css({
                "background-color":"white"
            });
        });

        $("#next").click(function(){
            node++;
            if(node >= ea){
                node = 0;
            }
            $(".banner_ulcss").animate({
                "left":-banner_width+"px"
            },function(){
                var z = $(".banner_ulcss>li").eq(0).clone();
                $(".banner_ulcss>li").eq(0).remove();
                $(".banner_ulcss").append(z);
                $(".banner_ulcss").css({
                    "left":"0px"
                });
            });
            $(".banner_btn > li").css({
                "background-color":"rgba(255, 255, 255, 0.5)"
            });
            $(".banner_btn > li").eq(node).css({
                "background-color":"white"
            });
        });
}
