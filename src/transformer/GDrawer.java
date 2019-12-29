package transformer;

import java.awt.Graphics2D;

public class GDrawer extends GTransformer {

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().setOrigin(x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.getgShape().setPoint(x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
		
	}

	@Override
	public void continueTransforming(Graphics2D g2D, int x, int y) {
		this.getgShape().addPoint(x, y);
	}
}
