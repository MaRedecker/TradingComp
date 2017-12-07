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

import model.Settings;

public class OptionsDisplay extends JFrame {
	
	private Settings settings;
	
	private JSpinner gameDuration;
	
	private JSpinner ticksUntilNextOffer;
	
	private JSpinner ticksUntilDeleteOffer;
	
	private JSpinner offersPerPlayer;
	
	private JLabel content;
	
	private JButton okButton;
	
	private JButton cancelButton;
	
	public OptionsDisplay(Settings settings)
	{
		this.settings = settings;
	}
	
	public void init()
	{
		this.setTitle("Options");
		this.setMinimumSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(800,600));
		this.setResizable(false);
		content = new JLabel();
		content.setLayout(new GridBagLayout());
		//content.setSize(800, 600);
		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		this.initSpinners();
		this.initOkButton();
		this.initCancelButton();
		c.weightx = 0.1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.25;
		content.add(new JLabel("Duration in Ticks : "),c);
		c.gridy = 0;
		c.gridx = 1;
		content.add(gameDuration, c);
		c.gridx = 0;
		c.gridy = 1;
		content.add(new JLabel("Ticks until next Offer: "), c);
		c.gridx = 1;
		content.add(ticksUntilNextOffer, c);
		this.add(content);
		c.gridx = 0;
		c.gridy = 2;
		//c.anchor = GridBagConstraints.NORTHEAST;
		content.add(new JLabel("Ticks until an Offer vanishes: "), c);
		c.gridx = 1;
		//c.anchor = GridBagConstraints.NORTHWEST;
		content.add(this.ticksUntilDeleteOffer, c);
		c.gridx = 0;
		c.gridy = 3;
		//c.anchor = GridBagConstraints.NORTHEAST;
		content.add(new JLabel("Offers generated per Player: "), c);
		c.gridx = 1;
		//c.anchor = GridBagConstraints.NORTHWEST;
		content.add(this.offersPerPlayer, c);
		c.gridx = 0;
		c.gridy = 4;
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
		gameDuration = new JSpinner(new SpinnerNumberModel(settings.getMaxTicks(), 10000, 100000, 100));
		ticksUntilNextOffer = new JSpinner(new SpinnerNumberModel(settings.getTicksUntilNextOffer(), 100, 400, 1));
		ticksUntilDeleteOffer = new JSpinner(new SpinnerNumberModel(settings.getTicksUntilDeleteOffer(), 200, 800, 1));
		offersPerPlayer = new JSpinner(new SpinnerNumberModel(settings.getOffersGeneratedPerPlayer(), 1, 3, 1));
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

	private void applySettings()
	{
		settings.setMaxTicks((int) gameDuration.getValue());
		settings.setTicksUntilNextOffer((int)ticksUntilNextOffer.getValue());
		settings.setTicksUntilDeleteOffer((int) ticksUntilDeleteOffer.getValue());
		settings.setOffersGeneratedPerPlayer((int) offersPerPlayer.getValue());
	}
	
	private void closeWindow()
	{
		this.dispose();
	}

}
