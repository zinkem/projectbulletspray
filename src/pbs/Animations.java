package pbs;

import pbs.Entity.*;

public class Animations {

    public static class Animation implements CustomAnimation {
	long frameLength;
	long thisFrame;

	public Animation(long f){
	    frameLength = f;
	}
	
	public void animate(Entity e, long deltaMs){
	    thisFrame += deltaMs;
	    if(thisFrame > frameLength){
		thisFrame -= frameLength;
		e.incrementFrame(1);
	    }
	}

    }

    public static class AnimateOnce extends Animation {
	int framecount;

	public AnimateOnce(long f){
	    super(f);
	    framecount = 0;
	}
	
	public void animate(Entity e, long deltaMs){
	    super.animate(e, deltaMs);
	    if(framecount >= e.getFrameCount()){
		e.kill();
	    }
	}
	
    }


}