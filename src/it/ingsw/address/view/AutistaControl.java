package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Sessione;
import it.ingsw.address.model.Turno;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AutistaControl {
	private MainApp mainApp;
	
	@FXML
	private Label nomeLabel;
	
	@FXML
	private Label cognomeLabel;
	
	@FXML
	private Label matricolaLabel;
	
	@FXML
	private Label emailLabel;
	
	@FXML
	private Label nascitaLabel;
	
	@FXML
	private Label stipendioLabel;
	
	@FXML
	private Label turnoLabel;
	
	@FXML
	private Label oreLabel;
	
	@FXML
	private Label disponibile;

	@FXML
	private Button indisponibilita;

	@FXML
	private Button logout;
	
	public AutistaControl() {
		
	}
	
	/**
	 * @author Federico Augello
	 * @description funzione che si avvia entrando nell'area Autista
	 */
	
	@FXML
	private void initialize() throws SQLException{
		this.nomeLabel.setText(Sessione.impiegato.getNome());
		this.cognomeLabel.setText(Sessione.impiegato.getCognome());
		this.matricolaLabel.setText(Sessione.impiegato.getMatricola());
		this.emailLabel.setText(Sessione.impiegato.getEmail());	
		this.nascitaLabel.setText(Sessione.impiegato.getDataNascita().toString());
		this.stipendioLabel.setText(String.valueOf(Sessione.impiegato.getStipendio()) + " €");
		if(Sessione.impiegato.getDisponibilita() == true) {
			this.disponibile.setText("Stato: Disponibile");
		} else {
			this.disponibile.setText("Stato: Non Disponibile");
		}
		if(Sessione.impiegato.getTurno() == Turno.NonAssegnato || Sessione.impiegato.getTurno() == null) {
			this.turnoLabel.setText("Non Assegnato");
		}else {
			this.turnoLabel.setText(String.valueOf(Sessione.impiegato.getTurno()));
		}
	}
	
	@FXML
	private void comunicaIndisponibilita() {
		Impiegato i = Sessione.impiegato;
		try {
			DBImpiegato dbm = DBImpiegato.getInstance();
			dbm.comunicaNonDisponibilita(i);
			switchDisponibilita();
		} catch (SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Connection Information");
            alert.setHeaderText("Connessione Non Disponibile");
            alert.setContentText("Controlla la connessione e riprova.");
            alert.showAndWait();
		}
	}
	
	public void switchDisponibilita() {
		Impiegato i = Sessione.impiegato;
		if(i.getDisponibilita() == true) {
			i.setDisponibilita(false);
			disponibile.setText("Stato: Non Disponibile");
		}else {
			i.setDisponibilita(true);
			disponibile.setText("Stato: Disponibile");
		}
	}
	
	@FXML
	public void logoutA() throws IOException{
		Sessione.impiegato = new Impiegato();
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/SchermataPrincipale.fxml"));
		AnchorPane schermataPrincipale = (AnchorPane) loader.load();
		Scene scene = new Scene(schermataPrincipale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		SchermataPrincipaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
