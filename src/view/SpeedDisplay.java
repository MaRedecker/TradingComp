package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Settings;
import model.Speed;

public class SpeedDisplay extends JPanel {
	
	private Settings settings;
	
	public SpeedDisplay(Settings settings)
	{   	
		this.settings = settings;
        final JComboBox<Speed> speed = new JComboBox<Speed>();
        final JLabel label = new JLabel("Speed: ");
        speed.addItem(Speed.VERYFAST);
        speed.addItem(Speed.FAST);
        speed.addItem(Speed.NORMAL);
        speed.addItem(Speed.SLOW);
        speed.addItem(Speed.VERYSLOW);
        speed.setSelectedItem(settings.getSpeed());
        this.add(label);
        speed.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(final ActionEvent event) {
                    	
                    	SpeedDisplay.this.settings.setSpeed((Speed)speed.getSelectedItem());
                    }
                });
        this.add(speed);
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 50);
    }
}
