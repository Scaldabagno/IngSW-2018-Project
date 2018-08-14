package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBEmployeeShow;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
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
import javafx.scene.control.TableRow;
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
	private Button logout;
	
	@FXML
	private TextField ricercaImpiegato;
	
	@FXML
	private Button nuovoImpiegato;
	
	@FXML
	private Button modificaImpiegato;
	
	@FXML
	private Button cancellaImpiegato;
	
	@FXML
	private Button calcolaStipendio;
	
	@FXML
	private Button allocaMezzo;
	
	@FXML
	private Button allocaTurno;
	
	private Impiegato impiegatoModel;
	ObservableList<DatiImpiegato> listEmployee = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException{
		listEmployee = DBEmployeeShow.getInstance().getAllEmployees();
//		matricolaColumn.setCellValueFactory(
//				new PropertyValueFactory<DatiImpiegato, String>("matricola"));
//		cognomeColumn.setCellValueFactory(
//				new PropertyValueFactory<DatiImpiegato, String>("cognome"));
		matricolaColumn.setCellValueFactory(cellData -> cellData.getValue().matricolaProperty());
		cognomeColumn.setCellValueFactory(cellData -> cellData.getValue().cognomeProperty());
		showImpiegatoDetails(null);
		
		tabellaImpiegati.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showImpiegatoDetails(newValue));
		
		System.out.println(listEmployee);
		
		FilteredList<DatiImpiegato> filteredData = new FilteredList<>(listEmployee, p -> true);

		ricercaImpiegato.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(line -> {
		          // If filter text is empty, display all persons.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Compare first name and last name of every person with filter text.
		          String lowerCaseFilter = newValue.toLowerCase();

		          return impiegatoModel.getMatricola().toLowerCase().contains(lowerCaseFilter) /*||
		          			 line.getStartTerminal().toLowerCase().contains(lowerCaseFilter) ||
		          			 line.getEndTerminal().toLowerCase().contains(lowerCaseFilter)*/;
		      });
		    });
		SortedList<DatiImpiegato> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaImpiegati.comparatorProperty());

	  	tabellaImpiegati.setItems(sortedData);

	  	tabellaImpiegati.setRowFactory(tv -> {
			TableRow<DatiImpiegato> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!row.isEmpty())) {
					impiegatoModel = row.getItem().getImpiegato();
		//			showPath();
				}
			});
			return row;
		});
	 // 	timerStart();
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
	
	private void showImpiegatoDetails(DatiImpiegato datiImpiegato) {
	    if (datiImpiegato != null) {
	        // Fill the labels with info from the person object.
	        nomeLabel.setText(datiImpiegato.getDatiNome());
	        cognomeLabel.setText(datiImpiegato.getDatiCognome());
	        matricolaLabel.setText(datiImpiegato.getDatiMatricola());
	        // TODO: We need a way to convert the birthday into a String!
	        // birthdayLabel.setText(...);
	    } else {
	        // Person is null, remove all the text.
	        nomeLabel.setText("");
	        cognomeLabel.setText("");
	    }
	}

}
