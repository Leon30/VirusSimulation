package simulacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;

public class JPDraw extends DoubleBuffer{

	private static final long serialVersionUID = 1L;

	Body[] bodies;

	public JPDraw(Body[] bodies) {
		super();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.bodies = bodies;
	}
	
	@Override
	public void paintBuffer(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		g2d.setBackground(Color.WHITE);
		g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		for (Body body : bodies) {
			body.draw(g2d);
		}
	}
}
