package control;

import java.util.Iterator;
import java.util.List;

import Generators.OfferGenerator;
import model.Article;
import model.Offer;
import model.Settings;
import model.TendingOffers;

public class TendingOffersControl {
	
	private TendingOffers tendingOffers;
	private TendingOffers tendingOffers_cpy;
	
	List<Article> allArticles;
	
	OfferGenerator offerGenerator;
	
	
	public TendingOffersControl(List<Article> articles)
	{
		tendingOffers = new TendingOffers();
		tendingOffers_cpy = new TendingOffers();
		allArticles = articles;
		offerGenerator = new OfferGenerator(articles);
	}
	
	public void addOffer(Offer newOffer)
	{
		tendingOffers._addOffer(newOffer);
	}
	
	public void deleteOffer(int ID)
	{
		for (Offer offer : tendingOffers._allOffers())
		{
			if (offer.getOfferID() == ID)
			{
				tendingOffers._allOffers().remove(offer);
				return;
			}
		}
	}
	
	public void setOffers(List<Offer>offers)
	{
		tendingOffers.setOffers(offers);
	}
	
	public TendingOffers getTendingOffersCpy()
	{
		return tendingOffers_cpy;
	}
	
	public void generateNewOffer(int tick)
	{
		tendingOffers._addOffer(offerGenerator.generateNewOffer(tick));
	}
	
	public void updateAll(int tick, Settings settings)
	{
		for (Iterator<Offer> offerIterator = tendingOffers._allOffers().iterator(); offerIterator.hasNext();)
		{
			Offer offer = offerIterator.next();
			if (tick - offer.getStartTime() > settings.getTicksUntilDeleteOffer())
			{
				offerIterator.remove();
			}
		}
		if (tick % settings.getTicksUntilNextOffer() == 0)
		{
			this.generateNewOffer(tick);
		}
		tendingOffers.updateOffers(tick, settings.getLowestPriceFactor(), settings.getTicksUntilDecreasePrice());
	}
	
	public Offer getOffer(int id)
	{
		for (Offer offer : tendingOffers._allOffers())
		{
			if (offer.getOfferID() == id)
			{
				return offer;
			}
		}
		return null;
	}
	
	public void updateOfferData()
	{
		tendingOffers_cpy.updateData(tendingOffers);
	}

	public TendingOffers getTendingOffers() 
	{
		return tendingOffers;
	}

	public void reset() {
		this.tendingOffers._allOffers().clear();
		this.tendingOffers_cpy._allOffers().clear();
		
	}
	
}
