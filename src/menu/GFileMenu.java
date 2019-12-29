package menu;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import drawingPanel.GDrawingPanel;
import global.Constants.EFileMenu;
import global.Constants.EFileState;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private File directory, file;
	private GDrawingPanel drawingPanel;
	public void associate(GDrawingPanel drawingPanel) { this.drawingPanel = drawingPanel; }
	public GFileMenu(String text) {
		super(text);
		
		this.file = null;
		this.directory = new File("data");
		
		ActionHandler actionHandler = new ActionHandler();
		
		for(EFileMenu eMenuItem: EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText(), new ImageIcon(eMenuItem.getPath()));
			menuItem.setActionCommand(eMenuItem.getMethod());
			menuItem.addActionListener(actionHandler);
			
			if(eMenuItem.getText().equals(EFileMenu.newItem.getText())) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('N', Event.CTRL_MASK));
			} else if(eMenuItem.getText().equals(EFileMenu.openItem.getText())) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
			} else if(eMenuItem.getText().equals(EFileMenu.saveItem.getText())) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
			} else if(eMenuItem.getText().equals(EFileMenu.printItem.getText())) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('P', Event.CTRL_MASK));
			} else if(eMenuItem.getText().equals(EFileMenu.closeItem.getText())) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.CTRL_MASK));
			}
			
			add(menuItem);
			if(eMenuItem.getText().equals(EFileMenu.saveAsItem.getText()))
				this.addSeparator();
			if(eMenuItem.getText().equals(EFileMenu.printItem.getText()))
				this.addSeparator();
		}
		this.setMnemonic(KeyEvent.VK_F);
	}
	
	public void initialize() {
	}
	
	public void nnew() {	
		this.save();
		
		this.file = null;
		this.drawingPanel.setFile(this.file);	
		
		this.drawingPanel.setCurrentShape(null);
		this.drawingPanel.restoreShapeVector(null);
		this.drawingPanel.setUpdated(true);
		this.drawingPanel.getUpdatingLabel().setText(EFileState.nnew.getState());
	}
	
	public void open() {
		this.save();
		
		this.drawingPanel.setCurrentShape(null);
		JFileChooser chooser = new JFileChooser(this.directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data(*.god)", "god");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.directory = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.readObject();
		}
		this.drawingPanel.setFile(this.file);
	}
	
	private void readObject() {
		try {
			ObjectInputStream objectInputStream;
			objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			Object object = objectInputStream.readObject();
			this.drawingPanel.restoreShapeVector(object);
			objectInputStream.close();
			this.drawingPanel.setUpdated(false);
			this.drawingPanel.getUpdatingLabel().setText(EFileState.save.getState());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.drawingPanel.setFile(this.file);
	}
	
	public void save() {
		if (this.drawingPanel.isUpdated()) {
			if (this.file == null) {
				this.saveAs();
			} else {
				this.writeObject();
			}
		}
		this.drawingPanel.setFile(this.file);
	}
	
	public void saveAs() {
		JFileChooser chooser = new JFileChooser(this.directory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Graphics Data(*.god)", "god");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this.drawingPanel);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			this.directory = chooser.getCurrentDirectory();
			this.file = chooser.getSelectedFile();
			this.writeObject();
		}
		this.drawingPanel.setFile(this.file);
	}
	
	private void writeObject() {
		try {
			ObjectOutputStream objectOutputStream;
			objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream(file)));
			objectOutputStream.writeObject(this.drawingPanel.getShapeVector());
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false);
			this.drawingPanel.getUpdatingLabel().setText(EFileState.save.getState());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.drawingPanel.setFile(this.file);
	}
	
	public void print() {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PageFormat pageFormat = new PageFormat();
        printerJob.setPrintable(this.drawingPanel, pageFormat);
        boolean ok = printerJob.printDialog(printRequestAttributeSet);
        if (ok) {
            try {
            	printerJob.print(printRequestAttributeSet);
            } catch (PrinterException ex) {
            }
        }
	}
	
	public void close() {
		this.save();
		System.exit(0);
	}
	
	private void invokeMethod(String name) {
		try {
			this.getClass().getMethod(name).invoke(this);
		} catch (IllegalAccessException | IllegalArgumentException 
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			invokeMethod(event.getActionCommand());
		}
	}
}
