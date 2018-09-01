package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.DatiLinea;

/**
 * @author Federico Augello
 * @description gestisce la Schermata Principale, "L'area cittadino" 
 */
public class SchermataPrincipaleControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiLinea> tabellaLinee;
	
	@FXML
	private TableColumn<DatiLinea, String> numeroLinea;
	
	@FXML
	private Label linea;
	
	@FXML
	private Label capolinea;
	
	@FXML
	private Label fermate;
	
	@FXML
	private Label orari;
	
	@FXML
	private TextField ricercaLinea;
	
	@FXML 
	private Button ricercaPercorso;
	
	@FXML
	private Button areaRiservata;
	
	public SchermataPrincipaleControl() {
		
	}
	
	ObservableList<DatiLinea> listLinea = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException{
		listLinea = DBLinea.getInstance().getLinee();
		numeroLinea.setCellValueFactory(
				new PropertyValueFactory<DatiLinea, String>("numeroLinea"));
		dettagliLinea(null);
		
		tabellaLinee.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> dettagliLinea(newValue));
		
		
		FilteredList<DatiLinea> filteredData = new FilteredList<>(listLinea, p -> true);

		ricercaLinea.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(linea -> {
		          // Se il testo della barra è vuoto, restituisce tutte le linee.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Ricerca linee, sia per capolinea che per numero.
		          String lowerCaseFilter = newValue.toLowerCase();

		          return linea.getDatiNumeroLinea().toLowerCase().contains(lowerCaseFilter) ||
		          			 linea.getDatiCapolineaI().toLowerCase().contains(lowerCaseFilter) ||
		          			 linea.getDatiCapolineaF().toLowerCase().contains(lowerCaseFilter);
		      });
		    });
		SortedList<DatiLinea> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaLinee.comparatorProperty());

	  	tabellaLinee.setItems(sortedData);
	}
	
	@FXML
	public void ricercaPercorso() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/RicercaPercorso.fxml"));
		AnchorPane ricercaPercorso = (AnchorPane) loader.load();
		Scene scene = new Scene(ricercaPercorso);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		RicercaPercorsoControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void areaRiservataButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaRiservata.fxml"));
		AnchorPane areaRiservata = (AnchorPane) loader.load();
		Scene scene = new Scene(areaRiservata);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AreaRiservataControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	private void dettagliLinea(DatiLinea datiLinea) {
	    if (datiLinea != null) {
	        // Riempie le label con numero linea, capolinea, fermate, orari 
	        linea.setText(datiLinea.getDatiNumeroLinea());
	        capolinea.setText(datiLinea.getDatiCapolineaI() + "-" + datiLinea.getDatiCapolineaF());
	        fermate.setText(datiLinea.getDatiCapolineaI() + ", " + datiLinea.getDatiFermate().replace("[", "").replace("]", "") + ", " + datiLinea.getDatiCapolineaF());
	    } else {
	        // Se non viene selezionata nessuna linea, non mostra nulla.
	        linea.setText("");
	        capolinea.setText("");
	        fermate.setText("");
	    }
	}
	
}
