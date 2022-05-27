/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ptit.webservice.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ngosi
 */
public class ValidateUtility {
   private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
 
    private static final Pattern VALID_PHONE_NUMBER_REGEX = 
    Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);
    
   public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
   
//   public static boolean validatePhoneNumber(String phoneNumber) {
//        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
//        return matcher.find();
//    }
}