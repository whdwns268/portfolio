$(function () {

    $.ajax({
        url: "./m_json/new_pet.json",
        cashe: false,
        type: "GET",
        dataType: "JSON",

        success: function (data2) {

            $.fn.new_pro(data2);

        },
        error: function () {
            console.log("통신오류")
        }

    })

    $.fn.new_pro = function (data2) {
        var li_apd;

        $.each(data2, function (a, b) {

            if (b.pet_part == "dog") {
                li_apd = `<li>
            <img src="` + b.pet_img + `">
            <em>` + b.pet_title + `</em>
                                  
        </li>`;
                $("#new_ul2").append(li_apd);
            }

            // else if (b.pet_part == "dog") {

            //     li_apd = `<li>
            //     <img src="`+b.pet_img+`">
            //     <em>`+b.pet_title+`</em>

            // </li>`;

            // $("#new_ul2").append(li_apd);
            // }

            console.log(a);

            // console.log(b.pet_title);
            // console.log(b.pet_img);
            // console.log(b.pet_part);

        });

        $("#dogcl").click(function () {

            $("#dogc1").css({
                "border-bottom": "0px"
            })
            $("#catcl").css({
                "border-bottom": "1px solid black"
            })


            var li_apd = "";
            $.each(data2, function (a, b) {

                if (b.pet_part == "dog") {
                    li_apd += `<li>
                <img src="` + b.pet_img + `">
                <em>` + b.pet_title + `</em>
            </li>`;
                }

            });
            $("#new_ul2").html(li_apd);
        });

        $("#catcl").click(function () {
            $("#dogc1").css({
                "border-bottom": "1px solid black"
            })

            $("#catcl").css({
                "border-bottom": "0px"
            })
            var li_apd = "";
            $.each(data2, function (a, b) {

                if (b.pet_part == "cat") {
                    li_apd += `<li>
                <img src="` + b.pet_img + `">
                <em>` + b.pet_title + `</em>
            </li>`;
                }

            });
            $("#new_ul2").html(li_apd);
        });

    }

});