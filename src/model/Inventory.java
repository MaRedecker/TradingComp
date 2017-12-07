package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of money, the truck and all articles in a Company's warehouse
 * @author Max
 *
 */

public class Inventory implements InventoryInformation {
		
	private Truck truck;
	private double money;
	private List<StoredArticle> storage;
	private int boughtOfferID;
	private boolean isBuying;
	private double totalSold;
	
	
	public Inventory(List<Article> allArticles, int money)
	{
		this.isBuying = false;
		this.money = money;
		this.truck = new Truck(0, null, 0);
		this.storage = new ArrayList<StoredArticle>();
		for (Article article : allArticles)
		{
			StoredArticle newStoredArticle = new StoredArticle(new Article(article), 
												 article.getBasePrice(), 
												 0);
			this.storage.add(newStoredArticle);
		}
	}
	
	public Inventory(List<Article> allArticles) 
	{
		this(allArticles, 1000);
	}

	public List<StoredArticle> getStorage()
	{
		return storage;
	}

	@Override
	public StoredArticleInformation getArticleByName(String Name)
	{
		for (StoredArticle article : storage)
		{
			if (article.getName() == Name)
				return article;
		}
		return null;
	}
	
	public StoredArticleInformation getArticleByID(int ID)
	{
		for (StoredArticle article : storage)
		{
			if (article.getID() == ID)
				return article;
		}
		return null;
	}
	
	public double getMoney()
	{
		return money;
	}
	
	public void setMoney(double newMoney)
	{
		money = newMoney;
	}
	
	public void addMoney(double amount)
	{
		money = money += amount;
	}

	@Override
	public List<StoredArticleInformation> getAllArticles() 
	{
		List<StoredArticleInformation> allArticles = new ArrayList<StoredArticleInformation>();
		for (StoredArticle article : storage)
		{
			allArticles.add((StoredArticleInformation)article);
		}
		return allArticles;
	}
	
	public void buy(int ID)
	{
		boughtOfferID = ID;
		isBuying = true;
	}
	
	public void buy(OfferInformation offer)
	{
		buy(offer.getOfferID());
	}
	
	public int getBoughtOffer()
	{
		return boughtOfferID;
	}
	
	public boolean wantsToBuy()
	{
		return isBuying;
	}
	
	public void resetBuying()
	{
		isBuying = false;
	}
	
	public Truck getTruck()
	{
		return truck;
	}
	
	public TruckInformation getTruckInformation()
	{
		return truck;
	}
	
	public void addBoughtArticles(int articleID, int amount)
	{
		for (StoredArticle article : storage)
		{
			if(article.getID() == articleID)
			{
				article.setAmount(amount + article.getAmount());
				return;
			}
		}
	}
	
	public List<StoredArticle> getArticles()
	{
		return storage;
	}
	
	public void updateData(Inventory inventory)
	{
		isBuying = inventory.wantsToBuy();
		money = inventory.getMoney();
		boughtOfferID = inventory.getBoughtOffer();
		truck.updateData(inventory.getTruck());
		totalSold = inventory.getTotalSold();
		for (int i = 0; i < inventory.getArticles().size(); i++)
		{
			storage.get(i).updateData(inventory.getArticles().get(i));
		}
	}
	
	public double getTotalSold()
	{
		return totalSold;
	}
	
	public void setTotalSold(double val)
	{
		totalSold = val;
	}

	@Override
	public int getAmountOfStoredArticles() {
		
		int amountOfArticles = 0;
		for (StoredArticle article : storage)
		{
			amountOfArticles += article.getAmount();
		}
		return amountOfArticles;
	}	
}
