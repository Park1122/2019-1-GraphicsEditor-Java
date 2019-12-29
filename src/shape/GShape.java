package shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import shape.GAnchors.EAnchors;

public abstract class GShape implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public enum EOnState {
		eOnShape, eOnResize, eOnRotate
	};

	protected Shape shape;
	protected int px;
	protected int py;
	private float a;
	private Color fillColor;
	private Color lineColor;
	protected GAnchors anchors;
	protected EAnchors selectedAnchors;
	public EAnchors getSelectedAnchors() { return selectedAnchors; }

	protected boolean selected;
	public boolean isSelected() { return selected; }
	public void setSelected(boolean selected) {
		this.selected = selected;
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());
		}
	}
	public boolean getSelected() {
		return this.selected;
	}

	public GAnchors getAnchors() { return anchors; }
	public void setAnchors(GAnchors anchors) { this.anchors = anchors; }

	public Shape getShape() { return this.shape; }

	public GShape() {
		this.selected = false;
		this.anchors = new GAnchors();
		this.lineColor = Color.BLACK;
		this.fillColor = null;
		
		this.a = 2.0f;
	}

	abstract public void setOrigin(int x, int y);

	abstract public void setPoint(int x, int y);

	abstract public void addPoint(int x, int y);
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	public Color getFillColor() { return this.fillColor; }
	public Color getLineColor() { return this.lineColor; }
	
	public void transformShape(AffineTransform affineTransform) {
		this.shape = affineTransform.createTransformedShape(this.shape);
	}
	public void setStroke(float stroke) {
		this.a = stroke;
	}
	// Moving
	public void initMoving(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());
			this.anchors.draw(graphics2d);
		}
	}
	public void keepMoving(Graphics2D graphics2d, int x, int y) {
		int dw = x - this.px;
		int dh = y - this.py;

		AffineTransform aff = new AffineTransform();
		aff.translate(dw, dh);
		this.shape = aff.createTransformedShape(this.shape);

		this.px = x;
		this.py = y;
	}
	abstract public void finishMoving(Graphics2D graphics2d, int x, int y);
	
	//Resizing
	public double getWidth() { return this.shape.getBounds2D().getWidth(); }
	public double getHeight() { return this.shape.getBounds2D().getHeight(); }	
	public Point2D getResizeOrigin(EAnchors eSelectedAnchor) {
		Point2D p = new Point2D.Double();
		GAnchors anchors = this.getAnchors();
		switch (eSelectedAnchor) {
		case EE:
			p.setLocation(anchors.getCenterX(EAnchors.WW), 0);
			break;
		case WW:
			p.setLocation(anchors.getCenterX(EAnchors.EE), 0);
			break;
		case SS:
			p.setLocation(0, anchors.getCenterY(EAnchors.NN));
			break;
		case NN:
			p.setLocation(0, anchors.getCenterY(EAnchors.SS));
			break;
		case SE:
			p.setLocation(anchors.getCenterX(EAnchors.NW), anchors.getCenterY(EAnchors.NW));
			break;
		case NE:
			p.setLocation(anchors.getCenterX(EAnchors.SW), anchors.getCenterY(EAnchors.SW));
			break;
		case SW:
			p.setLocation(anchors.getCenterX(EAnchors.NE), anchors.getCenterY(EAnchors.NE));
			break;
		case NW:
			p.setLocation(anchors.getCenterX(EAnchors.SE), anchors.getCenterY(EAnchors.SE));
			break;
		default:
			break;
		}
		return p;
	}
	public void resize(Point2D resizeFactor, Point2D resizeOrigin, EAnchors eSelectedAnchor) {
		AffineTransform aff = new AffineTransform();
		aff.setToTranslation(resizeOrigin.getX(), resizeOrigin.getY());
		aff.scale(resizeFactor.getX(), resizeFactor.getY());
		aff.translate(-resizeOrigin.getX(), -resizeOrigin.getY());
		this.transformShape(aff);	
	}

	public GShape clone() {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this);
			
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return (GShape)objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract GShape newInstance();

	public void draw(Graphics2D graphics2d) {
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke savedStroke = graphics2d.getStroke();
		Stroke stroke = new BasicStroke(this.a);
		graphics2d.setStroke(stroke);
		
		if (this.shape != null) {
			if(this.fillColor != null){
				graphics2d.setColor(this.fillColor);
				graphics2d.fill(this.shape);
			}
			graphics2d.setColor(this.lineColor);
			graphics2d.draw(this.shape);
		}
		if (this.selected) {
			this.anchors.setBoundingRect(this.shape.getBounds());
			this.anchors.draw(graphics2d);
		}
		graphics2d.setStroke(savedStroke);
	}
	
	public EOnState onShape(int x, int y) {
		if (this.selected) {
			EAnchors eAnchor = this.anchors.onShape(x, y);
			this.selectedAnchors = eAnchor;
			if (eAnchor == EAnchors.RR) {
				return EOnState.eOnRotate;
			} else if (eAnchor == null) {
					if (this.shape.contains(x, y)) {
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
	
	public void modifyLocation() {
		AffineTransform aff = new AffineTransform();
		aff.setToTranslation(10, 10);
		this.shape = aff.createTransformedShape(this.shape);
	}
}
