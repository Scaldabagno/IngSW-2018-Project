package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.DatiMezzo;
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

/**
 * @author Federico Augello
 * @description gestisce l'area Addetto Ai Mezzi
 */
public class AddettoAiMezziControl {
	private MainApp mainApp;
	
	@FXML 
	private TableView<DatiMezzo> tabellaMezzi;
	
	@FXML
	private TableColumn<DatiMezzo, String> numeroTarga;
	
	@FXML
	private Label targa;
	
	@FXML
	private Label posto;
	
	@FXML
	private Label disponibilita;
	
	@FXML
	private Button logout;
	
	@FXML
	private TextField ricercaMezzo;
	
	@FXML
	private Button inserisciMezzo;
	
	@FXML
	private Button modificaMezzo;
	
	@FXML
	private Button eliminaMezzo;
	
	@FXML
	private Button disponibilitaMezzo;
	
	public AddettoAiMezziControl() {
		
	}
	
	ObservableList<DatiMezzo> listMezzo = FXCollections.observableArrayList();
	
	/**
	 * @author Federico Augello
	 * @description funzione che si avvia entrando nell'area Addetto Ai Mezzi
	 */
	
	@FXML
	private void initialize() throws SQLException{
		listMezzo = DBMezzo.getInstance().getMezzi();
		numeroTarga.setCellValueFactory(
				new PropertyValueFactory<DatiMezzo, String>("targa"));
		dettagliMezzo(null);
		
		tabellaMezzi.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> dettagliMezzo(newValue));
		
		System.out.println(listMezzo);
		
		FilteredList<DatiMezzo> filteredData = new FilteredList<>(listMezzo, p -> true);

		ricercaMezzo.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(mezzo -> {
		          // Se il testo della barra è vuoto, restituisce tutti i mezzi.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Ricerca mezzi per targa.
		          String lowerCaseFilter = newValue.toLowerCase();

		          return mezzo.getDatiTarga().toLowerCase().contains(lowerCaseFilter);
		      });
		    });
		SortedList<DatiMezzo> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaMezzi.comparatorProperty());

	  	tabellaMezzi.setItems(sortedData);
	}

	
	@FXML
	public void logoutAM() throws IOException{
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
	
	private void dettagliMezzo(DatiMezzo datiMezzo) {
	    if (datiMezzo != null) {
	        // Riempie le label con targa, posto nel deposito, disponibilità
	        targa.setText(datiMezzo.getDatiTarga());
	        // TODO: posto e disp
	    } else {
	        // Se non viene selezionato nessun mezzo, non mostra nulla.
	        targa.setText("");
	        posto.setText("");
	        disponibilita.setText("");
	    }
	}

}
