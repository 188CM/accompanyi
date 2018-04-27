package objects;

public class parameter {
	long startTime;
	long endTime;
	String Url;
	
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}

	public boolean isEmpry() {
		// TODO Auto-generated method stub
		if (startTime <= 0 || endTime <= 0) {
			return true;
		}	
		return false;
	}

	
	
}
