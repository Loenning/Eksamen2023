package com.example.eksamen2023;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class CitizenController {

    @Autowired
    CitizenRepository rep;

    @GetMapping("/hello")
    public String sjekk() {
        return "This is a test";
    }

    @PostMapping("/saveCitizen")
    public void saveCitizen(Citizen innCitizen, HttpServletResponse response) {
        rep.saveCitizen(innCitizen);
    }

    @Autowired
    private HttpSession session;


    @Async
    @GetMapping("/logIn")
    public boolean logIn(@RequestParam String username, @RequestParam String password) {
        if (rep.logIn(username, password)) {
            session.setAttribute("loggedIn", true);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/editCitizen")
    public boolean checkLogIn(){
        if(session.getAttribute("loggedIn") !=null){
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/getAllCitizens")
    public ArrayList<Citizen> getAllCitizens(HttpServletResponse response) throws IOException {
        if ((boolean) session.getAttribute("loggedIn")) {
            ArrayList<Citizen> allCitizens = rep.getAllCitizens();
            if (allCitizens == null) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error in DB - try again later");
            }
            return allCitizens;
        } else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Cannot show register - you have to be logged in");
            return null;
        }
    }

    @PostMapping("/deleteCitizensUnderEighteen")
    public void deleteCitizensUnderEighteen(){rep.deleteCitizensUnderEighteen();}

    @GetMapping("/logOut")
    public void logOut() {
        session.removeAttribute("loggedIn");
    }

    @GetMapping("/encryptAllPasswords")
    public boolean encryptAllPasswords(){
        return rep.encryptAllPasswords();
    }

}

