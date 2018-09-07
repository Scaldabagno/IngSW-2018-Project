package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				alert.setContentText("L'impiegato è stato aggiunto all'elenco dei mezzi");
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

		if (targaText.getText().equals("")) {
			alert.setContentText("Inserisci la targa");
			return alert;
		}
		if (postoText.getText().equals("")) {
			alert.setContentText("Inserisci un numero di posto");
			return alert;
		}
		String regex = "([A-Z][A-Z])([0-9][0-9][0-9])([A-Z][A-Z])";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(targaText.getText());
		if(! m.matches()) {
			alert.setContentText("Inserisci un numero di targa valido");
			return alert;
		}
		if (Integer.valueOf(postoText.getText()) > Deposito.getMax()) {
			alert.setContentText("Posto non esistente, il deposito ha " + Deposito.getMax() + " posti");
			return alert;
		}

		return null;
	}

	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaAddettoAiMezzi.fxml"));
		AnchorPane addettoAiMezzi = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAiMezzi);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAiMezziControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
