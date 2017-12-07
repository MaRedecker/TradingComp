package control.Samples;

import java.awt.Color;

import model.*;

public class TestingCompany extends Company {
	
	private boolean isDebugging;

	@Override
	public void update() {
		if (isDebugging)
		{
		System.out.println("Offers: \n");
		for (OfferInformation offer : this.getOfferInformation().getOffers())
		{
			System.out.println(offer.getArticle().getName() + " " + offer.getFullPrice() + " " + offer.getAmount());
		}
		System.out.println("\n\n Money:" + getInventory().getMoney() + " Truck: " + getTruck().getDistanceToTarget() +" Stored Articles: \n");
		for (StoredArticleInformation article : this.getInventory().getAllArticles())
		{
			System.out.println(article.getName() + " " + article.getAmount() + " " + article.getSellingPricePerUnit());
		}
		System.out.println("\n----------------------------------\n");
		}
		if (getOfferInformation().getOffers().size() > 1)
		if (getOfferInformation().getOffers().get(1).getFullPrice() < getInventory().getMoney())
		{
			System.out.println(getOfferInformation().getOffers().get(1).getFullPrice() + " " + getInventory().getMoney());
			buy(getOfferInformation().getOffers().get(1).getOfferID());}
	
		if (getTruck().isIdling() && getTruck().getPosition() == Buildings.WAREHOUSE)
		{
			if (!this.getTruck().isLoaded())
			{
				if (this.getInventory().getAmountOfStoredArticles() > 0)
				{
					for(StoredArticleInformation article : this.getInventory().getAllArticles())
					{
						if (article.getAmount() > 0)
						{
							getTruck().load(article.getArticleObject(), article.getAmount());
							
							return;
						}
					}
				}		
			}
			else
			{
				getTruck().driveTo(Buildings.SALES);
			}
		}
	}
		
		
	
	@Override
	public void onBoughtOffer()
	{
		getTruck().unload();
		System.out.println("\nunloading...\n");
	}
	
	@Override
	public void onTruckReachedTarget()
	{
		
	}

	@Override
	public void start() {
		this.isDebugging = false;

	}
	
	public Color getColor()
	{
		return Color.CYAN;
	}
	
	public String getName()
	{
		return "Debug Company";
	}

}
