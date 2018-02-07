package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import model.Companies;
import model.Settings;

public class OptionsDisplay extends JFrame {
	
	private Settings settings;
	
	private JSpinner gameDuration;
	
	private JSpinner ticksUntilNextOffer;
	
	private JSpinner ticksUntilDeleteOffer;
	
	private JSpinner offersPerPlayer;
	
	private JSpinner ticksUntilMaxPrice;
	
	private JSpinner maxPriceIncrease;
	
	private JSpinner ticksUntilOfferDecrease;
	
	private JLabel content;
	
	private JButton okButton;
	
	private JButton cancelButton;
	
	private Companies companies;
	
	public OptionsDisplay(Settings settings, Companies comps)
	{
		this.settings = settings;
		this.companies = comps;
	}
	
	public void init()
	{
		this.setTitle("Options");
		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(800,600));
		this.setResizable(false);
		content = new JLabel();
		content.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.initSpinners();
		this.initOkButton();
		this.initCancelButton();
		this.initCompaniesButton();
		c.weightx = 0.1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.25;
		content.add(new JLabel("Duration in ticks : "),c);
		c.gridy = 0;
		c.gridx = 1;
		content.add(gameDuration, c);
		c.gridx = 0;
		c.gridy = 1;
		content.add(new JLabel("Ticks until next offer: "), c);
		c.gridx = 1;
		content.add(ticksUntilNextOffer, c);
		this.add(content);
		c.gridx = 0;
		c.gridy = 2;
		content.add(new JLabel("Ticks until an offer vanishes: "), c);
		c.gridx = 1;
		content.add(this.ticksUntilDeleteOffer, c);
		c.gridx = 0;
		c.gridy = 3;
		content.add(new JLabel("Offers generated per player: "), c);
		c.gridx = 1;
		content.add(this.offersPerPlayer, c);
		c.gridx = 0;
		c.gridy = 4;
		content.add(new JLabel("Ticks until max price: "), c);
		c.gridx = 1;
		content.add(this.ticksUntilMaxPrice, c);
		c.gridx = 0;
		c.gridy = 5;
		content.add(new JLabel("Max price multiplier: "), c);
		c.gridx = 1;
		content.add(this.maxPriceIncrease, c);
		c.gridx = 0;
		c.gridy = 6;
		content.add(new JLabel("Turns until offer prices decrease: "), c);
		c.gridx = 1;
		content.add(this.ticksUntilOfferDecrease, c);
		c.gridx = 0;
		c.gridy = 7;
		content.add(this.okButton, c);
		c.gridx = 1;
		content.add(this.cancelButton, c);
	}
	
	public void start()
	{
		this.settings.setPause(true);
		this.pack();
		this.setVisible(true);	
		this.repaint();
	}
	
	private void initSpinners()
	{
		gameDuration = new JSpinner(new SpinnerNumberModel(
									settings.getMaxTicks(), 1000, 100000, 100));
		
		ticksUntilNextOffer = new JSpinner(new SpinnerNumberModel(
									settings.getTicksUntilNextOffer(), 100, 400, 1));
		
		ticksUntilDeleteOffer = new JSpinner(new SpinnerNumberModel(
									settings.getTicksUntilDeleteOffer(), 200, 800, 1));
		
		offersPerPlayer = new JSpinner(new SpinnerNumberModel(
									settings.getOffersGeneratedPerPlayer(), 0.5, 3, 0.5));
		
		maxPriceIncrease = new JSpinner(new SpinnerNumberModel(
									settings.getMaxArticlePriceIncrease(), 1, 5, 0.1));
		maxPriceIncrease.setPreferredSize(new Dimension(50,25));
		
		ticksUntilMaxPrice = new JSpinner(new SpinnerNumberModel(
									settings.getTurnsUntilMaxPriceIncrease(), 100, 5000, 50));
		
		ticksUntilOfferDecrease = new JSpinner(new SpinnerNumberModel(
									settings.getTicksUntilDecreasePrice(), 1, 300, 1));
	}
	
	private void initOkButton()
	{
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				applySettings();	
				closeWindow();
			}	
		});
	}		
	private void initCancelButton()
	{
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeWindow();	
			}		
		});
	}
	
	private void initCompaniesButton()
	{
		cancelButton = new JButton("Companies");
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SelectCompaniesDisplay selectCompanies = new SelectCompaniesDisplay(companies);
				selectCompanies.init();
				selectCompanies.start();
			}		
		});		
	}

	private void applySettings()
	{
		settings.setMaxTicks((int) gameDuration.getValue());
		settings.setTicksUntilNextOffer((int)ticksUntilNextOffer.getValue());
		settings.setTicksUntilDeleteOffer((int) ticksUntilDeleteOffer.getValue());
		settings.setOffersGeneratedPerPlayer((double) offersPerPlayer.getValue());
		settings.setMaxArticlePriceIncrease((double) maxPriceIncrease.getValue());
		settings.setTurnsUntilMaxPriceIncrease((int) ticksUntilMaxPrice.getValue());
		settings.setTicksUntilDecreasePrice((int) ticksUntilOfferDecrease.getValue());
	}
	
	private void closeWindow() 
	{
		this.dispose();
	}
}
