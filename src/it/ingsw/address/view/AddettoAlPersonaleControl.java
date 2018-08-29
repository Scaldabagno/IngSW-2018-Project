package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.DatiImpiegato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
 * @description gestisce l'area Addetto Al Personale
 */
public class AddettoAlPersonaleControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiImpiegato> tabellaImpiegati;
	
	@FXML
	private TableColumn<DatiImpiegato, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiImpiegato, String> cognomeColumn;
	
	@FXML
	private Label nomeLabel;
	
	@FXML
	private Label cognomeLabel;
	
	@FXML
	private Label matricolaLabel;
	
	@FXML
	private Label emailLabel;
	
	@FXML
	private Label ruoloLabel;
	
	@FXML
	private Label nascitaLabel;
	
	@FXML
	private Label stipendioLabel;
	
	@FXML
	private Button logout;
	
	@FXML
	private TextField ricercaImpiegato;
	
	@FXML
	private Button nuovoImpiegato;
	
	@FXML
	private Button modificaImpiegato;
	
	@FXML
	private Button calcolaStipendio;
	
	@FXML
	private Button allocaMezzo;
	
	@FXML
	private Button allocaTurno;
	
	public AddettoAlPersonaleControl() {
		
	}
	
	ObservableList<DatiImpiegato> listImpiegato = FXCollections.observableArrayList();
	
	/**
	 * @author Federico Augello
	 * @description funzione che si avvia entrando nell'area Addetto Al Personale
	 */
	
	@FXML
	private void initialize() throws SQLException{
		listImpiegato = DBImpiegato.getInstance().getImpiegati();
		matricolaColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("matricola"));
		cognomeColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("cognome"));
		dettagliImpiegato(null);
		
		tabellaImpiegati.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> dettagliImpiegato(newValue));
		
		System.out.println(listImpiegato);
		
		FilteredList<DatiImpiegato> filteredData = new FilteredList<>(listImpiegato, p -> true);

		ricercaImpiegato.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(impiegato -> {
		          // Se il testo della barra è vuoto, restituisce tutte le linee.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Ricerca linee, sia per capolinea che per numero.
		          String lowerCaseFilter = newValue.toLowerCase();

		          return impiegato.getDatiNome().toLowerCase().contains(lowerCaseFilter) ||
		          			 impiegato.getDatiCognome().toLowerCase().contains(lowerCaseFilter) ||
		          			 impiegato.getDatiMatricola().toLowerCase().contains(lowerCaseFilter);
		      });
		    });
		SortedList<DatiImpiegato> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaImpiegati.comparatorProperty());

	  	tabellaImpiegati.setItems(sortedData);
	}
	
	private void dettagliImpiegato(DatiImpiegato datiImpiegato) {
	    if (datiImpiegato != null) {
	        // Riempie le label con nome, cognome e gli altri dati
	        nomeLabel.setText(datiImpiegato.getDatiNome());
	        cognomeLabel.setText(datiImpiegato.getDatiCognome());
	        emailLabel.setText(datiImpiegato.getDatiEmail());
	        matricolaLabel.setText(datiImpiegato.getDatiMatricola());
	        ruoloLabel.setText(datiImpiegato.getDatiRuolo());
	        nascitaLabel.setText(String.valueOf(datiImpiegato.getDatiNascita()));
	        stipendioLabel.setText(String.valueOf(datiImpiegato.getDatiStipendio()) + " €");
	        
	        // TODO: Altri dati
	    } else {
	        // Se non viene selezionata nessuna linea, non mostra nulla.
	        nomeLabel.setText("");
	        cognomeLabel.setText("");
//	        TODO: Aggiungere gli altri
	        matricolaLabel.setText("");
	        emailLabel.setText("");
	        ruoloLabel.setText("");
	        nascitaLabel.setText("");
	        stipendioLabel.setText("");
	    }
	}
	
	@FXML
	public void aggiungiImpiegato() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AggiungiImpiegato.fxml"));
		AnchorPane aggiungiImpiegato = (AnchorPane) loader.load();
		Scene scene = new Scene(aggiungiImpiegato);
		System.out.println(scene);
		System.out.println(aggiungiImpiegato);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AggiungiImpiegatoControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	private void modificaImpiegato() {
	    DatiImpiegato impiegatoSel = tabellaImpiegati.getSelectionModel().getSelectedItem();
	    if (impiegatoSel != null) {
	        boolean okClicked = modificaImpiegatoScene(impiegatoSel);
	        if (okClicked) {
	            dettagliImpiegato(impiegatoSel);
	        }

	    } else {
	        // Nothing selected.
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(null);
	        alert.setTitle("Nessuna Selezione");
	        alert.setHeaderText("Nessun impiegato selezionato");
	        alert.setContentText("Selezionare un impiegato dall'elenco.");

	        alert.showAndWait();
	    }
	}
	
	public boolean modificaImpiegatoScene(DatiImpiegato datiImpiegato) {
		try {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/ModificaImpiegato.fxml"));
		AnchorPane modificaImpiegato = (AnchorPane) loader.load();
		Scene scene = new Scene(modificaImpiegato);
		System.out.println(scene);
		System.out.println(modificaImpiegato);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		ModificaImpiegatoControl controller = loader.getController();
		controller.setMainApp(mainApp);
        controller.setImpiegato(datiImpiegato);
        
        return controller.isOkClicked();
		}catch (IOException e){
			 e.printStackTrace();
		     return false;
		}
	}
	
	@FXML
	public void logoutAP() throws IOException{
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
