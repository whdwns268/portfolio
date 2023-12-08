var data1;
var array;
function win(){
    if(window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
}
function file(){
    if(data1.readyState==XMLHttpRequest.DONE && data1.status==200){
        array = JSON.parse(this.response);
        abc(JSON.parse(this.response));
    }
}
data1 = win();
data1.onreadystatechange = file;
data1.open("get","./m_json/best_product.json",true);
data1.send();

function abc(data1){
    //console.log(data1);
    data1.best_product.forEach(function(a,b,c){
        //console.log(c);
        var bediv = document.getElementById("bediv");
        var html_ul = document.createElement("ul");
        var html_li4 = document.createElement("li");
        var html_li3 = document.createElement("li");
        var html_li2 = document.createElement("li");
        var html_li = document.createElement("li");
        html_li.style.backgroundImage = "url('" + data1.best_product[b]["product_img"] + "')";
        html_ul.append(html_li);
        html_ul.append(html_li2);
        html_ul.append(html_li3);
        html_ul.append(html_li4);
        html_li2.innerHTML = data1.best_product[b]["product_nm"];
        html_li3.innerHTML = data1.best_product[b]["product_sample"];
        html_li4.innerHTML = data1.best_product[b]["product_money"]+" 원";
        bediv.append(html_ul);
        //console.log(data1.best_product[b]["product_img"]);
    });
}