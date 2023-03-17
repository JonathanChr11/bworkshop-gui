package com.bworkshop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.config.DBConnection;
import com.utility.Sparepart;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
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
import javafx.scene.text.Text;

	public class ManageSparepartMenu{
		
		int angka1,angka2,angka3;

		String GenerateID() {
		  String combination = null;
		  
		  if (angka1 !=9) {
		   angka1++;
		  }else if (angka1 ==9) {
		   angka1=0;
		   angka2++;
		  }else if(angka2 !=9) {
		   angka2++;
		  }else if(angka2 ==9){
		   angka2=0;
		   angka3++;
		  }else if(angka3!=9) {
		   angka3++;
		  }else if(angka3==9) {
		   angka3=0;
		   angka3++;
		  }
		  
		  combination = String.valueOf(angka3) + String.valueOf(angka2) + String.valueOf(angka1);
		  
		  return combination;
		 }

		Scene sc;
		BorderPane bp;
		
		GridPane gp, gp2, gp3;
		
		MenuBar menuBar;
		Menu adminMenu;
		MenuItem mngSparepart, mngUser, viewTfAdmin, logout;
		
		Text nameText, brandText, quantityText, priceText, updateStockText;
		Spinner<Integer>QtySpinner;
		Spinner<Integer>PriceSpinner;
		Spinner<Integer>UpSpinner;
		
		TextField inputNewSparepartName, inputNewSparepartBrand;
		
		Button InsertBtn, updateBtn, deleteBtn;
		
		TableView<Sparepart> sparepartTable;
		ArrayList<Sparepart> sparepartList;
		
		String SparePartID;
		
	 public ManageSparepartMenu() {
		 
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
			
			gp = new GridPane();
			gp2 = new GridPane();
			gp3 = new GridPane();
			
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
			
			nameText = new Text("Name");
			inputNewSparepartName = new TextField();
			
			brandText = new Text("Brand");
			inputNewSparepartBrand = new TextField();
			
			quantityText = new Text("Quantity");
			QtySpinner = new Spinner<>(1, 100, 0, 1);
			
			priceText = new Text("Price");
			PriceSpinner = new Spinner<>(10000, Integer.MAX_VALUE, 0, 1000);
			
			updateStockText = new Text("Update Stock");
			UpSpinner = new Spinner<>(0, 100, 0, 1);
			UpSpinner.setDisable(true);
			
			InsertBtn = new Button("Insert");
			
			updateBtn = new Button("Update");
			updateBtn.setDisable(true);
			
			deleteBtn = new Button("Delete");
			deleteBtn.setDisable(true);
			
			sparepartTable = new TableView<>();
			sparepartList = new ArrayList<>();
		}
		
		public void layout() {
			
			gp.add(nameText, 0, 0);
			gp.add(inputNewSparepartName, 2, 0);
			
			gp.add(brandText, 0, 1);
			gp.add(inputNewSparepartBrand, 2, 1);
			
			gp.add(quantityText, 0, 2);
			gp.add(QtySpinner, 2, 2);
			
			gp.add(priceText, 0, 3);
			gp.add(PriceSpinner, 2, 3);
			
			gp.add(InsertBtn, 1, 4);
			
			gp2.add(updateStockText, 1, 1);
			gp2.add(UpSpinner, 3, 1);
			gp2.add(updateBtn, 1, 2);
			gp2.add(deleteBtn, 2, 2);
			
			gp3.add(gp, 0, 0);
			gp3.add(gp2, 1, 0);
			
			bp.setTop(menuBar);
			bp.setCenter(sparepartTable);
			bp.setBottom(gp3);
			
		}
		
		public void style() {
			GridPane.setHalignment(gp, HPos.CENTER);
			GridPane.setValignment(gp, VPos.CENTER);
			
			GridPane.setHalignment(gp2, HPos.CENTER);
			GridPane.setValignment(gp2, VPos.CENTER);
			
			GridPane.setHalignment(gp3, HPos.CENTER);
			GridPane.setValignment(gp3, VPos.CENTER);
			
			BorderPane.setAlignment(sparepartTable, Pos.CENTER);
			BorderPane.setAlignment(gp, Pos.CENTER);
			BorderPane.setAlignment(gp2, Pos.CENTER);
			BorderPane.setAlignment(gp3, Pos.CENTER);
			
			gp.setHgap(10);
			gp.setVgap(10);

			gp2.setHgap(40);
			gp2.setVgap(10);
			
			gp3.setHgap(10);
			gp3.setVgap(10);
			
			bp.setMargin(gp3, new Insets(10));
			
			InsertBtn.setPadding(new Insets(5, 10, 5, 10));
			updateBtn.setPadding(new Insets(5, 10, 5, 10));
			deleteBtn.setPadding(new Insets(5, 10, 5, 10));

			bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		
		public void setTable() {
			
			TableColumn<Sparepart, String> sparepartIdColumn = new TableColumn<Sparepart, String>("ID");
			TableColumn<Sparepart, String> nameColumn = new TableColumn<Sparepart, String>("Name");
			TableColumn<Sparepart, String> brandColumn = new TableColumn<Sparepart, String>("Brand");
			TableColumn<Sparepart, Integer> priceColumn = new TableColumn<Sparepart, Integer>("Price");
			TableColumn<Sparepart, Integer> stockColumn = new TableColumn<Sparepart, Integer>("Stock");
			
			sparepartIdColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, String>("SparePartID"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, String>("SparePartName"));
			brandColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, String>("Brand"));
			priceColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, Integer>("Price"));
			stockColumn.setCellValueFactory(new PropertyValueFactory<Sparepart, Integer>("Stock"));
			
			sparepartTable.getColumns().addAll(sparepartIdColumn, nameColumn, brandColumn, priceColumn, stockColumn);
		}
		
		public void refreshTable() {
			sparepartList.clear();
			getSparepart();
			ObservableList<Sparepart> sparepartObs = FXCollections.observableArrayList(sparepartList);
			sparepartTable.setItems(sparepartObs);
		}
		
		public void getSparepart() {
			
			DBConnection dbConnect = DBConnection.getConnection();
			ResultSet rs = dbConnect.executeQuery("SELECT * FROM `Sparepart`");
			
			try {
				while(rs.next()) {
					
					String SparePartID = rs.getString(1);
					String SparePartName = rs.getString(2);
					String Brand = rs.getString(3);
					int Price = rs.getInt(4);
					int Stock= rs.getInt(5);
					
					sparepartList.add(new Sparepart(SparePartID, SparePartName, Brand, Price, Stock));
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
					
					inputNewSparepartName.setText(sparepart.getSparePartName());
					inputNewSparepartBrand.setText(sparepart.getBrand());
					QtySpinner.getValueFactory().setValue(sparepart.getStock());
					PriceSpinner.getValueFactory().setValue(sparepart.getPrice());
					
					SparePartID = sparepart.getSparePartID();
					
					UpSpinner.setDisable(false);
					updateBtn.setDisable(false);
					deleteBtn.setDisable(false);
				}
			});
			
			InsertBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					DBConnection dbConnect = DBConnection.getConnection();
					
					String GenerateID = "SP" + GenerateID();
					String SparePartID = GenerateID;
					String SparePartName = inputNewSparepartName.getText();
					String Brand = inputNewSparepartBrand.getText();
					int Stock = QtySpinner.getValue();
					int Price = PriceSpinner.getValue();
					
					if (SparePartName.length() == 0) {
						createAlert(AlertType.ERROR, "Sparepart Name Cannot be Empty!");
					} else if (SparePartName.length() < 5 || SparePartName.length() > 20) {
						createAlert(AlertType.ERROR, "Sparepart Name length must between 5 - 20 characters!");
					} else if (Brand.length() == 0) {
						createAlert(AlertType.ERROR, "Brand Cannot be Empty!");
					} else if (Stock < 0) {
						createAlert(AlertType.ERROR, "Quantity must be more than 0!");
					} else if (Price < 10000) {
						createAlert(AlertType.ERROR, "Price must be greater than 10000!");
					} else {
						createAlert(AlertType.INFORMATION, "Sparepart has been inserted!");
						dbConnect.execute(String.format("INSERT INTO `Sparepart` (`SparePartID`, `SparePartName`, `Brand`, `Price`, `Stock`) VALUES ('%s', '%s', '%s', '%d', '%d')", SparePartID, SparePartName, Brand, Price, Stock));
					}
					refreshTable();
				}
			});
			
			updateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					DBConnection dbConnect = DBConnection.getConnection();
					
					int UpdateStock = UpSpinner.getValue();
			
					dbConnect.executeUpdate(String.format("UPDATE `Sparepart` SET Stock = '%d' WHERE SparePartID = '%s'", UpdateStock, SparePartID));
					refreshTable();
				}
			});
			
			deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					DBConnection dbConnect = DBConnection.getConnection();
					
					Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete ?");
					Optional<ButtonType> option = alert.showAndWait();
					
					if (option.get().equals(ButtonType.OK)) {
						createAlert(AlertType.INFORMATION, "Sparepart has been deleted!");
						PreparedStatement ps = dbConnect.preparedStatement("DELETE FROM `Sparepart` WHERE SparePartID=?");
						try {
							ps.setString(1, SparePartID);
							ps.execute();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					refreshTable();
				}
			});
			
		}

		public Scene getManageSparepartMenu() {
			return sc;
		}
	}