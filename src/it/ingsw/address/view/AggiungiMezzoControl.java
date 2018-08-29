package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.Deposito;
import it.ingsw.address.model.Mezzo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AggiungiMezzoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField targaText;
	
	@FXML
	private TextField postoText;
	
	@FXML
	private Button aggiungi;
	
	@FXML
	private Button annulla;
	
	@FXML
	public void initialize() {
	}
	
	@FXML
	private void aggiungi() throws IOException {
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			try {
				DBMezzo.getInstance().aggiungiMezzo(getNuovoMezzo());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento avvenuto con successo!");
				alert.setContentText("L'impiegato è stato aggiunto all'elenco degli impiegati");
				alert.showAndWait();

				annullaButton();

			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento fallito!");
				alert.setContentText("Il numero di targa o il posto inserito è già stato assegnato");
				alert.showAndWait();
				e.printStackTrace();
			}
			String result = "Il mezzo con targa " + targaText.getText() + " é stato inserito con successo";
			System.out.println(result);
		}
	}
	
	private Mezzo getNuovoMezzo() throws SQLException {
		Mezzo mezzo = new Mezzo();
		mezzo.setTarga(targaText.getText());
		mezzo.setNumeroPosto(Integer.valueOf(postoText.getText()));
		return mezzo;
	}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check targa
		if (targaText.getText().equals("")) {
			alert.setContentText("Inserisci la targa");
			return alert;
		}
		// Check numero posto
		if (postoText.getText().equals("")) {
			alert.setContentText("Inserisci un numero di posto");
			return alert;
		}
		// Check numero posto
		if (Integer.valueOf(postoText.getText()) > Deposito.getMax()) {
			alert.setContentText("Posto non esistente, il deposito ha " + Deposito.getMax() + " posti");
			return alert;
		}

		// Data is ok
		return null;
	}

	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAiMezziScreen.fxml"));
		AnchorPane addettoAiMezzi = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAiMezzi);
		System.out.println(scene);
		System.out.println(addettoAiMezzi);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAiMezziControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
