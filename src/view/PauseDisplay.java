package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Competition;
import model.Settings;

public class PauseDisplay extends JPanel implements Observer {
	
	private JButton button;
	private Competition competition;
	private Settings settings;
	
	public PauseDisplay(Settings settings, Competition competition)
	{
		this.settings = settings;
		String buttonText;
		if (settings.isPaused())
			buttonText = "Unpause";
		else
			buttonText = "Pause";
		this.button = new JButton(buttonText);
		this.competition = competition;
		this.setButton();
	}
	
	public void setRunning(boolean isRunning)
	{
		competition.setRunning(isRunning);
	}
	
	public void setButton()
	{
		this.button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (competition.isRunning())
					PauseDisplay.this.settings.setPause(!settings.isPaused());
				if (settings.isPaused())
					button.setText("Unpause");
				else
					button.setText("Pause");	
			}
		});
		this.add(this.button);
	}
	
    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(100, 50);
    }

	@Override
	public void update(Observable arg0, Object arg1) 
	{
		if (settings.isPaused())
			button.setText("Unpause");
		else
			button.setText("Pause");	
	}
	
	
	
	
}
