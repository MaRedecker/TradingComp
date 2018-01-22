package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Competition;

public class StartAndStopButton extends JPanel {
	
	private Competition competition;
	
	private JButton button;
	
	public StartAndStopButton(Competition competition)
	{
		this.competition = competition;
		String buttonName;
		if (competition.isRunning())
		{
			buttonName = "Stop";
		}
		else
		{
			buttonName = "Start";
		}
		button = new JButton(buttonName);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (competition.isRunning())
				{
					competition.setRunning(false);
					competition.resetTurns();
					button.setText("Start");
				}
				else
				{
					button.setText("Stop");
					competition.setRunning(true);
					
				}
				StartAndStopButton.this.repaint();
				
			}
		});
			
		this.add(button);
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }
    
    public void reset()
    {
    	button.setText("Start");
    	StartAndStopButton.this.repaint();
    }
}
