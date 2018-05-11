package models;

import java.util.ArrayList;

public class MetricSeries {
	private String target;
	private ArrayList<Number[]> datapoints = new ArrayList<Number[]>();
//	private String instanceId;
	
	
	public String getName() {
		return target;
	}
	public void setName(String target) {
		this.target = target;
	}
	public ArrayList<Number[]> getPoints() {
		return datapoints;
	}
	public void setPoints(Number[] datapoints) {
		this.datapoints.add(datapoints);
	}
//	public String getInstanceId() {
//		return instanceId;
//	}
//	public void setInstanceId(String instanceId) {
//		this.instanceId = instanceId;
//	}
	
	
	
}
