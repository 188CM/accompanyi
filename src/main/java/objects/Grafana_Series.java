package objects;

import java.util.ArrayList;

public class Grafana_Series {
	private String target;
	private ArrayList<Object[]> datapoints = new ArrayList<Object[]>();
//	private String instanceId;
	
	
	public String getName() {
		return target;
	}
	public void setName(String target) {
		this.target = target;
	}
	public ArrayList<Object[]> getPoints() {
		return datapoints;
	}
	public void setPoints(Object[] datapoints) {
		this.datapoints.add(datapoints);
	}
//	public String getInstanceId() {
//		return instanceId;
//	}
//	public void setInstanceId(String instanceId) {
//		this.instanceId = instanceId;
//	}
	
	
	
}
