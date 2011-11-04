package pbs;

import jig.engine.hli.*;

public class PBSGame extends ScrollingScreenGame {

    public static int SCREEN_WIDTH = 640;
    public static int SCREEN_HEIGHT = 480;

    public PBSGame(){
	super(SCREEN_WIDTH, SCREEN_HEIGHT, false);
    }

    public static void main(String[] args){

	PBSGame game = new PBSGame();
	game.run();
    }
}