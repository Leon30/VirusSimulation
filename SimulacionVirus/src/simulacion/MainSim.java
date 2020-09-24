package simulacion;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.Timer;

public class MainSim {

	Body[] bodies;
	private Timer timer;
	Body[] initial;
	Timer t;
	Random r = new Random();
	Random r2 = new Random();
	
	public void generate() {
		for (int i = 0; i < bodies.length; i++) {//															  r.nextDouble()-0.5,r.nextDouble()-0.5
			bodies[i] = Body.generateBody(r);
			bodies[i].setId(i);
		}
		initial = new Body[bodies.length];
		for (int i = 0; i < bodies.length; i++) {
			Body body = bodies[i];
			try {
				initial[i]=(Body) body.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		//executorService.scheduleAtFixedRate(fixOverpos, 0, 1, TimeUnit.SECONDS);
		t = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fixOverpos();
				t.stop();
			}
		});
		t.start();
		fixOverpos();
	}
	
	public void fixOverpos() {
		for (Body body : bodies) {
			body.fixOver(bodies);
		}
	}
	
	public MainSim() {
		this.bodies = new Body[100];
		generate();
		JFMain jfMain = new JFMain(bodies, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < bodies.length; i++) {
					try {
						bodies[i]=(Body) initial[i].clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}
		},new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) {
					timer.stop();
				}else {
					timer.restart();
				}
			}
		}, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generate();
//				bodies[1] =	new Body(new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680), new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5), new PhysicsVector(0,0),r.nextDouble()*300,Color.GREEN);
//				bodies[2] = new Body(new PhysicsVector(r.nextDouble()*700, r.nextDouble()*680), new PhysicsVector(r.nextDouble()-0.5,r.nextDouble()-0.5), new PhysicsVector(0,0),r.nextDouble()*300,Color.BLUE);
			}
		});
		timer = new Timer(0,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Body body : bodies) {
					body.update(bodies, r);
//					System.out.println(body);
					if(body.isOutFlag()) {
						if(!body.checkOutside(jfMain.getJpDraw().getBounds())) {
							body.setOutFlag(false);
						}
					}else {
						if(body.checkOutside(jfMain.getJpDraw().getBounds())) {//Cuando sale
							double rand = r2.nextDouble(); 
							if(rand<0.25) {//generate on top
								System.out.println("arriba");
								PhysicsVector pv = new PhysicsVector();
								pv.x = r2.nextDouble()*jfMain.getJpDraw().getWidth();
								pv.y = -body.getSize();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0,0.5));
							}else if(rand<0.5) {//derecha
								System.out.println("derecha");
								PhysicsVector pv = new PhysicsVector();
								pv.x = jfMain.getJpDraw().getWidth()+body.getSize();
								pv.y = r2.nextDouble()*jfMain.getJpDraw().getHeight();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(-0.5,0));
							}else if(rand<0.75) {//izquierda
								System.out.println("left");
								PhysicsVector pv = new PhysicsVector();
								pv.x = -body.getSize();
								pv.y = r2.nextDouble()*jfMain.getJpDraw().getHeight();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0.5,0));
							}else {//abajo
								System.out.println("abajo");
								PhysicsVector pv = new PhysicsVector();
								pv.x = r2.nextDouble()*jfMain.getJpDraw().getWidth();
								pv.y = jfMain.getJpDraw().getWidth()+body.getSize();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0,-0.5));
							}
//							body.setInFlag(100);
							body.setOutFlag(true);
							body.regenerate(r);
						}
					}
					jfMain.update();
				}
			}
		});
	}
	
	public void run() {
		timer.start();
	}
	
	public static void main(String[] args) {
		new MainSim().run();
		
	}
}
