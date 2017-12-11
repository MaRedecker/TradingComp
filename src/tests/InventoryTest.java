package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import model.Article;
import model.Inventory;

import org.junit.Before;
import org.junit.Test;

public class InventoryTest {

	Inventory testInv;
	List<Article> articleList;
	int buyingOffer;
	
	@Before
	public void setUp()
	{
		articleList = new ArrayList<Article>();
		articleList.add(new Article("Testarticle1", 0, 100.5));
		articleList.add(new Article("Testarticle2", 1, 50));
		buyingOffer = 0;
	}

	@Test
	public void testBeforeTick() {
		testInv = new Inventory(articleList);
		assertEquals(testInv.getAmountOfStoredArticles(), 0);
		assertNotNull(testInv.getTruck());
		assertNotNull(testInv.getAllArticles());
		assertNotNull(testInv.getArticleByID(1));
		assertNotNull(testInv.getArticleByName("Testarticle1"));
		assertNull(testInv.getArticleByID(100));
		assertNull(testInv.getArticleByName("doesntExist"));
		assertEquals(testInv.getArticleByID(0), testInv.getArticleByName("Testarticle1"));
		assertFalse(testInv.wantsToBuy());
	}
	
	@Test
	public void testAfterTick()
	{
		testInv = new Inventory(articleList);
		testInv.buy(buyingOffer);
		assertTrue(testInv.wantsToBuy());
		assertEquals(testInv.getBoughtOffer(), buyingOffer);
		assertEquals(testInv.getAllArticles().size(), articleList.size());
		testInv.resetBuying();
		assertEquals(testInv.getBoughtOffer(), 0);
		assertFalse(testInv.wantsToBuy());
		testInv.addBoughtArticles(0, 100);
		assertEquals(testInv.getAmountOfStoredArticles(), 100);
		assertNotNull(testInv.getTruckInformation());		
	}

}
