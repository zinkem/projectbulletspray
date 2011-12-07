package pbs;

import pbs.Entity.*;

public class Triggers {

    public static class TimedTrigger extends Entity {
	protected long firetime;
	public TimedTrigger(String imgrsc, long t){
	    super(imgrsc);
	    firetime = t;
	}

	@Override public void fireTrigger(Level l, long deltaMs){
	    if(age > firetime){
		super.fireTrigger(l, deltaMs);
		kill();
	    }
	}
    }

    public static class CollisionTrigger extends Entity {
	public CollisionTrigger(String imgrsc){
	    super(imgrsc);
	}
	@Override public void fireTrigger(Level l, long deltaMs){
	    if(alive == false){
		super.fireTrigger(l, deltaMs);
	    }
	}
    }

    public static class OnscreenTrigger extends Entity {
	public OnscreenTrigger(String imgrsc){
	    super(imgrsc);
	}
	@Override public void fireTrigger(Level l, long deltaMs){
	    //if this position is on screen, fire trigger
	}
    }

}