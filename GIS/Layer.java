package GIS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import Geom.Point3D;


public class Layer implements GIS_layer{

	Set<GIS_element> elements = new HashSet<>();
	String time="";
	String data="";
	private MetaDataClass metaData;
	/**
	 * Constructor of Layer
	 */
	public Layer() {
		elements = new LinkedHashSet<GIS_element>();
		metaData = new MetaDataClass();
	}
	/**
	 * Returns the date and time in real time
	 * @return
	 */
	public String LocalTime()
	{
		//		"dd/MM/yyyy HH:mm"
		String s="";
		Date dateCreated = new Date();
		Calendar calendar = new GregorianCalendar();
		s=dateCreated.getDate()+"/"+(dateCreated.getMonth()+1)+"/"+
				calendar.get(calendar.YEAR)+" "+dateCreated.getHours()
				+":"+dateCreated.getMinutes();
		return s;
	}
	/**
	 * Constructor of Layer that read file CSV
	 * @param csv
	 * @param information
	 */
	public Layer (String csv,String information)
	{
		this.data=information;
		this.time=this.LocalTime();
		String line = "";
		String cvsSplitBy = ",";
		Layer layer = new Layer();
		try (BufferedReader br = new BufferedReader(new FileReader(csv))) 
		{
			line=br.readLine();
			line=br.readLine();
			while ((line = br.readLine()) != null) 
			{
				Element element =new Element(line);
				elements.add(element);
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	@Override
	public boolean add(GIS_element element) {
		return elements.add(element);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> element) {
		return elements.addAll(element);
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public boolean contains(Object element) {
		return elements.contains(element);
	}

	@Override
	public boolean containsAll(Collection<?> element) {
		return elements.containsAll(element);
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public Iterator<GIS_element> iterator() {
		return elements.iterator();
	}

	@Override
	public boolean remove(Object element) {
		return elements.remove(element);
	}

	@Override
	public boolean removeAll(Collection<?> element) {
		return elements.removeAll(element);
	}

	@Override
	public boolean retainAll(Collection<?> element) {
		return elements.retainAll(element);
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] element) {
		return elements.toArray(element);
	}

	@Override
	public Meta_data get_Meta_data() {
		return new MetaData(time,data);
	}

	/**
	 * print the CSV file
	 */
	@Override
	public String toString() {
		Iterator iterElement = this.iterator();
		int count = 1;
		String s = "Layer:\n";
		while (iterElement.hasNext()){
			s += count+" "+iterElement.next().toString()+"\n";
			count++;
		}
		s += "\n";
		return s;
	}
	/**
	 * write file kml
	 * @param output
	 */
	public void writeFileKml(String output) {
		ArrayList<String> contentKml = new ArrayList<String>();

		String startKml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n<Document><Style"
				+ " id=\"red\"><IconStyle><Icon><href>"
				+ "http://maps.google.com/mapfiles/ms/icons/red-dot.png</href>"
				+ "</Icon></IconStyle></Style><Style id=\"yellow\"><IconStyle>"
				+ "<Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
				+ "</href></Icon></IconStyle></Style><Style id=\"green\"><IconStyle>"
				+ "<Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png"
				+ "</href></Icon></IconStyle></Style><Folder><name>Wifi Networks</name>\n";
		contentKml.add(startKml);

		String endKml = "\n</Folder></Document></kml>";
		try{
			FileWriter fw = new FileWriter(output);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator Iterator=elements.iterator();
			while(Iterator.hasNext())
			{
				Element temp = (Element) Iterator.next();

				String elementkml ="<Placemark>\n" +
						"<name><![CDATA["+temp.getMAC()+"]]></name>\n" +
						"<description><![CDATA[BSSID: <b>"+temp.getMAC()+"</b><br/>Capabilities:"
						+ " <b>"+temp.getAuthMode()+"</b><br/>Frequency: <b>"+"</b><br/>Timestamp:"
						+ " <b>+"+"+</b><br/>Date: <b>"+temp.getTime()+"</b>]]></description>\n" +
						"<Point>\n <coordinates>"+temp.getPoint3D().x()+","+temp.getPoint3D().y()+"," + "</coordinates>" +
						"</Point>\n </Placemark>";
				contentKml.add(elementkml);
			}
			contentKml.add(endKml);
			String csv = contentKml.toString().replaceAll("</Placemark>, <Placemark>", "</Placemark><Placemark>").replaceAll("</Placemark>, ", "</Placemark>").replaceAll(", <Placemark>", "<Placemark>");
			csv = csv.substring(1, csv.length()-1);
			bw.write(csv);
			bw.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	//	test this class
		public static void main(String[] args) {
			Layer a=new Layer("C:\\Users\\User\\Documents\\אוניברסיטת אריאל\\שנה 2, סמסטר א\\מונחה עצמים\\מטלה 2\\WigleWifi_20171203085618.csv","test infphkljhjkhk");
			System.out.println(a);
			a.writeFileKml("C:\\Users\\User\\Documents\\אוניברסיטת אריאל\\שנה 2, סמסטר א\\מונחה עצמים\\מטלה 2\\nwe.kml");
			MetaData b=(MetaData) a.get_Meta_data();
			System.out.println(b);
	System.out.println(b.getUTC());
		}
}
