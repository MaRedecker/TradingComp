package control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Generators.ArticleGenerator;
import model.Article;
import model.Settings;

/**
 * Takes care of all articles in the competition, including calculating their prices. 
 * @author Max
 *
 */
public class ArticleControl {
	
	private List<Article> allArticles;
	private Map<Integer, Double> articleValues;
	private Map<Integer, Integer> articleAvailability;
	private ArticleGenerator articleGenerator;
	private Settings settings;
	
	public ArticleControl(Settings settings)
	{
		this.articleGenerator = new ArticleGenerator();
		this.articleValues = new HashMap<Integer, Double>();
		this.articleAvailability = new HashMap<Integer, Integer>();
		this.settings = settings;
	}
		
	public void init()
	{
		this.generateArticles();
		this.articleValues.clear();
		this.articleAvailability.clear();
		for (Article article : allArticles)
		{
			this.articleValues.put(article.getID(), article.getBasePrice());
			this.articleAvailability.put(article.getID(), 0);
		}
	}
	
	public void updateArticleSales(Article article, int addAmount)
	{
		int oldAmount = this.articleAvailability.get(article.getID());
		this.articleAvailability.put(article.getID(), oldAmount + addAmount);
	}
	
	public void updateArticles(int tick, int playerCount)
	{
		for (Article article : allArticles)
		{
			int amountAvailable = this.articleAvailability.get(article.getID()) - 1;
			this.articleAvailability.put(article.getID(), amountAvailable);
			double newPrice;
			double articleBasePrice = article.getBasePrice();
			if (amountAvailable < 0)
			{
				double maxFactor = settings.getMaxArticlePriceIncrease();
				double diff = articleBasePrice * maxFactor - articleBasePrice;
				double currentFactor = diff * Math.abs(amountAvailable) / settings.getTurnsUntilMaxPriceIncrease();
				newPrice = article.getBasePrice() + currentFactor;
				newPrice = Math.min(newPrice, article.getBasePrice() * settings.getMaxArticlePriceIncrease());
				newPrice = Math.max(newPrice, article.getBasePrice());
			}
			else
			{
				newPrice = article.getBasePrice();
			}		
			this.articleValues.put(article.getID(), newPrice);
		}
	}
	
	public List<Article> getAllArticles()
	{
		return allArticles;
	}
	
	public double getArticlePrice(Article article)
	{
		return this.articleValues.get(article.getID());
	}
	
	public void generateArticles()
	{
		this.allArticles = ArticleGenerator.getTestArticles();		
	}
}
