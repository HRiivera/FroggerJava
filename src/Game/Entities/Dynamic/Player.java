package Game.Entities.Dynamic;

import Game.Entities.EntityBase;
import Game.Entities.Static.Tree;
import Game.World.WorldManager;
import Main.Handler;
import Resources.Images;
import UI.UIManager;

import javax.swing.JFrame;
import javax.swing.JPanel;



import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/*
 * The Frog.
 */
public class Player extends EntityBase {


	private Handler handler;


	private Rectangle player;
	public String facing = "UP";
	public Boolean moving = false;
	private int moveCoolDown=0;

	private int index =0;

	private int Score=0;
	private int SubScore=0;
	private boolean collided = false;
	private boolean pointCooldown = false;

	public Player(Handler handler) {
		super(handler);
		this.handler = handler;
		this.handler.getEntityManager().getEntityList().add(this);

		player = new Rectangle(); 	// see UpdatePlayerRectangle(Graphics g) for its usage.
	}

	public void tick(){

		if(moving) {
			animateMovement();

		}

		if(!moving){
			move();
			score();
		}

	}

	private void reGrid() {
		if(facing.equals("UP")) {
			if(this.getX() % 64 >= 64 / 2 ) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			}
			else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY()-64);
		}
	}

	private void move(){

		if(moveCoolDown< 25){
			moveCoolDown++;
		}
		index=0;
		if(getX() > 576 && facing.equals("RIGHT")) {
			this.setX(576);
		}
		else if(getX() > 512 && facing.equals("RIGHT") == false) {
			this.setX(512);
		}
		else if(getX() < 64 && facing.equals("RIGHT")) {				
			this.setX(64);
		}
		else if(getX() <0 && facing.equals("RIGHT") == false) {
			this.setX(0);
		}
		////

		/////////////////MOVE UP///////////////
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && facing.equals("UP") && getY() > 64) {
			moving=true;

		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && facing.equals("UP") && getY() <= 0){
			moving=false;
		}

		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && !facing.equals("UP")){
			if(facing.equals("DOWN")) {
				if(this.getX() % 64 >= 64 / 2 ) {

					this.setX(this.getX() + (64 - this.getX() % 64));
				}
				else {
					this.setX(this.getX() - this.getX() % 64);
				}
				setY(getY() + 64);
			}
			if(facing.equals("LEFT")) {
				setY(getY() + 64);
			}
			if(facing.equals("RIGHT")) {
				setX(getX()-64);
				setY(getY()+64);
			}
			facing = "UP";
		}

		/////////////////MOVE LEFT///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving && facing.equals("LEFT") && getX() < 32){
			moving=false;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving && facing.equals("LEFT")){
			moving=true;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving&& !facing.equals("LEFT")){
			if(facing.equals("RIGHT")) {
				setX(getX()-64);
			}
			reGrid();
			facing = "LEFT";
		}
		/////////////////MOVE DOWN///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && facing.equals("DOWN")){
			moving=true;
		}
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && !facing.equals("DOWN")){
			reGrid();
			if(facing.equals("RIGHT")){
				setX(getX()-64);
			}
			facing = "DOWN";
		}

		/////////////////MOVE RIGHT///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving && facing.equals("RIGHT") && getX() > 544){
			moving=false;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving && facing.equals("RIGHT")) {
			moving=true;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving&& !facing.equals("RIGHT")){
			if(facing.equals("LEFT")) {
				setX(getX()+64);
			}
			if(facing.equals("UP")) {
				setX(getX()+64);
				setY(getY()-64);
			}
			if(facing.equals("DOWN")) {
				if(this.getX() % 64 >= 64 / 2 ) {
					this.setX(this.getX() + (64 - this.getX() % 64));
				}
				else {
					this.setX(this.getX() - this.getX() % 64);
				}

				setX(getX()+64);
			}

			facing = "RIGHT";
		}
	}

	private void score() {
		for(int i =0;i<handler.getWorld().getSpawnedHazards().size();i++) {
			if(handler.getWorld().getSpawnedHazards().get(i) instanceof Tree) {
				if (handler.getWorld().getSpawnedHazards().get(i).GetCollision() != null
						&& this.getPlayerCollision().intersects(handler.getWorld().getSpawnedHazards().get(i).GetCollision())
						&& collided==false) {
					collided = true;
					if(facing =="UP") {
					Score -=1;
					SubScore-=1;
					}
					else if(facing == "DOWN"){
						SubScore+=1;
					}
				}
				else if (!moving && collided){
					collided = false;
				}

			}
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && facing == "UP" && moving && pointCooldown == false) {		
				if (Score<=SubScore) {Score+=1;}
				SubScore+=1;
				pointCooldown = true;
			}
			else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && facing == "DOWN" && moving && pointCooldown == false) {
				pointCooldown = true;
				SubScore-=1;
			}
			else if(!moving) {
				pointCooldown = false;
			}



		}







	}



	private void animateMovement(){
		if(index==8) {
			moving = false;
			index = 0;
		}
		moveCoolDown = 0;
		index++;
		switch (facing) {
		case "UP":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() - (8));
			break;

		case "LEFT":
			setX(getX() - (8));
			break;

		case "DOWN":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() + (8));
			break;

		case "RIGHT":
			setX(getX() + (8));
			break;

		}
	}

	public void render(Graphics g){

		if(index>=8){
			index=0;
			moving = false;
		}

		switch (facing) {
		case "UP":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), -1 * getHeight(), null);
			break;
		case "DOWN":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "LEFT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "RIGHT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), -1 * getWidth(), getHeight(), null);
			break;
		}
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g.drawString(String.valueOf(this.Score),handler.getWidth()-75,50);


		UpdatePlayerRectangle(g);

	}

	// Rectangles are what is used as "collisions." 
	// The hazards have Rectangles of their own.
	// This is the Rectangle of the Player.
	// Both come in play inside the WorldManager.
	private void UpdatePlayerRectangle(Graphics g) {

		player = new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());

		if (facing.equals("UP")){
			player = new Rectangle(this.getX(), this.getY() - 64, getWidth(), getHeight());
		}
		else if (facing.equals("RIGHT")) {
			player = new Rectangle(this.getX() - 64, this.getY(), getWidth(), getHeight());
		}
	}

	@SuppressWarnings("SuspiciousNameCombination")
	private static BufferedImage rotateClockwise90(BufferedImage src) {
		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage dest = new BufferedImage(height, width, src.getType());

		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.translate((height - width) / 2, (height - width) / 2);
		graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
		graphics2D.drawRenderedImage(src, null);

		return dest;
	}
	public Rectangle getPlayerCollision() {
		return player;
	}

}
