package enums;

public enum MetricType {

	cpu("CPUUtilization"), netOut("NetworkOut"), netIn("NetworkIn");

	public static boolean exists(String msg) {
		for (MetricType type : MetricType.values()) {
			if (type.metricType.equals(msg)) {
				return true;
			}
		}
		return false;
	}
	
	final private String metricType;
	MetricType(String metricType) {
		this.metricType = metricType;
	}
	

}
