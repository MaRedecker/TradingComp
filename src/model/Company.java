package model;

import java.awt.Color;

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
	
	protected void buy(int ID)
	{
		inventory.buy(ID);
	}
	
	protected void buy(Offer offer)
	{
		buy(offer.getArticleID());
	}
	
	protected void sell()
	{
		
	}

	public InventoryInformation getInventory() {
		return inventory;
	}

	protected TendingOffersInformation getOfferInformation() {
		return offers;
	}


	public void _setData(InventoryInformation freshInventory, TendingOffersInformation freshOffers)
	{
		inventory = freshInventory;
		offers = freshOffers;
	}
	
	public boolean isBuying()
	{
		return inventory.wantsToBuy();
	}
	
	public TruckInformation getTruck()
	{
		return inventory.getTruck();
	}
	
	public void onBoughtOffer() {
	}
	
	public void onTruckReachedTarget() {	
	}
	
	public Color getColor()
	{
		return Color.WHITE;
	}
	
	public String getName()
	{
		return "Unknown";
	}
	
	
	
	

}
