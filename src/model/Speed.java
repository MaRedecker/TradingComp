package model;

/**
 * Different speeds which can be chosen for the competition.
 * @author Max
 *
 */

public enum Speed {
	
	NORMAL(100),
	
	FAST(50),
	
	VERYFAST(10),
	
	SLOW(200),
	
	VERYSLOW(500);
	
	private final int sleepTime;
	
    private Speed(final int sleepTime) {
    	
        this.sleepTime = sleepTime;
    }
    
    public int getSpeed()
    {
    	return sleepTime;
    }

}
