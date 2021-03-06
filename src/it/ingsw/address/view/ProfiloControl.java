package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.model.Ruolo;
import it.ingsw.address.model.Sessione;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProfiloControl {
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
	private Button annulla;
	
	public ProfiloControl() {
		
	}
	
	@FXML
	private void initialize() throws SQLException{
		this.nomeLabel.setText(Sessione.impiegato.getNome());
		this.cognomeLabel.setText(Sessione.impiegato.getCognome());
		this.matricolaLabel.setText(Sessione.impiegato.getMatricola());
		this.emailLabel.setText(Sessione.impiegato.getEmail());	
		this.nascitaLabel.setText(Sessione.impiegato.getDataNascita().toString());
		this.stipendioLabel.setText(String.valueOf(Sessione.impiegato.getStipendio()) + " �");		
	}
	
	@FXML
	public void annullaButton() throws IOException{
		if(Sessione.impiegato.getRuolo() == Ruolo.AddettoAlPersonale) {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AreaAddettoAlPersonale.fxml"));
			AnchorPane addettoAlPersonale = (AnchorPane) loader.load();
			Scene scene = new Scene(addettoAlPersonale);
			Stage stage = mainApp.getPrimaryStage();
			stage.setScene(scene);
			AddettoAlPersonaleControl controller = loader.getController();
			controller.setMainApp(mainApp);
		}
		if(Sessione.impiegato.getRuolo() == Ruolo.AddettoAiMezzi) {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AreaAddettoAiMezzi.fxml"));
			AnchorPane addettoAiMezzi = (AnchorPane) loader.load();
			Scene scene = new Scene(addettoAiMezzi);
			Stage stage = mainApp.getPrimaryStage();
			stage.setScene(scene);
			AddettoAiMezziControl controller = loader.getController();
			controller.setMainApp(mainApp);
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
