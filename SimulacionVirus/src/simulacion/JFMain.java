package simulacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFMain extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Body[] bodies;
	private JPDraw jpDraw;
	private JPConfig jpConfig;
	private JPanel jpStat;
	JLabel jlSick;
	JLabel jlHealty;
	JPanel jpInfo;
	ActionListener start;
	
	public JFMain(Body[] bodies,ActionListener generate, ActionListener pause, ActionListener random,ActionListener start) throws HeadlessException {
		super();
		this.start = start;
		this.setBodies(bodies);
		this.setJpDraw(new JPDraw(bodies));
		jpConfig = new JPConfig(this);
		initComponents(generate, pause, random);
		setVisible(true);
	}

	private void initComponents(ActionListener generate, ActionListener pause, ActionListener random) {
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().add(jpConfig,BorderLayout.CENTER);
		
		jpStat = new JPanel();
		jpStat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		jpStat.setLayout(new BoxLayout(jpStat, BoxLayout.LINE_AXIS));
		jlSick=new JLabel("Contagiados:         ");
		jlSick.setAlignmentX(CENTER_ALIGNMENT);
		jpStat.add(jlSick);
		jlHealty = new JLabel("Sanos: ");
		jlHealty.setAlignmentX(CENTER_ALIGNMENT);
		jpStat.add(jlHealty);
		jpStat.setAlignmentX(CENTER_ALIGNMENT);
		
		jpInfo = new JPanel();
		jpInfo.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
//		jpInfo.setLayout(new BoxLayout(jpInfo, BoxLayout.X_AXIS));
		
		JButton jbReset = new JButton("Reset");
		jbReset.addActionListener(generate);
		jpInfo.add(jbReset);
		
		JButton jbPause = new JButton("Pausa");
		jbPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jbPause.getText().equals("Pausa")) jbPause.setText("Resumen");
				else jbPause.setText("Pausa");
			}
		});
		jbPause.addActionListener(pause);
		jpInfo.add(jbPause);
		
		JButton jbGenerate = new JButton("Generar");
		jbGenerate.addActionListener(random);
		jpInfo.add(jbGenerate);
		
		JButton jbConfig = new JButton("Configuracion");
		jbConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(jpDraw);
				remove(jpInfo);
				remove(jpStat);
				setSize(600, 400);
				getContentPane().add(jpConfig,BorderLayout.CENTER);
				setLocationRelativeTo(null);
			}
		});
		jpInfo.add(jbConfig);

		jpInfo.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	public void update() {
		getJpDraw().repaint();
	}

	public JPDraw getJpDraw() {
		return jpDraw;
	}

	public void setJpDraw(JPDraw jpDraw) {
		this.jpDraw = jpDraw;
	}
	
	public void updateData(int sanos,int contagiados) {
		jlHealty.setText("Sanos: "+sanos);
		jlSick.setText("Contagiados: "+contagiados+"\t");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.remove(jpConfig);
		setSize(700, 700);
		setLocationRelativeTo(null);
		Body.PixelsToSpread=jpConfig.getDist();
		Body.Population=jpConfig.getAforo();
		Body.SpreadProbability=jpConfig.getProb();
		Body.SIZE=jpConfig.getSizeV();
		start.actionPerformed(null);
		getContentPane().add(jpDraw,BorderLayout.CENTER);
		getContentPane().add(jpInfo,BorderLayout.SOUTH);
		getContentPane().add(jpStat,BorderLayout.NORTH);
		repaint();
		revalidate();
	}

	public Body[] getBodies() {
		return bodies;
	}

	public void setBodies(Body[] bodies) {
		this.bodies = bodies;
		if(jpDraw != null) jpDraw.bodies=bodies;
	}
}
