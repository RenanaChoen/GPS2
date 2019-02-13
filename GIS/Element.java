package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class Element implements GIS_element {
	private String MAC , SSID , AuthMode , time ;
	private Point3D p;
	/**
	 * Constructor of Element
	 * @param s
	 */
	public Element () {
		
	}
	public Element (String s)
	{
		String[] temp=s.split(",");
		this.MAC=temp[0];
		this.SSID=temp[1];
		this.AuthMode=temp[2];
		this.time=temp[3];
		this.p=new Point3D(Double.parseDouble(temp[7]),Double.parseDouble(temp[6]),Double.parseDouble(temp[8]));
	}
	public String getAuthMode() {
		return AuthMode;
	}
	public String getSSID() {
		return SSID;
	}
	public String getTime() {
		return time;
	}
	public String getMAC() {
		return MAC;
	}
	public Point3D getPoint3D() {
		return p;
	}
	public void setPoint3D(Point3D p1) {
		this.p = p1;
	}
	public void setSSID(String sSID) {
		this.SSID = sSID;
	}
	public void settime(String t) {
		this.time = t;
	}
	public void setMAC(String mAC) {
		this.MAC = mAC;
	}
	public void setAuthMode(String authMode) {
		this.AuthMode = authMode;
	}
	/**
	 * Constructor of Element
	 * @param p
	 * @param MAC
	 * @param SSID
	 * @param AuthMode
	 */
	public Element(Point3D p,String MAC ,String SSID ,String AuthMode) {
		setMAC(MAC);
		setSSID(SSID);
		setAuthMode(AuthMode);
		setPoint3D(p);
	}

	@Override
	public Geom_element getGeom() {
		return p;
	}

	@Override
	public Meta_data getData() {
		return new MetaData(time,this.toString());
	}

	@Override
	public void translate(Point3D vec) {
		MyCoords m=new MyCoords();
		Point3D p=m.add(this.p,vec );
		this.p=new Point3D(p);
	}
	/**
	 * print the elements in CSV file
	 */
	public String toString() {
		String s=MAC+","+ SSID+","+AuthMode+","+time+","+p.toString();
		return s;
	}

	//	test this class
		public static void main(String[] args) {
			Element a=new Element ("40:65:a3:35:4c:c4,Efrat,[WPA-PSK-CCMP+TKIP][WPA2-PSK-CCMP+TKIP][ESS],01/12/2017 10:49,1,-75,32.17218268216534,34.81446401702757,13.65040888895076,6,WIFI");
			System.out.println(a);
			MetaData b=(MetaData) a.getData();
			System.out.println(b);
			System.out.println(b.getUTC());	
		}
}
