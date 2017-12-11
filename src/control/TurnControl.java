package control;

import model.Competition;
import model.Settings;

/**
 * controls what happens each tick.
 * @author Max
 *
 */
public class TurnControl implements Runnable {
	
	private CompetitionControl compControl;
	private Competition competition;
	private Settings settings;
	
	public TurnControl(Competition competition,
					   CompetitionControl compControl, Settings settings){
		
		this.competition = competition;
		this.compControl = compControl;
		this.settings = settings;
		
	}

	@Override
	public void run() {
		while(competition.isRunning())
		{
			try {
				if (!settings.isPaused())
				{
					this.compControl.turn();
					Thread.sleep(settings.getSpeed().getSpeed());
				}
				else
				{
					Thread.sleep(100);
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
