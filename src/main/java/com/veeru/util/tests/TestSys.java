package com.veeru.util.tests;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: veerana
 * Date: 12/2/14
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestSys {
    private static final Logger LOG = Logger.getLogger(TestSys.class.getName());

    public static void main(String[] args){

        Properties properties=System.getProperties();

        Gson gson=new Gson();
        String message=gson.toJson(properties,HashMap.class);
        LOG.info("JSON : " + message);
    }
}
