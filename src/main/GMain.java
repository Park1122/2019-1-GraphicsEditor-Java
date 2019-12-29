package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.mcwin.McWinLookAndFeel;

public class GMain {
	static private GMainFrame mainFrame;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new McWinLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		mainFrame = new GMainFrame();
		mainFrame.initialize();
		mainFrame.setVisible(true);
	}
}
