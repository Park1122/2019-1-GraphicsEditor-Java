package transformer;

import java.awt.Graphics2D;

public class GMover extends GTransformer {

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
//		if(this.getgShapes()!=null)
			this.getgShape().initMoving(graphics2d,x, y);
//		else
//			for(GShape gshape:this.getgShapes())
//				gshape.initMoving(graphics2d,x, y);
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
//		if(this.getgShapes()!=null)
			this.getgShape().keepMoving(graphics2d,x, y);
//		else
//			for(GShape gshape:this.getgShapes())
//				gshape.keepMoving(graphics2d,x, y);
	}

	@Override
	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
//		if(this.getgShapes()!=null)
			this.getgShape().finishMoving(graphics2d,x, y);
//		else
//			for(GShape gshape:this.getgShapes())
//				gshape.finishMoving(graphics2d,x, y);

	}

}
