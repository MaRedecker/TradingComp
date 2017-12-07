package model;

import java.util.List;

public interface InventoryInformation {
	
	public List<StoredArticleInformation> getAllArticles();
	
	public StoredArticleInformation getArticleByID(int ID);
	
	public StoredArticleInformation getArticleByName(String name);
	
	public void buy(int ID);
	
	public void buy(OfferInformation offer);
	
	public boolean wantsToBuy();
	
	public double getMoney();
	
	public Truck getTruck();
	
	public int getAmountOfStoredArticles();
	
	public double getTotalSold();

}
