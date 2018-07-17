package it.ingsw.address.view;

import java.io.IOException;

import it.ingsw.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddettoAlPersonaleControl {
	private MainApp mainApp;
	
	@FXML
	private Button logout;
	
	@FXML
	private TextField ricercaImpiegato;
	
	@FXML
	private Button nuovoImpiegato;
	
	@FXML
	private Button modificaImpiegato;
	
	@FXML
	private Button cancellaImpiegato;
	
	@FXML
	private Button calcolaStipendio;
	
	@FXML
	private Button allocaMezzo;
	
	@FXML
	private Button allocaTurno;
	
	@FXML
	public void logoutAP() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/SchermataPrincipale.fxml"));
		AnchorPane schermataPrincipale = (AnchorPane) loader.load();
		Scene scene = new Scene(schermataPrincipale);
		System.out.println(scene);
		System.out.println(schermataPrincipale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		SchermataPrincipaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

}
