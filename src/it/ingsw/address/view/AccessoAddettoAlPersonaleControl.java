package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

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

/**
 * @author Federico Augello
 * @description gestisce l'accesso di addetto al personale 
 */
public class AccessoAddettoAlPersonaleControl {
	private MainApp mainApp;
	
	@FXML 
	private TextField emailAP;
	
	@FXML
	private PasswordField passwordAP;
	
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
		if(emailAP.getText().equals("") && passwordAP.getText().equals("")) {
//			TODO: da togliere
			Sessione.impiegato = new Impiegato();
			Impiegato i = Sessione.impiegato;
			i.setNome("Ciao");
			i.setCognome("Amico");
			i.setEmail(emailAP.getText() + "Niente");
			i.setMatricola("1234");
			i.setRuolo(Ruolo.AddettoAlPersonale);
			i.setDataNascita(LocalDate.now());
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleArea.fxml"));
			AnchorPane areaAddettoAlPersonale = (AnchorPane) loader.load();
			Scene scene = new Scene(areaAddettoAlPersonale);
			Stage stage = mainApp.getPrimaryStage();
			stage.setScene(scene);
			AddettoAlPersonaleControl controller = loader.getController();
			controller.setMainApp(mainApp);
		}else {
			  try {
					DBImpiegato dbm = DBImpiegato.getInstance();
					Ruolo ruolo = Ruolo.AddettoAlPersonale;
					Impiegato impiegato = dbm.loginAddettoAlPersonale(emailAP.getText(), passwordAP.getText(), ruolo);
							if(impiegato != null && impiegato.getRuolo() == ruolo) {
									Sessione.impiegato = impiegato;
									FXMLLoader loader=new FXMLLoader();
									loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleArea.fxml"));
									AnchorPane areaAddettoAlPersonale = (AnchorPane) loader.load();
									Scene scene = new Scene(areaAddettoAlPersonale);
									Stage stage = mainApp.getPrimaryStage();
									stage.setScene(scene);
									AddettoAlPersonaleControl controller = loader.getController();
									controller.setMainApp(mainApp);
							} else if (impiegato == null || impiegato.getRuolo() != Ruolo.AddettoAlPersonale) {
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
