package model;

import java.util.Observable;
import java.util.Observer;


/**
 * Keeps track of some informations about the competition
 * @author Max
 *
 */
public class Competition extends Observable {
	
	private int turns;
	private boolean isRunning;
	private boolean endOfTime;
	private int maxTime;
	
	public Competition(int maxTurns)
	{
		endOfTime = false;
		maxTime = maxTurns;
	}

	public int getTurns() {
		return turns;
	}
	
	public void resetTurns()
	{
		turns = 0;
		endOfTime = false;
	}
	
	public void setMaxTurns(int maxTurns)
	{
		maxTime = maxTurns;
	}
	
	public void turn()
	{
		turns++;
		if (maxTime < turns)
		{
			endOfTime = true;
			this.setRunning(false);
			this.resetTurns();
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
	
	public boolean outOfTime()
	{
		return endOfTime;
	}
	
	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
		this.setChanged();
		this.notifyObservers();
	}	
}
