package tests;

import static org.junit.Assert.*;
import model.Offer;
import model.Settings;
import model.StoredArticle;
import model.PendingOffers;

import org.junit.Before;
import org.junit.Test;

import control.ArticleControl;
import control.InventoryControl;
import control.PendingOffersControl;
import control.Samples.TestingCompany;

public class IntegrityTest {
	
	private TestingCompany company;
	private ArticleControl articleControl;
	private InventoryControl invControl;
	private PendingOffersControl tendingOffersControl;
	private Settings settings;

	@Before
	public void setUp() throws Exception {
		settings = new Settings();
		company = new TestingCompany();
		articleControl = new ArticleControl(settings);
		articleControl.init();
		invControl = new InventoryControl(articleControl);
		tendingOffersControl = new PendingOffersControl(articleControl.getAllArticles());
		tendingOffersControl.generateNewOffer(0);
		tendingOffersControl.generateNewOffer(0);
		invControl.initInventories(1);
		tendingOffersControl.updateOfferData();
		invControl.updateInventoryData();
		company._setData(invControl.getInventoryCopy(0), tendingOffersControl.getPendingOffersCpy());
		
	}

	@Test
	public void testIntegrity() {
		
		Offer companyOffer;
		Offer controlOffer;
		StoredArticle companyStoredArticle; 
		StoredArticle controlStoredArticle; 
		int index = 0;
		companyOffer = (Offer)company.getOfferInformation().getOffers().get(index);
		companyStoredArticle = (StoredArticle)company.getInventory().getAllArticles().get(index);
		controlOffer = tendingOffersControl.getOffer(0);
		controlStoredArticle = (StoredArticle) invControl.getInventories().get(0).getArticleByID(0);
		
		assertTrue(controlStoredArticle.getAmount() == companyStoredArticle.getAmount());
		assertTrue(controlOffer.getPricePerUnit() == companyOffer.getPricePerUnit());
		companyOffer.setPrice(0);
		companyStoredArticle.setAmount(1000000);
		assertFalse(controlStoredArticle.getAmount() == companyStoredArticle.getAmount());	
		assertFalse(controlOffer.getPricePerUnit() == companyOffer.getPricePerUnit());
		
		tendingOffersControl.updateOfferData();
		invControl.updateInventoryData();		
		assertTrue(controlStoredArticle.getAmount() == companyStoredArticle.getAmount());
		assertTrue(controlOffer.getPricePerUnit() == companyOffer.getPricePerUnit());			
	}
}
