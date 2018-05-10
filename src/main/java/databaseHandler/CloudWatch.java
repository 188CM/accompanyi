package databaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.google.gson.Gson;

import objects.GrafanaResObject;
import objects.Grafana_Series;

public class CloudWatch {

	String AWS_ACCESS_KEY_ID = "**";
	String AWS_SECRET_ACCESS_KEY = "**";
	String AWS_REGION = "**";
	String AWS_InstanceId = "**";
	
	

	
	
	private static AmazonCloudWatch cloudWatch;
	
	public CloudWatch(){
		AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);
		cloudWatch = AmazonCloudWatchClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(AWS_REGION)
                .build();	
	}
	
	public Object select(String msg, Date startDate, Date endDate)
	{
		 GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
	        		.withDimensions(new Dimension().withName("InstanceId").withValue(AWS_InstanceId))
	        		.withMetricName(msg)//parms.getUrl()
	        		.withStatistics("Minimum","Maximum", "Sum", "Average")
	                .withNamespace("AWS/EC2")
	                .withPeriod(60 * 5)//5분
	                .withStartTime(startDate)
	                .withEndTime(endDate);
		 
		 GetMetricStatisticsResult getMetricStatisticsResult = null;
		 try {
		        getMetricStatisticsResult = cloudWatch.getMetricStatistics(request);

		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}

		 	
		 	GrafanaResObject resObj = new GrafanaResObject();
		 	resObj.setRefId("A");
		 	
		 	
	
        	Grafana_Series series_minimum = new Grafana_Series();
        	series_minimum.setName(msg+"_minimum");
//        	series_minimum.setInstanceId(AWS_InstanceId);
    
        	Grafana_Series series_maximum= new Grafana_Series();
        	series_maximum.setName(msg+"_maximum");
//        	series_maximum.setInstanceId(AWS_InstanceId);
        	
        	Grafana_Series series_average= new Grafana_Series();
        	series_average.setName(msg+"_average");
//        	series_average.setInstanceId(AWS_InstanceId);
        	
        	Grafana_Series series_sum= new Grafana_Series();
        	series_sum.setName(msg+"_sum");
//        	series_sum.setInstanceId(AWS_InstanceId);
        	
        	
        	List<Datapoint> pointList = getMetricStatisticsResult.getDatapoints();
        	
        	Collections.sort(pointList, (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
        	
	        for (Datapoint aDataPoint : pointList) {	        	
//	        	{"timestamp":"May 9, 2018 2:30:00 AM",
//	        	"sampleCount":5.0,
//	        	"average":0.26783365749840604,
//	        	"sum":1.33916828749203,
//	        	"minimum":0.166666666676368,
//	        	"maximum":0.338983050842525,
//	        	"unit":
//	          	
	        	Date time = aDataPoint.getTimestamp();
	        	System.out.println(time);
	        	series_minimum.setPoints(new Object[] {aDataPoint.getMinimum(),aDataPoint.getTimestamp().getTime()});
	        	series_maximum.setPoints(new Object[] {aDataPoint.getMaximum(),aDataPoint.getTimestamp().getTime()});
	        	series_average.setPoints(new Object[] {aDataPoint.getAverage(),aDataPoint.getTimestamp().getTime()});
	        	series_sum.setPoints(new Object[] {aDataPoint.getSum(),aDataPoint.getTimestamp().getTime()});

	        }

	        
	        
	        
	        resObj.setSeries(series_minimum);
	        resObj.setSeries(series_maximum);
	        resObj.setSeries(series_average);
	        resObj.setSeries(series_sum);
	        


		return new Gson().toJson(resObj.getSeries());
	}
		
	public void parsRet(ArrayList<Object> ret)
	{
		
		
	}
	
	
	
	
	public void getAWS() {
		
		long offsetInMilliseconds = 1000 * 60 * 120;
        GetMetricStatisticsRequest request = new GetMetricStatisticsRequest()
        		.withDimensions(new Dimension().withName("InstanceId").withValue("i-08fa624bdac6f30b0"))
        		.withMetricName("NetworkOut")
        		.withStatistics("Minimum","Maximum", "Sum", "Average", "SampleCount")
                .withNamespace("AWS/EC2")
                .withPeriod(60 * 1)
                .withStartTime(new Date(new Date().getTime() - offsetInMilliseconds))
                .withEndTime(new Date());
        GetMetricStatisticsResult getMetricStatisticsResult = cloudWatch.getMetricStatistics(request);
        
        for (Object aDataPoint : getMetricStatisticsResult.getDatapoints()) {
            Datapoint dp = (Datapoint) aDataPoint;
//            avgCPUUtilization = dp.getAverage();
            System.out.println(getMetricStatisticsResult.getLabel());
            System.out.println(dp);
        }
        


		
		
		
	}
}
