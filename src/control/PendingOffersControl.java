package control;

import java.util.Iterator;
import java.util.List;

import Generators.OfferGenerator;
import model.Article;
import model.Offer;
import model.Settings;
import model.PendingOffers;

/**
 * Takes care of all offers which are available to the companies.
 * @author Max
 *
 */
public class PendingOffersControl {
	
	private PendingOffers pendingOffers;
	private PendingOffers pendingOffers_cpy;
	
	List<Article> allArticles;
	
	OfferGenerator offerGenerator;
	
	
	public PendingOffersControl(List<Article> articles)
	{
		pendingOffers = new PendingOffers();
		pendingOffers_cpy = new PendingOffers();
		allArticles = articles;
		offerGenerator = new OfferGenerator(articles);
	}
	
	public void addOffer(Offer newOffer)
	{
		pendingOffers.addOffer(newOffer);
	}
	
	public void deleteOffer(int ID)
	{
		for (Offer offer : pendingOffers.getAllOffers())
		{
			if (offer.getOfferID() == ID)
			{
				pendingOffers.getAllOffers().remove(offer);
				return;
			}
		}
	}
	
	public void setOffers(List<Offer>offers)
	{
		pendingOffers.setOffers(offers);
	}
	
	public PendingOffers getPendingOffersCpy()
	{
		return pendingOffers_cpy;
	}
	
	public void generateNewOffer(int tick)
	{
		pendingOffers.addOffer(offerGenerator.generateNewOffer(tick));
	}
	
	public void updateAll(int tick, Settings settings, int numberOfParticipants)
	{
		for (Iterator<Offer> offerIterator = pendingOffers.getAllOffers().iterator(); offerIterator.hasNext();)
		{
			Offer offer = offerIterator.next();
			if (tick - offer.getStartTime() > settings.getTicksUntilDeleteOffer())
			{
				offerIterator.remove();
			}
		}
		if (tick % settings.getTicksUntilNextOffer() == 0)
		{
			for (int i = 0; i < settings.getOffersGeneratedPerPlayer() * numberOfParticipants; i++)
				this.generateNewOffer(tick);
		}
		pendingOffers.updateOffers(tick, settings.getLowestPriceFactor() * numberOfParticipants,
									 settings.getTicksUntilDecreasePrice());
	}
	
	public Offer getOffer(int id)
	{
		for (Offer offer : pendingOffers.getAllOffers())
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
		pendingOffers_cpy.updateData(pendingOffers);
	}

	public PendingOffers getPendingOffers() 
	{
		return pendingOffers;
	}

	public void reset() {
		this.pendingOffers.getAllOffers().clear();
		this.pendingOffers_cpy.getAllOffers().clear();
	}
}
