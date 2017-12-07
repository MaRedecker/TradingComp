package model;

import java.awt.Color;

/**
 * Base class for all companies which want to join the competition. 
 * For starters, take a look at the examples.
 * @author Max
 *
 */

public abstract class Company {
	
	private InventoryInformation inventory;
	private TendingOffersInformation offers;
	
	/**
	 * Will be called each tick.
	 */
	public abstract void update();
	
	/**
	 * Called once at the start of the game.
	 */
	public abstract void start();
	
	/**
	 * Tries to buy an offer with the specified ID. Will only work if the truck is at the store, 
	 * is empty and enough money is available. The buy will be carried out at the end of the tick.
	 * On a successful buy, onBoughtOffer() gets called.
	 * @param ID ID of the desired Offer.
	 */
	protected void buy(int ID)
	{
		inventory.buy(ID);
	}

	/**
	 * Tries to buy an offer. Will only work if the truck is at the store, 
	 * is empty and enough money is available. The buy will be carried out at the end of the tick.
	 * On a successful buy, onBoughtOffer() gets called.
	 * @param offer the desired offer.
	 */
	protected void buy(Offer offer)
	{
		buy(offer.getArticleID());
	}
	
	/**
	 * Lets the truck head to the sales department to sell its inventory.
	 */
	protected void sell()
	{
		this.getTruck().sell();
	}

	/**
	 * 
	 * @return an InventoryInformation object, which holds all information 
	 * about a company warehouse and its truck.
	 */
	public InventoryInformation getInventory() {
		return inventory;
	}

	/**
	 * 
	 * @return a TendingOffersInformation object, which holds all information
	 * about currently tending offers which are available.
	 */
	protected TendingOffersInformation getOfferInformation() {
		return offers;
	}

	/**
	 * Used by the game to update data. Don't call on your own.
	 * @param freshInventory
	 * @param freshOffers
	 */
	public void _setData(InventoryInformation freshInventory, TendingOffersInformation freshOffers)
	{
		inventory = freshInventory;
		offers = freshOffers;
	}
	
	/**
	 * 
	 * @return returns true if company tries to buy, else false.
	 */
	public boolean isBuying()
	{
		return inventory.wantsToBuy();
	}
	
	/**
	 * 
	 * @return a TruckInformation object, which holds all information about the truck.
	 */
	public TruckInformation getTruck()
	{
		return inventory.getTruck();
	}
	
	/**
	 * this method gets called if an offer is succefully bought.
	 */
	public void onBoughtOffer() {
	}
	
	/**
	 * gets called when the truck reached its destination.
	 */
	public void onTruckReachedTarget() {	
	}
	
	/**
	 * sets the color for your company.
	 * @return your color.
	 */
	public Color getColor()
	{
		return Color.WHITE;
	}
	
	/**
	 * sets the name for the company.
	 * @return your company name.
	 */
	public String getName()
	{
		return "Unknown";
	}
	
	
	
	

}
