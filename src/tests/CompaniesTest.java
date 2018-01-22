package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Companies;
import model.Company;
import model.Inventory;
import model.PendingOffers;
import model.Truck;

import org.junit.Before;
import org.junit.Test;

import Generators.ArticleGenerator;
import control.Samples.TestingCompany;

public class CompaniesTest {

	Companies companies;
	List<Company> participants;
	Inventory inv1;
	Inventory inv2;
	PendingOffers offers;
	@Before
	public void init()
	{
		inv1 = new Inventory(ArticleGenerator.getTestArticles());
		inv2 = new Inventory(ArticleGenerator.getTestArticles());
		offers = new PendingOffers();
		participants = new ArrayList<Company>();
		participants.add(new TestingCompany());
		participants.get(0)._setData(inv1, offers);
		participants.add(new TestingCompany());
		participants.get(1)._setData(inv2, offers);
	}
	
	@Test
	public void testEmpty() {
		companies = new Companies();
		assertEquals(companies.getCompanies().size(), 0);
		assertEquals(companies.getCompaniesWhichBuy().size(), 0);
		assertEquals(companies.getCompaniesWhichTrucksReachDest().size(), 0);
	}
	
	@Test
	public void testAfterParticipantsSet()
	{
		companies = new Companies();
		companies.setCompanies(participants);
		assertEquals(companies.getCompanies().size(), 2);
		assertEquals(companies.getCompaniesWhichBuy().size(), 0);
		assertEquals(companies.getCompaniesWhichTrucksReachDest().size(), 2);	
		Truck inv1Truck = inv1.getTruck();
		inv1Truck.driveTo(inv1Truck.getPosition());
		companies.Tick();
		inv1Truck.update();
		inv2.getTruck().update();
		assertEquals(companies.getCompaniesWhichTrucksReachDest().size(), 2);
		inv1Truck.driveTo(100);
		inv1Truck.update();
		assertEquals(companies.getCompaniesWhichTrucksReachDest().size(), 1);
		inv1.buy(10);
		assertEquals(companies.getCompaniesWhichBuy().size(), 1);
		inv1.resetBuying();
		inv2.buy(20);
		assertEquals(companies.getCompaniesWhichBuy().size(), 1);
		inv1.buy(30);
		assertEquals(companies.getCompaniesWhichBuy().size(), 2);
	}

}
