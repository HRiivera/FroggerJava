package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class Grass extends StaticBase {
	//////
	private Rectangle grass;
	//////
	public Grass(Handler handler,int xPosition,int yPosition ) {
		super(handler);
		this.setX(xPosition); 
        this.setY(yPosition);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.object[1], this.getX(), this.getY(), 64, 64, null);
	}
	
    @Override
    public Rectangle GetCollision() {
    	
    	return grass;
    }
}
