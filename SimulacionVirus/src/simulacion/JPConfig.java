package simulacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JPConfig extends JPanel{

	private static final long serialVersionUID = 1L;
	JButton jbRun;
	GridBagConstraints gbc;

	public JPConfig(ActionListener listener) {
		gbc=new GridBagConstraints();
		initComps(listener);
	}


	private void initComps(ActionListener listener) {
		this.setLayout(new GridBagLayout());
		this.add(new JLabel("<html><h1>Configuración</h1></html>"),gbc);
		jbRun = new JButton("Correr simulación");
		jbRun.addActionListener(listener);
		gbc.gridy=1;
		this.add(jbRun,gbc);
	}
}
