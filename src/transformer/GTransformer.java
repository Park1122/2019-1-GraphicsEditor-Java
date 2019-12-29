package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Vector;

import shape.GShape;

public abstract class GTransformer {

	protected GShape gShape;
	protected Vector<GShape> gShapes;
	protected Point2D previous , center;
	
	public GTransformer() {
		this.setgShape(null);
		this.previous = new Point2D.Double();
		this.center = new Point2D.Double(); 
	}
	
	public GShape getgShape() { return gShape; }
	public void setgShape(GShape gShape) { this.gShape = gShape; }
	
	public Vector<GShape> getgShapes() {
		return gShapes;
	}

	public void setgShapes(Vector<GShape> gShapes) {
		this.gShapes = gShapes;
	}

	abstract public void initTransforming(Graphics2D graphics2d, int x, int y);
	abstract public void keepTransforming(Graphics2D graphics2d, int x, int y);
	abstract public void finishTransforming(Graphics2D graphics2d, int x, int y);

	public void continueTransforming(Graphics2D g2d, int x, int y) {
	}

}
