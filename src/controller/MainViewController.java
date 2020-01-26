package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartament;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private void onMenuItemSellerAction() {
		System.out.println("onMenuItemSellerAction");
	}
	
	@FXML
	private void onMenuItemDepartamentAction() {
		loadView("/gui/DepartmentList.fxml");
	}
	
	@FXML
	private void onMenuItemAboutAction() {
		loadView("/gui/About.fxml");
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized void loadView(String absoluteName) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene(); // Pega a cena da tela principal
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent(); // Pega o VBox da tela principal
			
			Node mainMenu = mainVBox.getChildren().get(0); // Pega o MenuBar da tela principal
			
			mainVBox.getChildren().clear(); // Exclue todos os nós filhos do VBox da tela principal
			
			// Adiciona nos filhos do VBox da tela principal o MenuBar da tela principal
			mainVBox.getChildren().add(mainMenu);
			 // Adiciona nos filhos do VBox da tela principal os filhos do VBox da nova tela
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
		} catch (IOException e) {
			Alerts.showAlert("IOException", "Error loading view", e.getMessage(), AlertType.ERROR);
			e.printStackTrace();
		}
	}

}
