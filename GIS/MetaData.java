package GIS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Geom.Point3D;

public class MetaData implements Meta_data{
String Time="";
String data="";
	public MetaData(String time,String info)
	{
		this.Time=time;
		this.data=info;
	}
	/**
	 * get the time
	 */
	@Override
	public long getUTC() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date;
		try {
			date = sdf.parse(Time);
			long timeInMillis = date.getTime();
			 return timeInMillis;
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return 0;
	}
	/**
	 * get the orientation
	 */
	@Override
	public Point3D get_Orientation() {
		return null;
	}
	/**
	 * return string of the Metadata
	 */
	@Override
	public String toString() {
		return "Time: " +Time +" information: "+ data;
	}
	}
