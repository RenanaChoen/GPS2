package GIS;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public class Project implements GIS_project{

	Set<GIS_layer> layers = new HashSet<>();
	String time="";
	String data="";
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
	public void setData(String data)
	{
		this.data=data;
	}
	/**
	 * Scans the folder recursively to find files of the CSV type
	 * @param parentDirectory
	 */
	public void parseForCsvFiles(String parentDirectory){
		this.time=this.LocalTime();
		File[] filesInDirectory = new File(parentDirectory).listFiles();
		for(File f : filesInDirectory){
			if(f.isDirectory()){
				parseForCsvFiles(f.getAbsolutePath());
			}
			String filePath = f.getAbsolutePath();
			String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1,filePath.length());
			if("csv".equals(fileExtenstion)){
				System.out.println("CSV file found -> " + filePath);
				// Call the method checkForCobalt(filePath);
				Layer temp=new Layer(filePath,"data");
				layers.add(temp);               
			}
		} 
	}
	@Override
	public boolean add(GIS_layer layer) {
		return layers.add(layer);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> layer) {
		return layers.addAll(layer);
	}

	@Override
	public void clear() {
		layers.clear();
	}

	@Override
	public boolean contains(Object layer) {
		return layers.contains(layer);
	}

	@Override
	public boolean containsAll(Collection<?> layer) {
		return layers.containsAll(layer);
	}

	@Override
	public boolean isEmpty() {
		return layers.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		return layers.iterator();
	}

	@Override
	public boolean remove(Object layer) {
		return layers.remove(layer);
	}

	@Override
	public boolean removeAll(Collection<?> layer) {
		return layers.removeAll(layer);
	}

	@Override
	public boolean retainAll(Collection<?> layer) {
		return layers.retainAll(layer);
	}

	@Override
	public int size() {
		return layers.size();
	}

	@Override
	public Object[] toArray() {
		return layers.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return layers.toArray(a);
	}

	@Override
	public Meta_data get_Meta_data() {
		return new MetaData(time,data);
	}
	/**
	 * print the CSV files
	 */
	@Override
	public String toString() {
		Iterator iterator = layers.iterator();
		String s = "Project:\n";
		while (iterator.hasNext()){
			s += iterator.next().toString();
		}
		return s;
	}
//		test this class
		public static void main(String[] args) {
			Project a=new Project();
			a.parseForCsvFiles("C:\\Users\\User\\Documents\\אוניברסיטת אריאל\\שנה 2, סמסטר א\\מונחה עצמים\\מטלה 2");
			System.out.println(a);
			a.setData("My Test");
			MetaData b=(MetaData) a.get_Meta_data();
			System.out.println(b);
			System.out.println(b.getUTC());
		}	
}
