package com.veeru.ws;

import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import com.sun.jersey.multipart.FormDataParam;
import com.veeru.util.Utilities;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: veerana
 * Date: 12/2/14
 * Time: 7:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("platform/file")
public class FileUtilities {
    private static final Logger logger = Logger.getLogger(FileUtilities.class.getName());
    private static final String SERVER_UPLOAD_LOCATION_FOLDER ="/tmp/files";

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") java.io.InputStream fileInputStream,@FormDataParam("file") com.sun.jersey.core.header.FormDataContentDisposition contentDispositionHeader) {
        String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();
        ResponseBuilderImpl builder = new ResponseBuilderImpl();

        Boolean isSuccess=saveFile(fileInputStream, filePath);
        if(isSuccess) {
        String output = "File saved to server location : " + filePath;
            builder.status(200);
            builder.entity(output);
            return Utilities.makeCORS(builder);
        }  else {
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


}
