package com.example.eksamen2023;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CitizenRepository {

    @Autowired
    private JdbcTemplate db;

    public void saveCitizen(Citizen citizen) {
        String citizenSQL = "INSERT INTO citizens (firstName,surname,dateOfBirth,ssn,phoneNumber,email,city,street) VALUES(?,?,?,?,?,?,?,?)";
        db.update(citizenSQL, citizen.getFirstName(), citizen.getSurname(), citizen.getDateOfBirth(), citizen.getSsn(), citizen.getPhoneNumber(), citizen.getEmail(), citizen.getCity(), citizen.getStreet());
    }

    public ArrayList<Citizen> getAllCitizens() {
        String citizenSQL = "SELECT * FROM citizens ORDER BY dateOfBirth ASC";
        List<Citizen> allCitizens = db.query(citizenSQL, new BeanPropertyRowMapper<>(Citizen.class));
        return new ArrayList<>(allCitizens);
    }

    public void deleteCitizensUnderEighteen(){
        String deletionSQL = "SELECT * FROM citizens WHERE STR_TO_DATE(dateOfBirth, '%Y-%m-%d') >= DATE_SUB(CURDATE(), INTERVAL 18 YEAR)";
        db.update(deletionSQL);
    }

    private String encryptPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(15));
    }

    public boolean checkPassword(String password, String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }

    public boolean logIn(String username, String password) {
        String userSQL = "SELECT * FROM users WHERE username = ?";
        try {
            List<User> users = db.query(userSQL, new BeanPropertyRowMapper(User.class), username);

            if(users != null) {
                if (checkPassword(password, users.get(0).getPassword()));{
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }


    public boolean encryptAllPasswords(){
        String sql = "SELECT * FROM users";
        String encryptedPassword;
        try {
            List<User> allUsers = db.query(sql, new BeanPropertyRowMapper(User.class));
            for(User u : allUsers){
                encryptedPassword = encryptPassword(u.getPassword());

                sql = "UPDATE users SET password=? WHERE id=?";
                db.update(sql, encryptedPassword, u.getId());
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
