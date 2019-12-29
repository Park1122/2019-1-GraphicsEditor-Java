package shape;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import shape.GAnchors.EAnchors;

public class GLine extends GShape {

	private static final long serialVersionUID = 1L;
	
	private Line2D line;
	
	public GLine() {
		super();
		this.shape = new Line2D.Double();
		this.line = (Line2D)this.shape;
	}
	
	public GShape newInstance() {
		return new GLine();
	}
	
	public EOnState onShape(int x, int y) {
		if (this.selected) {
			EAnchors eAnchor = this.anchors.onShape(x, y);
			this.selectedAnchors = eAnchor;
			if (eAnchor == EAnchors.RR) {
				return EOnState.eOnRotate;
			} else if (eAnchor == null) {
					if (this.line.getBounds2D().contains(x, y)) {
						return EOnState.eOnShape;
					}
			} else {
				return EOnState.eOnResize;
			}
		} else {
			if (this.shape.contains(x, y)) {
				return EOnState.eOnShape;
			}
		}
		return null;
	}
	
	public void setOrigin(int x, int y) {
		this.px = x;
		this.py = y;
	}

	public void setPoint(int x, int y) {
		this.line.setLine(px, py, x, y);
		
	}
	public void addPoint(int x, int y) {
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {		
	}
}
