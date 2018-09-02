package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Sessione;
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
import javafx.scene.layout.GridPane;
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
	private GridPane gridImpiegato;
	
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
	private Label turnoLabel;
	
	@FXML
	private Label turnoLabel1;
	
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
	private Button profilo;
	
	@FXML
	private Button allocaCorsa;
	
	@FXML
	private Button visualizzaCorsa;
	
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
		
		
		FilteredList<DatiImpiegato> filteredData = new FilteredList<>(listImpiegato, p -> true);

		ricercaImpiegato.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(impiegato -> {
		          // Se il testo della barra è vuoto, restituisce tutti gli impiegati.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Ricerca impiegati.
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
	        if(datiImpiegato.getDatiRuolo() == "AddettoAlPersonale") {
	        	ruoloLabel.setText("Addetto Al Personale");
	        	turnoLabel.setVisible(false);
	        	turnoLabel.setDisable(true);
	        	turnoLabel1.setVisible(false);
	        	turnoLabel1.setDisable(true);
	        }else if(datiImpiegato.getDatiRuolo() == "AddettoAiMezzi") {
	        	ruoloLabel.setText("Addetto Ai Mezzi");
	        	turnoLabel.setVisible(false);
	        	turnoLabel.setDisable(true);
	        }else {
		        ruoloLabel.setText(datiImpiegato.getDatiRuolo());
		        turnoLabel.setVisible(true);
		        turnoLabel.setDisable(false);
		        turnoLabel1.setVisible(true);
	        	turnoLabel1.setDisable(false);
	        }
	        nascitaLabel.setText(String.valueOf(datiImpiegato.getDatiNascita()));
	        stipendioLabel.setText(String.valueOf(datiImpiegato.getDatiStipendio()) + " €");
	        if(datiImpiegato.getDatiTurno() == "NonAssegnato") {
	        	turnoLabel.setText("Non Assegnato");
	        } else {
		        turnoLabel.setText(datiImpiegato.getDatiTurno());
	        }
	    } else {
	        // Se non viene selezionata nessuna linea, non mostra nulla.
	        nomeLabel.setText("");
	        cognomeLabel.setText("");
	        matricolaLabel.setText("");
	        emailLabel.setText("");
	        ruoloLabel.setText("");
	        nascitaLabel.setText("");
	        stipendioLabel.setText("");
	        turnoLabel.setText("");
	    }
	}
	
	@FXML
	public void profiloScene() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Profilo.fxml"));
		AnchorPane profilo = (AnchorPane) loader.load();
		Scene scene = new Scene(profilo);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		ProfiloControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	
	@FXML
	public void aggiungiImpiegato() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AggiungiImpiegato.fxml"));
		AnchorPane aggiungiImpiegato = (AnchorPane) loader.load();
		Scene scene = new Scene(aggiungiImpiegato);
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
	        alert.initOwner(mainApp.getPrimaryStage());
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
	public void calcolaStipendio() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/CalcolaStipendio.fxml"));
		AnchorPane calcolaStipendio = (AnchorPane) loader.load();
		Scene scene = new Scene(calcolaStipendio);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		CalcolaStipendioControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void allocaCorsa() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AllocaCorsa.fxml"));
		AnchorPane allocaCorsa = (AnchorPane) loader.load();
		Scene scene = new Scene(allocaCorsa);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AllocaCorsaControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void logoutAP() throws IOException{
		Sessione.impiegato = new Impiegato();
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
