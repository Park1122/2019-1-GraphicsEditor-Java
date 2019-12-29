package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;

public class GSelect extends GRectangle {

	private static final long serialVersionUID = 1L;
	
	private Vector<GShape> containedShapes;
	
	
	public GSelect() {
		this.containedShapes = new Vector<GShape>();
	}
	
	public Vector<GShape> getContainedShapes() {
		return containedShapes;
	}

	public GShape newInstance() {
		return new GSelect();
	}
	
	public void draw(Graphics2D graphics2d) {
		if (this.shape != null) {
			Stroke savedStroke = graphics2d.getStroke();
			Stroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
											new float[] {2.0f,5.0f},0.0f);
			graphics2d.setColor(Color.BLACK);
			graphics2d.setStroke(stroke);
			graphics2d.draw(this.shape);
			graphics2d.setStroke(savedStroke);
		}
	}
	
	public void contains(Vector<GShape> shapeVector) {
		for(GShape shape: shapeVector) {
			if(this.getRange().contains(shape.getShape().getBounds())) {
				this.containedShapes.add(shape);
				shape.setSelected(true);
			}
		}
	}
	public void finish() {
		this.shape = null;
	}
}
