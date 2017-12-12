package control.Samples;

import java.awt.Color;
import java.util.List;

import model.Article;
import model.Buildings;
import model.Company;
import model.InventoryInformation;
import model.OfferInformation;
import model.TruckInformation;

/**
 * Buys the same article and stores it in the warehouse until money is out. Then everything stored will be sold.
 * @author Max
 *
 */
public class InvestInOneArticleCompany extends Company {

	Article desiredArticle;
	int waitingTime;
	@Override
	public void update() {
		InventoryInformation inv = this.getInventory();
		TruckInformation truck = this.getTruck();
		if (truck.getPosition() == Buildings.MARKET && truck.isIdling())
		{
			waitingTime++;
			OfferInformation goodOffer = this.getGoodOffer();
			if (goodOffer != null)
			{
				this.buy(goodOffer.getOfferID());
				waitingTime = 0;
			}
			else if (waitingTime > 200)
			{
				truck.driveTo(Buildings.WAREHOUSE);
				waitingTime = 0;
			}
		}
		else if(truck.getPosition() == Buildings.WAREHOUSE && truck.isIdling())
		{
			if (this.getGoodOffer() != null)
			{
				truck.driveTo(Buildings.MARKET);
			}
			else if (inv.getArticleByID(desiredArticle.getID()).getSellingPricePerUnit()
						> desiredArticle.getBasePrice() && !truck.isLoaded())
			{
				truck.load(desiredArticle, inv.getArticleByID(desiredArticle.getID()).getAmount());
			}
			else if (truck.isLoaded())
			{
				truck.sell();
			}
		}
		
		if (truck.getPosition() == Buildings.SALES && !truck.isLoaded())
		{
			truck.driveTo(Buildings.MARKET);
		}
	}

	@Override
	public void start() {
		
		List<OfferInformation> offers = this.getOfferInformation().getOffers();
		desiredArticle = offers.get(offers.size()-1).getArticle();
		waitingTime = 0;
	}
	
	@Override
	public void onBoughtOffer()
	{
		getTruck().unload();
	}
	
	private OfferInformation getGoodOffer() {
		for (OfferInformation offer : this.getOfferInformation().getOffers())
		{
			if (offer.getArticle() == desiredArticle)
			{
				if (offer.getPricePerUnit() < desiredArticle.getBasePrice() * 1.1 && 
						this.getInventory().getMoney() >= offer.getFullPrice())
				{
					return offer;
				}
			}
				
		}
		return null;
	}
	
	@Override
	public Color getColor()
	{
		return Color.BLUE;
	}
	
	@Override
	public String getName()
	{
		return "InvestInOneArticle";
	}

}
