package it.ingsw.address.view;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RicercaPercorsoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField partenza;
	
	@FXML
	private TextField arrivo;
	
	@FXML
	private ListView<String> percorso;
	
	@FXML
	private Button calcola;
	
	@FXML
	private Button annulla;
	
	private ArrayList<Linea> arrayLinee = null;
	private ArrayList<Linea> contieneInizio = new ArrayList<>();
	private ArrayList<Linea> contieneFine = new ArrayList<>();
	
	@FXML
	private void calcolaPercorso() {
		percorso.setItems(FXCollections.observableArrayList());
		ArrayList<String> percorsoStringa = new ArrayList<>();
		ArrayList<Fermata> fermate;
		//ArrayList<String[]> sequence = new ArrayList<>();
		try {
			arrayLinee = DBLinea.getInstance().getArrayLinee(null);
		} catch (SQLException e1) {
			// TODO caduta di connessione
			e1.printStackTrace();
		}
		
		for(int i=0; i<arrayLinee.size(); i++) {
			fermate = arrayLinee.get(i).getFermate();
			for(int j=0; j<fermate.size(); j++) {
				if(fermate.get(j).getFermata().equalsIgnoreCase(partenza.getText())) 
					contieneInizio.add(arrayLinee.get(i));
				//percorsoStringa.add(fermate.get(i));
				if(fermate.get(j).getFermata().equalsIgnoreCase(arrivo.getText())) 
					contieneFine.add(arrayLinee.get(i));
				//percorsoStringa.add();
			}
		}
		percorso.setItems(FXCollections.observableArrayList(percorsoStringa));
	}
	
	@FXML
	public void annullaButton() throws IOException{
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
