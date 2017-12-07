package control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Article;
import model.Buildings;
import model.Companies;
import model.Company;
import model.Inventory;
import model.StoredArticle;
import model.Truck;
import model.TruckState;

public class InventoryControl {
	
	private List<Inventory> inventories;
	private List<Inventory> inventories_cpy;
	private ArticleControl articleControl;
	
	public InventoryControl(ArticleControl articleControl)
	{
		inventories = new ArrayList<Inventory>();
		inventories_cpy = new ArrayList<Inventory>();
		this.articleControl = articleControl;
	}
	
	public void changeStoredArticle(int companyIndex, int articleID, int amount)
	{
		for (StoredArticle article : getStoredArticles(companyIndex))
		{
			if (article.getID() == articleID)
			{
				article.setAmount(article.getAmount() + amount);
				return;
			}
		}
	}
	
	public void changeMoney(int companyIndex ,double amount)
	{
		inventories.get(companyIndex).addMoney(amount);
	}
	
	private List<StoredArticle> getStoredArticles(int companyIndex)
	{
		return inventories.get(companyIndex).getStorage();
	}
	
	public void initInventories(int participants)
	{
		List<Article> articleList = this.articleControl.getAllArticles();
		inventories.clear();
		inventories_cpy.clear();
		for (int i = 0; i < participants; i++)
		{
			inventories.add(new Inventory(articleList));
			inventories_cpy.add(new Inventory(articleList));
		}
	}
	
	public Inventory getInventoryCopy(int index)
	{
		return inventories_cpy.get(index);
	}
	
	public List<Inventory> getInventories()
	{
		return inventories;
	}
	
	public void updateTrucks(Companies participants)
	{
		for (int i = 0; i < inventories.size(); i++)
		{
			Truck truck = inventories.get(i).getTruck();
			Truck truck_cpy = inventories_cpy.get(i).getTruck();
			truck.driveTo(truck_cpy.getTargetPosition());
			truck.setTruckState(truck_cpy.getTruckState());
			if(truck.isLoading())
			{
				truck.load(truck_cpy.getDesiredArticleID(), truck_cpy.getDesiredAmount());
			}
			truck.update();
			if (truck_cpy.hasReachedPosition())
			{
				truck_cpy.updateData(truck);
				Inventory inventoryWithTruck = inventories.get(i);
				Inventory inventoryWithTruck_cpy = inventories_cpy.get(i);
				if (truck.isUnloading() && truck.getPosition() == Buildings.WAREHOUSE)
				{
					inventoryWithTruck.addBoughtArticles(truck.getLoadedArticle().getID(), truck.getAmount());
					inventoryWithTruck_cpy.addBoughtArticles(truck.getLoadedArticle().getID(), truck.getAmount());
					truck.setAmount(0);
					truck_cpy.setAmount(0);
					truck.setLoadedArticle(null);
					truck_cpy.setLoadedArticle(null);
				}
				else if (truck.isSelling() && truck.getPosition() == Buildings.SALES)
				{
					articleControl.updateArticleSales(truck.getLoadedArticle(), truck.getAmount());
					double moneyToAdd = inventoryWithTruck.getArticleByID(truck.getLoadedArticle().getID()).
																getSellingPricePerUnit() * truck.getAmount();
					inventoryWithTruck.addMoney(moneyToAdd);
					inventoryWithTruck.setTotalSold(inventoryWithTruck.getTotalSold() + moneyToAdd);
					truck.setAmount(0);
					truck.setLoadedArticle(null);
				}
				else if (truck.isLoading() && truck.getPosition() == Buildings.WAREHOUSE)
				{
					int desiredAmount = truck.getDesiredAmount();
					int desiredArticleID = truck.getDesiredArticleID();
					StoredArticle article = (StoredArticle) inventoryWithTruck.getArticleByID(desiredArticleID);
					if (article.getAmount() >= desiredAmount)
					{
						article.setAmount(article.getAmount() - desiredAmount);
						truck.setLoadedArticle(article.getArticleObject());
						truck.setAmount(desiredAmount);
					}
					else
					{
						truck.setLoadedArticle(article.getArticleObject());
						truck.setAmount(article.getAmount());
						article.setAmount(0);
					}
				}
				truck_cpy.resetState();
				truck.resetState();
				participants.getCompanies().get(i).onTruckReachedTarget();
				truck.driveTo(truck_cpy.getTargetPosition());
				truck.setTruckState(truck_cpy.getTruckState());
				
			}
		}
	}
	
	public void updateInventoryPrices()
	{
		for (Inventory inv : this.inventories)
		{
			for (StoredArticle article : inv.getArticles())
			{
				article.setPrice(articleControl.getArticlePrice(article.getArticleObject()));
			}
		}
	}
	
	public void updateInventoryData()
	{
		for (int i = 0; i < inventories.size(); i++)
		{
			inventories_cpy.get(i).updateData(inventories.get(i));
		}
	}
	
	public List<Inventory> getCompaniesWhichBuy()
	{
		return this.inventories_cpy.stream().filter(Inventory::wantsToBuy).collect(Collectors.toList());	
	}

	public List<Inventory> getInventoriesCpy() {
		
		return inventories_cpy;
		
	}

}
