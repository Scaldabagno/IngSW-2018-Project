package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;


import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.DatiMezzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AllocaMezzoControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiImpiegato> tabellaImpiegati;
	
	@FXML
	private TableView<DatiMezzo> tabellaMezzi;
	
	@FXML
	private TableColumn<DatiImpiegato, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiImpiegato, String> cognomeColumn;
	
	@FXML
	private TableColumn<DatiMezzo, String> targaColumn;
	
	@FXML
	private Button annulla;
	
	@FXML
	private Button allocaMezzo;
	
	ObservableList<DatiImpiegato> listImpiegato = FXCollections.observableArrayList();
	ObservableList<DatiMezzo> listMezzo = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException {

		listMatricole();
		listTarghe();
	  	
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
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
