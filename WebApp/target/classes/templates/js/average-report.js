$(document).ready(function() {
    $.ajax({
        url: "http://127.0.0.1:8080/average"
    }).then(function(data) {
        if(jQuery.isEmptyObject(data)){
            $('.title').append("No listings yet!")
        }
        else{
            $('.title').append("Report")
            $('.private').append(data.private);
            $('.dealer').append(data.dealer);
            $('.other').append(data.other);
        }
    });
});