package jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import database.InfluxDb;
import enums.UrlType;
import objects.parameter;

@Singleton
@Path("/api")
public class NettyEndpoint {
	
	
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
		if(!UrlType.exists(msg)) {
			return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
		}
			
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA);
		SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.KOREA);
		Date startD;
		Date endD;
		try {
			startD = dt1.parse(startTime);
			endD = dt2.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
		}
		
		
		parameter parms = new parameter();
		parms.setStartTime(startD.getTime());
		parms.setEndTime(endD.getTime());
		parms.setUrl(msg);
		
		if(parms.isEmpry()) {
			return Response.status(Status.BAD_REQUEST).entity(Status.BAD_REQUEST.toString()).build();
		}
		
		InfluxDb db = new InfluxDb();
		
		String ret = db.select(parms);
		
		return Response.status(Status.OK).entity(ret).build();
	}
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "Netty Test";
    }

}