package toolbar;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import drawingPanel.GDrawingPanel;
import global.Constants.EToolBar;
import main.GMainFrame;

public class GToolBar extends JToolBar {
	
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private Vector<JToggleButton> buttons;
	private GColorButton lineColorBtn;
	private GColorButton paintColorBtn;
	private JButton eraserBtn;
	private JSlider strokeSlider;
	
	//associations
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.lineColorBtn.associate(this.drawingPanel);
		this.paintColorBtn.associate(this.drawingPanel);
	}
	
	public GDrawingPanel getDrawingPanel() { return drawingPanel; }

	public GToolBar() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		this.buttons = new Vector<JToggleButton>();
		ActionHandler actionHandler = new ActionHandler();
		for(EToolBar eToolBar: EToolBar.values()) {
			JToggleButton button = new JToggleButton();
			button.setPreferredSize(new Dimension(45, 45));
			button.setActionCommand(eToolBar.name());
			button.addActionListener(actionHandler);
			
			ImageIcon origin1 = new ImageIcon(eToolBar.getPath1());
			Image origin2 = origin1.getImage();
			Image changeImg1 = origin2.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
			ImageIcon icon1 = new ImageIcon(changeImg1);
			
			ImageIcon origin3 = new ImageIcon(eToolBar.getPath2());
			Image origin4 = origin3.getImage();
			Image changeImg2 = origin4.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
			ImageIcon icon2 = new ImageIcon(changeImg2);
			
			button.setIcon(icon1);
			button.setSelectedIcon(icon2);
			button.setBorderPainted(false);
			button.setContentAreaFilled(false);
			button.setToolTipText(eToolBar.getToolTip());
			this.buttons.add(button);
			this.add(button);
			buttonGroup.add(button);
		}
		
		ToolBarHandler toolBarHandler = new ToolBarHandler();
		
		this.lineColorBtn = new GColorButton();
		this.lineColorBtn.addActionListener(toolBarHandler);
		this.lineColorBtn.setActionCommand("LineColor");
		this.lineColorBtn.setPreferredSize(new Dimension(45, 45));
		
		ImageIcon origin1 = new ImageIcon("resource/LineColor.jpg");
		Image origin2 = origin1.getImage();
		Image changeImg1 = origin2.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		ImageIcon icon1 = new ImageIcon(changeImg1);
		
		this.lineColorBtn.setIcon(icon1);
		this.lineColorBtn.setBorderPainted(false);
		this.lineColorBtn.setContentAreaFilled(false);
		this.lineColorBtn.setToolTipText("ColorChooser");
		this.add(this.lineColorBtn);
		buttonGroup.add(this.lineColorBtn);
		
		
		this.paintColorBtn = new GColorButton();
		this.paintColorBtn.addActionListener(toolBarHandler);
		this.paintColorBtn.setActionCommand("PaintColor");
		this.paintColorBtn.setPreferredSize(new Dimension(45, 45));
		
		ImageIcon origin3 = new ImageIcon("resource/PaintColor.jpg");
		Image origin4 = origin3.getImage();
		Image changeImg2 = origin4.getScaledInstance(44, 44, Image.SCALE_SMOOTH);
		ImageIcon icon2 = new ImageIcon(changeImg2);
		
		this.paintColorBtn.setIcon(icon2);
		this.paintColorBtn.setBorderPainted(false);
		this.paintColorBtn.setContentAreaFilled(false);
		this.paintColorBtn.setToolTipText("ColorChooser");
		this.add(this.paintColorBtn);
		buttonGroup.add(this.paintColorBtn);
		
		
		this.eraserBtn = new JButton();
		this.eraserBtn.addActionListener(toolBarHandler);
		this.eraserBtn.setActionCommand("Erase");
		this.eraserBtn.setPreferredSize(new Dimension(45, 45));
		
		ImageIcon origin5 = new ImageIcon("resource/Eraser.jpg");
		Image origin6 = origin5.getImage();
		Image changeImg3 = origin6.getScaledInstance(44, 44, Image.SCALE_SMOOTH);
		ImageIcon icon3 = new ImageIcon(changeImg3);
		
		this.eraserBtn.setIcon(icon3);
		this.eraserBtn.setBorderPainted(false);
		this.eraserBtn.setContentAreaFilled(false);
		this.eraserBtn.setToolTipText("Eraser");
		this.add(this.eraserBtn);
		buttonGroup.add(this.eraserBtn);
		
		StrokeHandler strokeHandler = new StrokeHandler();
		
		this.strokeSlider = new JSlider(1,31,1);
		this.strokeSlider.setMajorTickSpacing(5);
		this.strokeSlider.setPaintLabels(true);
		this.strokeSlider.addChangeListener(strokeHandler);
		
		this.add(this.strokeSlider);
		
		this.setCursor(GMainFrame.drawCursor);
	}
	
	public void initialize() {
		this.buttons.get(EToolBar.rectangle.ordinal()).doClick();
	}
	
	public void erase() {
		this.drawingPanel.erase();
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			drawingPanel.setCurrentTool(EToolBar.valueOf(event.getActionCommand()));
		}
	}
	private class ToolBarHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals("LineColor")) {
				lineColorBtn.setColor("LineColor");
			} else if(event.getActionCommand().equals("PaintColor")) {
				paintColorBtn.setColor("PaintColor");
			} else if(event.getActionCommand().equals("Erase")) {
				erase();
			}
		}
	}
	
	private class StrokeHandler implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider selectedSlide = (JSlider) e.getSource();
			float a = selectedSlide.getValue();
			
			drawingPanel.setStroke(a);
		}
		
	}
}
