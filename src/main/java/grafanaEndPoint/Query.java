package grafanaEndPoint;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Util;
import databaseHandler.CloudWatch;
import objects.Range;

@Path("/query")
public class Query {
	
	@Context
	private HttpServletRequest httpRequest;

	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMyResources() {		
		return Response.noContent()
                .header("Access-Control-Allow-Headers", "accept, content-type")
                .header("Access-Control-Allow-Methods", "POST")
                .header("Access-Control-Allow-Origin", "*")
                .build();
	}
		
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post() {		
		
		String resMsg = Util.reqToString(httpRequest);		
		JsonObject query = new JsonParser().parse(resMsg).getAsJsonObject();					
		
		String metricType = null;
		Range timeRange = null;
		JsonArray targets = query.getAsJsonArray("targets");		
		
		if (targets != null) {
			//메트릭
			metricType = targets.get(0).getAsJsonObject().get("target").getAsString();
        }
		
		timeRange = Range.from(query.getAsJsonObject("range"));
		Date dt1 = new Date();
		Date dt2 = new Date();

		dt1.setTime(timeRange.getStart());
		dt2.setTime(timeRange.getEnd());
		
	
		CloudWatch db = new CloudWatch();
		
		Object ret = db.select(metricType, dt1, dt2);
		
		System.out.println(ret);
		
		
		return Response.status(Status.OK).entity(ret).build();
	}
	

}