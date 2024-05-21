//--------- Validering -----------

function validerPhoneno(){
    const phoneNo = $("#phoneNo").val();
    const regexp = /^[0-9]{8}$/;
    const ok = regexp.test(phoneNo);
    if(!ok){
        $("#wrongPhoneNo").html("Phone number should be 8 digits long");
        return false;
    } else{
        $("#wrongPhoneNo").html("");
        return true;
    }
}

function validerEmail(){
    const email = $("#email").val();
    const regexp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    const ok = regexp.test(email);
    if(!ok){
        $("#wrongEmail").html("You have to enter a valid email");
        return false;
    } else{
        $("#wrongEmail").html("");
        return true;
    }
}

//--------- Opprette objekt og bruke AJAX -----------

function sendForm(){
    const citizen ={
        firstname: $("#firstname").val(),
        lastname: $("#surname").val(),
        dateOfBirth: $("#dateOfBirth").val(),
        ssn: $("#SSN").val(),
        phoneNo: $("#phoneNo").val(),
        email: $("#email").val(),
        city: $("#city").val(),
        street: $("#street").val()
    };
    console.log(citizen)
    if(validerEmail() && validerPhoneno()) {
        $.post("/saveCitizen", citizen, function () {
        })
            .fail(function (jqXHR) {
                const json = $.parseJSON(jqXHR.responseText);
                $("#wrong").html(json.message);
            })
    }
}

//--------- Auto-fill for testing, ikke eksamensrelevant -----------

function autoFillIn() {
    $("#firstname").val("Thomas");
    $("#surname").val("Lonning");
    $("#dateOfBirth").val("1995-08-12");
    $("#SSN").val("345345345");
    $("#phoneNo").val("12345678");
    $("#email").val("hei@lol.no");
    $("#city").val("Kongsberg");
    $("#street").val("Streetboiiii");
}


//--------- Henting av objekter fra server-----------
function getCitizens(){
    $.get("/showCitizens", function(data) {
        console.log("Citizens:", data);
    }, 'json');
}


//--------- Sletting av objekter fra server -----------
function removeUnderage(){
    $.get("/removeUnderage", function(data) {}
    )
    getCitizens();
}