package tests;

import static org.junit.Assert.*;

import java.util.List;

import model.Article;
import model.Buildings;
import model.Company;
import model.Competition;
import model.Inventory;
import model.Offer;
import model.Settings;
import model.Truck;
import model.TruckState;

import org.junit.Before;
import org.junit.Test;

import control.CompetitionControl;

public class CompetitionControlTest {

	Settings settings;
	CompetitionControl competition;
	Inventory inv_cpy;
	Inventory inv;
	Truck truck_cpy;
	Truck truck;
	Company comp;
	@Before
	public void setUp() {
		
		settings = new Settings();
	}

	@Test
	public void testAfterInit() {
		this.init();
		assertFalse(competition.getCompanies().getCompanies().isEmpty());
		assertNotNull(competition.getCompetition());
		assertNotNull(competition.getTendingOffers());
	}
	
	@Test
	public void testInitCompanies()
	{
		this.init();
		for (Company comp : competition.getCompanies().getCompanies())
		{
			assertNotNull(comp.getInventory());
			assertNotNull(comp.getOfferInformation());
		}
	}
	
	@Test
	public void testBuyingCompanies()
	{
		this.init();
		assertTrue(competition.getCompanies().getCompaniesWhichBuy().isEmpty());
		Offer offer = competition.getTendingOffers().getAllOffers().get(0);
		if (offer.getFullPrice() > 1000)
			offer.setPrice(0.001);
		int offerAmount = offer.getAmount();
		Article article = offer.getArticle();
		double price = offer.getFullPrice();
		this.setReferencesToCompany(0);
		inv_cpy.setMoney(5000);
		truck.setAmount(0);
		truck.setPosition(Buildings.SALES);
		inv_cpy.buy(offer.getOfferID());
		truck_cpy.setTruckState(TruckState.IDLE);
		this.setReferencesToCompany(1);
		inv.setMoney(0);
		inv_cpy.buy(offer.getOfferID());
		truck.setPosition(Buildings.MARKET);
		this.setReferencesToCompany(2);
		truck.setAmount(200);
		truck.setLoadedArticle(offer.getArticle());
		truck.setPosition(Buildings.MARKET);
		inv_cpy.buy(offer.getOfferID());
		this.setReferencesToCompany(3);
		truck.setAmount(0);
		truck.setPosition(Buildings.MARKET);
		inv.setMoney(5000);
		inv_cpy.buy(offer.getOfferID());
		double oldMoney = inv.getMoney();
		assertFalse(competition.getCompanies().getCompaniesWhichBuy().isEmpty());
		assertEquals(competition.getCompanies().getCompaniesWhichBuy().size(), 
							competition.getCompanies().getCompanies().size());	
		competition.handleBuyingCompanies();
		assertEquals(oldMoney - price, inv.getMoney(), 0.1);
		assertEquals(truck.getAmount(), offerAmount);	
		assertEquals(truck.getLoadedArticle(), article);
		competition.turn();
	}
	
	private void init()
	{
		competition = new CompetitionControl(settings);
		competition.loadTestCompanies();
		competition.InitInventories();
		competition.InitOffers();
		competition.start();		
	}
	
	private void setReferencesToCompany(int index)
	{
		comp = competition.getCompanies().getCompanies().get(index);
		inv = competition.getInventoryControl().getInventories().get(index);
		truck = inv.getTruck();
		inv_cpy = (Inventory) comp.getInventory();
		truck_cpy = inv_cpy.getTruck();
				
	}

}
