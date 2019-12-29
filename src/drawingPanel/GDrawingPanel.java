package drawingPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import global.Constants.ECursorList;
import global.Constants.EFileState;
import global.Constants.EToolBar;
import menu.GPopUpMenu;
import shape.GPolygon;
import shape.GSelect;
import shape.GShape;
import shape.GShape.EOnState;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GTransformer;

public class GDrawingPanel extends JPanel implements Printable {

	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private GPopUpMenu popUpMenu;
	private MouseHandler mouseHandler;
	private PopupHandler popupHandler;
	private Clipboard clipboard;
	private static final Toolkit toolKit = Toolkit.getDefaultToolkit();
	private static final Vector<Cursor> cursorList = new Vector<Cursor>();
	
	private Vector<GShape> shapeVector;
//	private Vector<GShape> temp;
	static {
		for(ECursorList cursor: ECursorList.values()) {
			Image image = toolKit.getImage(cursor.getPath());
			cursorList.add(toolKit.createCustomCursor(image, cursor.getHotspot(), cursor.getPName()));
		}
	}
	
	public Vector<GShape> getShapeVector() {
		return shapeVector;
	}
	@SuppressWarnings("unchecked")
	public void restoreShapeVector(Object shapeVector) {
		if(shapeVector == null) {
			this.shapeVector.clear();
		} else {
			this.shapeVector = (Vector<GShape>) shapeVector;
		}
		repaint();
	}
	
	// working variables
	private enum EActionState {eReady, eTransforming, eNTransforming};
	private EActionState eActionState;
	
	private File file;
	public void setFile(File file) {
		this.file = file;
		if(this.file == null) {
			this.fileName.setText("力格绝澜 - ");
		} else {
			this.fileName.setText(file.getName() + " - ");
		}
		
	}
	private boolean updated;
	public boolean isUpdated() { return this.updated; }
	public void setUpdated(boolean updated) { this.updated = updated; }
	
	private GShape currentShape;
	private GTransformer transformer;
	private JLabel coordinate;
	private JLabel updatingLabel;
	private JLabel fileName;
	public JLabel getUpdatingLabel() {
		return this.updatingLabel;
	}
	private GShape currentTool;
	public void setCurrentTool(EToolBar currentTool) {
		this.currentTool = currentTool.getShape();
	}

