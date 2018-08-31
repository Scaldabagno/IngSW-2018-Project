package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.DatiMezzo;
import it.ingsw.address.model.Turno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AllocaCorsaControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiImpiegato> tabellaImpiegati;
	
	@FXML
	private TableView<DatiMezzo> tabellaMezzi;
	
	@FXML
	private TableView<DatiLinea> tabellaLinee;
	
	@FXML
	private TableColumn<DatiImpiegato, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiImpiegato, String> cognomeColumn;
	
	@FXML
	private TableColumn<DatiMezzo, String> targaColumn;
	
	@FXML
	private TableColumn<DatiLinea, String> lineaColumn;
	
	@FXML
	private Spinner<String> turnoSpinner;
	
	@FXML
	private Button annulla;
	
	@FXML
	private Button allocaCorsa;
	
	ObservableList<DatiImpiegato> listImpiegato = FXCollections.observableArrayList();
	ObservableList<DatiMezzo> listMezzo = FXCollections.observableArrayList();
	ObservableList<DatiLinea> listLinea = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException {
		String[] variTurni = {"Mattina" , "Pomeriggio", "Sera", "NonAssegnato"};
		ObservableList<String> turni = FXCollections.observableArrayList(variTurni);
		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(turni);
		turnoSpinner.setValueFactory(valueFactory);
		
		turnoSpinner.getValueFactory().setValue(variTurni[0]);
		
		listMatricole();
		listTarghe();
		listLinee();
	  	
	}
	
	@FXML
	private void selezionaImpiegato() {
	    DatiImpiegato impiegatoSel = tabellaImpiegati.getSelectionModel().getSelectedItem();
	    if (impiegatoSel != null) {
	        boolean okClicked = modificaImpiegatoScene(impiegatoSel);
	        if (okClicked) {
	        	System.out.println("Autista selezionato");
	        }
	        
	        DatiMezzo mezzoSel = tabellaMezzi.getSelectionModel().getSelectedItem();
		    if (mezzoSel != null) {
		        boolean okClicked1 = modificaMezzoScene(mezzoSel);
		        if (okClicked1) {
		            System.out.println("Mezzo selezionato");
		        }
		        DatiLinea lineaSel = tabellaLinee.getSelectionModel().getSelectedItem();
			    if (lineaSel != null) {
			        boolean okClicked2 = modificaLineaScene(lineaSel);
			        if (okClicked2) {
			        	System.out.println("Linea selezionata");
			        }

			    } else {
			        // Nothing selected.
			        Alert alert = new Alert(AlertType.WARNING);
			        alert.initOwner(mainApp.getPrimaryStage());
			        alert.setTitle("Nessuna Selezione");
			        alert.setHeaderText("Nessun linea selezionata");
			        alert.setContentText("Selezionare una linea dall'elenco.");

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
	
//	@FXML
//	private void selezionaMezzo() {
//	    DatiMezzo mezzoSel = tabellaMezzi.getSelectionModel().getSelectedItem();
//	    if (mezzoSel != null) {
//	        boolean okClicked = modificaMezzoScene(mezzoSel);
//	        if (okClicked) {
//	            dettagliImpiegato(mezzoSel);
//	        }
//
//	    } else {
//	        // Nothing selected.
//	        Alert alert = new Alert(AlertType.WARNING);
//	        alert.initOwner(mainApp.getPrimaryStage());
//	        alert.setTitle("Nessuna Selezione");
//	        alert.setHeaderText("Nessun mezzo selezionato");
//	        alert.setContentText("Selezionare un mezzo dall'elenco.");
//
//	        alert.showAndWait();
//	    }
//	}
//	
//	@FXML
//	private void selezionaLinea() {
//	    DatiLinea lineaSel = tabellaLinee.getSelectionModel().getSelectedItem();
//	    if (lineaSel != null) {
//	        boolean okClicked = modificaLineaScene(lineaSel);
//	        if (okClicked) {
//	            dettagliImpiegato(lineaSel);
//	        }
//
//	    } else {
//	        // Nothing selected.
//	        Alert alert = new Alert(AlertType.WARNING);
//	        alert.initOwner(mainApp.getPrimaryStage());
//	        alert.setTitle("Nessuna Selezione");
//	        alert.setHeaderText("Nessun linea selezionata");
//	        alert.setContentText("Selezionare una linea dall'elenco.");
//
//	        alert.showAndWait();
//	    }
//	}
	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleArea.fxml"));
		AnchorPane addettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAlPersonale);
		System.out.println(scene);
		System.out.println(addettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	private void listMatricole() throws SQLException {
		listImpiegato = DBImpiegato.getInstance().getAutistiDisponibili();
		matricolaColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("matricola"));
		cognomeColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("cognome"));
		
		tabellaImpiegati.getSelectionModel().selectedItemProperty();
		
		System.out.println(listImpiegato);
		
		FilteredList<DatiImpiegato> filteredData = new FilteredList<>(listImpiegato, p -> true);
		
		SortedList<DatiImpiegato> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaImpiegati.comparatorProperty());

	  	tabellaImpiegati.setItems(sortedData);
	}
	
	private void listTarghe() throws SQLException {
		listMezzo = DBMezzo.getInstance().getMezziDisponibili();
		targaColumn.setCellValueFactory(
				new PropertyValueFactory<DatiMezzo, String>("targa"));
		
		tabellaMezzi.getSelectionModel().selectedItemProperty();
		
		System.out.println(listMezzo);
		
		FilteredList<DatiMezzo> filteredData = new FilteredList<>(listMezzo, p -> true);
		
		SortedList<DatiMezzo> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaMezzi.comparatorProperty());

	  	tabellaMezzi.setItems(sortedData);
	}
	
	private void listLinee() throws SQLException {
		listLinea = DBLinea.getInstance().getLinee();
		lineaColumn.setCellValueFactory(
				new PropertyValueFactory<DatiLinea, String>("numeroLinea"));
		
		tabellaLinee.getSelectionModel().selectedItemProperty();
		
		System.out.println(listLinea);
		
		FilteredList<DatiLinea> filteredData = new FilteredList<>(listLinea, p -> true);
		
		SortedList<DatiLinea> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaLinee.comparatorProperty());

	  	tabellaLinee.setItems(sortedData);
	}
	
	public boolean modificaImpiegatoScene(DatiImpiegato impiegato) {
		return true;
	}
	
	public boolean modificaMezzoScene(DatiMezzo mezzo) {
		return true;
	}

	public boolean modificaLineaScene(DatiLinea linea) {
		return true;
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
