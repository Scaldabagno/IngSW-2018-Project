package it.ingsw.address.view;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RicercaPercorsoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField partenza;
	
	@FXML
	private TextField arrivo;
	
	@FXML
	private Spinner<String> partenzaSpinner;
	
	@FXML
	private Spinner<String> arrivoSpinner;
	
	
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
	private void initialize() {
		String[] varieFermate = {"Stadio", "Stazione", "P.zza Politeama", "Mondello", "Via Roma", "Ospedale Civico", "C.so Tukory", "C.so Alberto Amedeo", "Viale Della Libertà", "P.zza Valdesi", "Notarbartolo"};
		ObservableList<String> fermate = FXCollections.observableArrayList(varieFermate);
		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(fermate);
		SpinnerValueFactory<String> valueFactory1 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(fermate);
		partenzaSpinner.setValueFactory(valueFactory);
		arrivoSpinner.setValueFactory(valueFactory1);
		
		partenzaSpinner.getValueFactory().setValue(varieFermate[0]);
		arrivoSpinner.getValueFactory().setValue(varieFermate[0]);
	}
	
	@FXML
	private void calcolaPercorso() throws SQLException{
		try {
			DBLinea.getInstance().getLinee();
			if(DBLinea.getInstance().getLinee().contains(partenzaSpinner.getValue())) {
				System.out.println(DBLinea.getInstance().getLinee());
			}else {
				;
			}
				
		} catch(SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Avviso");
			alert.setHeaderText("Ricerca fallita");
			alert.setContentText("Non è stato possibile calcolare il percorso");
			alert.showAndWait();
			e.printStackTrace();
		}
	}
	
	private void calcFermate() {
		
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
