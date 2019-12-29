package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shape.GAnchors.EAnchors;

public class GResizer extends GTransformer {

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.previous.setLocation(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		EAnchors eSelectedAnchor = this.getgShape().getSelectedAnchors();
		Point2D resizeOrigin = this.getgShape().getResizeOrigin(eSelectedAnchor);
		Point2D resizeFactor = this.computeResizeFactor(this.previous, new Point2D.Double(x, y));
//		if(this.getgShapes()!=null)
//			for(GShape gshape:this.getgShapes())
//				gshape.resize(resizeFactor, resizeOrigin, eSelectedAnchor);
//		else
			this.getgShape().resize(resizeFactor, resizeOrigin, eSelectedAnchor);
		this.previous.setLocation(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		
	}
	
	public Point2D computeResizeFactor(Point2D previous, Point2D current) {
		double px = previous.getX();
		double py = previous.getY();
		double cx = current.getX();
		double cy = current.getY();
		double width = this.getgShape().getWidth();
		double height = this.getgShape().getHeight();
		double deltaW = 0;
		double deltaH = 0;
		
		switch (this.getgShape().getSelectedAnchors()) {
			case EE:  deltaW =  cx-px; 	deltaH=  0; 	 break;
			case WW:  deltaW =-(cx-px);	deltaH=  0; 	 break;
			case SS:  deltaW =  0;		deltaH=  cy-py;  break;
			case NN:  deltaW =  0;		deltaH=-(cy-py); break;
			case SE: deltaW =  cx-px; 	deltaH=  cy-py;	 break;
			case NE: deltaW =  cx-px; 	deltaH=-(cy-py); break;
			case SW: deltaW =-(cx-px);	deltaH=  cy-py;	 break;
			case NW: deltaW =-(cx-px);	deltaH=-(cy-py); break;
			default: break;
		}
		
		double xFactor = 1.0;
		double yFactor = 1.0;
		if(width > 0.0) {
			xFactor = deltaW / width + xFactor;
		}
		if(height > 0.0) {
			yFactor = deltaH / height + yFactor;
		}	
		return new Point2D.Double(xFactor, yFactor);
	}
}
