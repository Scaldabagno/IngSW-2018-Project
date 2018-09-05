package it.ingsw.address.view;



import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RicercaPercorsoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField partenza;
	
	@FXML
	private TextField arrivo;
	
	@FXML
	private Spinner<String> partenzaSpinner;
	
	@FXML
	private Spinner<String> arrivoSpinner;
	
	
	@FXML
	private ListView<String> percorso;
	
	@FXML
	private Button calcola;
	
	@FXML
	private Button annulla;
	
	private ArrayList<Linea> arrayLinee = null;
	private ArrayList<Linea> contieneInizio = new ArrayList<>();
	private ArrayList<Linea> contieneFine = new ArrayList<>();
	
	@FXML
	private void initialize() {
////		TODO: Aggiustare
//		linesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//
//			@Override
//		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//		    		String[] steps = newValue.split(" -> ");
//		    		Linea line1 = null;
//		    		Linea line2 = null;
//		    		ArrayList<Fermata> stops2 = null;
//		    		ArrayList<Fermata> stops1 = null;
//		    		for(int i=0; i<contieneInizio.size(); i++) {
//		    			if(contieneFine.get(i).getNumeroLinea().equals(steps[0])) 
//		    				line1 = contieneInizio.get(i);
//		    		}
//		    		if(steps.length!=1) {
//			    		for(int i=0; i<contieneFine.size(); i++) {
//			    			if(contieneFine.get(i).getNumeroLinea().equals(steps[2])) 
//			    				line2 = contieneFine.get(i);
//			    		}
//			    		 stops2 = line2.getFermate();
//		    		} 
//		    		
//		    		ArrayList<String> path = new ArrayList<>();
//		    		
//		    		stops1 = line1.getFermate();
//		    		
//		    		boolean inside = false;
//		    		path.add("Dirigiti verso la fermata "+partenza.getText());
//		    		path.add("Sali sul bus "+steps[0]);
//		    		if(steps.length!=1) {
//			    		for(int i=0; i<stops1.size(); i++) {
//			    			if(stops1.get(i).getFermata().equals(steps[1])) {
//			    				break;
//			    			}
//			    			
//			    			if(inside) {
//			    				path.add("Fermata "+stops1.get(i).getFermata());	
//			    			}
//			    			
//			    			if(partenza.getText().equals(stops1.get(i).getFermata())) {
//			    				inside = true;
//			    			}
//			    		}
//			    		
//			    		path.add("Scendi alla fermata "+steps[1]);
//			    		
//			    		path.add("Sali sul "+steps[2]);
//			    		
//			    		inside = false;
//			    		
//			    		for(int i=0; i<stops2.size(); i++) {
//			    			if(arrivo.getText().equals(stops2.get(i).getFermata())) {
//			    				break;
//			    			}
//			    			
//			    			if(inside) {
//			    				path.add("Fermata "+stops2.get(i).getFermata());
//			    			}
//			    			
//			    			if(stops2.get(i).getFermata().equals(steps[1])) {
//			    				inside = true;
//			    			}
//			    		}
//			    		
//			    		path.add("Scendi alla fermata "+arrivo.getText());	    			
//		    		}
//		    		else {
//		    			inside = false;
//			    		for(int i=0; i<stops1.size(); i++) {
//			    			if(stops1.get(i).getFermata().equals(arrivo.getText())) {
//			    				break;
//			    			}
//			    			
//			    			if(inside) {
//			    				path.add("Fermata "+stops1.get(i).getFermata());	
//			    			}
//
//			    			if(partenza.getText().equals(partenza.getText())) {
//			    				inside = true;
//			    			}
//			    		}
//		    		}
//		    		path.add("Scendi alla fermata "+arrivo.getText());
//		    		path.add("Sei arrivato a destinazione");
//		    		
//		    		percorso.setItems(FXCollections.observableArrayList(path));
//		    }
//		});
			
		String[] varieFermate = {"Stadio", "Stazione", "P.zza Politeama", "Mondello", "Via Roma", "Ospedale Civico", "C.so Tukory", "C.so Alberto Amedeo", "Viale Della Libertà", "P.zza Valdesi", "Notarbartolo"};
		ObservableList<String> fermate = FXCollections.observableArrayList(varieFermate);
		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(fermate);
		SpinnerValueFactory<String> valueFactory1 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(fermate);
		partenzaSpinner.setValueFactory(valueFactory);
		arrivoSpinner.setValueFactory(valueFactory1);
		
		partenzaSpinner.getValueFactory().setValue(varieFermate[0]);
		arrivoSpinner.getValueFactory().setValue(varieFermate[0]);
	}
	
	@FXML
	private void calcolaPercorso() throws SQLException{
////		TODO: Aggiustare
//		percorso.setItems(FXCollections.observableArrayList());
//		ArrayList<String> percorsoStringa = new ArrayList<>();
//		ArrayList<Fermata> fermate;
//		ArrayList<String[]> sequence = new ArrayList<>();
//		try {
//			arrayLinee = DBLinea.getInstance().getArrayLinee(null);
//		} catch (SQLException e1) {
//			// TODO caduta di connessione
//			e1.printStackTrace();
//		}
//		
//		for(int i=0; i<arrayLinee.size(); i++) {
//			fermate = arrayLinee.get(i).getFermate();
//			for(int j=0; j<fermate.size(); j++) {
//				if(fermate.get(j).getFermata().equalsIgnoreCase(partenza.getText())) 
//					contieneInizio.add(arrayLinee.get(i));
//					percorsoStringa.add(fermate.get(i).getFermata());
//				if(fermate.get(j).getFermata().equalsIgnoreCase(arrivo.getText())) 
//					contieneFine.add(arrayLinee.get(i));
//					percorsoStringa.add(fermate.get(i).getFermata());
//			}
//		}
//		percorso.setItems(FXCollections.observableArrayList(percorsoStringa));
//		if(partenzaSpinner.getValue() == arrivoSpinner.getValue()) {
//			
//			return;
//		}else {
			try {
				DBLinea.getInstance().getLinee();
				if(DBLinea.getInstance().getLinee().contains(partenzaSpinner.getValue())) {
					System.out.println(DBLinea.getInstance().getLinee());
				}else {
					
				}
				
			} catch(SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Ricerca fallita");
				alert.setContentText("Non è stato possibile calcolare il percorso");
				alert.showAndWait();
				e.printStackTrace();
			}
//		}
	}
	
