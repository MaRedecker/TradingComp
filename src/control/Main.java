package control;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.Settings;
import view.BoardDisplay;
import view.GameDisplay;
import view.SmallOptionsDisplay;
import view.SpeedDisplay;

public class Main {

	public static void main(String[] args) {
		
		Settings settings = new Settings();
		CompetitionControl control = new CompetitionControl(settings);
		//control.run();
		GameDisplay gameDisplay = new GameDisplay(control, settings);
		gameDisplay.init();
		gameDisplay.show();
		

	}

}
