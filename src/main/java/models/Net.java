package models;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "net")
public class Net {

	@Column(name = "time")
	private Instant time;

	@Column(name = "totalRx")
    long totalRx;
    
	@Column(name = "totalTx")
    long totalTx;
	
	@Column(name = "host", tag = true)
    String host;
}
