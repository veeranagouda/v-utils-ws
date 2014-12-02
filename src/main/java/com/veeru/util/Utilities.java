package com.veeru.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: veerana
 * Date: 12/2/14
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Utilities {
    private static final Logger logger = Logger.getLogger(Utilities.class.getName());
    private static  String _corsHeaders;
    public static String generateRandomPhoneNumber(){
        String phone=null;
        boolean validPhone=false;
        do {
            Random rand = new Random();
            int num1 = rand.nextInt(600) + 100;
        /*//Aded below to make sure that 1st three digits are always more than 200, because if number starts with 1, local api is ignoring 1st number and treating it as invalid number.
        if(num1<200){
            num1=num1+100;
        }*/
            int num2 = rand.nextInt(641) + 100;
            int num3 = rand.nextInt(8999) + 1000;
            phone = num1 + "" + num2 + "" + num3;
            validPhone=isValidUSPhone(phone);
        }while(!validPhone);
        logger.info("Generated Valid US Phone Number -> " + phone);
        return phone;
    }

    public static boolean isValidUSPhone(String phone){
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber usNumberProto = phoneUtil.parse(phone, "US");
            return  phoneUtil.isValidNumber(usNumberProto);
        } catch (NumberParseException e) {
            logger.info("Phone NumberParseException was thrown: " + e.toString());
            return false;
        }
    }



    public static Response makeCORS(Response.ResponseBuilder req, String returnMethod) {
        Response.ResponseBuilder rb = req.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        if (!"".equals(returnMethod)) {
            rb.header("Access-Control-Allow-Headers", returnMethod);
        }

        return rb.build();
    }

    public static Response makeCORS(Response.ResponseBuilder req) {
        return makeCORS(req, _corsHeaders);
    }
}
