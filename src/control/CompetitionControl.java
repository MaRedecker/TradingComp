package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import control.Samples.LowPriceBestPrice;
import control.Samples.TestingCompany;
import model.*;

/**
 * Starts and ends a competition.
 * @author Max
 *
 */
public class CompetitionControl implements Observer {
	
	private Competition competition;
	private Companies participants;
	private TendingOffersControl offers;
	private InventoryControl inventories;
	private TurnControl turnControl;
	private ArticleControl articleControl;
	private Settings settings;
	
	public CompetitionControl(Settings settings)
	{
		competition = new Competition();
		participants = new Companies();
		articleControl = new ArticleControl(settings);
		articleControl.init();
		offers = new TendingOffersControl(articleControl.getAllArticles());
		inventories = new InventoryControl(articleControl);
		turnControl = new TurnControl(competition, this, settings);
		competition.addObserver(this);
		this.settings = settings;
	}
	
	public void turn()
	{
		participants.Tick();	
		handleBuyingCompanies();
		inventories.updateTrucks(participants);
		offers.updateAll(competition.getTurns(), settings, participants.getNumberOfParticipants());
		articleControl.updateArticles(competition.getTurns());
		inventories.updateInventoryPrices();
		inventories.updateInventoryData();
		offers.updateOfferData();
		competition.turn();
	}
	
	public void start()
	{
		initCompanies();
		for(Company company : participants.getCompanies())
		{
			company.start();
		}
	}
	
	public void run()
	{
		Thread turnThread = new Thread(turnControl);
		loadCompanies();
		articleControl.init();
		InitInventories();
		InitOffers();
		start();
		turnThread.start();
	}
	
	public void loadCompanies()
	{
		List<Company> companies = new ArrayList<Company>();
		companies.add(new TestingCompany());
		companies.add(new LowPriceBestPrice());
		participants.setCompanies(companies);
	}
	
	public void InitInventories()
	{
		inventories.initInventories(participants.getCompanies().size());
	}
	
	public void InitOffers()
	{
		offers.reset();
		offers.generateNewOffer(competition.getTurns());
		offers.generateNewOffer(competition.getTurns());
		offers.generateNewOffer(competition.getTurns());	
	}
	
	public void initCompanies()
	{
		int CompanyIndex = 0;
		for (Company company : participants.getCompanies())
		{
			company._setData(inventories.getInventoryCopy(CompanyIndex), 
					this.offers.getTendingOffersCpy());
			CompanyIndex++;
		}
	}
	
	public void handleBuyingCompanies()
	{
		List<Inventory> allCompsWhichBuy = inventories.getCompaniesWhichBuy();
		for (Inventory companyInvCpy : allCompsWhichBuy)
		{
			int companyIndex = inventories.getInventoriesCpy().indexOf(companyInvCpy);
			Inventory companyInv = inventories.getInventories().get(companyIndex);
			int offerID = companyInvCpy.getBoughtOffer();
			Offer boughtOffer = offers.getOffer(offerID);
			if (boughtOffer != null 
					&& companyInv.getMoney() >= boughtOffer.getFullPrice()
					&& companyInv.getTruck().getPosition() == Buildings.MARKET
					&& companyInv.getTruck().isLoaded() == false)
			{
				companyInv.getTruck().loadBoughtOffer(boughtOffer);
				this.inventories.changeMoney(companyIndex, -boughtOffer.getFullPrice());
				this.offers.deleteOffer(boughtOffer.getOfferID());
				this.participants.getCompanies().get(companyIndex).onBoughtOffer();
			}
			companyInv.resetBuying();
		}
	}
	
	public TendingOffers getTendingOffers()
	{
		return offers.getTendingOffers();
	}
	
	public Companies getCompanies()
	{
		return this.participants;
	}
	
	public Competition getCompetition()
	{
		return this.competition;
	}
	
	public void reset()
	{
		this.run();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (competition.isRunning() && competition.getTurns() == 0)
		{
			this.run();
		}
	}
	
	

}
