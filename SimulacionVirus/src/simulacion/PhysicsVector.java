package simulacion;

import java.awt.geom.Point2D;

public class PhysicsVector extends Point2D.Double{

	private static final long serialVersionUID = 1L;

	public PhysicsVector() {
		super();
	}

	public PhysicsVector(double arg0, double arg1) {
		super(arg0, arg1);
	}
	
	public void add(PhysicsVector otherVector) {
		this.x += otherVector.x;
		this.y += otherVector.y;
	}

	public void scalarMult(double d) {
		double x = this.getX() * d;
		double y = this.getY() * d;
		this.setLocation(x, y);
	}
	
	public PhysicsVector between(PhysicsVector otherVector) {
		double x = this.x-otherVector.x;
		double y = this.y-otherVector.y;
		return new PhysicsVector(x,y);
	}
	
	public PhysicsVector unit() {
		double m = this.distance(new Point2D.Double(0,0));
		double x = this.x / m;
		double y = this.y / m;
		return new PhysicsVector(x,y);
	}
	
	/**
	 * 
	 */
	public void decrease(double factor) {
		this.x=this.x*factor;
		this.y=this.y*factor;
	}
	
	public double getMag() {
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
