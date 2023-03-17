package com.bworkshop;

import java.util.Vector;

import com.config.DBConnection;
import com.utility.User;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Register {
	
	Scene sc;
	BorderPane bp;
	
	GridPane gp, gpBtn;
	
	Text pageTitle, userText, emailText, passText, confirmText;
	
	TextField inputUser, inputEmail, inputPass, inputConfirm;
	
	Button registerBtn;
	
	Vector<User> userData;
	
	DBConnection dbConnection;
	
	public Register() {
		init();
		layout();
		style();
		setEventHandler();
	}
	
	public void init() {
		bp = new BorderPane();
		
		sc = new Scene(bp, 450, 500);
		
		gp = new GridPane();
		gpBtn = new GridPane();
		
		pageTitle = new Text("Register");
		
		userText = new Text("Username");
		inputUser = new TextField();
		
		emailText = new Text("Email");
		inputEmail = new TextField();
		
		passText = new Text("Password");
		inputPass = new PasswordField();
		
		confirmText = new Text("Confirm Password");
		inputConfirm = new PasswordField();
		
		registerBtn = new Button("Register");
		
		userData = new Vector<>();
		
		dbConnection = dbConnection.getConnection();
	}
	
	public void layout() {
		
		gp.add(userText, 0, 0);
		gp.add(inputUser, 1, 0);
		
		gp.add(emailText, 0, 1);
		gp.add(inputEmail, 1, 1);
		
		gp.add(passText, 0, 2);
		gp.add(inputPass, 1, 2);
		
		gp.add(confirmText, 0, 3);
		gp.add(inputConfirm, 1, 3);
		
		gpBtn.add(registerBtn, 0, 0);
		
		bp.setTop(pageTitle);
		bp.setCenter(gp);
		bp.setBottom(gpBtn);
	}
	
	public void style() {
		GridPane.setHalignment(gp, HPos.CENTER);
		GridPane.setValignment(gp, VPos.CENTER);
		
		BorderPane.setAlignment(pageTitle, Pos.CENTER);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setAlignment(gpBtn, Pos.CENTER);
		
		gp.setAlignment(Pos.TOP_CENTER);
		gpBtn.setAlignment(Pos.TOP_CENTER);
		
		gp.setHgap(40);
		gp.setVgap(40);
		
		gpBtn.setHgap(75);
		
		bp.setMargin(pageTitle, new Insets(20));
		bp.setMargin(gp, new Insets(10));
		bp.setMargin(gpBtn, new Insets(0, 0, 100, 0));
		
		registerBtn.setPadding(new Insets(7, 45, 7, 45));
		
		pageTitle.setFont(Font.font("Arial", FontWeight.MEDIUM, 28));
		pageTitle.setFill(Color.WHITE);
		userText.setFill(Color.WHITE);
		emailText.setFill(Color.WHITE);
		passText.setFill(Color.WHITE);
		confirmText.setFill(Color.WHITE);
		bp.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public void createAlert(AlertType alertType, String message) {
		Alert alert = new Alert(alertType);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public void addUser() {
		String username = inputUser.getText();
		String email = inputEmail.getText();
		String password = inputPass.getText();
		String confirmPass = inputConfirm.getText();
		String role = "";
		
		// User Validation
		if (username.length() == 0) {
			createAlert(AlertType.ERROR, "Username cannot be empty!");
		} else if (username.length() < 5 || username.length() > 25) {
			createAlert(AlertType.ERROR, "Username length must between 5 - 25 characters!");
		} else if (email.length() == 0) {
			createAlert(AlertType.ERROR, "Email cannot be empty!");
		} else if (!email.contains("@") || !email.endsWith(".com")) {
			createAlert(AlertType.ERROR, "Email must be valid");
		} else if (password.length() == 0) {
			createAlert(AlertType.ERROR, "Password cannot be empty!");
		} else if (password.length() < 5) {
			createAlert(AlertType.ERROR, "Password length must greater than 5 characters!");
		} else if (!password.matches("^[a-zA-Z0-9]*$")) {
			createAlert(AlertType.ERROR, "Password must be Alphanumeric!");
		} else if (!confirmPass.equals(password)) {
			createAlert(AlertType.ERROR, "Password doesn't match!");
		} else if (username.equals("Jonathan Christian")
				|| username.equals("Reynaldo Chandra")
				|| username.equals("Glorious Hermawan")
				|| username.equals("Christian Ceng")) {
			role = "admin";
			createAlert(AlertType.INFORMATION, "Account Created!");
			String query = String.format("INSERT INTO `user` (`UserID`, `Username`, `Email`, `Password`, `Role`) VALUES (NULL, '%s', '%s', '%s', '%s')", username, email, password, role);
			dbConnection.executeUpdate(query);
			Main.authStage.displayLogin();
		}
		else {
			role = "customer";
			createAlert(AlertType.INFORMATION, "Account Created!");
			String query = String.format("INSERT INTO `user` (`UserID`, `Username`, `Email`, `Password`, `Role`) VALUES (NULL, '%s', '%s', '%s', '%s')", username, email, password, role);
			dbConnection.executeUpdate(query);
			Main.authStage.displayLogin();
		}
	}
	
	public void setEventHandler() {
		registerBtn.setOnAction(event -> {
			addUser();
		});
	}

	public Scene getRegister() {
		return sc;
	}
}