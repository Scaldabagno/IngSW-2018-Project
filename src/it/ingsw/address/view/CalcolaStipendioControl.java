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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CalcolaStipendioControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiImpiegato> tabellaAutisti;
	
	@FXML
	private TableColumn<DatiImpiegato, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiImpiegato, String> cognomeColumn;
	
	@FXML
	private TableColumn<DatiImpiegato, String> stipendioColumn;
	
	@FXML
	private TextField giorniText;
	
	@FXML
	private Button calcola; 
	
	@FXML
	private Button annulla;
	
	ObservableList<DatiImpiegato> listImpiegato = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException {
		listImpiegato = DBImpiegato.getInstance().getAutisti();
		matricolaColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("matricola"));
		cognomeColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("cognome"));
		stipendioColumn.setCellValueFactory(
				new PropertyValueFactory<DatiImpiegato, String>("stipendio"));
		
		tabellaAutisti.getSelectionModel().selectedItemProperty();
		
		
		FilteredList<DatiImpiegato> filteredData = new FilteredList<>(listImpiegato, p -> true);
		
		SortedList<DatiImpiegato> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaAutisti.comparatorProperty());

	  	tabellaAutisti.setItems(sortedData);
	}
	
	@FXML
	private void calcolaStipendio() throws IOException {
		DatiImpiegato impiegatoSel = tabellaAutisti.getSelectionModel().getSelectedItem();
			if (impiegatoSel != null) {
		        	Alert error = check();
		    		if (error != null) {
		    			error.showAndWait();
		    		} else {
		    			impiegatoSel.setDatiStipendio(String.valueOf(Double.valueOf(giorniText.getText())*48));
		    			try {
		    				DBImpiegato.getInstance().calcolaStipendio(impiegatoSel);
		    				Alert alert = new Alert(AlertType.INFORMATION);
		    				alert.initOwner(mainApp.getPrimaryStage());
		    				alert.setTitle("Avviso");
		    				alert.setHeaderText("Modifica avvenuto con successo!");
		    				alert.setContentText("L'impiegato è stato modificato all'elenco degli impiegati");
		    				alert.showAndWait();
		    			} catch (SQLException e) {
		    				Alert alert = new Alert(AlertType.WARNING);
		    				alert.initOwner(mainApp.getPrimaryStage());
		    				alert.setTitle("Avviso");
		    				alert.setHeaderText("Inserimento fallito!");
		    				alert.setContentText("Il numero di matricola inserito è già stato assegnato");
		    				alert.showAndWait();
		    				e.printStackTrace();
		    			}

		    		}

		    } else {
		        // Nothing selected.
		        Alert alert = new Alert(AlertType.WARNING);
		        alert.initOwner(mainApp.getPrimaryStage());
		        alert.setTitle("Nessuna Selezione");
		        alert.setHeaderText("Nessun autista selezionato");
		        alert.setContentText("Selezionare un autista dall'elenco.");

		        alert.showAndWait();
		    }
		}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check nome
		if (giorniText.getText().equals("")) {
			alert.setContentText("Inserisci un giorno");
			return alert;
		}
		if (!giorniText.getText().equals("")) {
			try {
				int a = Integer.parseInt(giorniText.getText());
				if(a < 0 || a >= 31)	throw new NumberFormatException();
			} catch(NumberFormatException e) {
				alert.setContentText("Inserisci un valore valido per i giorni, tra 0 e 30");
				return alert;
				}
			}
		return null;
	}
	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaAddettoAlPersonale.fxml"));
		AnchorPane addettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
