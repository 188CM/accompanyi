package databaseHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

import com.google.gson.Gson;

import objects.parameter;
import objects.cpu;

public class InfluxDb {
    private Properties config = new Properties();
    private InfluxDB influxDB = null;
    private String dbName;
    public InfluxDb() {
    	connect();
    }
    
    public void connect() {
    	
    	Properties configfile = new Properties();
    	try {
			configfile.load(new FileReader("db.config"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	config = subProps("influxdb.", configfile);
    	
    	String url = config.getProperty("url");
		String userName = config.getProperty("username");
		String password = config.getProperty("password");
		dbName = config.getProperty("dbName");
		connectDB(url, userName, password);	
    }
    
    
    
    public String select(parameter parms)
    {
    	
//    	parms.setStartTime("1519620605454");
//    	parms.setEndTime("1519620722986");;
    	
    	String queryStr = String.format("SELECT *"
    			+ " FROM \"autogen\".\"%s\""
    			+ " WHERE time >= %sms and time <= %sms ",
    			parms.getUrl(), parms.getStartTime(), parms.getEndTime() );
    	
   
    	Query query = new Query(queryStr, dbName);
    	QueryResult queryResult = influxDB.query(query);


    	InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
    	List<cpu> cpuList = resultMapper.toPOJO(queryResult, cpu.class);
    	
    	
    	
    	
    	return new Gson().toJson(cpuList);
    }
    
    
    
    public void connectDB(String url, String userName, String password)
	{
		this.influxDB = InfluxDBFactory.connect(url, userName, password);		
	}
    
	public Properties subProps(String prefix, Properties props) {
		final int head = prefix.length();
		Properties sub = new Properties();
		props.stringPropertyNames().stream().filter(k -> k.startsWith(prefix))
				.forEach(k -> sub.setProperty(k.substring(head), props.getProperty(k)));
		return sub;
	}
}
