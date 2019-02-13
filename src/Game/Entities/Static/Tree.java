package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class Tree extends StaticBase {
	//////
	private Rectangle tree;
	//////
	public Tree(Handler handler,int xPosition,int yPosition ) {
		super(handler);
		this.setX(xPosition); 
        this.setY(yPosition);
	}

	@Override
	public void render(Graphics g) {
		/////
		g.drawImage(Images.object[2], this.getX(), this.getY(), 64, 64, null);
     tree = new Rectangle(this.getX()+1, this.getY()+1, 63, 63);
     ////
	}
	
    @Override
    public Rectangle GetCollision() {
    	
    	return tree;
    }
}
