package simulacion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.font.NumericShaper;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class JPConfig extends JPanel{

	private static final long serialVersionUID = 1L;
	JButton jbRun;
	GridBagConstraints gbc;
	JSpinner jsAforo;
	JSpinner jsDist;
	JSpinner jsProb;
	JSpinner jsSize;

	public JPConfig(ActionListener listener) {
		gbc=new GridBagConstraints();
		initComps(listener);
	}


	private void initComps(ActionListener listener) {
		this.setLayout(new GridBagLayout());
		
		gbc.gridwidth = 2;
		gbc.anchor=GridBagConstraints.CENTER;
		JLabel jLabel = new JLabel("<html><h1>Configuración</h1></html>");
		jLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(jLabel,gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy=1;
		gbc.ipadx=20;
		gbc.anchor=GridBagConstraints.LINE_START;
		JLabel jLabel2 = new JLabel("<html><h2>Maximo aforo</h2></html>");
		jLabel2.setAlignmentY(CENTER_ALIGNMENT);
		jLabel2.setAlignmentX(LEFT_ALIGNMENT);
		this.add(jLabel2,gbc);
		
		jsAforo = new JSpinner(new SpinnerNumberModel(50, 0, 1000, 1));
		jsAforo.setPreferredSize(new Dimension(150, 25));
		gbc.ipady=3;
		gbc.gridx=1;
		this.add(jsAforo,gbc);
		
		gbc.gridy=2;
		gbc.gridx=0;
		gbc.ipadx=20;
		JLabel jLabel3 = new JLabel("<html><h2>Distancia de riesgo <br>de contagio</h2></html>");
		jLabel3.setAlignmentX(LEFT_ALIGNMENT);
		this.add(jLabel3,gbc);
		
		jsDist = new JSpinner(new SpinnerNumberModel(10, 0, 1000, 1));
		jsDist.setPreferredSize(new Dimension(150, 25));
		gbc.ipady=3;
		gbc.gridx=1;
		this.add(jsDist,gbc);
		
		gbc.gridy=3;
		gbc.gridx=0;
		gbc.ipadx=20;
		JLabel jLabel4 = new JLabel("<html><h2>Probabilidad de contagio</h2></html>");
		jLabel4.setAlignmentX(LEFT_ALIGNMENT);
		this.add(jLabel4,gbc);
		
		jsProb = new JSpinner(new SpinnerNumberModel(0.01, 0, 1, 0.1));
		jsProb.setPreferredSize(new Dimension(150, 25));
		gbc.ipady=3;
		gbc.gridx=1;
		this.add(jsProb,gbc);
		
		gbc.gridy=4;
		gbc.gridx=0;
		gbc.ipadx=20;
		JLabel jLabel5 = new JLabel("<html><h2>Tamaño</h2></html>");
		jLabel5.setAlignmentX(LEFT_ALIGNMENT);
		this.add(jLabel5,gbc);
		
		jsSize = new JSpinner(new SpinnerNumberModel(15, 0, 100, 1));
		jsSize.setPreferredSize(new Dimension(150, 25));
		gbc.ipady=3;
		gbc.gridx=1;
		this.add(jsSize,gbc);
		
		jbRun = new JButton("Correr simulación");
		jbRun.addActionListener(listener);
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth = 2;
		gbc.anchor=GridBagConstraints.CENTER;
		this.add(jbRun,gbc);
	}
	
	public double getProb() {
		return (double) jsProb.getValue();
	}
	
	public int getAforo() {
		return (int) jsAforo.getValue();
	}
	
	public int getDist() {
		return (int) jsDist.getValue();
	}
	
	public int getSizeV() {
		return (int) jsSize.getValue();
	}
}
