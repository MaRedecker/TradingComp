package model;

/**
 * Allows access to important information about an offer. 
 * @author Max
 *
 */
public interface OfferInformation {
	
	/**
	 * 
	 * @return the article object which is for sale.
	 */
	public Article getArticle();
	
	/**
	 * 
	 * @return the buying price of this offer.
	 */
	public double getFullPrice();
	
	
	/**
	 * 
	 * @return the price of one article.
	 */
	public double getPricePerUnit();
	
	/**
	 * 
	 * @return total amount of sold articles in this offer.
	 */
	public int getAmount();

	/**
	 * 
	 * @return the unique offer ID.
	 */
	public int getOfferID();

}
