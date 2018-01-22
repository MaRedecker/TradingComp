package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import control.CompetitionControl;
import model.Companies;
import model.Competition;
import model.Settings;
import model.TendingOffers;

public class GameDisplay implements Observer {
	
	private JFrame mainWindow;
	private TendingOffers offers;
	private Companies companies;
	private SpeedDisplay speedDisplay;
	private SmallOptionsDisplay smallOptionsDisplay;
	private PauseDisplay pauseDisplay;
	private BoardDisplay boardDisplay;
	private Settings settings;
	private CompetitionControl competitionControl;
	private Competition competition;
	private StartAndStopButton startAndStopButton;
	private boolean isRunning;
	
	
	public GameDisplay(CompetitionControl competitionControl, Settings settings)
	{
		mainWindow = new JFrame("TradingGame");
		mainWindow.setMinimumSize(new Dimension(800,600));
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.settings = settings;
		this.competition = competitionControl.getCompetition();
		this.competitionControl = competitionControl;
		this.competition.addObserver(this);
		this.isRunning = false;
	}
	
	public void init()
	{
		this.mainWindow.getContentPane().removeAll();
		this.offers = competitionControl.getTendingOffers();
		this.companies = competitionControl.getCompanies();
		this.speedDisplay = new SpeedDisplay(settings);
		this.smallOptionsDisplay = new SmallOptionsDisplay(settings);
		this.boardDisplay = new BoardDisplay(this.offers, this.companies);
		this.pauseDisplay = new PauseDisplay(settings, competition);
		this.speedDisplay = new SpeedDisplay(settings);
		this.startAndStopButton = new StartAndStopButton(competition);
		this.settings.addObserver(pauseDisplay);
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridwidth = 4;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		content.add(this.boardDisplay, c);
		c.weightx = 0.25;
		c.weighty = 0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		content.add(this.speedDisplay, c);
		c.gridx = 1;
		content.add(this.pauseDisplay, c);
		c.gridx = 2;
		content.add(this.startAndStopButton, c);
		c.gridx = 3;
		c.gridy = 1;
		content.add(smallOptionsDisplay, c);		
		mainWindow.getContentPane().add(content);
		this.show();
	}
	
	public void show()
	{
		mainWindow.pack();
		mainWindow.setVisible(true);		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (competition.isRunning())
		{
			if (this.isRunning == true)
			{
				mainWindow.repaint();
				mainWindow.setTitle("TradingGame Tick: " + this.competition.getTurns());
			}
			else
			{
				this.init();
				this.isRunning = true;
			}
		}
		else
		{
			this.isRunning = false;
		}
	}
}
