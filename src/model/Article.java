package model;
/**
 * Article which can be bought and sold by an AI.
 * @author Max
 *
 */
public class Article {
	
	private final String name;
	private final int id;
	private final double basePrice;
	
	/**
	 * 
	 * @param Name Name of the article
	 * @param ID ID of the article
	 */
	public Article(String Name, int ID, double BasePrice)
	{
		name = Name;
		id = ID;
		basePrice = BasePrice;
	}
	
	public Article(Article article) {
		this(article.getName(), article.getID(), article.getBasePrice());
	}

	/**
	 * Gets the name of the article
	 * 
	 * @return name of the article
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Gets the ID of the article
	 * @return ID of the article
	 */
	public int getID()
	{
		return id;
	}

	public double getBasePrice() {
		
		return basePrice;
	}
}
