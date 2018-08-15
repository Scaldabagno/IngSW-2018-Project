package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.DatiImpiegato;
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
	private Button resocontoStipendio;

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
		DatiImpiegato autista = DBImpiegato.getInstance().getAutista();
		ShowAutistaDetails(autista);
		}
	
	@FXML
	public void logoutA() throws IOException{
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
	
	public void ShowAutistaDetails(DatiImpiegato datiAutista) {
		if (datiAutista != null) {
	        // Riempie le label con nome, cognome e gli altri dati
	        nomeLabel.setText(datiAutista.getDatiNome());
	        cognomeLabel.setText(datiAutista.getDatiCognome());
	        matricolaLabel.setText(datiAutista.getDatiMatricola());
	        emailLabel.setText(datiAutista.getDatiEmail());
	        // TODO: Altri dati
	    } else {
	        // Se non viene selezionata nessuna linea, non mostra nulla.
	        nomeLabel.setText("");
	        cognomeLabel.setText("");
	        matricolaLabel.setText("");
	        emailLabel.setText("");
	    }

	}
}
