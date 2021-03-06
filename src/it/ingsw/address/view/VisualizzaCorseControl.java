package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.DatiCorsa;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VisualizzaCorseControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiCorsa> tabellaCorse;
	
	@FXML
	private TableView<DatiImpiegato> tabellaImpiegati;
	
	@FXML
	private TableColumn<DatiImpiegato, String> matricola1Column;
	
	@FXML
	private TableColumn<DatiImpiegato, String> turnoColumn;
	
	@FXML
	private TableColumn<DatiCorsa, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiCorsa, String> targaColumn;
	
	@FXML
	private TableColumn<DatiCorsa, String> lineaColumn;
	
	@FXML
	private Button deallocaCorsa;
	
	@FXML
	private Button deallocaCorse;
	
	@FXML
	private Button annulla;
	
	
	ObservableList<DatiCorsa> listCorse = FXCollections.observableArrayList();
	ObservableList<DatiImpiegato> listImpiegato = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException {
		listCorse = DBLinea.getInstance().getCorse();
		listImpiegato = DBImpiegato.getInstance().getAutisti();
		matricola1Column.setCellValueFactory(cellData -> cellData.getValue().matricolaProperty());
		turnoColumn.setCellValueFactory(cellData -> cellData.getValue().turnoProperty());
		matricolaColumn.setCellValueFactory(cellData -> cellData.getValue().matricolaImpiegatoProperty());
        targaColumn.setCellValueFactory(cellData -> cellData.getValue().targaMezzoProperty());
        lineaColumn.setCellValueFactory(cellData -> cellData.getValue().numeroLineaProperty());
		tabellaCorse.getSelectionModel().selectedItemProperty();
		
		FilteredList<DatiImpiegato> filteredData1 = new FilteredList<>(listImpiegato, p -> true);

		SortedList<DatiImpiegato> sortedData1 = new SortedList<>(filteredData1);
	    sortedData1.comparatorProperty().bind(tabellaImpiegati.comparatorProperty());

	  	tabellaImpiegati.setItems(sortedData1);
		
		FilteredList<DatiCorsa> filteredData = new FilteredList<>(listCorse, p -> true);

		SortedList<DatiCorsa> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaCorse.comparatorProperty());

	  	tabellaCorse.setItems(sortedData);
	  	
	}
	
	@FXML
	private void deallocaCorsa() throws SQLException{
		Alert error = check();
		if(error != null) {
			error.showAndWait();
		}else {
			try {
				DBLinea.getInstance().deallocaCorsaQuery(tabellaCorse.getSelectionModel().getSelectedItem());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("La corsa � stata deallocata con successo!");
				alert.setContentText("La corsa � stata deallocata con successo!");
				alert.showAndWait();
				initialize();
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Eccezione");
			alert.setHeaderText("L'impiegato non � stato assegnato ad una corsa");
			alert.setContentText("Selezionare un altro impiegato o allocare quello gi� selezionato");
			alert.showAndWait();
			e.printStackTrace();
		}
		}
		}
	
	@FXML
	private void deallocaCorse() throws SQLException, IOException{
			try {
				DBLinea.getInstance().deallocaCorseQuery();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("La corse sono state deallocate con successo!");
				alert.setContentText("La corse sono state deallocate con successo!");
				alert.showAndWait();
				initialize();
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Eccezione");
			alert.setHeaderText("L'impiegato non � stato assegnato ad una corsa");
			alert.setContentText("Selezionare un altro impiegato o allocare quello gi� selezionato");
			alert.showAndWait();
			e.printStackTrace();
		}
		}
	
	private Alert check() throws SQLException {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		if (tabellaCorse.getSelectionModel().getSelectedItem() == null) {
			alert.setContentText("Seleziona una corsa");
			return alert;
		}
		
		return null;
	}
	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaAddettoAlPersonale.fxml"));
		AnchorPane schermataPrincipale = (AnchorPane) loader.load();
		Scene scene = new Scene(schermataPrincipale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
