package simulacion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Body {

	public static final double DRAWING_RELATION = 1f/2f;
	public static final double GRAVITY_CTE = 0.00006674;//(6.674e-11)*800000000f;
	
	private PhysicsVector position;
	private PhysicsVector velocity;
	private PhysicsVector acceleration;
	private char state;//S:Suceptible,I:infectado,R:recuperado
	private Color color = Color.RED;
	private int size;
	
	public Body(PhysicsVector position, PhysicsVector velocity, PhysicsVector acceleration, int size) {
		super();
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.setSize(size);
	}

	public Body(PhysicsVector position, PhysicsVector velocity, PhysicsVector acceleration, int size, char state) {
		super();
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.state = state;
		this.color = state=='S'?Color.BLUE:(state=='I'?Color.RED:Color.GRAY);
		this.setSize(size);
	}
	
	public static final Body generateBody(Random r) {
		return new Body(new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680), new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5), new PhysicsVector(0,0),15,r.nextFloat()<0.1?'I':'S');
	}
	
	public void regenerate(Random r) {
		position = new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680);
		velocity = new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5);
		acceleration = new PhysicsVector(0,0);
	}

	public Body() {
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		int x = (int)((position.getX()-(getSize()/2)));
		int y = (int)((position.getY()-(getSize()/2)));
		g.fillOval(x, y, getSize(), getSize());
		
//		PhysicsVector pos2 = (PhysicsVector) position.clone();
//		PhysicsVector auxVel = (PhysicsVector) velocity.clone();
//		auxVel.scalarMult(4);
//		pos2.add(auxVel);
//		g.setStroke(new BasicStroke(2));
//		g.setColor(Color.BLACK);
//		g.drawLine((int)this.position.x, (int)this.position.y, (int)pos2.x, (int)pos2.y);
		
//		String strpos = String.format("posicion (%.3f,%.3f)",position.x,position.y);
//		g.drawBytes(strpos.getBytes(), 0, strpos.length(), x+size/2, y-15);
//		
//		String strvel = String.format("velocidad (%.3f,%.3f)",velocity.x,velocity.y);
//		g.drawBytes(strvel.getBytes(), 0, strvel.length(), x+size/2, y);
	}
	
	public void applyCollision(Body body) {
		 if(isIn(body)) {
//			 double auxvx = velocity.x;
//			 double auxvy = velocity.y;
//			 velocity.x = body.velocity.x;
//			 velocity.y = body.velocity.y;
//			 body.velocity.x=auxvx;
//			 body.velocity.y=auxvy;
			 
		 }
	}
	
	public void fixOver(Body[] bodies) {
		for (Body body : bodies) {
			if(body!=this && isIn(body)) {
				this.position.x+=body.position.x-this.position.x;
			}
		}
	}
	
	public boolean isIn(Body body) {
		double dist = this.position.distance(body.position);
		return dist <= ((this.getSize()/2) + (body.getSize()/2));
	}
	
	public void update(Body[] bodies,Random r) {
		for (Body body : bodies) {
			if(body!=this) {
				this.applyCollision(body);
			}
		}
		
//		velocity.add(acceleration);
		position.add(velocity);
//		acceleration.decrease(0.8);
		if(r.nextDouble()<0.01) {
			velocity.x = (r.nextDouble()*0.5)-0.25;
			velocity.y = (r.nextDouble()*0.5)-0.25;
		}
//		velocity.decrease();
	}
	
	public boolean checkOutside(Rectangle rect) {
		return (position.x<rect.x && position.y<rect.y) || (position.x>(rect.x+rect.width) && position.y>(rect.y+rect.height));
	}
	
	public void repos(PhysicsVector pv) {
		position.setLocation(pv);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PhysicsVector position = (PhysicsVector) this.position.clone();
		PhysicsVector velocity = (PhysicsVector) this.velocity.clone();
		PhysicsVector acceleration = (PhysicsVector) this.acceleration.clone();
		char state = this.state;
		int size = this.getSize();
		return new Body(position, velocity, acceleration, size, state);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
