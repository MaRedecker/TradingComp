package model;

/**
 * Allows access to important truck informations and orders.
 * @author Max
 *
 */
public interface TruckInformation {
	
	/**
	 * 
	 * @return The remaining distance (in ticks) to the Destination.
	 */
	public int getDistanceToTarget();
	
	/**
	 * 
	 * @param target the specified target.
	 * @return The remaining distance(in ticks) to the specified target.
	 */
	public int getDistanceToTarget(int target);
	
	/**
	 * 
	 * @return current target position.
	 */
	public int getTargetPosition();
	
	/**
	 * 
	 * @return has the truck anything on board?
	 */
	public boolean isLoaded();
	
	/**
	 * 
	 * @return amount of articles the truck has stored.
	 */
	public int getAmount();
	
	/**
	 * drive to the target position.
	 * @param targetPos the new destination.
	 */
	public void driveTo(int targetPos);
	
	/**
	 * 
	 * @return current position.
	 */
	public int getPosition();
	
	/**
	 * 
	 * @return current loaded article type. If there is none, returns null. 
	 */
	public Article getLoadedArticle();
	
	/**
	 * set destination to the warehouse and unloads all articles there.
	 */
	public void unload();
	
	/**
	 * set destination to the sales and sell everything which is currently on board.
	 */
	public void sell();
	
	/**
	 * load an amount of an article type on the truck.
	 * @param article the desired article type.
	 * @param amount the desired amount.
	 */
	public void load(Article article, int amount);
	
	/**
	 * stop the current action.
	 */
	public void resetState();
	
	/**
	 * 
	 * @return is the truck driving to a location? 
	 */
	public boolean isDriving();
	
	/**
	 * 
	 * @return does the truck try to sell something?
	 */
	public boolean isSelling();
	
	/**
	 * 
	 * @return is the truck on the way to the warehouse to unload?
	 */
	public boolean isUnloading();
	
	/**
	 * 
	 * @return does the truck do nothing?
	 */
	public boolean isIdling();

}
