package Coords;

import Geom.Point3D;

public class MyCoords implements coords_converter  {

	int RWorld= 6371000;
	/**
	 * Converts a point from degrees to meters
	 * @param deg
	 * @return
	 */
	public Point3D deg2meter(Point3D deg) {
		Point3D meter= new Point3D (Math.sin(deg.x()*Math.PI/180)*RWorld,
				Math.sin(deg.y()*Math.PI/180)*RWorld, deg.z());
		return meter;
	}
	/**
	 * Converts a point from meter to degrees
	 * @param meter
	 * @return
	 */
	public Point3D meter2deg(Point3D meter) {
		Point3D deg= new Point3D ((180/Math.PI)*Math.asin(meter.x()/RWorld), 
				(180/Math.PI)*Math.asin(meter.y()/RWorld), meter.z());
		return deg;
	}
	/**
	computes a new point which is the gps point transformed by a 3D vector (in meters)*/
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		MyCoords m= new MyCoords();
		m.deg2meter(gps);
		double new_x= m.deg2meter(gps).x() + local_vector_in_meter.x();
		double new_y= m.deg2meter(gps).y() + local_vector_in_meter.y();
		double new_z= local_vector_in_meter.z() + gps.z();
		Point3D new_p= new Point3D(new_x, new_y,new_z);
		return m.meter2deg(new_p);
	}
	/** computes the 3D distance (in meters) between the two gps like points */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		MyCoords m= new MyCoords();
		double x = (m.deg2meter(gps0).x()-m.deg2meter(gps1).x())*
				(m.deg2meter(gps0).x()-m.deg2meter(gps1).x());
		double y = (m.deg2meter(gps0).y()-m.deg2meter(gps1).y())*
				(m.deg2meter(gps0).y()-m.deg2meter(gps1).y());
		return Math.sqrt(x+y);
	}
	/** computes the 3D vector (in meters) between two gps like points */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		MyCoords m= new MyCoords();
		double x = m.deg2meter(gps0).x()-m.deg2meter(gps1).x();
		double y = m.deg2meter(gps0).y()-m.deg2meter(gps1).y();
		double z = m.deg2meter(gps0).z()-m.deg2meter(gps1).z();
		Point3D vector3D= new Point3D(x, y, z);
		return vector3D;
	}
	/** computes the polar representation of the 3D vector be gps0-->gps1 

	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance*/
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		MyCoords m= new MyCoords();
		double [] a_e_d= new double [3];
		double y = gps1.y()-gps0.y();
		double azimuth = Math.atan2(Math.sin(Math.toRadians(y))*Math.cos(Math.toRadians(gps1.x())), Math.cos(Math.toRadians(gps0.x()))*Math.sin(Math.toRadians(gps1.x()))-Math.sin(Math.toRadians(gps0.x()))*Math.cos(Math.toRadians(gps1.x()))*Math.cos(Math.toRadians(y)));
		azimuth = Math.toDegrees(azimuth);
		while (azimuth<0)
			azimuth=azimuth+360;
		a_e_d [0] = azimuth;
		a_e_d [1] = gps0.z()-gps1.z();
		a_e_d [2] = m.distance3d(gps0, gps1);
		return a_e_d;
	}
	/**

	 * return true iff this point is a valid lon, lat, lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if (p.x()<=180 && p.x()>=-180 &&
				p.y()<=90 && p.y()>=-90
				&& p.z()>=-450)
			return true;
		return false;
	}

}
