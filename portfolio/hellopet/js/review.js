

    var re_data;
    function win(){
        if(window.XMLHttpRequest){
            return new XMLHttpRequest();
        }
    }
    function fileopen(){
        if(re_data.readyState==XMLHttpRequest.DONE && re_data.status==200){
            var array = JSON.parse(this.response);
            //JSON.parse : Javascript전용 json배열화 함수
            re_fn(JSON.parse(this.response));
        }
    }
    re_data = win();
    re_data.onreadystatechange = fileopen;
    re_data.open("GET","./m_json/review.json",true);
    re_data.send();

function re_fn(re_data){
    //console.log(re_data);

    var re_ul = document.getElementById("re_ul");
    re_data.reviews.forEach(function(a,b,c){
            //console.log(re_data.reviews[b]);
            var re_li = document.createElement("li");
            re_li.append(re_data.reviews[b]);
            re_ul.append(re_li);
            


            $(function(){
            var re_timer;
            var re_node = $('#re_ul > li').length;
            var re_outohe = $('#re_ul > li').eq(0).height();
            var re_nn=0;
            $.fn.re_abc = function(){
            
                re_nn++;
                if(re_nn >= re_node){
                    re_nn = 0;
                }
            
                //console.log(re_nn);
                var $ul = $('#re_ul');
                $ul.stop().animate({
                    "bottom": re_outohe+"px"
                },700, function() {
                    var re_copy = $ul.children().eq(0).clone();
                    $ul.children().eq(0).remove();
                    $ul.append(re_copy);
                    $ul.css({
                        "bottom":"0px"
                    });
                });
                re_timer = setTimeout($.fn.re_abc,5000);
            };
            re_timer = setTimeout($.fn.re_abc,5000);
            });
    });
}

