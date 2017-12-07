package control;

import java.util.*;

import control.Samples.TestingCompany;
import model.*;

public class TestingModel {
	
	private List<Article> articleList;
	private TendingOffers tendingOffers;
	
	public void TestCompany()
	{
		articleList = generateArticleList();
		tendingOffers = new TendingOffers();
		List<StoredArticle> storedArticleList = new ArrayList<StoredArticle>();
		for (Article article : articleList)
		{
			storedArticleList.add(new StoredArticle(article, 10, 0));
		}
		Inventory inv = new Inventory(storedArticleList, 1000);
		List<OfferInformation> offerList = generateOffers();
		for (OfferInformation offer : offerList)
		{
			tendingOffers._addOffer((Offer) offer);
		}
		TestingCompany company = new TestingCompany();
		company._setData(inv, tendingOffers);
		company.update();
		tendingOffers._addOffer(new Offer(40, 20, articleList.get(1), 4, 0));
		offerList.add(new Offer(40, 20, articleList.get(1), 4, 0));
		company.update();
		
	}
	
	
	public List<Article> generateArticleList()
	{
		articleList = new ArrayList<Article>();
		articleList.add(new Article("Apple", 0, 1));
		articleList.add(new Article("Banana", 1, 1.5));
		articleList.add(new Article("Cereal", 2, 2));
		
		return articleList;
	}
	
	public List<OfferInformation> generateOffers()
	{
		
		List<OfferInformation> offerList = new ArrayList<OfferInformation>();
		offerList.add(new Offer(20.5, 10, articleList.get(0), 0, 0));
		offerList.add(new Offer(10.5, 20, articleList.get(1), 1, 0));
		offerList.add(new Offer(5.5, 35, articleList.get(2), 2, 0));
		
		return offerList;
	}

}
