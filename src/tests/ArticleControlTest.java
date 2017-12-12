package tests;

import static org.junit.Assert.*;
import model.Article;
import model.Settings;

import org.junit.Before;
import org.junit.Test;

import control.ArticleControl;



public class ArticleControlTest {
	
	ArticleControl articleControl;
	Settings settings;
	@Before
	public void setUp()
	{
		settings = new Settings();
	}

	@Test
	public void testAfterInit() {
		articleControl = new ArticleControl(settings);
		articleControl.init();
		assertFalse(articleControl.getAllArticles().isEmpty());
		Article article = articleControl.getAllArticles().get(0);
		assertEquals(article.getBasePrice(), articleControl.getArticlePrice(article), 0.1);
	}
	
	@Test
	public void testUpdate() {
		articleControl = new ArticleControl(settings);
		articleControl.init();
		Article article = articleControl.getAllArticles().get(0);
		double oldPrice = article.getBasePrice();
		articleControl.updateArticles(1, 1);
		double newPrice = articleControl.getArticlePrice(article);
		assertFalse(oldPrice == newPrice);
		assertFalse(newPrice < article.getBasePrice());
		assertFalse(newPrice > article.getBasePrice() * settings.getMaxArticlePriceIncrease());
		articleControl.updateArticleSales(article, 10000);
		articleControl.updateArticles(2, 1);
		newPrice = articleControl.getArticlePrice(article);
		assertTrue(oldPrice == newPrice);
		assertFalse(newPrice < article.getBasePrice());
		assertFalse(newPrice > article.getBasePrice() * settings.getMaxArticlePriceIncrease());		
	}

}
