$(function(){

    var array = [
        "",
        "게임덱후들(유민상,김준현)",
        "44층 지하던전",
        "켠왕켜놩(켠김에왕까지-허준)",
        "핑거게임(신동엽, 장도연)",
        "플레이어2 (이수근,이진호,이용진,이이경,이진호,정혁,황치열,김동현)"
    ]
    var a = 1;
    while(a<=5){
        //console.log(array);
        //console.log(array[a]);
        $("#contentul").append(
            `<li title="`+array[a]+`">
                <img src="./contents/mini`+a+`-black.png">
                <img src="./contents/mini`+a+`-color.png">
            </li>`
        )
        a++;
    }

    $("#contentul > li").bind({

        "mouseenter":function(){
            var nodes = $(this).index();
            $("#contentul > li:eq("+nodes+") > img").eq(1).css({
                "display":"block"
            })
        },
        "mouseleave":function(){
            var nodes = $(this).index();
            $("#contentul > li:eq("+nodes+") > img").eq(1).css({
                "display":"none"
            })
        }
    })
    
})