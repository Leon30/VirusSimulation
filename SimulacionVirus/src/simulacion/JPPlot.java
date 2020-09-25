package simulacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class JPPlot extends DoubleBuffer{

	private static final long serialVersionUID = 1L;

	MainSim mainSim;
	int t=0;
	int[][] paintMem;
	
	public JPPlot(MainSim mainSim) {
		this.mainSim=mainSim;
		setPreferredSize(new Dimension(400, 100));
	}
	
	@Override
	public void paintBuffer(Graphics g) {
		super.paintBuffer(g);
		if(paintMem==null) { paintMem = new int[getWidth()][3];}
		Graphics2D g2d=(Graphics2D) g;
		g2d.drawRect(0, 0, getWidth(), getHeight());
		int i = 0;
		for (int[] is : paintMem) {
			g2d.setColor(Color.GRAY);
			g2d.drawLine(i, 0, i, is[0]);
			
			int aux = is[0];
			g2d.setColor(Color.WHITE);
			g2d.drawLine(i, aux, i, is[1]+aux);
			
			aux += is[1];
			g2d.setColor(Color.RED);
			g2d.drawLine(i, aux, i, is[2]+aux);
			i++;
		}
		
		if(paintMem[t]==null) paintMem[t]=new int[3];
		g2d.setColor(Color.GRAY);
		int rscale = mainSim.countRecov*(getHeight()/Body.Population);
		g2d.drawLine(t, 0, t, rscale);
		paintMem[t][0]=rscale;
		
		int aux = rscale;
		g2d.setColor(Color.WHITE);
		int sscale = mainSim.countSusep*(getHeight()/Body.Population);
		g2d.drawLine(t, aux, t, sscale+aux);
		paintMem[t][1]=sscale;
		
		aux += sscale;
		g2d.setColor(Color.RED);
		int iscale = mainSim.countInfected*(getHeight()/Body.Population);
		g2d.drawLine(t, aux, t, iscale+aux);
		paintMem[t][2]=iscale;
		
		t++;
		if(t>=getWidth()) {
			t=0;
		}
	}
	
	public void reset() {
		t=0;
	}
}
