package shape;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class GPen extends GShape {

	private static final long serialVersionUID = 1L;
		
	public GPen() {
		super();
		this.shape = new Path2D.Double();
	}
	
	public GShape newInstance() {
		return new GPen();
	}
	
	public void setOrigin(int x, int y) {
		((Path2D) this.shape).moveTo(x, y);
	}

	public void setPoint(int x, int y) {
		((Path2D) this.shape).lineTo(x, y);
	}
	
	public void addPoint(int x, int y) {
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	}
}
