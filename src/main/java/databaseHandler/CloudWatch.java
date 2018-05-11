package databaseHandler;

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

public class CloudWatch {

	String AWS_ACCESS_KEY_ID = "*";
	String AWS_SECRET_ACCESS_KEY = "*";
	String AWS_REGION = "*";
	String AWS_InstanceId = "*";
	

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
		 
     	List<Datapoint> pointList = getMetricStatisticsResult.getDatapoints();	
     	Collections.sort(pointList, (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
	        
        return pointList;
	}
}
