package toolbar;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import drawingPanel.GDrawingPanel;

public class GColorButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	private GDrawingPanel drawingPanel;
	
	public GColorButton() {
		
	}

	public void setColor(String selection) {
		Color color = JColorChooser.showDialog(this.drawingPanel, "Color Table", Color.WHITE);
		try {
			if(selection.equals("LineColor")) {
				this.drawingPanel.setLineColor(color);
			} else if(selection.equals("PaintColor")) {
				this.drawingPanel.setFillColor(color);
			}	
		} catch (Exception e) {
		}
	}

	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		
	}
}