//	@FXML
//	private void onCalcClicked() {
//		Alert error = check();
//
//		if(error != null) {
//			error.showAndWait();
//			return;
//		}else {
//		// Clear ListViews
//		percorso.setItems(FXCollections.observableArrayList());
//		
//		/*
//		 * 
//		 * Cristian test
//		 */
//
//		ArrayList<Fermata> stops;
//		ArrayList<String[]> sequence = new ArrayList<>();
//		try {
//			arrayLinee = DBLinea.getInstance().getArrayLinee(null);
//		} catch (SQLException e1) {
//			// TODO caduta di connessione
//			e1.printStackTrace();
//		}
//		
//		for(int i=0; i<arrayLinee.size(); i++) {
//			stops = arrayLinee.get(i).getFermate();
//			for(int j=0; j<stops.size(); j++) {
//				if(stops.get(j).getFermata().equalsIgnoreCase(partenza.getText())) 
//					contieneInizio.add(arrayLinee.get(i));
//				if(stops.get(j).getFermata().equalsIgnoreCase(arrivo.getText())) 
//					contieneFine.add(arrayLinee.get(i));
//			}
//		}
//		
//		System.out.println(contieneInizio);
//		System.out.println(contieneFine);
//		
//		for(int i=0; i<contieneInizio.size(); i++) {
//			for(int j=0; j<contieneFine.size(); j++) {
//				ArrayList<Fermata> startStops = contieneInizio.get(i).getFermate();
//				ArrayList<Fermata> endStops = contieneFine.get(j).getFermate();
//				System.out.println("!!" + startStops + " ?? " + endStops);
//				innerLoop:
//				for(int k=0; k<endStops.size(); k++) {
//					for(int l=0; l<startStops.size(); l++) {
//						if(startStops.get(l).getFermata().equals(endStops.get(k).getFermata())) {
//							String[] twoLines = new String[3];
//							twoLines[0] = contieneInizio.get(i).getNumeroLinea();
//							twoLines[1] = contieneFine.get(j).getNumeroLinea();
//							twoLines[2] = endStops.get(k).getFermata();
//							sequence.add(twoLines);
//							break innerLoop;
//						}
//					}
//				}
//			}
//		}
//
//		if (areStopsOk()) {
//			if(sequence.isEmpty()) {
//				percorso.setItems(FXCollections.observableArrayList("Nessun percorso"));
//			}
//			else {
//				ArrayList<String> al = new ArrayList<>();
//				for(int i=0; i<sequence.size(); i++) {
//					if(sequence.get(i)[0].equals(sequence.get(i)[1]))
//						al.add(sequence.get(i)[0]);
//					else
//						al.add(sequence.get(i)[0] + " -> " +sequence.get(i)[2] +" -> " +sequence.get(i)[1]);
//				}
//				System.out.println("AL"+al);
//				
//
//			}
//			percorso.refresh();
//
//		}
//		else {
//			Alert alert = new Alert(AlertType.WARNING);
//		    alert.initOwner(mainApp.getPrimaryStage());
//		    alert.setTitle("Avviso");
//		    alert.setHeaderText("Calcolo percorso fallito");
//		    alert.setContentText("Le fermate inserite non esistono");
//		    alert.showAndWait();
//		}
//		}
//	}
//
//	private boolean areStopsOk() {
//		Fermata inizio = new Fermata(partenza.getText());
//		Fermata fine = new Fermata(arrivo.getText());
//		ArrayList<Fermata> allStops = null;
//		try {
//			allStops = DBLinea.getInstance().getArrayFermate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			Alert alert = new Alert(AlertType.WARNING);
//		    alert.initOwner(mainApp.getPrimaryStage());
//		    alert.setTitle("Avviso");
//		    alert.setHeaderText("Connessione non disponibile");
//		    alert.setContentText("Controlla la connessione e riprova");
//		    alert.showAndWait();
//			return false;
//		}
//		boolean s1 = false, s2 = false, retval;
//		for(Fermata s: allStops) {
//			if (s.getFermata().toLowerCase().equals(inizio.getFermata().toLowerCase())) {
//				s1 = true;
//			}
//			if(s.getFermata().toLowerCase().equals(fine.getFermata().toLowerCase())) {
//				s2 = true;
//			}
//		}
//		retval = s1 && s2;
//		System.out.println("VALID STOPS: " + retval);
//		return retval;
//	}

//	private Alert check() {
//		Alert alert = new Alert(AlertType.WARNING);
//	    alert.initOwner(mainApp.getPrimaryStage());
//	    alert.setTitle("Avviso");
//	    alert.setHeaderText("Calcolo percorso fallito");
//	    
//	    if(partenzaSpinner.getValue() == arrivoSpinner.getValue()) {
//	    	alert.setContentText("Hai selezionato la stessa fermata sia per partenza che per arrivo");
//	    	return alert;
//	    }
//
//		// If it's all ok
//		return null;
//	}
	
	private void calcFermate() {
		
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
