package model;

import java.util.List;
/**
 * Allows access to important information about the warehouse and the truck.
 * @author Max
 *
 */
public interface InventoryInformation {
		
	/**
	 * 
	 * @return a list of all stored articles of the company
	 */
	public List<StoredArticleInformation> getAllArticles();
	
	
	/**
	 * 
	 * @param ID ID of the desired stored article
	 * @return informations about the desired stored article
	 */
	public StoredArticleInformation getArticleByID(int ID);
	
	/**
	 * 
	 * @param name of a stored article
	 * @return informations about the desired stored article
	 */
	public StoredArticleInformation getArticleByName(String name);
	
	
	/**
	 * Buys an offer if the empty truck is at the market. 
	 * @param ID ID of the offer which was chosen to be bought.
	 */
	public void buy(int ID);
	
	/**
	 * Buys an offer at the end of a tick, if the truck is at the market and has space.
	 * @param offer offer which was chosen to be bought.
	 */
	public void buy(OfferInformation offer);
	
	/**
	 * 
	 * @return True if this company is already buying, else false.
	 */
	public boolean wantsToBuy();
	
	/**
	 * 
	 * @return the current available money of the company.
	 */
	public double getMoney();
	
	/**
	 * 
	 * @return the Truck which is used to move articles.
	 */
	public Truck getTruck();
	
	/**
	 * 
	 * @return total amount of goods in the warehouse
	 */
	public int getAmountOfStoredArticles();

}
