package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;
import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.Corsa;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.DatiMezzo;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Linea;
import it.ingsw.address.model.Mezzo;
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
import javafx.scene.control.Label;
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
	private Label matricolaText;
	
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
	private void allocaCorsa(){
		Alert error = check();
		
		if(error != null) {
			error.showAndWait();
		}else{
			try {
				DBLinea.getInstance().allocaCorsaQuery(getNuovaCorsa());
				DBLinea.getInstance().turnoQuery(getNuovoTurno());
//				Alert alert = new Alert(AlertType.INFORMATION);
//				alert.initOwner(mainApp.getPrimaryStage());
//				alert.setTitle("Avviso");
//				alert.setHeaderText("La corsa � stata allocata con successo!");
//				alert.setContentText("L'impiegato, il mezzo e la linea sono stati allocati con successi");
//				alert.showAndWait();
				
				
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Allocazione fallita!");
				alert.setContentText("ERRORE");
				alert.showAndWait();
				e.printStackTrace();
			}
		}
	}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check nome
		if (tabellaImpiegati.getSelectionModel().getSelectedItem() == null) {
			alert.setContentText("Seleziona un impiegato");
			return alert;
		}
		if (tabellaMezzi.getSelectionModel().getSelectedItem() == null) {
			alert.setContentText("Seleziona un mezzo");
			return alert;
		}
		if (tabellaLinee.getSelectionModel().getSelectedItem() == null) {
			alert.setContentText("Seleziona un impiegato");
			return alert;
		}
		if(turnoSpinner.getValue() == null) {
			alert.setContentText("Seleziona un turno");
			return alert;
		}
		
		// Data is ok
		return null;
	}
	
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
		matricolaText.setText(String.valueOf(tabellaImpiegati.getSelectionModel().getSelectedItem()));
		
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
	
	private Corsa getNuovaCorsa() throws SQLException {
		DatiImpiegato impiegatoSel = tabellaImpiegati.getSelectionModel().getSelectedItem();
		DatiMezzo mezzoSel = tabellaMezzi.getSelectionModel().getSelectedItem();
		DatiLinea lineaSel = tabellaLinee.getSelectionModel().getSelectedItem();
		
		System.out.println(impiegatoSel.getDatiMatricola());
		System.out.println(mezzoSel.getDatiTarga());
		System.out.println(lineaSel.getDatiNumeroLinea());
		
		Impiegato impiegato = new Impiegato();
		Mezzo mezzo = new Mezzo();
		Linea linea = new Linea();
		
		impiegato.setMatricola(impiegatoSel.getDatiMatricola());
		mezzo.setTarga(mezzoSel.getDatiTarga());
		linea.setNumeroLinea(lineaSel.getDatiNumeroLinea());
		
		Corsa corsa = new Corsa(impiegato, mezzo, linea);
		
		
		
		return corsa;
	}
	
	private Impiegato getNuovoTurno() throws SQLException {
		Impiegato impiegato = new Impiegato();
		
		impiegato.setMatricola(matricolaText.getText());
		impiegato.setTurno(Turno.valueOf(turnoSpinner.getValue()));
		
		return impiegato;
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