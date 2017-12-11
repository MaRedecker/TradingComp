package control.Samples;

import java.awt.Color;

import model.Buildings;
import model.Company;
import model.OfferInformation;

public class LowPriceBestPrice extends Company {

	@Override
	public void update() {
		if (this.getTruck().getPosition() == Buildings.MARKET)
		{
			OfferInformation offer = findCheapOffer();
			if (offer != null)
				this.buy(offer.getOfferID());
		}
		else if (getTruck().isIdling() && !getTruck().isLoaded())
		{
			getTruck().driveTo(Buildings.MARKET);
		}
		
	}
	
	public void onBoughtOffer() {
		
		this.getTruck().sell();
	}
	
	public void onTruckReachedTarget() {	
		
		if (this.getTruck().getPosition() == Buildings.SALES)
		{
			this.getTruck().driveTo(Buildings.MARKET);
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	public Color getColor() {
		return Color.ORANGE;
	}
	
	public String getName() {
		return "HalfPriceBestPrice Ltd";
	}
	
	private OfferInformation findCheapOffer()
	{
			for (OfferInformation offer : this.getOfferInformation().getOffers())
			{
				if (offer.getArticle().getBasePrice() >= offer.getPricePerUnit() * 1.5)
				{
					return offer;
				}
			}
			return null;
	}

}
