package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.ingsw.address.MainApp;
import it.ingsw.address.model.Linea;

/**
 * @author Federico Augello
 * @description gestisce la Schermata Principale "L'area cittadino" 
 */
public class SchermataPrincipaleControl {
	private MainApp mainApp;
	
	@FXML
	private Label linea;
	
	@FXML
	private Label capolinea;
	
	@FXML
	private Label fermate;
	
	@FXML
	private TextField ricercaLinea;
	
	@FXML
	private Button areaRiservata;
	
	private ObservableList<Linea> listLine = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException{
		
		
	} 
	
	public SchermataPrincipaleControl() {
		listLine.add(new Linea(100, "Muster", 10));
	}
	
	@FXML
	public void areaRiservataButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaRiservata.fxml"));
		AnchorPane areaRiservata = (AnchorPane) loader.load();
		Scene scene = new Scene(areaRiservata);
		System.out.println(scene);
		System.out.println(areaRiservata);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AreaRiservataControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}



	
}
