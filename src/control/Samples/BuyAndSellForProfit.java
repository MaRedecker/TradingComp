package control.Samples;

import java.awt.Color;

import model.Buildings;
import model.Company;
import model.InventoryInformation;
import model.OfferInformation;
import model.Truck;
import model.TruckInformation;

/**
 * Searches for the offer with the most profit and sells it. 
 * @author Max
 *
 */
public class BuyAndSellForProfit extends Company {

	@Override
	public void update() {
		TruckInformation truck = this.getTruck();	
		if (truck.getPosition() == Buildings.MARKET && truck.isIdling())
		{
			OfferInformation profitOffer = this.searchProfitOrder();
			if (profitOffer != null)
			{
				this.buy(profitOffer.getOfferID());
			}
		}
		else if (truck.getPosition() == Buildings.SALES && truck.isIdling())
		{
			truck.driveTo(Buildings.MARKET);
		}
	}

	private OfferInformation searchProfitOrder() {
		OfferInformation bestOffer = null;
		InventoryInformation inv = this.getInventory();
		int articleID;
		double bestOfferProfit = 0;
		double turnover;
		for (OfferInformation offer : this.getOfferInformation().getOffers())
		{
			if (offer.getFullPrice() <= this.getInventory().getMoney())
			{
				articleID = offer.getArticle().getID();
				turnover =  inv.getArticleByID(articleID).getSellingPricePerUnit() * offer.getAmount();
				if (bestOffer == null)
					bestOffer = offer;	
				else if(bestOffer.getPricePerUnit() < inv.getArticleByID(articleID).getSellingPricePerUnit())
				{
					if (turnover - offer.getFullPrice() > bestOfferProfit)
						bestOffer = offer;
				}
				bestOfferProfit = inv.getArticleByID(bestOffer.getArticle().getID()).getSellingPricePerUnit() * 
							offer.getAmount() - offer.getFullPrice();
			}
		}
		return bestOffer;
	}
	
	@Override
	public void onBoughtOffer()
	{
		this.getTruck().sell();
	}

	@Override
	public void start() {
		
	}
	
	@Override
	public Color getColor()
	{
		return Color.MAGENTA;
	}
	
	@Override
	public String getName()
	{
		return "EveryProfitIsGoodProfit";
	}
}
