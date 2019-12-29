package global;

import java.awt.Point;

import shape.G4Star;
import shape.GEllipse;
import shape.GLine;
import shape.GPen;
import shape.GPolygon;
import shape.GRectangle;
import shape.GSelect;
import shape.GShape;

public class Constants {
	
	public enum EMainFrame {
		title("GraphicsEditor"),
		x(200),
		y(100),
		w(1280),
		h(1000),
		;
		
		private String string;
		private int value;
		private EMainFrame(int value) {
			this.value = value;
		}
		private EMainFrame(String string) {
			this.string = string;
		}
		public int getValue() {
			return this.value;
		}
		public String getString() {
			return this.string;
		}
	}
	
	public enum EToolBar {
		select("����", new GSelect(), "resource/Select.jpg", "resource/Select2.jpg", "Select"),
		rectangle("�׸�", new GRectangle(), "resource/Rect.jpg", "resource/Rect2.jpg", "Rectangle"),
		polygon("������", new GPolygon(), "resource/Polygon.jpg", "resource/Polygon2.jpg", "Polygon"),
		line("����", new GLine(), "resource/Line.jpg", "resource/Line2.jpg", "StraightLine"),
		freeline("����", new GPen(), "resource/Pen.jpg", "resource/Pen2.jpg", "FreeLine"),
		ellipse("Ÿ��", new GEllipse(), "resource/Oval.jpg", "resource/Oval2.jpg", "Ellipse"),
		star4("ǥâ", new G4Star(), "resource/4Star.jpg", "resource/4Star2.jpg", "4angleStar")
		;
		private String text;
		private GShape shape;
		private String path1;
		private String path2;
		private String toolTip;
		private EToolBar(String text) {
			this.text = text;
		}
		private EToolBar(String text, GShape shape) {
			this.text = text;
			this.shape = shape;
		}
		private EToolBar(String text, GShape shape, String path1, String path2, String toolTip) {
			this.text = text;
			this.shape = shape;
			this.path1 = path1;
			this.path2 = path2;
			this.toolTip = toolTip;
		}
		public String getText() {
			return this.text;
		}
		public GShape getShape() {
			return this.shape;
		}
		public String getPath1() {
			return this.path1;
		}
		public String getPath2() {
			return this.path2;
		}
		public String getToolTip() {
			return this.toolTip;
		}
	}
	
	public enum EMenu {
		fileMenu("File"),
		editMenu("Edit"),
		;
		private String text;
		private EMenu(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	public enum EFileMenu {
		newItem("���� �����", "nnew", "resource/NewIcon.jpg"),
		openItem("����", "open", "resource/OpenIcon.jpg"),
		saveItem("����", "save", "resource/SaveIcon.jpg"),
		saveAsItem("�ٸ��̸�����", "saveAs", "resource/SaveAsIcon.jpg"),
		printItem("���","print", "resource/PrintIcon.jpg"),
		closeItem("�ݱ�", "close", ""),
		;
		private String text;
		private String method;
		private String path;
		private EFileMenu(String text, String method, String path) {
			this.text = text;
			this.method = method;
			this.path = path;
		}
		public String getText() {
			return this.text;
		}
		public String getMethod() {
			return this.method;
		}
		public String getPath() {
			return this.path;
		}
	}
	public enum EEditMenu {
		undo("�ǵ�����", "undo"),
		redo("�ٽý���", "redo"),
		cut("�߶󳻱�", "cut"),
		copy("�����ϱ�", "copy"),
		paste("�ٿ��ֱ�","paste"),
		group("������", "group"),
		ungroup("������", "ungroup"),
		;
		private String text;
		private String method;
		private EEditMenu(String text, String method) {
			this.text = text;
			this.method = method;
		}
		public String getText() {
			return this.text;
		}
		public String getMethod() {
			return this.method;
		}
	}
	public enum EFileState {
		update("Updated!"),
		save("Saved!"),
		nnew("New!")
		;
		
		private String state;
		private EFileState(String state) {
			this.state = state;
		}
		public String getState() {
			return this.state;
		}
	}
	public enum ECursorList {
		drawCursor("cursor/draw.png", new Point(15, 5), "draw", 0),
		moveCursor("cursor/move.png", new Point(15,15), "move", 1),
		resize1Cursor("cursor/resize1.png", new Point(15,15), "resizeL", 2),
		resize2Cursor("cursor/resize2.png", new Point(15,15), "resizeR", 3),
		resizeLRCursor("cursor/resizelr.png", new Point(15,15), "resizeLR", 4),
		resizeTDCursor("cursor/resizetd.png", new Point(15,15), "resizeTD", 5),
		rotateCursor("cursor/rotate.png", new Point(15,15), "rotate", 6),
		selectCursor("cursor/select.png", new Point(8,1), "select", 7),
		textCursor("cursor/text.png", new Point(15,15), "text", 8),
		;
		
		private String path;
		private Point hotspot;
		private String pName;
		private int index;
		private ECursorList(String path, Point hotspot, String pName, int index) {
			this.path = path;
			this.hotspot = hotspot;
			this.pName = pName;
			this.index = index;
		}
		public String getPath() {
			return this.path;
		}
		public Point getHotspot() {
			return this.hotspot;
		}
		public String getPName() {
			return this.pName;
		}
		public int getIndex() {
			return this.index;
		}
	}
}
