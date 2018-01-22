package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the available offers.
 * @author Max
 *
 */
public class PendingOffers implements PendingOffersInformation {
	
	private List<Offer> allOffers;
	
	public PendingOffers()
	{
		allOffers = new ArrayList<Offer>();
	}
	
	public List<Offer> getAllOffers()
	{
		return allOffers;
	}
	
	public void addOffer(Offer newOffer)
	{
		allOffers.add(newOffer);
	}
	
	public List<OfferInformation> getOffers()
	{
		List<OfferInformation> offers = new ArrayList<OfferInformation>();
		for (Offer offer : allOffers)
		{
			offers.add(offer);
		}
		return offers;
	}
	
	public void setOffers(List<Offer> offers)
	{
		allOffers = offers;
	}
	
	public void updateData(PendingOffers offers)
	{
		if (offers.getOffers().size() == allOffers.size())
		{
			for (int i = 0; i < offers.getAllOffers().size(); i++)
			{
				allOffers.get(i).updateData(offers.getAllOffers().get(i));
			}
		}
		else
		{
			allOffers.clear();
			for (Offer offer : offers.getAllOffers())
			{
				allOffers.add(new Offer(offer));
			}
		}
	}
	
	public void updateOffers(int Tick, double lowestPriceFactor, int tickUntilDecrease)
	{
		for (Offer offer : allOffers)
		{
			offer.updatePrice(Tick, lowestPriceFactor, tickUntilDecrease);
		}
	}	
}
