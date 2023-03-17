package com.bworkshop;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.config.DBConnection;
import com.utility.TransactionUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UserTableView {
	
	Scene sc;
	BorderPane bp;
	
	MenuBar menuBar;
	Menu userMenu;
	MenuItem buySparepart, viewTransaction, logout;
	
	TableView<TransactionUser> tfUserTable;
	ArrayList<TransactionUser> tfUserList;
	
	int TransactionID;
	
	public UserTableView() {
		init();
		layout();
		style();
		setTable();
		refreshTable();
	}
	
public void init() {
		
		bp = new BorderPane();
		
		sc = new Scene(bp, 900, 600);
		
		menuBar = new MenuBar();
		userMenu = new Menu("Menu");
		
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
		
		userMenu.getItems().addAll(buySparepart, viewTransaction, logout);
		
		menuBar.getMenus().addAll(userMenu);
		
		tfUserTable = new TableView<>();
		
		tfUserList = new ArrayList<>();
	}
	
	public void layout() {
		
		bp.setTop(menuBar);
		bp.setCenter(tfUserTable);
		
	}
	
	public void style() {

		bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void setTable() {
		
		TableColumn<TransactionUser, Integer> transactionIdColumn = new TableColumn<TransactionUser, Integer>("TransactionID");
		TableColumn<TransactionUser, String> sparepartColumn = new TableColumn<TransactionUser, String>("Sparepart");
		TableColumn<TransactionUser, String> brandColumn = new TableColumn<TransactionUser, String>("Brand");
		TableColumn<TransactionUser, Integer> quantityColumn = new TableColumn<TransactionUser, Integer>("Quantity");
		TableColumn<TransactionUser, Integer> priceColumn = new TableColumn<TransactionUser, Integer> ("Sparepart Price");
		
		transactionIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionUser, Integer>("TransactionID"));
		sparepartColumn.setCellValueFactory(new PropertyValueFactory<TransactionUser, String>("SparePartName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<TransactionUser, String>("Brand"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<TransactionUser, Integer>("Stock"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<TransactionUser, Integer>("Price"));
		
		tfUserTable.getColumns().addAll(transactionIdColumn, sparepartColumn, brandColumn, quantityColumn, priceColumn);
	}
	
	public void refreshTable() {
		tfUserList.clear();
		getTransactionUser();
		ObservableList<TransactionUser> tfUserObs = FXCollections.observableArrayList(tfUserList);
		tfUserTable.setItems(tfUserObs);
	}
	
	public void getTransactionUser() {
		
		DBConnection dbConnect = DBConnection.getConnection();
		ResultSet rs = dbConnect.executeQuery("SELECT th.TransactionID, sp.SparePartName, sp.Brand, sp.Stock, sp.Price FROM ((TransactionHeader th JOIN TransactionDetail td ON th.TransactionID = td.TransactionID) JOIN Sparepart sp ON td.SparePartID = sp.SparePartID)");
		
		try {
			while(rs.next()) {
				
				Integer TransactionID = rs.getInt(1);
				String Sparepart = rs.getString(2);
				String Brand = rs.getString(3);
				Integer Stock = rs.getInt(4);
				Integer Price = rs.getInt(5);
				
				
				tfUserList.add(new TransactionUser(TransactionID, Sparepart, Brand, Stock, Price));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scene getUserTableView() {
		return sc;
	}
}
