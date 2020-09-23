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

public class JFMain extends JFrame{

	private static final long serialVersionUID = 1L;
	Body[] bodies;
	private JPDraw jpDraw;
	
	public JFMain(Body[] bodies,ActionListener generate, ActionListener pause, ActionListener random) throws HeadlessException {
		super();
		this.bodies = bodies;
		this.setJpDraw(new JPDraw(bodies));
		initComponents(generate, pause, random);
		setVisible(true);
	}

	private void initComponents(ActionListener generate, ActionListener pause, ActionListener random) {
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().add(getJpDraw(),BorderLayout.CENTER);
		
		JPanel jpInfo = new JPanel();
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

		jpInfo.setAlignmentX(CENTER_ALIGNMENT);
		getContentPane().add(jpInfo,BorderLayout.SOUTH);
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
}
