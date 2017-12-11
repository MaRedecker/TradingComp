package control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Article;
import model.Buildings;
import model.Companies;
import model.Inventory;
import model.StoredArticle;
import model.Truck;

/**
 * Initializes and updates all inventories.
 * @author Max
 *
 */
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
		this.readCompanyInput();
		for (int i = 0; i < inventories.size(); i++)
		{
			Truck truck = inventories.get(i).getTruck();
			truck.update();
		}			
		this.updateSellingTrucks();
		this.updateLoadingTrucks();
		this.updateUnloadingTrucks();	
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
	
	private void updateSellingTrucks()
	{
		List<Inventory> compsWhichSell = this.getCompaniesWhichSell();
		for (Inventory inventory : compsWhichSell)
		{
			Truck truck = inventory.getTruck();
			articleControl.updateArticleSales(truck.getLoadedArticle(), truck.getAmount());
			double moneyToAdd = inventory.getArticleByID(truck.getLoadedArticle().getID()).
														getSellingPricePerUnit() * truck.getAmount();		
			inventory.addMoney(moneyToAdd);
			inventory.setTotalSold(inventory.getTotalSold() + moneyToAdd);
			truck.setAmount(0);
			truck.setLoadedArticle(null);
			truck.resetState();
		}
	}
	
	private void updateLoadingTrucks()
	{
		List<Inventory> compsWhichLoad = this.getCompaniesWhichLoad();
		for (Inventory inventory : compsWhichLoad)
		{
			Truck truck = inventory.getTruck();
			int desiredAmount = truck.getDesiredAmount();
			int desiredArticleID = truck.getDesiredArticleID();
			StoredArticle article = (StoredArticle) inventory.getArticleByID(desiredArticleID);
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
			truck.resetState();	
		}
	}
	
	private void updateUnloadingTrucks()
	{
		List<Inventory> compsWhichUnload = this.getCompaniesWhichUnload();
		for (Inventory inventory : compsWhichUnload)
		{
			Truck truck = inventory.getTruck();		
			inventory.addBoughtArticles(truck.getLoadedArticle().getID(), truck.getAmount());
			inventory.addBoughtArticles(truck.getLoadedArticle().getID(), truck.getAmount());
			truck.setAmount(0);
			truck.setLoadedArticle(null);	
			truck.resetState();	
		}
	}
	
	public void updateInventoryData()
	{
		for (int i = 0; i < inventories.size(); i++)
		{
			inventories_cpy.get(i).updateData(inventories.get(i));
		}
	}
	
	public void readCompanyInput()
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
		}
	}
	
	public List<Inventory> getCompaniesWhichBuy()
	{
		return this.inventories_cpy.stream().filter(Inventory::wantsToBuy).collect(Collectors.toList());	
	}
	
	public List<Inventory> getCompaniesWhichSell()
	{
		return this.inventories.stream().filter(c -> c.getTruck().isSelling() && c.getTruck().hasReachedPosition())
											.collect(Collectors.toList());	
	}
	
	public List<Inventory> getCompaniesWhichLoad()
	{
		return this.inventories.stream().filter(c -> c.getTruck().isLoading() &&
											c.getTruck().getPosition() == Buildings.WAREHOUSE)
											.collect(Collectors.toList());	
	}
	
	public List<Inventory> getCompaniesWhichUnload()
	{
		return this.inventories.stream().filter(c -> c.getTruck().isUnloading() &&
											c.getTruck().getPosition() == Buildings.WAREHOUSE)
											.collect(Collectors.toList());	
	}
	public List<Inventory> getInventoriesCpy() 
	{	
		return inventories_cpy;
	}

}
