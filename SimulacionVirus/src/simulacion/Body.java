package simulacion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Body {

	public static int PixelsToSpread = 10;
	public static int Population = 50;
	public static double SpreadProbability = 0.01;
	public static int SIZE = 15;
	private PhysicsVector position;
	private PhysicsVector velocity;
	private char state;//S:Suceptible,I:infectado,R:recuperado
	private Color color = Color.RED;
	private int virusTimer = 0;
	private int size;
	private int id;
	private boolean outFlag = false;
	
	public Body(PhysicsVector position, PhysicsVector velocity, int size) {
		super();
		this.position = position;
		this.setVelocity(velocity);
		this.setSize(size);
	}

	public Body(PhysicsVector position, PhysicsVector velocity, int size, char state) {
		super();
		this.position = position;
		this.setVelocity(velocity);
		this.setState(state);
		this.setSize(size);
	}
	
	public static final Body generateBody(Random r) {
		return new Body(new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680), new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5), SIZE,r.nextFloat()<0.1?'I':'S');
	}
	
	public void regenerate(Random r) {
		position = new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680);
		setVelocity(new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5));
		double rand = r.nextFloat();
		setState(rand<0.2?(rand<0.1?'I':'R'):'S');
	}

	public Body() {
	}

	public void draw(Graphics2D g) {
		
		g.setColor(color);
		int x = (int)((position.getX()-(getSize()/2)));
		int y = (int)((position.getY()-(getSize()/2)));
		g.fillOval(x, y, getSize(), getSize());
		
//		String strpos = String.valueOf(getId());
//		g.drawBytes(strpos.getBytes(), 0, strpos.length(), x+size/2, y-15);
	}
	
	public void applySpread(Body body, MainSim mainSim) {
		 if(isIn(body, PixelsToSpread)) {
			 if(state=='S' && body.getState()=='I' && Math.random() < SpreadProbability) {
				 this.setState('I');
				 virusTimer=5000;
				 mainSim.getSick();
			 }
		 }
	}
	
	public void fixOver(Body[] bodies) {
		for (Body body : bodies) {
			if(body!=this && isIn(body,0)) {
				this.position.x+=body.position.x-this.position.x;
			}
		}
	}
	
	public boolean isIn(Body body, int min_dist) {
		double dist = this.position.distance(body.position);
		return dist <= ((this.getSize()/2) + (body.getSize()/2)) + min_dist;
	}
	
	public void update(Body[] bodies,Random r,MainSim mainSim) {
		for (Body body : bodies) {
			if(body!=this) {
				this.applySpread(body, mainSim);
			}
		}
		
		if(virusTimer>1) {
			virusTimer--;
		}else if(virusTimer == 1) {
			setState('R');
			mainSim.recover();
			virusTimer--;
		}
		
//		velocity.add(acceleration);
		position.add(getVelocity());
//		acceleration.decrease(0.8);
		if(!outFlag && r.nextDouble()<0.005) {
			getVelocity().x = (r.nextDouble())-0.5;
			getVelocity().y = (r.nextDouble())-0.5;
		}
//		velocity.decrease();
	}
	
	public boolean checkOutside(Rectangle rect) {
		rect.y=0;
		return !rect.contains(position);
	}
	
	public void repos(PhysicsVector pv) {
		position.setLocation(pv);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PhysicsVector position = (PhysicsVector) this.position.clone();
		PhysicsVector velocity = (PhysicsVector) this.getVelocity().clone();
		char state = this.getState();
		int size = this.getSize();
		return new Body(position, velocity, size, state);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PhysicsVector getVelocity() {
		return velocity;
	}

	public void setVelocity(PhysicsVector velocity) {
		this.velocity=velocity;
	}
	
	public void editVelocity(PhysicsVector velocity) {
		this.velocity.setLocation(velocity);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
		this.color = state=='S'?Color.BLUE:(state=='I'?Color.RED:Color.GRAY);
	}

	@Override
	public String toString() {
		return "Body [position=" + position + ", velocity=" + velocity + ", state="
				+ state + ", color=" + color + ", size=" + size + ", id=" + id + "]";
	}

	public boolean isOutFlag() {
		return outFlag;
	}

	public void setOutFlag(boolean outFlag) {
		this.outFlag = outFlag;
	}

	public void regenerateState(Random r) {
		setState(r.nextFloat()<0.1?'I':'S');
	}
}
