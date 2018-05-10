package objects;

import java.util.ArrayList;

public class GrafanaResObject {
	
	private String refId;
	private ArrayList<Grafana_Series> series = new ArrayList<Grafana_Series>();
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public ArrayList<Grafana_Series> getSeries() {
		return series;
	}
	public void setSeries(Grafana_Series series) {
		this.series.add(series);
	}

	
}
