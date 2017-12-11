package control;

import model.Settings;

import view.GameDisplay;

public class Main {

	public static void main(String[] args) {
		
		Settings settings = new Settings();
		CompetitionControl control = new CompetitionControl(settings);
		GameDisplay gameDisplay = new GameDisplay(control, settings);
		gameDisplay.init();
		gameDisplay.show();
	}
}
