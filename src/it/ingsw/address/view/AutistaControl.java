package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Federico Augello
 * @description gestisce l'area Autista  
 */
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
	}
	
	@FXML
	public void logoutA() throws IOException{
		Sessione.impiegato = new Impiegato();
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
