package it.ingsw.address.view;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.ingsw.address.MainApp;

public class SchermataPrincipaleControl {
	private MainApp mainApp;
	
	@FXML
	private Label linea;
	
	@FXML
	private Label capolinea;
	
	@FXML
	private Label fermate;
	
	@FXML
	private TextField ricercaLinea;
	
	@FXML
	private Button areaRiservata;
	
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void areaRiservataButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaRiservata.fxml"));
		AnchorPane areaRiservata = (AnchorPane) loader.load();
		Scene scene = new Scene(areaRiservata);
		System.out.println(scene);
		System.out.println(areaRiservata);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AreaRiservataControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}



	
}
