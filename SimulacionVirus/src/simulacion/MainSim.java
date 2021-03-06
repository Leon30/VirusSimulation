package simulacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class MainSim {

	Body[] bodies;
	private Timer timer;
	Body[] initial;
	Timer t;
	Random r = new Random();
	Random r2 = new Random();
	int countSusep=0;;
	int countInfected=0;
	int countRecov=0;
	JFMain jfMain;
	
	public void generate() {
		for (int i = 0; i < bodies.length; i++) {
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
		countSusep = countState('S');
		countInfected = countState('I');
		countRecov = countState('R');
	}
	
	public int countState(char state) {
		int sum = 0;
		for (Body body : bodies) {
			if(body.getState()==state) {
				sum++;
			}
		}
		return sum;
	}
	
	public void fixOverpos() {
		for (Body body : bodies) {
			body.fixOver(bodies);
		}
	}
	
	public MainSim() {
		jfMain = new JFMain(bodies, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jfMain.reset();
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
				jfMain.reset();
				generate();
			}
		},new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				bodies = new Body[Body.Population];
				jfMain.setBodies(bodies);
				generate();
				run();
			}
		}, this);
		MainSim out = this;
		timer = new Timer(0,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Body body : bodies) {
					body.update(bodies, r, out);
//					System.out.println(body);
					if(body.isOutFlag()) {
						if(!body.checkOutside(jfMain.getJpDraw().getBounds())) {
							body.setOutFlag(false);
						}
					}else {
						if(body.checkOutside(jfMain.getJpDraw().getBounds())) {//Cuando sale
							double rand = r2.nextDouble(); 
							if(rand<0.25) {//generate on top
								PhysicsVector pv = new PhysicsVector();
								pv.x = r2.nextDouble()*jfMain.getJpDraw().getWidth();
								pv.y = -body.getSize();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0,0.5));
							}else if(rand<0.5) {//derecha
								PhysicsVector pv = new PhysicsVector();
								pv.x = jfMain.getJpDraw().getWidth()+body.getSize();
								pv.y = r2.nextDouble()*jfMain.getJpDraw().getHeight();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(-0.5,0));
							}else if(rand<0.75) {//izquierda
								PhysicsVector pv = new PhysicsVector();
								pv.x = -body.getSize();
								pv.y = r2.nextDouble()*jfMain.getJpDraw().getHeight();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0.5,0));
							}else {//abajo
								PhysicsVector pv = new PhysicsVector();
								pv.x = r2.nextDouble()*jfMain.getJpDraw().getWidth();
								pv.y = jfMain.getJpDraw().getWidth()+body.getSize();
								body.repos(pv);
								body.editVelocity(new PhysicsVector(0,-0.5));
							}
							body.setOutFlag(true);
							//Hace los cambios en los contadores segun como cambie el estado
							char auxState = body.getState();
							body.regenerateState(r);
							if(auxState != body.getState()) {
								if(body.getState() == 'I') {
									if(auxState == 'S') {
										countSusep--;
									}else {
										countRecov--;
									}
									countInfected++;
								}else if(body.getState() == 'S'){
									if(auxState == 'I') {
										countInfected--;
									}else {
										countRecov--;
									}
									countSusep++;
								}else {
									if(auxState == 'I') {
										countInfected--;
									}else {
										countSusep--;
									}
									countRecov++;
								}
							}
						}
					}
					jfMain.update();
					jfMain.updateData(countSusep, countInfected, countRecov);
				}
			}
		});
	}
	
	public void run() {
		timer.start();
	}
	
	public static void main(String[] args) {
		new MainSim();
		
	}

	public void getSick() {
		countInfected++;
		countSusep--;
	}
	
	public void recover() {
		countRecov++;
		countInfected--;
	}
}
