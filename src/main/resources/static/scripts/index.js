$(document).ready(function () {

    $("#linkForm").submit(function (event) {
        event.preventDefault();
        short();
    });

});

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
        },
        error: function (e) {
            console.log("ERROR");
            console.log(e);
        }
    });

}
