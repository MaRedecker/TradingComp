package model;

import java.util.ArrayList;
import java.util.List;

public class TendingOffers implements TendingOffersInformation {
	
	private List<Offer> allOffers;
	
	public TendingOffers()
	{
		allOffers = new ArrayList<Offer>();
	}
	
	public List<Offer> _allOffers()
	{
		return allOffers;
	}
	
	public void _addOffer(Offer newOffer)
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
	
	
	public void updateData(TendingOffers offers)
	{
		if (offers.getOffers().size() == allOffers.size())
		{
			
			for (int i = 0; i < offers._allOffers().size(); i++)
			{
				allOffers.get(i).updateData(offers._allOffers().get(i));
			}
		}
		else
		{
			allOffers.clear();
			for (Offer offer : offers._allOffers())
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
