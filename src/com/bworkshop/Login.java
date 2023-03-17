package com.bworkshop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.config.DBConnection;
import com.utility.User;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

public class Login {
	
	Scene sc;
	BorderPane bp;
	
	GridPane gp, gpBtn;
	
	Text loginTitle, emailText, passText, invisRoleText;
	
	TextField inputEmail, inputPass, invisRoleField;
	
	Button registerBtn, submitBtn;
	
	Vector<User> userData;
	
	DBConnection dbConnection;
	
	public Login() {
		init();
		layout();
		style();
		setEvent();
	}
	
	public void init() {
		bp = new BorderPane();
		
		sc = new Scene(bp, 750, 550);
		
		gp = new GridPane();
		gpBtn = new GridPane();
		
		loginTitle = new Text("Login");
		
		emailText = new Text("Email");
		inputEmail = new TextField();
		
		passText = new Text("Password");
		inputPass = new PasswordField();
		
		invisRoleText = new Text("Invis");
		invisRoleField = new PasswordField();
		
		registerBtn = new Button("Register");
		submitBtn = new Button("Submit");
		
		userData = new Vector<>();
		
		dbConnection = dbConnection.getConnection();
	}
	
	public void layout() {
		gp.add(emailText, 0, 0);
		gp.add(inputEmail, 1, 0, 4, 1);
		
		gp.add(passText, 0, 1);
		gp.add(inputPass, 1, 1, 4, 1);
		
		gp.add(invisRoleText, 0, 2);
		gp.add(invisRoleField, 1, 2);
		
		gpBtn.add(registerBtn, 0, 0);
		gpBtn.add(submitBtn, 1, 0);
		
		bp.setTop(loginTitle);
		bp.setCenter(gp);
		bp.setBottom(gpBtn);
	}
	
	public void style() {
		GridPane.setHalignment(gp, HPos.CENTER);
		GridPane.setValignment(gp, VPos.CENTER);
		
		BorderPane.setAlignment(loginTitle, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(gpBtn, Pos.CENTER);
		
		gp.setAlignment(Pos.TOP_CENTER);
		gpBtn.setAlignment(Pos.TOP_CENTER);
		
		gp.setHgap(40);
		gp.setVgap(40);
		
		gpBtn.setHgap(75);
		
		bp.setMargin(loginTitle, new Insets(65));
		bp.setMargin(gp, new Insets(10));
		bp.setMargin(gpBtn, new Insets(0, 0, 165, 0));
		
		registerBtn.setPadding(new Insets(7, 45, 7, 45));
		submitBtn.setPadding(new Insets(7, 45, 7, 45));

		invisRoleText.setVisible(false);
		invisRoleField.setVisible(false);
		
		loginTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, 36));
		loginTitle.setFill(Color.WHITE);
		emailText.setFill(Color.WHITE);
		passText.setFill(Color.WHITE);
		bp.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void createAlert(AlertType alertType, String message) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public void setEvent() {
		registerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Main.authStage.displayRegister();
			}
		});
		
		submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String iEmail = inputEmail.getText();
				String iPass = inputPass.getText();
				
				String query = String.format("SELECT * FROM `User` WHERE Email = '%s' AND Password = '%s'" , iEmail , iPass);
				ResultSet resultQuery = dbConnection.executeQuery(query);
				
				if (iEmail.length() == 0) {
					createAlert(AlertType.ERROR, "Email cannot be empty!");
				} else if (!iEmail.contains("@") || !iEmail.endsWith(".com")) {
					createAlert(AlertType.ERROR, "Email must be valid");
				} else if (iPass.length() == 0) {
					createAlert(AlertType.ERROR, "Password cannot be empty!");
				}
					
				try {
					while (resultQuery.next()) {
						String username = resultQuery.getString("Username");
						String email = resultQuery.getString("Email");
						String password = resultQuery.getString("Password");
						String role = resultQuery.getString("Role");
						
						if (!iEmail.equals(email) && !iPass.equals(password)) {
							createAlert(AlertType.ERROR, "Invalid Email or Password!");
						} else {
							if (role.equals("customer")) {
								createAlert(AlertType.INFORMATION, "Login Success!\nWelcome " + username);
								Main.authStage.displayMainCustomer();
							} else if (role.equals("admin")) {
								createAlert(AlertType.INFORMATION, "Login Success!\nWelcome " + username);
								Main.authStage.displayMainAdmin();
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public Scene getLogin() {
		return sc;
	}
}
