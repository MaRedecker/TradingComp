package model;

public interface TruckInformation {
	
	public int getDistanceToTarget();
	
	public int getDistanceToTarget(int target);
	
	public int getTargetPosition();
	
	public boolean isLoaded();
	
	public int getAmount();
	
	public void driveTo(int targetPos);
	
	public int getPosition();
	
	public Article getLoadedArticle();
	
	public void unload();
	
	public void sell();
	
	public void load(Article article, int amount);
	
	public void resetState();
	
	public boolean isDriving();
	
	public boolean isSelling();
	
	public boolean isUnloading();
	
	public boolean isIdling();

}
