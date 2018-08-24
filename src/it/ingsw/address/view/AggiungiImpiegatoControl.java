package it.ingsw.address.view;



import java.io.IOException;

import it.ingsw.address.MainApp;
import it.ingsw.address.model.Ruolo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AggiungiImpiegatoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField nomeText;
	
	@FXML
	private TextField cognomeText;
	
	@FXML
	private TextField emailText;
	
	@FXML
	private TextField passwordText;
	
	@FXML
	private TextField matricolaText;
	
	@FXML
	private DatePicker dataDiNascitaText;
	
	@FXML
	private Spinner<Ruolo> RuoloText;
	
	@FXML
	private Button annulla;
	
	@FXML
	private Button aggiungi;
	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleScreen.fxml"));
		AnchorPane addettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAlPersonale);
		System.out.println(scene);
		System.out.println(addettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
