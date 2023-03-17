package com.bworkshop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.config.DBConnection;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
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

public class ManageUser {
	
	Scene sc;
	BorderPane bp;
	
	GridPane gp;
	
	MenuBar menuBar;
	Menu adminMenu;
	MenuItem mngSparepart, mngUser, viewTfAdmin, logout;
	
	Text newUsernameText, newPasswordText;
	
	TextField inputNewUsername;
	
	PasswordField inputNewPassword;
	
	Button updateBtn, deleteBtn;
	
	TableView<User> userTable;
	ArrayList<User> userList;
	
	int UserID;
	
	public ManageUser() {
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
		
		newUsernameText = new Text("New Username");
		inputNewUsername = new TextField();
		inputNewUsername.setDisable(true);
		
		newPasswordText = new Text("New Password");
		inputNewPassword = new PasswordField();
		inputNewPassword.setDisable(true);
		
		updateBtn = new Button("Update");
		updateBtn.setDisable(true);
		deleteBtn = new Button("Delete");
		deleteBtn.setDisable(true);
		
		userTable = new TableView<>();
		
		userList = new ArrayList<>();
	}
	
	public void layout() {
		
		gp.add(newUsernameText, 0, 0);
		gp.add(inputNewUsername, 2, 0);
		
		gp.add(newPasswordText, 0, 1);
		gp.add(inputNewPassword, 2, 1);
		
		gp.add(updateBtn, 0, 2);
		gp.add(deleteBtn, 1, 2);
		
		bp.setTop(menuBar);
		bp.setCenter(userTable);
		bp.setBottom(gp);
		
	}
	
	public void style() {
		GridPane.setHalignment(gp, HPos.CENTER);
		GridPane.setValignment(gp, VPos.CENTER);
		
		BorderPane.setAlignment(userTable, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		
		gp.setAlignment(Pos.TOP_LEFT);
		
		gp.setHgap(40);
		gp.setVgap(40);
		
		bp.setMargin(gp, new Insets(10));
		
		updateBtn.setPadding(new Insets(7, 37, 7, 37));
		deleteBtn.setPadding(new Insets(7, 40, 7, 40));

		bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void setTable() {
		
		TableColumn<User, Integer> userIdColumn = new TableColumn<User, Integer>("UserID");
		TableColumn<User, String> usernameColumn = new TableColumn<User, String>("Username");
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		TableColumn<User, String> passwordColumn = new TableColumn<User, String>("Password");
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
		
		userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("UserID"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("Role"));
		
		userTable.getColumns().addAll(userIdColumn, usernameColumn, emailColumn, passwordColumn, roleColumn);
	}
	
	public void refreshTable() {
		userList.clear();
		getUser();
		ObservableList<User> userObs = FXCollections.observableArrayList(userList);
		userTable.setItems(userObs);
	}
	
	public void getUser() {
		
		DBConnection dbConnect = DBConnection.getConnection();
		ResultSet rs = dbConnect.executeQuery("SELECT * FROM `User`");
		
		try {
			while(rs.next()) {
				
				Integer UserID = rs.getInt(1);
				String Username = rs.getString(2);
				String Email = rs.getString(3);
				String Password = rs.getString(4);
				String Role = rs.getString(5);
				
				userList.add(new User(UserID, Username, Email, Password, Role));
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

		userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TableSelectionModel<User> selectedUser = userTable.getSelectionModel();
				selectedUser.setSelectionMode(SelectionMode.SINGLE);
				
				User user = selectedUser.getSelectedItem();
				
				inputNewUsername.setText(user.getUsername());
				inputNewPassword.setText(user.getPassword());
				UserID = user.getUserID();
				
				inputNewUsername.setDisable(false);
				inputNewPassword.setDisable(false);
				updateBtn.setDisable(false);
				deleteBtn.setDisable(false);
			}
		});
		
		updateBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DBConnection dbConnect = DBConnection.getConnection();
				
				// Update Validation
				String username = inputNewUsername.getText();
				String password = inputNewPassword.getText();
				
				if (username.length() == 0) {
					createAlert(AlertType.ERROR, "Username cannot be empty!");
				} else if (username.length() < 5 || username.length() > 25) {
					createAlert(AlertType.ERROR, "Username length must between 5 - 25 characters!");
				} else if (password.length() == 0) {
					createAlert(AlertType.ERROR, "Password cannot be empty!");
				} else if (password.length() < 5) {
					createAlert(AlertType.ERROR, "Password length must greater than 5 characters!");
				} else if (!password.matches("^[a-zA-Z0-9]*$")) {
					createAlert(AlertType.ERROR, "Password must be Alphanumeric!");
				} else {
					createAlert(AlertType.INFORMATION, "Account updated!");
					dbConnect.executeUpdate(String.format("UPDATE `User` SET Username = '%s', Password = '%s' WHERE UserID = %d", inputNewUsername.getText(), inputNewPassword.getText(), UserID));
				}
				refreshTable();
			}
		});
		
		deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				DBConnection dbConnect = DBConnection.getConnection();
				
				String username = inputNewUsername.getText();
				
				Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + username + "?");
				Optional<ButtonType> option = alert.showAndWait();
				
				if (option.get().equals(ButtonType.OK)) {
					createAlert(AlertType.INFORMATION, username + " has been deleted!");
					PreparedStatement ps = dbConnect.preparedStatement("DELETE FROM `User` WHERE UserID=?");
					try {
						ps.setInt(1, UserID);
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

	public Scene getManageUser() {
		return sc;
	}
}
