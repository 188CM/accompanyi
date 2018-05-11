package models;

import java.time.Instant;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "cpu")
public class Cpu {

	@Column(name = "time")
	private Instant time;

	@Column(name = "user")
	private long user;
	
	@Column(name = "nice")
	private long nice;
	
	@Column(name = "sys")
	private long sys;
	
	@Column(name = "idle")
	private long idle;
	
	@Column(name = "wait")
	private long wait;
	
	@Column(name = "irq")
	private long irq;
	
	@Column(name = "softIrq")
	private long softIrq;
	
	@Column(name = "stolen")
	private long stolen;
	
	@Column(name = "combined")
	private long combined;
	
	@Column(name = "host", tag = true)
	private String host;
}
