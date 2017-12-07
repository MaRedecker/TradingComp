package model;

/**
 * Keeps track of the amount and current price of one article in a company's warehouse.
 * @author Max
 *
 */
public class StoredArticle implements StoredArticleInformation {
	
	private double price;
	private int amount;
	private Article article;

	/**
	 * 
	 * @param Name Name of the article
	 * @param ID ID of the article
	 * @param Price Current price of the article
	 * @param Amount Amount stored in the warehouse
	 */
	public StoredArticle(Article article, double Price, int Amount) {
		
		price = Price;
		amount = Amount;
		this.article = article;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public double getSellingPricePerUnit()
	{
		return price;
	}
	
	public void setAmount(int newAmount)
	{
		amount = newAmount;
	}
	
	public void setPrice(double newPrice)
	{
		price = newPrice;
	}

	@Override
	public String getName() {
		
		return article.getName();
	}

	@Override
	public int getID() {
		
		return article.getID();
	}
	
	public Article getArticleObject()
	{
		return article;
	}
	
	public void updateData(StoredArticle storedArticle)
	{
		price = storedArticle.getSellingPricePerUnit();
		article = storedArticle.getArticleObject();
		amount = storedArticle.getAmount();
	}
}
