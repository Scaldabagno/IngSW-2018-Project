package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLineShow;
import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.Linea;

/**
 * @author Federico Augello
 * @description gestisce la Schermata Principale, "L'area cittadino" 
 */
public class SchermataPrincipaleControl {
	private MainApp mainApp;
	
	@FXML
	private TableView<DatiLinea> tabellaLinee;
	
	@FXML
	private TableColumn<DatiLinea, String> numeroLinea;
	
	@FXML
	private Label linea;
	
	@FXML
	private Label capolinea;
	
	@FXML
	private Label fermate;
	
	@FXML
	private TextField ricercaLinea;
	
	@FXML
	private Button areaRiservata;
	
	public SchermataPrincipaleControl() {
		
	}
	
	private Linea lineaModel;
	ObservableList<DatiLinea> listLine = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() throws SQLException{
		listLine = DBLineShow.getInstance().getAllLines();
//		numeroLinea.setCellValueFactory(
//				new PropertyValueFactory<DatiLinea, String>("numeroLinea"));
		//linea.setText();
		//capolinea.setText();
		numeroLinea.setCellValueFactory(cellData -> cellData.getValue().numeroLineaProperty());
		showLineaDetails(null);
		
		tabellaLinee.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> showLineaDetails(newValue));
		
		System.out.println(listLine);
		
		FilteredList<DatiLinea> filteredData = new FilteredList<>(listLine, p -> true);

		ricercaLinea.textProperty().addListener((observable, oldValue, newValue) -> {
		      filteredData.setPredicate(line -> {
		          // If filter text is empty, display all persons.
		          if (newValue == null || newValue.isEmpty()) {
		              return true;
		          }

		          // Compare first name and last name of every person with filter text.
		          String lowerCaseFilter = newValue.toLowerCase();

		          return lineaModel.getNumeroLinea().toLowerCase().contains(lowerCaseFilter) /*||
		          			 line.getStartTerminal().toLowerCase().contains(lowerCaseFilter) ||
		          			 line.getEndTerminal().toLowerCase().contains(lowerCaseFilter)*/;
		      });
		    });
		SortedList<DatiLinea> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(tabellaLinee.comparatorProperty());

	  	tabellaLinee.setItems(sortedData);

	  	tabellaLinee.setRowFactory(tv -> {
			TableRow<DatiLinea> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!row.isEmpty())) {
					lineaModel = row.getItem().getLinea();
		//			showPath();
				}
			});
			return row;
		});
	 // 	timerStart();
	}
	
	@FXML
	public void areaRiservataButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AreaRiservata.fxml"));
		AnchorPane areaRiservata = (AnchorPane) loader.load();
		Scene scene = new Scene(areaRiservata);
		System.out.println(scene);
		System.out.println(areaRiservata);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AreaRiservataControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}


	private void showLineaDetails(DatiLinea datiLinea) {
	    if (datiLinea != null) {
	        // Fill the labels with info from the person object.
	        linea.setText(datiLinea.getDatiNumeroLinea());
	        capolinea.setText(datiLinea.getDatiCapolineaI() + "-" + datiLinea.getDatiCapolineaF());
	        // TODO: We need a way to convert the birthday into a String!
	        // birthdayLabel.setText(...);
	    } else {
	        // Person is null, remove all the text.
	        linea.setText("");
	        capolinea.setText("");
	    }
	}
	
}
