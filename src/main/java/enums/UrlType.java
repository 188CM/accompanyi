package enums;

public enum UrlType {
	cpu("cpu"), mem("mem"), net("net");

	public static boolean exists(String msg) {
		for (UrlType type : UrlType.values()) {
			if (type.toString().equals(msg)) {
				return true;
			}
		}
		return false;
	}
	
	final private String urlType;
	UrlType(String urlType) {
		this.urlType = urlType;
	}
	

}
