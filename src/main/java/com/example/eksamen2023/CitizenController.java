package com.example.eksamen2023;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class CitizenController {

    @Autowired
    private JdbcTemplate db;
    @Autowired
    HttpSession session;

    private Logger logger = LoggerFactory.getLogger(CitizenController.class);

    @GetMapping("/hello")
    public String check() {
        return "Your controller is working";
    }

    @PostMapping("/saveCitizen")
    public void saveCitizen (@RequestBody Citizen c, HttpServletResponse response) throws IOException {
        System.out.println(c);
        String sql = "INSERT INTO citizens (firstName, lastName, dateOfBirth, ssn, phoneNumber, email, city, street) VALUES (?,?,?,?,?,?,?,?)";
        try{
            db.update(sql, c.getFirstname(), c.getLastname(), c.getDateOfBirth(), c.getSsn(), c.getPhoneNo(), c.getEmail(), c.getCity(), c.getStreet());
        } catch (Exception e){
            logger.error("An error occured when saving citizen to database: "+e );
            response.sendError (HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occured when saving to database");
        }
    }

    @PostMapping("/login")
    public void saveCitizen(User user, HttpServletResponse response) throws IOException {
        session.setAttribute("loggedIn", user);
    }
    @GetMapping("/removeUnderage")
    public boolean removeUnderage(HttpServletResponse response) throws IOException {
        String getCitizens = "SELECT * FROM citizens";
        String removeCitizen = "DELETE FROM citizens WHERE cid=?";
//check if logged in
            try {
                List<Citizen> citizens = db.query(getCitizens, BeanPropertyRowMapper.newInstance(Citizen.class));
                for(Citizen citizen : citizens) {
                    if(calculateAge(citizen) < 18) { //born after 2005
                        System.out.println(citizen);
                        db.update(removeCitizen, citizen.getCid());
                    }
                }
                return true;
            } catch(Exception e) {
                logger.error("Error in removing underage citizens from database", e);
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return false;
            }

    }
    @GetMapping("/logout")
    public void logOut() {
        session.removeAttribute("loggedIn");
    }

    public static int calculateAge(Citizen citizen) {
        if (citizen.getDateOfBirth() != null) {
            System.out.println(Period.between(LocalDate.parse(citizen.getDateOfBirth()), LocalDate.now()).getYears());
            return Period.between(LocalDate.parse(citizen.getDateOfBirth()), LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }

    @GetMapping("/showCitizens")
    public List<Citizen> showCitizens(){
        String getCitizens = "SELECT * FROM citizens";
        List<Citizen> citizenList = db.query(getCitizens, BeanPropertyRowMapper.newInstance(Citizen.class));;; // we first get the list of all citizens from the DB
        Collections.sort(citizenList, Comparator.comparing(Citizen::getLastname)); // Sorting list by lastname using comparator
        logger.info("",citizenList);
        return citizenList; // Returning a list with JSON objects

    }
}

