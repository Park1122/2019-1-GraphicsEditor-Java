package shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GRectangle extends GShape {

	private static final long serialVersionUID = 1L;
	
	private Rectangle rectangle;
	
	public GRectangle() {
		super();
		this.shape = new Rectangle();
		this.rectangle = (Rectangle)this.shape;
	}
	
	Rectangle getRange() {
		return this.rectangle;
	}
	
	public GShape newInstance() {
		return new GRectangle();
	}
	
	public void setOrigin(int x, int y) {
		this.rectangle.setBounds(x, y, 0, 0);
	}

	public void setPoint(int x, int y) {
		this.rectangle.setSize(x-this.rectangle.x, y-this.rectangle.y);
	}

	public void addPoint(int x, int y) {
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	}
}
