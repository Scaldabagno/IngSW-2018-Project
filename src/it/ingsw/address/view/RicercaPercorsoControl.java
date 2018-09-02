package it.ingsw.address.view;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Linea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RicercaPercorsoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField partenza;
	
	@FXML
	private TextField arrivo;
	
	@FXML
	private ListView<String> percorso;
	
	@FXML
	private Button calcola;
	
	@FXML
	private Button annulla;
	
	private ArrayList<Linea> arrayLinee = null;
	private ArrayList<Linea> contieneInizio = new ArrayList<>();
	private ArrayList<Linea> contieneFine = new ArrayList<>();
	private ListView<String> linesList;
	private ListView<String> pathList;
	
	@FXML
	private void initialize() {
//		TODO: Aggiustare
		linesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    		String[] steps = newValue.split(" -> ");
		    		Linea line1 = null;
		    		Linea line2 = null;
		    		ArrayList<Fermata> stops2 = null;
		    		ArrayList<Fermata> stops1 = null;
		    		for(int i=0; i<contieneInizio.size(); i++) {
		    			if(contieneFine.get(i).getNumeroLinea().equals(steps[0])) 
		    				line1 = contieneInizio.get(i);
		    		}
		    		if(steps.length!=1) {
			    		for(int i=0; i<contieneFine.size(); i++) {
			    			if(contieneFine.get(i).getNumeroLinea().equals(steps[2])) 
			    				line2 = contieneFine.get(i);
			    		}
			    		 stops2 = line2.getFermate();
		    		} 
		    		
		    		ArrayList<String> path = new ArrayList<>();
		    		
		    		stops1 = line1.getFermate();
		    		
		    		boolean inside = false;
		    		path.add("Dirigiti verso la fermata "+partenza.getText());
		    		path.add("Sali sul bus "+steps[0]);
		    		if(steps.length!=1) {
			    		for(int i=0; i<stops1.size(); i++) {
			    			if(stops1.get(i).getFermata().equals(steps[1])) {
			    				break;
			    			}
			    			
			    			if(inside) {
			    				path.add("Fermata "+stops1.get(i).getFermata());	
			    			}
			    			
			    			if(partenza.getText().equals(stops1.get(i).getFermata())) {
			    				inside = true;
			    			}
			    		}
			    		
			    		path.add("Scendi alla fermata "+steps[1]);
			    		
			    		path.add("Sali sul "+steps[2]);
			    		
			    		inside = false;
			    		
			    		for(int i=0; i<stops2.size(); i++) {
			    			if(arrivo.getText().equals(stops2.get(i).getFermata())) {
			    				break;
			    			}
			    			
			    			if(inside) {
			    				path.add("Fermata "+stops2.get(i).getFermata());
			    			}
			    			
			    			if(stops2.get(i).getFermata().equals(steps[1])) {
			    				inside = true;
			    			}
			    		}
			    		
			    		path.add("Scendi alla fermata "+arrivo.getText());	    			
		    		}
		    		else {
		    			inside = false;
			    		for(int i=0; i<stops1.size(); i++) {
			    			if(stops1.get(i).getFermata().equals(arrivo.getText())) {
			    				break;
			    			}
			    			
			    			if(inside) {
			    				path.add("Fermata "+stops1.get(i).getFermata());	
			    			}

			    			if(partenza.getText().equals(partenza.getText())) {
			    				inside = true;
			    			}
			    		}
		    		}
		    		path.add("Scendi alla fermata "+arrivo.getText());
		    		path.add("Sei arrivato a destinazione");
		    		
		    		pathList.setItems(FXCollections.observableArrayList(path));
		    }
		});
			
	}
	
	@FXML
	private void calcolaPercorso() {
//		TODO: Aggiustare
		percorso.setItems(FXCollections.observableArrayList());
		ArrayList<String> percorsoStringa = new ArrayList<>();
		ArrayList<Fermata> fermate;
		//ArrayList<String[]> sequence = new ArrayList<>();
		try {
			arrayLinee = DBLinea.getInstance().getArrayLinee(null);
		} catch (SQLException e1) {
			// TODO caduta di connessione
			e1.printStackTrace();
		}
		
		for(int i=0; i<arrayLinee.size(); i++) {
			fermate = arrayLinee.get(i).getFermate();
			for(int j=0; j<fermate.size(); j++) {
				if(fermate.get(j).getFermata().equalsIgnoreCase(partenza.getText())) 
					contieneInizio.add(arrayLinee.get(i));
					percorsoStringa.add(fermate.get(i).getFermata());
				if(fermate.get(j).getFermata().equalsIgnoreCase(arrivo.getText())) 
					contieneFine.add(arrayLinee.get(i));
					percorsoStringa.add(fermate.get(i).getFermata());
			}
		}
		percorso.setItems(FXCollections.observableArrayList(percorsoStringa));
	}
	
	@FXML
	public void annullaButton() throws IOException{
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
