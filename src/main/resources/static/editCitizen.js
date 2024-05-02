function getAllCitizens() {
    $.get("/getAllCitizens", function (citizens) {
        let citizenList = `<table class="table table-striped table-bordered">
                   <thead>
                       <tr>
                           <td>First Name</td>
                           <td>Surname</td>
                           <td>Date of birth</td>
                           <td>SSN</td>
                           <td>Phone Number</td>
                           <td>Email</td>
                           <td>City</td>
                           <td>Street</td>
                           <td></td>
                       </tr>
                   </thead>
                   `;
        citizens?.forEach(function (citizen) {
            console.log('String', citizen.ssn)
            citizenList += `<tbody>
                               <tr>
                                   <td> ${citizen.firstName} </td>
                                   <td> ${citizen.surname} </td>
                                   <td> ${citizen.dateOfBirth} </td>
                                   <td> ${citizen.ssn} </td>
                                   <td> ${citizen.phoneNumber} </td>
                                   <td> ${citizen.email} </td>
                                   <td> ${citizen.city} </td>
                                   <td> ${citizen.street} </td>
                               </tr>
                          </tbody>`
            })
            citizenList += "</table>"
            document.getElementById("/getAllCitizens").innerHTML = citizenList;
        })
        .fail(function(jqXHR) {
            const json = $.parseJSON(jqXHR.responseText);
            $("#wrongEdit").html(json.message);

            //return json.message;
            });
}

function deleteCitizensUnderEighteen(){
    $.post ("/deleteCitizensUnderEighteen", function (){
        getAllCitizens()
    })
}