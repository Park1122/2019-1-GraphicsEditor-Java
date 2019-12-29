package main;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import drawingPanel.GDrawingPanel;
import global.Constants.ECursorList;
import global.Constants.EMainFrame;
import menu.GMenuBar;
import menu.GPopUpMenu;
import toolbar.GToolBar;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	// components
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;
	private GPopUpMenu popUpMenu;
	
	private FrameHandler frameHandler;
	private static final Toolkit toolKit = Toolkit.getDefaultToolkit();
	public static final Cursor drawCursor = toolKit.createCustomCursor(
			toolKit.getImage(
					ECursorList.drawCursor.getPath()),
					ECursorList.drawCursor.getHotspot(),
					ECursorList.drawCursor.getPName());

	public GMainFrame() {
		// attribute
		this.setTitle(EMainFrame.title.getString());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(EMainFrame.x.getValue(), EMainFrame.y.getValue(), EMainFrame.w.getValue(),
				EMainFrame.h.getValue());
		this.setLocationRelativeTo(null);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("resource/TitleBarIcon.png"));
		
		this.setLayout(new BorderLayout());
		
		this.frameHandler = new FrameHandler();
		this.addWindowListener(this.frameHandler);
		
		// components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);

		this.drawingPanel = new GDrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		
		this.popUpMenu = new GPopUpMenu();
		
		this.setCursor(drawCursor);
	}

	public void initialize() {
		this.menuBar.associate(this.drawingPanel);
		this.toolBar.associate(this.drawingPanel);
		this.popUpMenu.associate(this.drawingPanel);
		this.drawingPanel.associate(this.popUpMenu);
				
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}
	
	private class FrameHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			menuBar.closeSaving();
		}
	}
}
