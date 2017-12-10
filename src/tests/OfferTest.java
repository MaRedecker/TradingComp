package tests;

import static org.junit.Assert.*;
import model.Article;
import model.Offer;

import org.junit.Test;

import Generators.OfferGenerator;

public class OfferTest {

	Offer offer;
	Article article;
	OfferGenerator offerGenerator;
	
	@Test
	public void testPrices() {

		this.init();
		assertEquals(offer.getFullPrice(), offer.getPricePerUnit() * offer.getAmount(), 0.2);
	}

	@Test
	public void testChangePrices()
	{
		this.init();
		double oldPrice = offer.getFullPrice();
		offer.updatePrice(100, 2, 10);
		assertTrue(oldPrice > offer.getFullPrice());
	}
	
	private void init()
	{
		article = new Article("TestArticle", 0, 1);
		offer = new Offer(1, 100, article, 0, 0);		
	}
}
