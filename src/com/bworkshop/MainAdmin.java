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

public class MainAdmin {

	Scene sc;
	BorderPane bp;
	
	MenuBar menuBar;
	Menu adminMenu;
	MenuItem mngSparepart, mngUser, viewTfAdmin, logout;
	
	Image imgBackground;
	ImageView imgView;
	
	public MainAdmin() {
		init();
		layout();
		style();
	}
	
public void init() {
		
		bp = new BorderPane();
		
		sc = new Scene(bp, 900, 600);
		
		menuBar = new MenuBar();
		adminMenu = new Menu("Menu");
		
		mngSparepart = new MenuItem("Manage Sparepart");
		mngSparepart.setOnAction(e -> {
			Main.authStage.displayManageSparepartMenu();
		});
		
		mngUser = new MenuItem("Manage User");
		mngUser.setOnAction(e -> {
			Main.authStage.displayManageUser();
		});
		
		viewTfAdmin = new MenuItem("View Transaction");
		viewTfAdmin.setOnAction(e -> {
			Main.authStage.displayViewTransactionAdmin();
		});
		
		logout = new MenuItem("Logout");
		logout.setOnAction(e -> {
			Main.authStage.displayLogin();
		});
		
		adminMenu.getItems().addAll(mngSparepart, mngUser, viewTfAdmin, logout);
		
		menuBar.getMenus().addAll(adminMenu);
		
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
	
	public Scene getMainAdmin() {
		return sc;
	}
}
