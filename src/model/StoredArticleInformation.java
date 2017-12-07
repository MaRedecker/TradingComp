package model;

/**
 * Allows access to important information about one stored article in a company's warehouse.
 * @author Max
 *
 */
public interface StoredArticleInformation {
	
	/**
	 * 
	 * @return current price of the article. 
	 */
	public double getSellingPricePerUnit();
	
	/**
	 * 
	 * @return amount of articles of this type which are stored.
	 */
	public int getAmount();
	
	
	/**
	 * 
	 * @return name of this stored article.
	 */
	public String getName();
	
	/**
	 * 
	 * @return ID of this article
	 */
	public int getID();
	
	/**
	 * 
	 * @return the object of the article.
	 */
	public Article getArticleObject();

}
