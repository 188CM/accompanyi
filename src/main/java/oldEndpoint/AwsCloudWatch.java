package oldEndpoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.google.gson.Gson;

import common.Util;
import databaseHandler.CloudWatch;
import enums.MetricType;
import objects.ChartObject;

@Path("/awsCloudWatch")
public class AwsCloudWatch {
	
	@Context
	private HttpServletRequest httpRequest;

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
		
	
		ArrayList<ChartObject> _Minimum = new ArrayList<ChartObject>();
		
		ArrayList<ChartObject> _Maximum = new ArrayList<ChartObject>();
		
		ArrayList<ChartObject> _Average = new ArrayList<ChartObject>();
		
		ArrayList<ChartObject> _Sum = new ArrayList<ChartObject>();
		
		CloudWatch db = new CloudWatch();
		
		Object ret = db.select(msg, dt1, dt2);
		
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		
        for (Datapoint aDataPoint : (List<Datapoint>)ret) {	        	
////        	{"timestamp":"May 9, 2018 2:30:00 AM", "sampleCount":5.0,
////        	"average":0.26783365749840604,"sum":1.33916828749203,
////        	"minimum":0.166666666676368, "maximum":0.338983050842525
        	_Minimum.add(new ChartObject(aDataPoint.getTimestamp().getTime(), aDataPoint.getMinimum()));
        	_Maximum.add(new ChartObject(aDataPoint.getTimestamp().getTime(), aDataPoint.getMaximum()));
        	_Average.add(new ChartObject(aDataPoint.getTimestamp().getTime(), aDataPoint.getAverage()));
        	_Sum.add(new ChartObject(aDataPoint.getTimestamp().getTime(), aDataPoint.getSum()));
    
        }
		
        ArrayList<ArrayList<ChartObject>> retObj = new ArrayList<ArrayList<ChartObject>>();
        retObj.add(_Minimum);
        retObj.add(_Maximum);
        retObj.add(_Average);
        retObj.add(_Sum);
        
        String tmp = new Gson().toJson(retObj);
        System.out.println(tmp);
        
		return Response.status(Status.OK).entity(tmp).header("Access-Control-Allow-Origin", "*").build();
	}
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String post() {
        return "Netty Test";
    }

}