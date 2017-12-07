package model;

import java.util.Observable;


/**
 * Keeps track of some informations about the competition
 * @author Max
 *
 */
public class Competition extends Observable {
	
	private int turns;
	private boolean isRunning;

	public int getTurns() {
		return turns;
	}
	
	public void resetTurns()
	{
		turns = 0;
	}
	
	public void turn()
	{
		turns++;
		this.setChanged();
		this.notifyObservers();
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
	
	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
		this.setChanged();
		this.notifyObservers();
	}
	
}
