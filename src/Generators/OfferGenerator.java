package Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Article;
import model.Offer;

public class OfferGenerator {
	
	private Random random;
	private int nextOfferID;
	List<Article> allArticles;
	
	public OfferGenerator(List<Article> articles)
	{
		random = new Random();
		nextOfferID = 0;
		allArticles = articles;
	}
	
	public Offer generateNewOffer(int tick)
	{
		Article article = allArticles.get(getRandomInt(0, allArticles.size() - 1));
		double price = getRandomDouble(0.8, 1.2) * article.getBasePrice();
		int amount = getRandomInt(50, 1000);
		Offer offer = new Offer(price, amount, article, getNextID(), tick);
		return offer;
	}
	
	public static List<Offer> generateTestOffers(List<Article> articles)
	{
		List<Offer> offers = new ArrayList<Offer>();
		offers.add(new Offer(1, 100, articles.get(0), 0, 0));
		offers.add(new Offer(1.5, 200, articles.get(1), 1, 0));
		offers.add(new Offer(0.5, 300, articles.get(2), 2, 0));
		
		return offers;
	}
	
	public double getRandomDouble(double min, double max)
	{
		return Math.round((min + (max - min) * random.nextDouble()));
	}
	
	public int getRandomInt(int min, int max)
	{
		return random.nextInt(max - min + 1) + min;
	}
	
	public int getNextID()
	{
		return nextOfferID++;
	}

}
