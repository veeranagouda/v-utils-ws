package com.veeru.util.ws;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: veerana
 * Date: 12/2/14
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("platform/utils")
public class Utils {
    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    @GET
    @Path("/date")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response sayDate(@DefaultValue("IST") @QueryParam("zone") String timeZone){

        String[] timeZones={"IST","PDT","EDT","UTC","CST","EST","JTZ"};
        HashMap<String,String> timeMap=new HashMap<>();
        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for(String zone:timeZones){
            TimeZone tZone = TimeZone.getTimeZone(zone);
            sdf.setTimeZone(tZone);
            Date resultDate =  new Date(currentTimeInMillis);
            timeMap.put(zone,sdf.format(resultDate));
        }
        Gson gson=new Gson();
        String message=gson.toJson(timeMap,HashMap.class);
        return Response.ok(message, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/sys")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getSysDetails(){
        Properties properties=System.getProperties();
        Gson gson=new Gson();
        String message=gson.toJson(properties,HashMap.class);
        return Response.ok(message, MediaType.APPLICATION_JSON).build();
    }

}
