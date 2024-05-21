package com.example.eksamen2023;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//--------- Huske å legge på @RestController -----------

@RestController
public class CitizenController {

    //--------- Autowire beans for databaselagring og session -----------

    @Autowired
    private JdbcTemplate db;
    @Autowired
    HttpSession session;

    //--------- Opprette logger -----------
    private Logger logger = LoggerFactory.getLogger(CitizenController.class);


    //--------- Test-endpoint -----------
    @GetMapping("/hello")
    public String check() {
        return "Your controller is working";
    }


    //--------- Endpoint for lagring av POJO i database -----------
    @PostMapping("/saveCitizen")
    public void saveCitizen (Citizen c, HttpServletResponse response) throws IOException {
        System.out.println(c);
        String sql = "INSERT INTO citizens (firstName, lastName, dateOfBirth, ssn, phoneNumber, email, city, street) VALUES (?,?,?,?,?,?,?,?)";
        try{
            db.update(sql, c.getFirstname(), c.getLastname(), c.getDateOfBirth(), c.getSsn(), c.getPhoneNo(), c.getEmail(), c.getCity(), c.getStreet());
        } catch (Exception e){
            logger.error("An error occured when saving citizen to database: "+e );
            response.sendError (HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occured when saving to database");
        }
    }


    //--------- Endpoint for login -----------
    @PostMapping("/login")
    public boolean saveCitizen(User user, HttpServletResponse response) throws IOException {
        try {
            session.setAttribute("loggedIn", user);
            return true;
        } catch (Exception e){
            logger.error("Error logging in");
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return false;
        }
    }

    //--------- Endpoint for logout -----------
    @GetMapping("/logout")
    public void logOut() {
        session.removeAttribute("loggedIn");
    }


    //--------- Endpoint for sletting av spesifikke objekter basert på alder -----------
    @GetMapping("/removeUnderage")
    public boolean removeUnderage(HttpServletResponse response) throws IOException {
        String getCitizens = "SELECT * FROM citizens";
        String removeCitizen = "DELETE FROM citizens WHERE cid=?";
        //check if logged in
        //if(session.getAttribute("loggedIn") != null) {
            try {
                List<Citizen> citizens = db.query(getCitizens, BeanPropertyRowMapper.newInstance(Citizen.class));
                for (Citizen citizen : citizens) {
                    if (calculateAge(citizen) < 18) { //yngre enn 18
                        System.out.println(citizen);
                        db.update(removeCitizen, citizen.getCid());
                    }
                }
                return true;
            } catch (Exception e) {
                logger.error("Error in removing underage citizens from database", e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return false;
            }
        }

    //--------- Funksjon for kalkulering av alder for bruk i endpoint over -----------
    public static int calculateAge(Citizen citizen) {
        if (citizen.getDateOfBirth() != null) {
            System.out.println(Period.between(LocalDate.parse(citizen.getDateOfBirth()), LocalDate.now()).getYears());
            return Period.between(LocalDate.parse(citizen.getDateOfBirth()), LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }


    //--------- Endpoint for å hente ut objektene og sende de som liste med JSON -----------
    @GetMapping("/showCitizens")
    public List<Citizen> showCitizens(){
        String getCitizens = "SELECT * FROM citizens";
        List<Citizen> citizenList = db.query(getCitizens, BeanPropertyRowMapper.newInstance(Citizen.class));;; // we first get the list of all citizens from the DB
        citizenList.sort(Comparator.comparing(Citizen::getLastname)); // Sorting list by lastname using comparator
        logger.info("",citizenList);
        return citizenList; // Returning a list with JSON objects
    }
}

