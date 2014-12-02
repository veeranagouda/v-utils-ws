package com.veeru.ws;

import com.google.gson.Gson;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import com.sun.jersey.multipart.FormDataParam;
import com.veeru.util.Utilities;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: veerana
 * Date: 12/2/14
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("platform/utils")
public class GeneralUtilities {
    private static final Logger logger = Logger.getLogger(GeneralUtilities.class.getName());
    private static final String SERVER_UPLOAD_LOCATION_FOLDER ="/tmp/files";

    @GET
    @Path("/date")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response sayDate(@DefaultValue("IST") @QueryParam("zone") String timeZone){


        String[] timeZones={"IST","PDT","EDT","UTC","CST","EST","JTZ"};
        HashMap<String,String> timeMap=new HashMap<>();
        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for(String zone:timeZones){
            logger.info("Getting date & time for : " + zone);
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
        logger.info("Getting System Properties.");
        Properties properties=System.getProperties();
        Gson gson=new Gson();
        String message=gson.toJson(properties,HashMap.class);
        return Response.ok(message, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/usphone")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    public Response getUsPhoneNumbers(@DefaultValue("5") @QueryParam("count") Integer count){

        List<String> phones=new ArrayList<String>();


        for(int i=1;i<=count;i++){
        phones.add(Utilities.generateRandomPhoneNumber());
        }

        Gson gson=new Gson();
        String message=gson.toJson(phones,List.class);
        return Response.ok(message, MediaType.APPLICATION_JSON).build();
    }

   /*
    @POST
    @Path("file/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") java.io.InputStream fileInputStream,@FormDataParam("file") com.sun.jersey.core.header.FormDataContentDisposition contentDispositionHeader) {
        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();
        ResponseBuilderImpl builder = new ResponseBuilderImpl();
        logger.info("Uploading file : " + filePath);
        Boolean isSuccess=saveFile(fileInputStream, filePath);
        if(isSuccess) {
            String output = "File saved to server location : " + filePath;
            logger.info(output);
            builder.status(200);
            builder.entity(output);
            return Utilities.makeCORS(builder);
        }  else {
            logger.error("Error in uploading file : " + filePath);
            builder.status(500);
            builder.entity("<b>Error in saving file.</b>");
            return Utilities.makeCORS(builder);
        }
    }


    private Boolean saveFile(InputStream uploadedInputStream,String serverLocation) {
        Boolean isSuccess=false;
        try {
            OutputStream outputStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            outputStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            isSuccess=true;
        } catch (IOException e) {
            logger.error("Unable to save file : " + e.getMessage());
            isSuccess=false;
            e.printStackTrace();
        }
        return isSuccess;
    }

*/
}
