package objects;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "mem")
public class Mem {

	@Column(name = "time")
	private Instant time;

	@Column(name = "total")
	long total;
	
	@Column(name = "free")
	long free;
	
	@Column(name = "used")
	long used;
	
	@Column(name = "ram")
	long ram;
	
	@Column(name = "actualFree")
	long actualFree;
	
	@Column(name = "actualUsed")
	long actualUsed;
	
	@Column(name = "usedPercent")
	long usedPercent;
	
	@Column(name = "freePercent")
	long freePercent;
	
	@Column(name = "host", tag = true)
	String host;
}
