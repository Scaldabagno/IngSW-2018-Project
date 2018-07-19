package it.ingsw.address.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.ingsw.address.MainApp;

/**
 * @author Federico Augello
 * @description gestisce la prima schermata 
 */
public class AvvioProgrammaControl {
	private MainApp mainApp;
	
	@FXML
	private Button avvioProgramma;
	
	@FXML
	private void initialize() {
	}
	
	/*FXMLLoader loader=new FXMLLoader(); per creare l'oggetto che caricherà la schermata
	 * loader.setLocation(MainApp.class.getResource("view/SchermataPrincipale.fxml")); per dire al "caricatore" di settare la posizione della schermata che si vuole usare
	 * AnchorPane schermataPrincipale = (AnchorPane) loader.load(); per caricare effettivamente la pagina
	 * Scene scene = new Scene(schermataPrincipale); per creare una nuova scena con parametro il caricamento della schermata
	 * Stage stage = mainApp.getPrimaryStage(); per settare la "fase" in modo da collegare tutto a MainApp
	 * stage.setScene(scene); nella fase si setta la scena
	 * SchermataPrincipaleControl controller = loader.getController(); il caricatore deve prendersi il controller della prossima schermata sennò NullPointerException e altre
	 * Per settare il controller collegandolo alla MainApp
	 * Stessa cosa avviene per le altre "transizioni"
	 * */
	@FXML
	public void avvioProgrammaButton() throws IOException{
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
