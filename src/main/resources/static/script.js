function validateName(firstName) {
    if (firstName) {
        $("#wrongName").html("");
        return true;
    } else {
        $("#wrongName").html("Fill in name");
        return false;
    }
}

function validateSurname(surname) {
    if (surname) {
        $("#wrongSurname").html("");
        return true;
    } else {
        $("#wrongSurname").html("Fill in surname");
        return false;
    }
}

function validateDateOfBirth(dateOfBirth) {
    if (dateOfBirth) {
        $("#wrongDateOfBirth").html("");
        return true;
    } else {
        $("#wrongDateOfBirth").html("Fill in date of birth");
        return false;
    }
}

function validateSSN(ssn) {
    if (ssn) {
        $("#wrongSSN").html("");
        return true;
    } else {
        $("#wrongSSN").html("Fill in Social Security Number");
        return false;
    }
}

function validatePhoneNumber(phoneNumber) {
    let regex = /^[0-9]{8}$/;
    if (regex.test(phoneNumber)) {
        $("#wrongPhoneNumber").html("");
        return true;
    } else {
        $("#wrongPhoneNumber").html("Fill in phone number (8 numbers)");
        return false;
    }
}

function validateEmail(email) {
    let regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (regex.test(email)) {
        $("#wrongEmail").html("");
        return true;
    } else {
        $("#wrongEmail").html("Fill in valid email address");
        return false;
    }
}

function validateCity(city) {
    if (city) {
        $("#wrongCity").html("");
        return true;
    } else {
        $("#wrongCity").html("Fill in city");
        return false;
    }
}

function validateStreet(street) {
    if (street) {
        $("#wrongStreet").html("");
        return true;
    } else {
        $("#wrongStreet").html("Fill in street");
        return false;
    }
}

function registerCitizen() {
    let citizen = {
        "firstName": $("#firstName").val(),
        "surname": $("#surname").val(),
        "dateOfBirth": $("#dateOfBirth").val(),
        "ssn": $("#ssn").val(),
        "phoneNumber": $("#phoneNumber").val(),
        "email": $("#email").val(),
        "city": $("#city").val(),
        "street": $("#street").val(),
    }
    console.log(citizen)
    $.post("/saveCitizen", citizen, function () {
    })

    firstName.value = "";
    surname.value = "";
    dateOfBirth.value = "";
    ssn.value = "";
    phoneNumber.value = "";
    email.value = "";
    city.value = "";
    street.value = "";
}

function validateAndRegister() {
    const nameOK = validateName($("#firstName").val())
    const surnameOK = validateSurname($("#surname").val())
    const dateofbirthOK = validateDateOfBirth($("#dateOfBirth").val())
    const ssnOK = validateSSN($("#ssn").val())
    const phonenumberOK = validatePhoneNumber($("#phoneNumber").val())
    const emailOK = validateEmail($("#email").val())
    const cityOK = validateCity($("#city").val())
    const streetOK = validateStreet($("#street").val())

    if (nameOK && surnameOK && dateofbirthOK && ssnOK && phonenumberOK && emailOK && cityOK && streetOK) {
        registerCitizen()
    }
}

function autoFillIn() {
    const firstNames = ["John", "Jane", "Peter", "Mary"];
    const lastNames = ["Smith", "Johnson", "Williams"];
    const cities = ["New York", "Los Angeles", "Chicago"];
    const streetNames = ["Main", "Elm", "Maple"];

    // Generate random values using destructuring from arrays
    const [firstName, lastName] = [firstNames, lastNames].map(names => names[Math.floor(Math.random() * names.length)]);
    const randomYear = Math.floor(Math.random() * (2020 - 1944 + 1)) + 1944; // Ensures age >= 18
    const dob = new Date(randomYear, Math.floor(Math.random() * 12), Math.floor(Math.random() * 30) + 1).toISOString().slice(0, 10);

    // Generate random SSN (replace with your country's format if needed)
    let ssn;
    do {
        ssn = Math.floor(Math.random() * 1000000000).toString().padStart(9, '0'); // 9 digits for SSN
    } while (ssn === '0'.repeat(9)); // Ensures SSN is not all zeros

    // Generate random 8-digit phone number (replace with your country's format if needed)
    const phone = Math.floor(Math.random() * 100000000).toString().padStart(8, '0');

    const email = `${firstName.toLowerCase()}.${lastName.toLowerCase()}@example.com`;
    const city = cities[Math.floor(Math.random() * cities.length)];
    const streetNumber = Math.floor(Math.random() * 1000) + 1;
    const street = `${streetNumber} ${streetNames[Math.floor(Math.random() * streetNames.length)]} St`;

    // Set the values in the form fields using template literals
    $('#firstName').val(firstName);
    $('#surname').val(lastName);
    $('#dateOfBirth').val(dob);
    $('#ssn').val(ssn);
    $('#phoneNumber').val(phone);
    $('#email').val(email);
    $('#city').val(city);
    $('#street').val(street);
}