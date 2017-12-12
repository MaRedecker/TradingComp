package model;

import java.util.Observable;

/**
 * Settings of the competition.
 * @author Max
 *
 */
public class Settings extends Observable {
	
	private Speed speed;
	
	private boolean paused;
	
	private int maxTicks;
	
	private double lowestPriceFactor;
	
	private int ticksUntilDecreasePrice;
	
	private int ticksUntilNextOffer;
	
	private int ticksUntilDeleteOffer;
	
	private double offersGeneratedPerPlayer;
	
	private double maxArticlePriceIncreaseFactor;
	
	private int turnsUntilMaxPriceIncrease;
	
	public Settings()
	{
		speed = Speed.NORMAL;
		
		paused = false;
		
		maxTicks = 20000;
		
		setLowestPriceFactor(2);
		
		setTicksUntilNextOffer(150);
		
		setTicksUntilDeleteOffer(300);
		
		setTicksUntilDecreasePrice(5);
		
		offersGeneratedPerPlayer = 1;
		
		maxArticlePriceIncreaseFactor = 1.5;
		
		turnsUntilMaxPriceIncrease = 300;
		
		
	}

	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
		this.setChanged();
		this.notifyObservers();
	}

	public boolean isPaused() {
		return paused;
	}

	public void pause() {
		this.paused = true;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void unPause() {
		this.paused = false;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setPause(boolean pause)
	{
		this.paused = pause;
	}

	public int getMaxTicks() {
		return maxTicks;
	}

	public void setMaxTicks(int maxTicks) {
		this.maxTicks = maxTicks;
		this.setChanged();
		this.notifyObservers();
	}

	public double getLowestPriceFactor() {
		return lowestPriceFactor;
	}

	public void setLowestPriceFactor(double lowestPriceFactor) {
		this.lowestPriceFactor = lowestPriceFactor;
	}

	public int getTicksUntilNextOffer() {
		return ticksUntilNextOffer;
	}

	public void setTicksUntilNextOffer(int ticksUntilNextOffer) {
		this.ticksUntilNextOffer = ticksUntilNextOffer;
	}

	public int getTicksUntilDeleteOffer() {
		return ticksUntilDeleteOffer;
	}

	public void setTicksUntilDeleteOffer(int ticksUntilDeleteOffer) {
		this.ticksUntilDeleteOffer = ticksUntilDeleteOffer;
	}

	public int getTicksUntilDecreasePrice() {
		return ticksUntilDecreasePrice;
	}

	public void setTicksUntilDecreasePrice(int ticksUntilDecreasePrice) {
		this.ticksUntilDecreasePrice = ticksUntilDecreasePrice;
	}

	public double getOffersGeneratedPerPlayer() {
		return offersGeneratedPerPlayer;
	}

	public void setOffersGeneratedPerPlayer(double d) {
		this.offersGeneratedPerPlayer = d;
	}

	public double getMaxArticlePriceIncrease() {
		return maxArticlePriceIncreaseFactor;
	}

	public void setMaxArticlePriceIncrease(double maxArticlePriceIncrease) {
		this.maxArticlePriceIncreaseFactor = maxArticlePriceIncrease;
	}

	public int getTurnsUntilMaxPriceIncrease() {
		return turnsUntilMaxPriceIncrease;
	}

	public void setTurnsUntilMaxPriceIncrease(int turnsUntilMaxPriceIncrease) {
		this.turnsUntilMaxPriceIncrease = turnsUntilMaxPriceIncrease;
	}
	
	

}
