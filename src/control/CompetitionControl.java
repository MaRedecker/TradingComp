package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import control.Samples.BuyAndSellForProfit;
import control.Samples.InvestInOneArticleCompany;
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
	private PendingOffersControl offers;
	private InventoryControl inventories;
	private TurnControl turnControl;
	private ArticleControl articleControl;
	private Settings settings;
	
	public CompetitionControl(Settings settings)
	{
		competition = new Competition(settings.getMaxTicks());
		participants = new Companies();
		articleControl = new ArticleControl(settings);
		articleControl.init();
		offers = new PendingOffersControl(articleControl.getAllArticles());
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
		articleControl.updateArticles(competition.getTurns(), participants.getNumberOfParticipants());
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
		competition.setMaxTurns(settings.getMaxTicks());
		Thread turnThread = new Thread(turnControl);
		loadCompanies();
		articleControl.init();
		InitInventories();
		InitOffers();
		inventories.updateInventoryData();
		offers.updateOfferData();
		start();
		turnThread.start();
	}
	
	public void loadCompanies()
	{
		if (participants.hasUserChoice())
		{
			participants.compile();
		}
		else
		{
			List<Company> companies = new ArrayList<Company>();
			companies.add(new LowPriceBestPrice());
			companies.add(new InvestInOneArticleCompany());
			companies.add(new BuyAndSellForProfit());
			participants.setCompanies(companies);
		}
	}
	
	public void loadTestCompanies()
	{
		List<Company> companies = new ArrayList<Company>();
		companies.add(new TestingCompany());
		companies.add(new TestingCompany());
		companies.add(new TestingCompany());
		companies.add(new TestingCompany());
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
					this.offers.getPendingOffersCpy());
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
	
	public PendingOffers getPendingOffers()
	{
		return offers.getPendingOffers();
	}
	
	public Companies getCompanies()
	{
		return this.participants;
	}
	
	public Competition getCompetition()
	{
		return this.competition;
	}
	
	public InventoryControl getInventoryControl()
	{
		return this.inventories;
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
