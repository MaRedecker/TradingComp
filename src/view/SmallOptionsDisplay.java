package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;

import model.Settings;

public class SmallOptionsDisplay extends JPanel {
	
	private OptionsDisplay optionsMenu;
	private Settings settings;
	
	public SmallOptionsDisplay(Settings settings)
	{   	
		addOptionsButton();
		this.settings = settings;
		
	}
	
	private void addOptionsButton()
	{
		JButton button = new JButton("Options");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				showOptions();
			}
			
		});
		this.add(button);
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }
    
    private void showOptions()
    {
    	optionsMenu = new OptionsDisplay(settings);
    	optionsMenu.init();
    	optionsMenu.start();
    }
	
	

}