	public void setCurrentShape(GShape currentShape) {
		for (GShape shape : this.shapeVector) {
			if (shape != null) {
				shape.setSelected(false);
			}
		}
		
		this.currentShape = currentShape;
		
		if (this.currentShape != null) {
			this.currentShape.setSelected(true);
		}
		
		this.repaint();
	}
	public GDrawingPanel() {
		this.setLayout(new BorderLayout());
		this.eActionState = EActionState.eReady;
		this.updated = false;
		
		this.setForeground(Color.BLACK);
		this.setBackground(Color.WHITE);
		
		this.mouseHandler = new MouseHandler();
		this.popupHandler = new PopupHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseListener(this.popupHandler);
		this.addMouseMotionListener(this.mouseHandler);
		
		this.clipboard = new Clipboard();
		
		this.shapeVector = new Vector<GShape>();
		this.transformer = null;
		
		this.file = null;
		
		this.coordinate = new JLabel("   [ 0, 0 ]");
		this.coordinate.setFont(this.coordinate.getFont().deriveFont(17.0f));
		
		this.updatingLabel = new JLabel(EFileState.nnew.getState());
		this.updatingLabel.setFont(this.updatingLabel.getFont().deriveFont(17.0f));
		
		this.fileName = new JLabel("力格绝澜 - ");
		this.fileName.setFont(this.updatingLabel.getFont().deriveFont(17.0f));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		panel.add(this.coordinate, c);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,20,0,0);
		panel.add(this.fileName, c);
		this.add(panel, BorderLayout.BEFORE_FIRST_LINE);
		
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,5,0,0);
		panel.add(this.updatingLabel, c);
		this.add(panel, BorderLayout.BEFORE_FIRST_LINE);
	}

	public void initialize() {
		this.currentShape = null;
	}
	public void associate(GPopUpMenu popUpMenu) {
		this.popUpMenu = popUpMenu;
		
	}
	public void paint(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		super.paint(graphics2d);
		
		for (GShape shape : this.shapeVector) {
			shape.draw(graphics2d);
		}
		if (this.currentShape != null) {
			this.currentShape.draw(graphics2d);
		}	
	}

	public void setFillColor(Color color) {
		for (GShape shape : this.shapeVector) {
			if (shape.isSelected()) {
				if (shape != null) {
					shape.setFillColor(color);
				}
			}
		}
		this.repaint();
	}
	
	public void setLineColor(Color color) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		for (GShape shape : this.shapeVector) {
			if (shape.isSelected()) {
				if (shape != null) {
					shape.setLineColor(color);
				}
				shape.draw(g2D);
			}
		}
		this.repaint();
	}
	
	private EOnState onShape(int x, int y) {
		this.currentShape = null;
		for (GShape shape : this.shapeVector) {
			EOnState eOnState = shape.onShape(x, y);
			if (eOnState != null) {
				this.setCurrentShape(shape);
				return eOnState;
			}
		}
		return null;
	}
	private EOnState cursorShape(int x, int y) {
//		this.currentShape = null;
		for (GShape shape : this.shapeVector) {
			EOnState eOnState = shape.onShape(x, y);
			if (eOnState != null) {
				return eOnState;
			}
		}
		return null;
	}

	private void defineActionState(int x, int y) {
		EOnState eOnState = onShape(x, y);
		if (eOnState == null) {
			this.transformer = new GDrawer();
		} else {
				switch (eOnState) {
				case eOnShape:
					this.transformer = new GMover();
					break;
				case eOnResize:
					this.transformer = new GResizer();
					break;
				case eOnRotate:
					this.transformer = new GRotator();
					break;
				default:
					// exception
					this.eActionState = null;
					break;
				}
		}
	}

	private void initTransforming(int x, int y) {
		if (this.transformer instanceof GDrawer) {
			this.setCurrentShape(this.currentTool.newInstance());
		}
		this.transformer.setgShape(this.currentShape);
//		if(this.temp!=null)this.transformer.setgShapes(temp);
		this.transformer.initTransforming((Graphics2D) this.getGraphics(), x, y);
	}

	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		this.transformer.keepTransforming(graphics2d, x, y);
		this.repaint();
	}

	private void finishTransforming(int x, int y) {
		this.transformer.finishTransforming((Graphics2D) this.getGraphics(), x, y);
		if (this.transformer instanceof GDrawer) {
			if(this.currentShape instanceof GSelect){
				((GSelect)(this.currentShape)).contains(this.shapeVector);
				this.currentShape = null;
//				this.temp = ((GSelect)(this.currentShape)).getContainedShapes();
			} else {
				this.shapeVector.add(this.currentShape);
			}
		}
		this.updated = true;
		this.updatingLabel.setText(EFileState.update.getState());
		this.repaint();
	}
	private void  continueTransforming(int x, int y) {
		if (this.transformer instanceof GDrawer) {
			Graphics2D g2D = (Graphics2D) this.getGraphics();
			this.transformer.continueTransforming(g2D, x, y);			
		}	
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		// https://stackoverflow.com/questions/30308208/java-using-printable-print-method
		if (pageIndex > 0) {
			return NO_SUCH_PAGE;
		}
		
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		for (GShape shape: this.shapeVector) {
			shape.draw((Graphics2D)graphics);
		}

		return PAGE_EXISTS;
	}
	
	public void cut() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for(int i=this.shapeVector.size()-1; i>=0; i--) {
			if(this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
				this.shapeVector.remove(i);
				this.currentShape = null;
			}
		}
		this.clipboard.setContents(selectedShapes);
		this.updated = true;
		this.updatingLabel.setText(EFileState.update.getState());
		this.repaint();
	}
	public void copy() {
		Vector<GShape> selectedShapes = new Vector<GShape>();
		for(int i=this.shapeVector.size()-1; i>=0; i--) {
			if(this.shapeVector.get(i).isSelected()) {
				selectedShapes.add(this.shapeVector.get(i));
			}
		}
		this.clipboard.setContents(selectedShapes);
		this.updated = true;
		this.updatingLabel.setText(EFileState.update.getState());
		this.repaint();
	}
	public void paste() {
		Vector<GShape> shapes = this.clipboard.getContents();
//		this.shapeVector.addAll(shapes);
		for(GShape shape: shapes) {
			shape.modifyLocation();
			this.shapeVector.add(shape);
		}
		this.clipboard.setContents(shapes);
		this.setCurrentShape(shapes.get(0));
		this.updated = true;
		this.updatingLabel.setText(EFileState.update.getState());
		this.repaint();
	}
	
	public void erase() {
		for(int i=this.shapeVector.size()-1; i>=0; i--) {
			if(this.shapeVector.get(i).isSelected()) {
				this.currentShape = null;
				this.shapeVector.remove(i);
			}
		}
		this.updated = true;
		this.updatingLabel.setText(EFileState.update.getState());
		this.repaint();
	}
	
	public void setStroke(float a) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		Stroke savedStroke = g2D.getStroke();
		
		for (GShape shape : this.shapeVector) {
			if (shape.isSelected()) {
				if (shape != null) {
					shape.setStroke(a);
				}
				shape.draw(g2D);
			}
		}
		g2D.setStroke(savedStroke);
		this.repaint();
	}
	
	public void changeCursor(int x, int y) {
		EOnState eOnState = cursorShape(x, y);
		if (this.currentTool instanceof GSelect) {
			setCursor(cursorList.get(ECursorList.selectCursor.getIndex()));
		} else {
			if (currentShape != null) {
				shape.GAnchors.EAnchors eAnchoring = currentShape.getAnchors().onShape(x, y);
				if (eAnchoring == null) {
					if (eOnState == EOnState.eOnShape) {
						setCursor(cursorList.get(ECursorList.moveCursor.getIndex()));
					}else setCursor(cursorList.get(ECursorList.drawCursor.getIndex()));
				} else {
					switch (eAnchoring) {
					case NW:
					case SE: setCursor(cursorList.get(ECursorList.resize1Cursor.getIndex()));	break;
					case NN:
					case SS: setCursor(cursorList.get(ECursorList.resizeTDCursor.getIndex()));	break;
					case NE:
					case SW: setCursor(cursorList.get(ECursorList.resize2Cursor.getIndex()));	break;
					case EE:
					case WW: setCursor(cursorList.get(ECursorList.resizeLRCursor.getIndex()));	break;
					case RR: setCursor(cursorList.get(ECursorList.rotateCursor.getIndex()));	break;
					default: break;
					}
				}
			} else {
				setCursor(cursorList.get(ECursorList.drawCursor.getIndex()));
			}
		}
	}
	
	private class MouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}
		public void mouse2Clicked(MouseEvent e) {
			if (eActionState == EActionState.eNTransforming) {
				if (currentShape instanceof GPolygon) {
					finishTransforming(e.getX(), e.getY());
					eActionState = EActionState.eReady;
				}
			}
		}
		public void mouse1Clicked(MouseEvent e) {
			if (eActionState == EActionState.eReady) {
				if ((currentShape instanceof GPolygon)&&(transformer instanceof GDrawer)) {
					initTransforming(e.getX(), e.getY());
					eActionState = EActionState.eNTransforming;
				}
			} else if (eActionState == EActionState.eNTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			coordinate.setText("   [ " + e.getX() + " , " + e.getY() + " ]");
			if (eActionState == EActionState.eReady) {
				changeCursor(e.getX(), e.getY());
			} else if (eActionState == EActionState.eNTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if (eActionState == EActionState.eReady) {
					defineActionState(e.getX(), e.getY());
					if(!((currentShape instanceof GPolygon)&&(transformer instanceof GDrawer))) {
						initTransforming(e.getX(), e.getY());
						eActionState = EActionState.eTransforming;
					}
				}
			}	
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (eActionState == EActionState.eTransforming) {
				finishTransforming(e.getX(), e.getY());
				eActionState = EActionState.eReady;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			coordinate.setText("   [ " + e.getX() + " , " + e.getY() + " ]");

			if (eActionState == EActionState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	
	private class PopupHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    public void mouseReleased(MouseEvent e) {
	        if (e.isPopupTrigger())
	            doPop(e);
	    }

	    private void doPop(MouseEvent e) {
	        popUpMenu.show(e.getComponent(), e.getX(), e.getY());
	    }
	}
}
