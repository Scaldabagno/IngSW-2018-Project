package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Ruolo;
import it.ingsw.address.model.Sessione;
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

public class AccessoAutistaControl {
	private MainApp mainApp;
	
	@FXML 
	private TextField emailA;
	
	@FXML
	private PasswordField passwordA;
	
	@FXML
	private Button loginA;
	
	@FXML
	private Button annulla;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void loginAreaAutista() throws IOException{
			  try {
					DBImpiegato dbm = DBImpiegato.getInstance();
					Ruolo ruolo = Ruolo.Autista;
					Impiegato impiegato = dbm.loginAutista(emailA.getText(), passwordA.getText(), ruolo);
							if(impiegato != null && impiegato.getRuolo() == ruolo) {
									Sessione.impiegato = impiegato;
									FXMLLoader loader=new FXMLLoader();
									loader.setLocation(MainApp.class.getResource("view/AreaAutista.fxml"));
									AnchorPane areaAutista = (AnchorPane) loader.load();
									Scene scene = new Scene(areaAutista);
									Stage stage = mainApp.getPrimaryStage();
									stage.setScene(scene);
									AutistaControl controller = loader.getController();
									controller.setMainApp(mainApp);
							} else if (impiegato == null || impiegato.getRuolo() != Ruolo.Autista) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.initOwner(mainApp.getPrimaryStage());
					            alert.setTitle("Connection Information");
					            alert.setHeaderText("Email e/o password errate");
					            alert.setContentText("Controlla le credenziali inserite e riprova.");
					            alert.showAndWait();
							}
					
				} catch (SQLException exc) {
					exc.printStackTrace();
					Alert alert = new Alert(AlertType.WARNING);
					alert.initOwner(mainApp.getPrimaryStage());
		            alert.setTitle("Connection Information");
		            alert.setHeaderText("Connessione Non Disponibile");
		            alert.setContentText("Controlla la connessione e riprova.");
		            alert.showAndWait();
				}
		  }
	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaRiservata.fxml"));
		AnchorPane areaRiservata = (AnchorPane) loader.load();
		Scene scene = new Scene(areaRiservata);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AreaRiservataControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
