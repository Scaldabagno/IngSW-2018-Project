package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.DatiCorsa;
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
	private TableColumn<DatiCorsa, String> matricolaColumn;
	
	@FXML
	private TableColumn<DatiCorsa, String> targaColumn;
	
	@FXML
	private TableColumn<DatiCorsa, String> lineaColumn;
	
	@FXML
	private Button riallocaCorsa;
	
	@FXML
	private Button disallocaCorsa;
	
	@FXML
	private Button disallocaCorse;
	
	
	ObservableList<DatiCorsa> listCorse = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException {
		listCorse = DBLinea.getInstance().getCorse();
		matricolaColumn.setCellValueFactory(cellData -> cellData.getValue().matricolaImpiegatoProperty());
        targaColumn.setCellValueFactory(cellData -> cellData.getValue().targaMezzoProperty());
        lineaColumn.setCellValueFactory(cellData -> cellData.getValue().numeroLineaProperty());
		tabellaCorse.getSelectionModel().selectedItemProperty();
		
		FilteredList<DatiCorsa> filteredData = new FilteredList<>(listCorse, p -> true);

		SortedList<DatiCorsa> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaCorse.comparatorProperty());

	  	tabellaCorse.setItems(sortedData);
	  	
	}
	
	@FXML
	private void disallocaCorsa() throws SQLException{
		Alert error = check();
		if(error != null) {
			error.showAndWait();
		}else {
			try {
				DBLinea.getInstance().disallocaCorsaQuery(tabellaCorse.getSelectionModel().getSelectedItem());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("La corsa è stata disallocata con successo!");
				alert.setContentText("La corsa è stata disallocata con successo!");
				alert.showAndWait();
				initialize();
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Eccezione");
			alert.setHeaderText("L'impiegato non è stato assegnato ad una corsa");
			alert.setContentText("Selezionare un altro impiegato o allocare quello già selezionato");
			alert.showAndWait();
			e.printStackTrace();
		}
		}
		}
	
	@FXML
	private void disallocaCorse() throws SQLException, IOException{
			try {
				DBLinea.getInstance().disallocaCorseQuery();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("La corse sono state disallocate con successo!");
				alert.setContentText("La corse sono state disallocate con successo!");
				alert.showAndWait();
				initialize();
		}catch (SQLException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Eccezione");
			alert.setHeaderText("L'impiegato non è stato assegnato ad una corsa");
			alert.setContentText("Selezionare un altro impiegato o allocare quello già selezionato");
			alert.showAndWait();
			e.printStackTrace();
		}
		}
	
	private Alert check() throws SQLException {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check nome
		if (tabellaCorse.getSelectionModel().getSelectedItem() == null) {
			alert.setContentText("Seleziona una corsa");
			return alert;
		}
		
		
		// Data is ok
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
