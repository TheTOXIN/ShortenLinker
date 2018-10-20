$(document).ready(function () {

    $("#linkForm").submit(function (event) {
        event.preventDefault();
        if (valid()) short();
    });

    $("#copy").click(function () {
        $("#hashLink").select();
        document.execCommand('copy');
    });

    $("#share").click(function () {
        console.log("SHARE");
    });

});

function valid() {
    var str = $("#link").val();

    var empty = str === '';
    var max  = str.length > 1000;
    var min  = str.length < 10;
    var url = !str.includes("http");

    if(empty || max || min || url) {
        $("#hashText").text("NOT VALID LINK");
        $("#hashText").css('color', 'red');
        $("#qr").attr("src", "image/pinus.png");
        return false;
    } else {
        return true;
    }
}

function short() {
    var request = {};
    request["link"] = $("#link").val();

    console.log("LINK " + request["link"]);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/short",
        data: JSON.stringify(request),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#hashLink").val(data.hashLink);
            $("#qr").attr("src", "/QR/" + data.hash);
            $("#hashText").text("HASH LINK");
            $("#hashText").css('color', 'green');
        },
        error: function (e) {
            console.log("ERROR");
            console.log(e);
        }
    });

}
