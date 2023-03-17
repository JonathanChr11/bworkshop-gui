package com.bworkshop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.config.DBConnection;
import com.utility.Sparepart;
import com.utility.SparepartCart;
import com.utility.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BuySparepartMenu {
	
	Scene sc;
	BorderPane bp;
	
	GridPane gp, gp2, gp3, gp4, gp5;
	
	MenuBar menuBar;
	Menu customerMenu;
	MenuItem buySparepart, viewTfCustomer, logout;
	
	Text buySparepartText, quantityText, cartListText;
	
	TextField invisPrice, invisStock;
	
	Spinner<Integer> quantitySpin;
	
	Button addBtn, checkoutBtn, clearBtn;
	
	ArrayList<User> userList;
	
	TableView<Sparepart> sparepartTable;
	ArrayList<Sparepart> sparepartList;
	
	TableView<SparepartCart> sparepartCartTable;
	ArrayList<SparepartCart> sparepartCartList;
	
	Integer UserID;
	String SparePartID;
	
	public BuySparepartMenu() {
		init();
		layout();
		style();
		setTableSparepart();
		setTableSparepartCart();
		refreshTableSparepart();
		refreshTableSparepartCart();
		setEvent();
	}
	
	public void init() {
		
		bp = new BorderPane();
		
		sc = new Scene(bp, 900, 600);
		
		gp = new GridPane();
		gp2 = new GridPane();
		gp3 = new GridPane();
		gp4 = new GridPane();
		gp5 = new GridPane();
		
		menuBar = new MenuBar();
		customerMenu = new Menu("Menu");
		
		buySparepart = new MenuItem("Buy Sparepart");
		buySparepart.setOnAction(e -> {
			Main.authStage.displayBuySparepartMenu();
		});
		
		viewTfCustomer = new MenuItem("View Your Transaction");
		viewTfCustomer.setOnAction(e -> {
			Main.authStage.displayUserTableView();
		});
		
		logout = new MenuItem("Logout");
		logout.setOnAction(e -> {
			Main.authStage.displayLogin();
		});
		
		customerMenu.getItems().addAll(buySparepart, viewTfCustomer, logout);
		
		menuBar.getMenus().addAll(customerMenu);
		
		buySparepartText = new Text("Buy Sparepart");
		quantityText = new Text("Quantity");

		invisPrice = new TextField();
		invisPrice.setVisible(false);
		invisStock = new TextField();
		invisStock.setVisible(false);
		
		quantitySpin = new Spinner<>(1, 100, 1, 1);
		quantitySpin.setDisable(true);
		
		cartListText = new Text("Cart List");
		
		addBtn = new Button("Add to cart");
		addBtn.setDisable(true);
		
		checkoutBtn = new Button("Checkout");
		
		clearBtn = new Button("Clear cart");
		
		sparepartTable = new TableView<>();
		sparepartCartTable = new TableView<>();
		
		userList = new ArrayList<>();
		sparepartList = new ArrayList<>();
		sparepartCartList = new ArrayList<>();
	}
	
	public void layout() {
		
		// Buy Sparepart layouting
		gp.add(invisPrice, 0, 0);
		gp.add(invisStock, 1, 0);
		gp.add(buySparepartText, 0, 2);
		gp.add(quantityText, 0, 3);
		gp.add(quantitySpin, 1, 3);
		gp.add(addBtn, 0, 4);
		
		// baris 1 layouting
		gp2.add(sparepartTable, 0, 0);
		gp2.add(gp, 1, 0);
		
		// Cartlist layouting
		gp3.add(cartListText, 0, 2);
		gp3.add(checkoutBtn, 0, 3);
		gp3.add(clearBtn, 1, 3);
		
		gp4.add(sparepartCartTable, 0, 0);
		gp4.add(gp3, 1, 0);
		
		gp5.add(gp2, 0, 0);
		gp5.add(gp4, 0, 1);
		
		bp.setTop(menuBar);
		bp.setCenter(gp5);
	}
	
	public void style() {
		
		GridPane.setHalignment(gp, HPos.CENTER);
		GridPane.setValignment(gp, VPos.CENTER);
		
		GridPane.setHalignment(gp2, HPos.CENTER);
		GridPane.setValignment(gp2, VPos.CENTER);
		
		GridPane.setHalignment(gp3, HPos.CENTER);
		GridPane.setValignment(gp3, VPos.CENTER);
		
		GridPane.setHalignment(gp4, HPos.CENTER);
		GridPane.setValignment(gp4, VPos.CENTER);
		
		GridPane.setHalignment(gp5, HPos.CENTER);
		GridPane.setValignment(gp5, VPos.CENTER);
		
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(gp2, Pos.CENTER);
		BorderPane.setAlignment(gp3, Pos.CENTER);
		BorderPane.setAlignment(gp4, Pos.CENTER);
		BorderPane.setAlignment(gp5, Pos.CENTER);
		
		gp.setHgap(20);
		gp.setVgap(20);
		
		gp2.setHgap(40);
		gp2.setVgap(40);
		
		gp3.setHgap(40);
		gp3.setVgap(40);
		
		gp4.setHgap(40);
		gp4.setVgap(40);

		addBtn.setPadding(new Insets(5, 10, 5, 10));
		checkoutBtn.setPadding(new Insets(7, 30, 7, 30));
		clearBtn.setPadding(new Insets(7, 30, 7, 30));

		buySparepartText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		cartListText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		
	}
	
	public void setTableSparepart() {
		
		TableColumn<Sparepart, String> sparepartNameColumn = new TableColumn<Sparepart, String>("Name");
		TableColumn<Sparepart, String> brandColumn = new TableColumn<Sparepart, String>("Brand");
		TableColumn<Sparepart, Integer> priceColumn = new TableColumn<Sparepart, Integer>("Price");
		TableColumn<Sparepart, Integer> stockColumn = new TableColumn<Sparepart, Integer>("Stock");
		
		sparepartNameColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, String>("SparePartName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, String>("Brand"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, Integer>("Price"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, Integer>("Stock"));
		
		sparepartTable.getColumns().addAll(sparepartNameColumn, brandColumn, priceColumn, stockColumn);
	}
	
	public void setTableSparepartCart() {
		
		TableColumn<SparepartCart, String> sparepartNameColumn = new TableColumn<SparepartCart, String>("Sparepart Name");
		TableColumn<SparepartCart, String> brandColumn = new TableColumn<SparepartCart, String>("Brand");
		TableColumn<SparepartCart, Integer> quantityColumn = new TableColumn<SparepartCart, Integer>("Quantity");
		TableColumn<SparepartCart, Integer> priceColumn = new TableColumn<SparepartCart, Integer>("Price");
		TableColumn<SparepartCart, Integer> totalColumn = new TableColumn<SparepartCart, Integer>("Total");
		
		sparepartNameColumn.setCellValueFactory(new PropertyValueFactory<SparepartCart, String>("SparePartName"));
		brandColumn.setCellValueFactory(new PropertyValueFactory<SparepartCart, String>("Brand"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<SparepartCart, Integer>("Quantity"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<SparepartCart, Integer>("Price"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<SparepartCart, Integer>("Total"));
		
		sparepartCartTable.getColumns().addAll(sparepartNameColumn, brandColumn, quantityColumn, priceColumn, totalColumn);
	}
	
	public void refreshTableSparepart() {
		sparepartList.clear();
		getSparepart();
		ObservableList<Sparepart> sparepartObs = FXCollections.observableArrayList(sparepartList);
		sparepartTable.setItems(sparepartObs);
	}
	
	public void refreshTableSparepartCart() {
		sparepartCartList.clear();
		getSparepartCart();
		ObservableList<SparepartCart> sparepartCartObs = FXCollections.observableArrayList(sparepartCartList);
		sparepartCartTable.setItems(sparepartCartObs);
	}
	
	public void getSparepart() {
		
		DBConnection dbConnect = DBConnection.getConnection();
		ResultSet rs = dbConnect.executeQuery("SELECT * FROM `Sparepart`");
		
		try {
			while(rs.next()) {
				
				String SparePartID = rs.getString(1);
				String SparePartName = rs.getString(2);
				String Brand = rs.getString(3);
				Integer Price = rs.getInt(4);
				Integer Stock = rs.getInt(5);
				
				sparepartList.add(new Sparepart(SparePartID, SparePartName, Brand, Price, Stock));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getSparepartCart() {
		
		DBConnection dbConnect = DBConnection.getConnection();
		ResultSet rs = dbConnect.executeQuery("SELECT sp.SparePartName, sp.Brand, spc.Quantity, sp.Price, spc.UserID FROM (SparepartCart spc JOIN Sparepart sp ON spc.SparePartID = sp.SparePartID)");
		
		try {
			while(rs.next()) {
				
				String SparePartName = rs.getString(1);
				String Brand = rs.getString(2);
				Integer Quantity = rs.getInt(3);
				Integer Price = rs.getInt(4);
				Integer Total = Quantity*Price;
				Integer UserID = rs.getInt(5);
				
				sparepartCartList.add(new SparepartCart(SparePartName, Brand, Quantity, Price, Total, UserID));
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

		sparepartTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<Sparepart> selectedSparepart = sparepartTable.getSelectionModel();
				selectedSparepart.setSelectionMode(SelectionMode.SINGLE);
				
				Sparepart sparepart = selectedSparepart.getSelectedItem();
				
				invisPrice.setText(String.valueOf(sparepart.getPrice()));
				invisStock.setText(String.valueOf(sparepart.getStock()));
				
				SparePartID = sparepart.getSparePartID();
				
				quantitySpin.setDisable(false);
				addBtn.setDisable(false);
			}
		});
		
		addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DBConnection dbConnect = DBConnection.getConnection();
				
				String stockStr = invisStock.getText();
				int Stock = Integer.valueOf(stockStr);
				String priceStr = invisPrice.getText();
				int Price = Integer.valueOf(priceStr);
				int Quantity = quantitySpin.getValue();
				int Total = Quantity*Price;
				
				if (Quantity < 0) {
					createAlert(AlertType.ERROR, "Quantity must be more than 0!");
				} else if (Quantity > Stock) {
					createAlert(AlertType.ERROR, "Quantity cannot be more than sparepart’s stock!");
				} else {
					createAlert(AlertType.INFORMATION, "Sparepart added to your cart!");
					dbConnect.execute(String.format("INSERT INTO `SparepartCart` (`SparePartID`, `UserID`, `Quantity`) VALUES ('%s', '%s', '%d')", SparePartID, UserID, Quantity));
				}
				refreshTableSparepartCart();
			}
		});
		
		checkoutBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DBConnection dbConnect = DBConnection.getConnection();
				createAlert(AlertType.INFORMATION, "Check out success!");
				refreshTableSparepartCart();
			}
		});
		
		clearBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DBConnection dbConnect = DBConnection.getConnection();
				createAlert(AlertType.INFORMATION, "Cart is cleared!");
				PreparedStatement ps = dbConnect.preparedStatement("DELETE FROM `SparepartCart`");
				refreshTableSparepartCart();
			}
		});
		
	}

	public Scene getBuySparepartMenu() {
		return sc;
	}
}
