package com.bworkshop;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.config.DBConnection;
import com.utility.TransactionAdmin;

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

public class ViewTransactionAdmin {
	
	Scene sc;
	BorderPane bp;
	
	MenuBar menuBar;
	Menu adminMenu;
	MenuItem mngSparepart, mngUser, viewTfAdmin, logout;
	
	TableView<TransactionAdmin> tfAdminTable;

	ArrayList<TransactionAdmin> tfAdminList;
	
	int TransactionID;
	
	public ViewTransactionAdmin() {
		init();
		layout();
		style();
		setTable();
		refreshTable();
		setEvent();
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
		
		tfAdminTable = new TableView<>();
		
		tfAdminList = new ArrayList<>();
	}
	
	public void layout() {
		
		bp.setTop(menuBar);
		bp.setCenter(tfAdminTable);
		
	}
	
	public void style() {

		bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void setTable() {
		
		TableColumn<TransactionAdmin, Integer> transactionIdColumn = new TableColumn<TransactionAdmin, Integer>("TransactionID");
		TableColumn<TransactionAdmin, String> usernameColumn = new TableColumn<TransactionAdmin, String>("Username");
		TableColumn<TransactionAdmin, String> sparepartColumn = new TableColumn<TransactionAdmin, String>("Sparepart");
		TableColumn<TransactionAdmin, Integer> quantityColumn = new TableColumn<TransactionAdmin, Integer>("Quantity");
		
		transactionIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionAdmin, Integer>("TransactionID"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<TransactionAdmin, String>("Username"));
		sparepartColumn.setCellValueFactory(new PropertyValueFactory<TransactionAdmin, String>("Sparepart"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<TransactionAdmin, Integer>("Quantity"));
		
		tfAdminTable.getColumns().addAll(transactionIdColumn, usernameColumn, sparepartColumn, quantityColumn);
	}
	
	public void refreshTable() {
		tfAdminList.clear();
		getTransactionAdmin();
		ObservableList<TransactionAdmin> tfAdminObs = FXCollections.observableArrayList(tfAdminList);
		tfAdminTable.setItems(tfAdminObs);
	}
	
	public void getTransactionAdmin() {
		
		DBConnection dbConnect = DBConnection.getConnection();
		ResultSet rs = dbConnect.executeQuery("SELECT th.TransactionID, usr.Username, sp.SparePartName, td.Quantity FROM (((TransactionHeader th JOIN TransactionDetail td ON th.TransactionID = td.TransactionID) JOIN User usr ON th.UserID = usr.UserID) JOIN Sparepart sp ON td.SparePartID = sp.SparePartID)");
		
		try {
			while(rs.next()) {
				
				Integer TransactionID = rs.getInt(1);
				String Username = rs.getString(2);
				String Sparepart = rs.getString(3);
				Integer Quantity = rs.getInt(4);
				
				tfAdminList.add(new TransactionAdmin(TransactionID, Username, Sparepart, Quantity));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createAlert(AlertType alertType, String message) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public void setEvent() {

		tfAdminTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<TransactionAdmin> selectedTfAdmin = tfAdminTable.getSelectionModel();
				selectedTfAdmin.setSelectionMode(SelectionMode.SINGLE);
				
				TransactionAdmin tfAdmin = selectedTfAdmin.getSelectedItem();
				
				TransactionID = tfAdmin.getTransactionID();

			}
		});

	}
	
	public Scene getViewTransactionAdmin() {
		return sc;
	}
}
