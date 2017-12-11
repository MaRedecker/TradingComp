package model;

/**
 * A truck which can move articles between buildings.
 * @author Max
 *
 */
public class Truck implements TruckInformation {
	
	private int position;
	private Article loadedArticle;
	private int amount;
	private int targetPosition;
	private TruckState state;
	private boolean reachedPosition;
	private int desiredAmount;
	private int desiredArticle;
	
	public Truck(int Position, Article LoadedArticle, int Amount)
	{
		position = Position;
		loadedArticle = LoadedArticle;
		amount = Amount;
		driveTo(Position);
		state = TruckState.IDLE;
		reachedPosition = false;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public Article getLoadedArticle()
	{
		return loadedArticle;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public boolean isLoaded()
	{
		if (amount > 0)
		{
			return true;
		}
		return false;
	}

	public int getTargetPosition() 
	{
		return targetPosition;
	}

	public void driveTo(int targetPos) 
	{
		if (Math.abs(targetPos - position) > 0)
		{
			targetPosition = targetPos;
			state = TruckState.DRIVING;
		}
	}
	
	public int getDistanceToTarget(int target)
	{
		return Math.abs(target - position);
	}
	
	public int getDistanceToTarget()
	{
		return Math.abs(targetPosition - position);
	}
	
	public void update()
	{
		reachedPosition = false;
		if (position > targetPosition)
		{
			position--;
		}
		else if (position < targetPosition)
		{
			position++;
		}	
		if (this.state != TruckState.IDLE && this.position == targetPosition)
		{
			reachedPosition = true;
		}
	}
	
	public void setLoadedArticle(Article article)
	{
		loadedArticle = article;
	}
	
	public void setPosition(int newPosition)
	{
		position = newPosition;
	}
	
	public void setAmount(int newAmount)
	{
		amount = newAmount;
	}
	
	public void resetReachedPosition()
	{
		this.reachedPosition = false;
	}
	
	public TruckState getTruckState()
	{
		return state;
	}
	
	public int getDesiredArticleID()
	{
		return this.desiredArticle;
	}
	
	public int getDesiredAmount()
	{
		return this.desiredAmount;
	}
	
	public void setTruckState(TruckState newTruckState)
	{
		state = newTruckState;
	}
	
	public boolean isDriving()
	{
		if (state == TruckState.DRIVING)
			return true;
		return false;
	}
	
	public boolean isSelling()
	{
		if (state == TruckState.SELLING)
			return true;
		return false;
	}
	
	public boolean isUnloading()
	{
		if (state == TruckState.UNLOADING)
			return true;
		return false;
	}
	
	public boolean isLoading() 
	{
		if (state == TruckState.LOADING)
			return true;
		return false;
	}
	
	@Override
	public boolean isIdling() 
	{
		if(state == TruckState.IDLE)
			return true;
		return false;
	}

	@Override
	public void unload() {
		state = TruckState.UNLOADING;
		targetPosition = Buildings.WAREHOUSE;
	}

	@Override
	public void sell() {
		state = TruckState.SELLING;
		targetPosition = Buildings.SALES;
	}
	
	@Override
	public void load(Article article, int desiredAmount) {
		
		this.load(article.getID(), desiredAmount);
	}
	
	public void load(int desiredArticleID, int desiredAmount) {
		
		this.desiredArticle = desiredArticleID;
		this.desiredAmount = desiredAmount;
		state = TruckState.LOADING;
		targetPosition = Buildings.WAREHOUSE;		
	}
	
	public void setDesiredArticle(int id)
	{
		this.desiredArticle = id;
	}
	
	public void setDesiredAmount(int amount)
	{
		this.desiredAmount = amount;
	}

	@Override
	public void resetState() {
		state = TruckState.IDLE;			
	}
	
	public void loadBoughtOffer(Offer offer)
	{
		setLoadedArticle(offer.getArticle());
		setAmount(offer.getAmount());
	}
	
	public boolean hasReachedPosition()
	{
		return reachedPosition;
	}

	public void updateData(Truck truck) 
	{
		position = truck.getPosition();
		loadedArticle = truck.getLoadedArticle();
		amount = truck.getAmount();
		targetPosition = truck.getTargetPosition();
		state = truck.getTruckState();
		reachedPosition = truck.hasReachedPosition();
		desiredArticle = truck.getDesiredArticleID();
		desiredAmount = truck.getDesiredAmount();
	}





}
