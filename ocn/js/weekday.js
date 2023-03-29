$('.ul_week > li').mouseenter(function(){
    var node = $(this).index();
    node= node-1;
    $('.ul_week > li > span').eq(node).slideDown();
    console.log("test")
});

$('.ul_week > li').mouseleave(function(){
    var node = $(this).index();
    node= node-1;
    $('.ul_week > li > span').eq(node).slideUp();
    console.log("test")
});