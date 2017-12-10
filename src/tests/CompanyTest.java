package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Article;
import model.Company;
import model.Inventory;
import model.TendingOffers;

import org.junit.Before;
import org.junit.Test;

import control.Samples.TestingCompany;

public class CompanyTest {
	
	private List<Article> articles;
	private Company company = new TestingCompany();
	private Inventory inv; 
	private TendingOffers offers;
	
	@Before
	public void init()
	{
		company = new TestingCompany();	
		articles = new ArrayList<Article>();
		articles.add(new Article("TestArticle", 0, 0));
		inv = new Inventory(articles);
		offers = new TendingOffers();
	}
	@Test
	public void testCompany() {
		
		assertNull(company.getInventory());		
		company._setData(inv, offers);		
		assertEquals(company.getInventory(), inv);
		assertNotNull(company.getTruck());
		assertEquals(company.getOfferInformation(), offers);	
	}

}
