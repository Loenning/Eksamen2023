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

function validerFirstname(firstname){
    if(firstname){
        $("#wrongFirstname").html("Field needs to be filled out");
        return false;
    } else{
        $("#wrongPhoneNo").html("");
        return true;
    }
}

function sendForm(){
    const citizen ={
        firstname: $("#firstname").val(),
        surname: $("#surname").val(),
        DoB: $("#dateOfBirth").val(),
        SSN: $("#SSN").val(),
        phoneNo: $("#phoneNo").val(),
        email: $("#email").val(),
        city: $("#city").val(),
        street: $("#street").val()
    };
    console.log(citizen)
    if(true) {
        $.post("/saveCitizen", citizen, function () {
        })
            .fail(function (jqXHR) {
                const json = $.parseJSON(jqXHR.responseText);
                $("#wrong").html(json.message);
            })
    }
}
