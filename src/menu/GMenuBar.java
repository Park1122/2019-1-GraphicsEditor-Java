package menu;
import javax.swing.JMenuBar;

import drawingPanel.GDrawingPanel;
import global.Constants.EMenu;
import main.GMainFrame;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	//components
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	
	//associations
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public GMenuBar() {
		// initialize attributes
		
		// create components
		this.fileMenu = new GFileMenu(EMenu.fileMenu.getText());
		this.add(this.fileMenu);
		
		this.editMenu = new GEditMenu(EMenu.editMenu.getText());
		this.add(this.editMenu);
		this.setCursor(GMainFrame.drawCursor);
	}

	public void initialize() {
		// associate
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		
		// initialize components
		this.fileMenu.initialize();
		this.editMenu.initialize();		
	}

	public void closeSaving() {
		this.fileMenu.close();
	}
}
