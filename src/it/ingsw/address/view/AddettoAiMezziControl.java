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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private Button profilo;
	
	@FXML
	private Button inserisciMezzo;
	
	@FXML
	private Button modificaMezzo;
	
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
	
	private void dettagliMezzo(DatiMezzo datiMezzo) {
	    if (datiMezzo != null) {
	        // Riempie le label con targa, posto nel deposito, disponibilità
	        targa.setText(datiMezzo.getDatiTarga());
	        posto.setText(datiMezzo.getDatiPosto());
	        if(datiMezzo.getDatiDisponibilita() == "true"){
	        	disponibilita.setText("Disponibile");
	        }else if(datiMezzo.getDatiDisponibilita() == "false"){
	        	disponibilita.setText("Non Disponibile");
	        }
	    } else {
	        // Se non viene selezionato nessun mezzo, non mostra nulla.
	        targa.setText("");
	        posto.setText("");
	        disponibilita.setText("");
	    }
	}

	
	@FXML
	public void profiloScene() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Profilo.fxml"));
		AnchorPane profilo = (AnchorPane) loader.load();
		Scene scene = new Scene(profilo);
		System.out.println(scene);
		System.out.println(profilo);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		ProfiloControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void aggiungiMezzo() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AggiungiMezzo.fxml"));
		AnchorPane aggiungiMezzo = (AnchorPane) loader.load();
		Scene scene = new Scene(aggiungiMezzo);
		System.out.println(scene);
		System.out.println(aggiungiMezzo);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AggiungiMezzoControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	private void modificaMezzo() {
	    DatiMezzo mezzoSel = tabellaMezzi.getSelectionModel().getSelectedItem();
	    if (mezzoSel != null) {
	        boolean okClicked = modificaMezzoScene(mezzoSel);
	        if (okClicked) {
	            dettagliMezzo(mezzoSel);
	        }

	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Nessuna Selezione");
	        alert.setHeaderText("Nessun mezzo selezionato");
	        alert.setContentText("Selezionare un mezzo dall'elenco.");

	        alert.showAndWait();
	    }
	}
	
	public boolean modificaMezzoScene(DatiMezzo datiMezzo) {
		try {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/ModificaMezzo.fxml"));
		AnchorPane modificaMezzo = (AnchorPane) loader.load();
		Scene scene = new Scene(modificaMezzo);
		System.out.println(scene);
		System.out.println(modificaMezzo);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		ModificaMezzoControl controller = loader.getController();
		controller.setMainApp(mainApp);
        controller.setMezzo(datiMezzo);
        
        return controller.isOkClicked();
		}catch (IOException e){
			 e.printStackTrace();
		     return false;
		}
	}
	
	
	@FXML
	private void comunicaDisponibilitaMezzo() {
		DatiMezzo mezzoSel = tabellaMezzi.getSelectionModel().getSelectedItem();
		if (mezzoSel != null) {
			System.out.println(mezzoSel);
			System.out.println(mezzoSel.getDatiDisponibilita());
			try {
			DBMezzo dbm = DBMezzo.getInstance();
			dbm.comunicaNonDisponibilita(mezzoSel);
			switchDisponibilita(mezzoSel);
			} catch (SQLException e){
				e.printStackTrace();
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Connection Information");
	            alert.setHeaderText("Connessione Non Disponibile");
	            alert.setContentText("Controlla la connessione e riprova.");
	            alert.showAndWait();
			}
	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Nessuna Selezione");
	        alert.setHeaderText("Nessun mezzo selezionato");
	        alert.setContentText("Selezionare un mezzo dall'elenco.");

	        alert.showAndWait();
	    }
		
	} 
	
	public void switchDisponibilita(DatiMezzo datiMezzo) {
		if(datiMezzo.getDatiDisponibilita() == "true") {
			datiMezzo.setDatiDisponibilita("false");
			disponibilita.setText("Non Disponibile");
				
				
		}else if(datiMezzo.getDatiDisponibilita() == "false"){
			datiMezzo.setDatiDisponibilita("true");
			disponibilita.setText("Disponibile");
		}
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
}
