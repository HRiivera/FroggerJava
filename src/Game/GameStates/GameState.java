package Game.GameStates;

import java.awt.Graphics;

import com.sun.glass.events.KeyEvent;

import Game.Entities.EntityManager;
import Game.Entities.Dynamic.Player;
import Game.World.WorldManager;
import Main.Handler;

/**
 * Created by AlexVR on 7/1/2018.
 */

/*
 * This is the state once the game is Started.
 * The WorldManager Class is constructed.
 */
public class GameState extends State {
	
	private Player player;

    public GameState(Handler handler){
        super(handler);
        handler.setEntityManager(new EntityManager(handler));
        handler.setWorldManager(new WorldManager(handler));

        player = new Player(handler);
    }


    @Override
    public void tick() {
        handler.getWorld().tick();
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }

    }

    @Override
    public void render(Graphics g) {
        handler.getWorld().render(g);

    }

}
