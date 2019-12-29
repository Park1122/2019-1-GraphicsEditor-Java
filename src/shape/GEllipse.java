package shape;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GEllipse extends GShape {

	private static final long serialVersionUID = 1L;
	
	private Ellipse2D ellipse;
	
	public GEllipse() {
		super();
		this.shape = new Ellipse2D.Double(0,0,0,0);
		this.ellipse = (Ellipse2D)this.shape;
	}
	
	public GShape newInstance() {
		return new GEllipse();
	}
	
	public void setOrigin(int x, int y) {
		this.ellipse.setFrame(x, y, this.ellipse.getWidth(), this.ellipse.getHeight());
	}

	public void setPoint(int x, int y) {
		double w = x - this.ellipse.getX();
		double h = y - this.ellipse.getY();
		this.ellipse.setFrame(this.ellipse.getX(), this.ellipse.getY(), w, h);
	}

	public void addPoint(int x, int y) {
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	}
}
