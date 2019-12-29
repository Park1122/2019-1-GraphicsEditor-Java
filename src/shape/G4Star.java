package shape;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class G4Star extends GShape {

	private static final long serialVersionUID = 1L;
	
	public G4Star() {
		super();
		this.shape = new GeneralPath();
	}
	
	public GShape newInstance() {
		return new G4Star();
	}
	
	public void setOrigin(int x, int y) {
		this.shape =  star4(px = x,py = y,0,0);
	}

	public void setPoint(int x, int y) {
		this.shape =  star4(px,py,x-px,y-py);
	}

	public Shape star4(int x, int y, int w, int h) {
		GeneralPath path = new GeneralPath();
		path.moveTo(x+w/2, y);
		path.lineTo(x+w*3/8, y+h*3/8);
		path.lineTo(x, y+h/2);
		path.lineTo(x+w*3/8, y+h*5/8);
		path.lineTo(x+w/2, y+h);
		path.lineTo(x+w*5/8, y+h*5/8);
		path.lineTo(x+w, y+h/2);
		path.lineTo(x+w*5/8, y+h*3/8);
		path.closePath();
		return path;
	}
	
	public void addPoint(int x, int y) {
	}
	
	public void finishMoving(Graphics2D graphics2d, int x, int y) {
	}
}
