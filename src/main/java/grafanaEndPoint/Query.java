package grafanaEndPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import common.Util;
import databaseHandler.CloudWatch;
import models.MetricSeries;
import models.Range;

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
		
	
	@SuppressWarnings("unchecked")
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
				
	  	MetricSeries series_minimum = new MetricSeries();
    	series_minimum.setName(metricType+"_Minimum");

    	MetricSeries series_maximum= new MetricSeries();
    	series_maximum.setName(metricType+"_Maximum");
    	
    	MetricSeries series_average= new MetricSeries();
    	series_average.setName(metricType+"_Average");
    	
    	MetricSeries series_sum= new MetricSeries();
    	series_sum.setName(metricType+"_Sum");
    	        	
    	
        for (Datapoint aDataPoint : (List<Datapoint>)ret) {	        	
//        	{"timestamp":"May 9, 2018 2:30:00 AM", "sampleCount":5.0,
//        	"average":0.26783365749840604,"sum":1.33916828749203,
//        	"minimum":0.166666666676368, "maximum":0.338983050842525

        	series_minimum.setPoints(new Number[] {aDataPoint.getMinimum(),aDataPoint.getTimestamp().getTime()});
        	series_maximum.setPoints(new Number[] {aDataPoint.getMaximum(),aDataPoint.getTimestamp().getTime()});
        	series_average.setPoints(new Number[] {aDataPoint.getAverage(),aDataPoint.getTimestamp().getTime()});
        	series_sum.setPoints(new Number[] {aDataPoint.getSum(),aDataPoint.getTimestamp().getTime()});
        }
   
        ArrayList<MetricSeries> series = new ArrayList<MetricSeries>();
        
        series.add(series_minimum);
        series.add(series_maximum);
        series.add(series_average);
        series.add(series_sum);
				
        
        String resStr = new Gson().toJson(series);
		System.out.println(resStr);
			
		return Response.status(Status.OK).entity(resStr).build();
	}
	

}