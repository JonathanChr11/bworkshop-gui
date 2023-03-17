package com.bworkshop;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	static Auth authStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		authStage = new Auth();
		authStage.setTitle("BWorkshop");
		authStage.show();
	}
}
