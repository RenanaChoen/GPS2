package Coords;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;


class MyCoordsTest {
	@Test
	public void testDistance3d() {
		MyCoords m= new MyCoords();
		Point3D gps0= new Point3D(35.209039, 32.103315, 670);
		Point3D gps1= new Point3D(35.205084882805735, 32.1069002806725,650.0);
		assertEquals(493.0523318301344, m.distance3d(gps0, gps1));
	}
}
