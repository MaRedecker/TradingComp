package tests;

import static org.junit.Assert.*;

import java.util.List;

import model.Article;
import model.Buildings;
import model.Inventory;
import model.Settings;
import model.StoredArticle;
import model.PendingOffers;
import model.Truck;
import model.TruckState;

import org.junit.Before;
import org.junit.Test;

import Generators.ArticleGenerator;
import control.ArticleControl;
import control.InventoryControl;
import control.Samples.TestingCompany;

public class InventoryControlTest {

	TestingCompany company1;
	TestingCompany company2;
	InventoryControl invControl;
	ArticleControl articleControl;
	Inventory inv1;
	Inventory inv2;
	PendingOffers offers;
	
	@Before
	public void setUp() throws Exception {
		
		offers = new PendingOffers();
	}

	@Test
	public void ChangeArticleTest() {
		this.init();
		for (Inventory inv : invControl.getInventories())
		{
			for ( StoredArticle storedArticle : inv.getArticles())
			{
				assertTrue(storedArticle.getAmount() == 0);
			}
		}
		Article article = articleControl.getAllArticles().get(0);
		int amount = 100;
		int inventoryIndex = 0;
		invControl.changeStoredArticle(inventoryIndex, article.getID(), amount);
		Inventory inv = invControl.getInventories().get(inventoryIndex);
		for ( StoredArticle storedArticle : inv.getArticles())
		{
			if (storedArticle.getID() == article.getID())
				assertEquals(storedArticle.getAmount(), 100);
			else
				assertEquals(storedArticle.getAmount(), 0);
		}	
	}
	
	@Test
	public void SellingTrucksTest()
	{
		this.init();
		Truck truck1 = inv1.getTruck();
		Article article = articleControl.getAllArticles().get(0);
		assertTrue(invControl.getCompaniesWhichSell().isEmpty());
		truck1.setPosition(Buildings.SALES -1);
		truck1.driveTo(Buildings.SALES);
		truck1.setLoadedArticle(article);
		truck1.setAmount(100);
		truck1.setTruckState(TruckState.SELLING);
		truck1.update();
		assertFalse(invControl.getCompaniesWhichSell().isEmpty());
		double oldMoney = inv1.getMoney();
		invControl.updateSellingTrucks();
		assertFalse(truck1.isLoaded());
		assertTrue(truck1.isIdling());
		assertTrue(inv1.getMoney() > oldMoney);
	}
	
	@Test
	public void UnloadingTrucksTest()
	{
		this.init();
		Truck truck1 = inv1.getTruck();
		Article article = articleControl.getAllArticles().get(0);
		assertTrue(invControl.getCompaniesWhichUnload().isEmpty());
		truck1.setPosition(Buildings.WAREHOUSE);
		truck1.setLoadedArticle(article);
		truck1.setAmount(100);
		truck1.unload();
		truck1.update();
		assertFalse(invControl.getCompaniesWhichUnload().isEmpty());
		int oldAmountStored = inv1.getAmountOfStoredArticles();
		assertEquals(oldAmountStored, 0);
		invControl.updateUnloadingTrucks();
		assertFalse(truck1.isLoaded());
		assertTrue(truck1.isIdling());
		assertEquals(inv1.getAmountOfStoredArticles(), 100);
	}
	
	@Test
	public void LoadingTrucksTest()
	{
		int amountStored = 100;
		int amountToLoad = 90;
		this.init();
		this.LoadingTrucksTestSub(amountStored, amountToLoad);
		amountStored = 150;
		amountToLoad = 300;
		this.init();
		this.LoadingTrucksTestSub(amountStored, amountToLoad);
	}
	
	private void LoadingTrucksTestSub(int amountStored, int amountToLoad)
	{
		Truck truck1 = inv1.getTruck();
		Article article = articleControl.getAllArticles().get(0);
		inv1.addBoughtArticles(article.getID(), amountStored);
		assertTrue(invControl.getCompaniesWhichLoad().isEmpty());
		truck1.setPosition(Buildings.WAREHOUSE);
		truck1.load(article, amountToLoad);
		truck1.update();
		assertFalse(invControl.getCompaniesWhichLoad().isEmpty());
		int oldAmountStored = inv1.getAmountOfStoredArticles();
		assertEquals(oldAmountStored, amountStored);
		invControl.updateLoadingTrucks();
		assertTrue(truck1.isLoaded());
		assertTrue(truck1.isIdling());
		if (amountStored - amountToLoad >= 0)
			assertEquals(inv1.getAmountOfStoredArticles(), amountStored - amountToLoad);
		else
		{
			assertEquals(inv1.getAmountOfStoredArticles(), 0);		
			assertEquals(truck1.getAmount(), amountStored);
		}
	}
	
	private void init()
	{
		articleControl = new ArticleControl(new Settings());
		articleControl.init();
		invControl = new InventoryControl(articleControl);
		invControl.initInventories(2);
		company1 = new TestingCompany();
		company2 = new TestingCompany();
		company1._setData(inv1, offers);
		company2._setData(inv2, offers);
		inv1 = invControl.getInventories().get(0);
		inv2 = invControl.getInventories().get(1);
	}

}
