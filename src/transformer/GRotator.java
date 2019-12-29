package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;;


public class GRotator extends GTransformer {

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.center.setLocation(
				this.getgShape().getShape().getBounds2D().getCenterX(),
				this.getgShape().getShape().getBounds2D().getCenterY());
		this.previous.setLocation(x, y);
	}

	private double computeRotationAngle(Point2D center, Point2D previous, Double double1 ){
		double startAngle = Math.toDegrees(
				Math.atan2(center.getX() - previous.getX(), center.getY() - previous.getY()));
		double endAngle = Math.toDegrees(
				Math.atan2(center.getX() - double1.getX(), center.getY() - double1.getY()));
		double angle = startAngle - endAngle;
		if(angle < 0) angle += 360;
		return angle;
	}
	
	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		AffineTransform affineTransform = new AffineTransform();
		double rotationAngle = computeRotationAngle(this.center, this.previous, new Point2D.Double(x,y));
		affineTransform.setToRotation(Math.toRadians(rotationAngle),center.getX(),center.getY());
		this.gShape.transformShape(affineTransform);
		
		this.previous.setLocation(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub

	}

}
