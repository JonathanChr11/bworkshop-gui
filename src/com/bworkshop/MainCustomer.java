package com.bworkshop;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainCustomer {
	Scene sc;
	BorderPane bp;
	
	MenuBar menuBar;
	Menu CustomerMenu;
	MenuItem buySparepart, viewTransaction, logout;
	
	Image imgBackground;
	ImageView imgView;
	
	public MainCustomer() {
		init();
		layout();
		style();
	}
public void init() {
		
		bp = new BorderPane();
		
		sc = new Scene(bp, 900, 600);
		
		menuBar = new MenuBar();
		CustomerMenu = new Menu("Menu");
		
		buySparepart = new MenuItem("Buy Sparepart");
		buySparepart.setOnAction(e -> {
			Main.authStage.displayBuySparepartMenu();
		});
		
		viewTransaction = new MenuItem("View Your Transaction");
		viewTransaction.setOnAction(e -> {
			Main.authStage.displayUserTableView();
		});
		
		logout = new MenuItem("Logout");
		logout.setOnAction(e -> {
			Main.authStage.displayLogin();
		});
		
		CustomerMenu.getItems().addAll(buySparepart, viewTransaction, logout);
		
		menuBar.getMenus().addAll(CustomerMenu);
		
		imgBackground = new Image("file:ProjectBackground.png");
		imgView = new ImageView(imgBackground);
		
	}
	
	public void layout() {
		
		imgView.setFitWidth(900);
		imgView.setFitHeight(600);
		
		bp.setTop(menuBar);
		bp.setCenter(imgView);
		
	}
	
	public void style() {

		bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public Scene getMainCustomer() {
		return sc;
	}
}
