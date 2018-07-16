package it.ingsw.address.view;

import java.io.IOException;

import it.ingsw.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccessoAddettoAiMezziControl {
	private MainApp mainApp;
	
	@FXML 
	TextField emailAM;
	
	@FXML
	PasswordField passwordAM;
	
	@FXML
	private Button loginAM;
	
	@FXML
	private Button annulla;
	
	@FXML
	private void initialize() {
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
