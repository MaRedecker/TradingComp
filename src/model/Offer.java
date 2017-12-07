package model;

/**
 * Stores information about one offer.
 * @author Max
 *
 */

public class Offer implements OfferInformation {
	
	private double price;
	private double startPrice;
	private int amount;
	private Article article;
	private int ID;
	private int startTime;
	
	public Offer(double Price, int Amount, Article article_to_sell, int newID, int starttime)
	{
		price = Price;
		startPrice = Price;
		amount = Amount;
		article = article_to_sell;
		ID = newID;
		startTime = starttime;
	}
	
	public Offer(Offer offer)
	{
		this(offer.getPricePerUnit(), offer.getAmount(),
				offer.getArticle(), offer.getOfferID(),
				offer.getStartTime());
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public Article getArticle()
	{
		return article;
	}
	
	public String getArticleName()
	{
		return article.getName();
	}
	
	public int getArticleID()
	{
		return article.getID();
	}
	
	public double getPricePerUnit()
	{
		return price;
	}
	
	public double getFullPrice()
	{
		return price * amount;
	}
	
	public double averagePrice()
	{
		return amount / price;
	}
	
	public void setPrice(double newPrice)
	{
		price = newPrice;
	}
	
	public int getOfferID()
	{
		return ID;
	}
	
	public int getStartTime()
	{
		return startTime;
	}
	
	public double getStartPrice()
	{
		return startPrice;
	}
	
	public void updatePrice(int Tick, double lowestPriceFactor, int tickUntilDecrease)
	{
		if (price * lowestPriceFactor > startPrice)
			price =  Math.floor((startPrice / 100 * (100 - ((Tick - startTime) / tickUntilDecrease)))* 100) / 100;
	}
	
	
	public void updateData(Offer offer)
	{
		price = offer.getPricePerUnit();
		amount = offer.getAmount();
		article = offer.getArticle();
		ID = offer.getOfferID();
		startTime = offer.getStartTime();
		startPrice = offer.getStartPrice();
	}

}
