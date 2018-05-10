package oldEndpoint;

import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import common.Util;
import databaseHandler.CloudWatch;
import enums.MetricType;

@Path("/awsCloudWatch")
public class AwsCloudWatch {
	
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMyResources() { 
		return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
	}
	
	
	@GET@Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMsg(@PathParam("param") String msg, 
				    		@QueryParam("startTime") String startTime,
				    		@QueryParam("endTime") String endTime) {
		
		// UrlCheck
		if(!MetricType.exists(msg)) {
			return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
		}
			
		

		Date dt1;
		Date dt2;

		try {
			dt1 = Util.dateParse(startTime);
			dt2 = Util.dateParse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
		}
		
	
		CloudWatch db = new CloudWatch();
		
		Object ret = db.select(msg, dt1, dt2);
		
		return Response.status(Status.OK).entity(ret).build();
	}
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "Netty Test";
    }

}