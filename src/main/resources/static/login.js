function logIn() {
    const user = {
        username: $("#username").val(),
        password: $("#password").val()
    }
    const url = "/logIn";
    var jqxhr = $.get(url, user, function (loggedIn) {
        console.log(loggedIn)
        if (loggedIn) {
            window.location.href = 'editCitizen.html';
        } else {
            $("#wrong").html("Wrong username or password");
        }
    })
        .fail(function () {
                console.log("test")
                $("#wrong").html("Server error - try again later");
            }
        );
}

function logOut() {
    const url = "/logOut";
    $.get(url, function () {
        window.location.href = 'login.html';
    })
}

function encryptAllPasswords(){
    $.get("/encryptAllPasswords", function (OK){
        if(OK){
            $("#wrong").html("Encryption completed!");
        } else {
            $("#wrong").html("Encryption failed");
        }
    })
        .fail(function (jqXHR){
            const json = $.parseJSON(jqXHR.responseText);
            $("#wrong").html(json.message);
        });
}