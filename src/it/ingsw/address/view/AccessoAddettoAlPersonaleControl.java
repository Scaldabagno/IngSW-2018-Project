package it.ingsw.address.view;

import java.io.IOException;

import it.ingsw.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Federico Augello
 * @description gestisce l'accesso di addetto al personale 
 */
public class AccessoAddettoAlPersonaleControl {
	private MainApp mainApp;
	
	@FXML 
	TextField emailAP;
	
	@FXML
	PasswordField passwordAP;
	
	@FXML
	private Button loginAP;
	
	@FXML
	private Button annulla;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void loginAreaAddettoAlPersonale() throws IOException{
		//da completare ovviamente
		if(emailAP.getText().equals("") || passwordAP.getText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(null);
            alert.setTitle("Connection Information");
            alert.setHeaderText("Matricola e/o password errate");
            alert.setContentText("Controlla le credenziali inserite e riprova.");
            alert.showAndWait();
		}else {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleScreen.fxml"));
		AnchorPane areaAddettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(areaAddettoAlPersonale);
		System.out.println(scene);
		System.out.println(areaAddettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
		}
	}

	@FXML
	public void annullaButton() throws IOException{
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
