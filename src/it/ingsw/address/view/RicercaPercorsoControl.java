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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RicercaPercorsoControl {
	private MainApp mainApp;
	
	@FXML
	private Spinner<String> partenzaSpinner;
	
	@FXML
	private Spinner<String> arrivoSpinner;
	
	
	@FXML
	private TextArea percorso;
	
	@FXML
	private Button calcola;
	
	@FXML
	private Button annulla;

	private ArrayList<Fermata> fermateDaInizio = new ArrayList<>();
	private ArrayList<Fermata> fermateDaFine = new ArrayList<>();
	
	@FXML
	private void initialize() {
		String[] varieFermate = {"Stadio", "Stazione", "P.zza Politeama", "Mondello", "Via Roma", "Ospedale Civico", "C.so Tukory", "C.so Alberto Amedeo", "Viale Della Libertà", "P.zza Valdesi", "Parcheggio Emiri", "Viale Del Fante"};
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
		percorso.setText("");
		try {
			
			Fermata partenza = new Fermata();
			Fermata arrivo = new Fermata();
			Fermata polit = new Fermata();
			polit.setFermata("P.zza Politeama");
			Fermata rom = new Fermata();
			rom.setFermata("Via Roma");
			Fermata stad = new Fermata();
			stad.setFermata("Stadio");
			
			partenza.setFermata(partenzaSpinner.getValue());
			arrivo.setFermata(arrivoSpinner.getValue());
			
			
			int idPartenza = DBLinea.getInstance().getIdFermataByFermata(partenza);
			int idArrivo = DBLinea.getInstance().getIdFermataByFermata(arrivo);
			
			partenza.setIdFermata(idPartenza);
			arrivo.setIdFermata(idArrivo);
			
			Linea a = DBLinea.getInstance().getLineaByFermata(partenza);
			Linea b = DBLinea.getInstance().getLineaByFermata(arrivo);
			
			Fermata inizioA = DBLinea.getInstance().getCapolinea(a, true);
			fermateDaInizio = DBLinea.getInstance().getFermate(a);
			Fermata fineA =  DBLinea.getInstance().getCapolinea(a, false);
			
			
			Fermata inizioB = DBLinea.getInstance().getCapolinea(b, true);
			fermateDaFine =DBLinea.getInstance().getFermate(b);
			Fermata fineB = DBLinea.getInstance().getCapolinea(b, false);
			
			a.setCapolineaI(partenza);
			b.setCapolineaF(arrivo);
			if(partenzaSpinner.getValue() == arrivoSpinner.getValue()) {
				
				percorso.setText("Sei già alla fermata " + partenzaSpinner.getValue());
			}
			
			else if(Integer.valueOf(a.getNumeroLinea()) == Integer.valueOf(b.getNumeroLinea())){
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());
				
			}
			
			else if((partenzaSpinner.getValue() == "P.zza Politeama" && Integer.valueOf(b.getNumeroLinea()) == 108) || (Integer.valueOf(a.getNumeroLinea()) == 108 && Integer.valueOf(b.getNumeroLinea()) == 108)){
				
				a.setNumeroLinea("108");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((arrivoSpinner.getValue() == "P.zza Politeama" && Integer.valueOf(a.getNumeroLinea()) == 108) || (Integer.valueOf(a.getNumeroLinea()) == 108 && Integer.valueOf(b.getNumeroLinea()) == 108)) {
				
				a.setNumeroLinea("108");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((partenzaSpinner.getValue() == "P.zza Politeama" && Integer.valueOf(b.getNumeroLinea()) == 806) || (Integer.valueOf(a.getNumeroLinea()) == 806 && Integer.valueOf(b.getNumeroLinea()) == 806)) {
				
				a.setNumeroLinea("806");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((arrivoSpinner.getValue() == "P.zza Politeama" && Integer.valueOf(a.getNumeroLinea()) == 806) || (Integer.valueOf(a.getNumeroLinea()) == 806 && Integer.valueOf(b.getNumeroLinea()) == 806)) {
				
				a.setNumeroLinea("806");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((partenzaSpinner.getValue() == "Via Roma" && Integer.valueOf(b.getNumeroLinea()) == 106) || (Integer.valueOf(a.getNumeroLinea()) == 106 && Integer.valueOf(b.getNumeroLinea()) == 106)) {
				
				a.setNumeroLinea("106");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((arrivoSpinner.getValue() == "Via Roma" && Integer.valueOf(a.getNumeroLinea()) == 106) || (Integer.valueOf(a.getNumeroLinea()) == 106 && Integer.valueOf(b.getNumeroLinea()) == 106)) {
				
				a.setNumeroLinea("106");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((partenzaSpinner.getValue() == "Stadio" && Integer.valueOf(b.getNumeroLinea()) == 106) || (Integer.valueOf(a.getNumeroLinea()) == 106 && Integer.valueOf(b.getNumeroLinea()) == 106)) {
				
				a.setNumeroLinea("106");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((arrivoSpinner.getValue() == "Stadio" && Integer.valueOf(a.getNumeroLinea()) == 106) || (Integer.valueOf(a.getNumeroLinea()) == 106 && Integer.valueOf(b.getNumeroLinea()) == 106)) {
				
				a.setNumeroLinea("106");
				inizioA = DBLinea.getInstance().getCapolinea(a, true);
				fermateDaInizio = DBLinea.getInstance().getFermate(a);
				fineA =  DBLinea.getInstance().getCapolinea(a, false);
				a.setFermate(fermateDaInizio);
				
				percorso.setText(inizioA + String.valueOf(fermateDaInizio).replace("[", ", ").replace("]", ", ") + fineA + "\n"
						+ "Salire alla fermata " + partenza + " e scendere alla fermata " + arrivo + "\n" + "Seguendo il percorso della linea " + a.getNumeroLinea());			
			}
			
			else if((fermateDaFine.contains(polit) || fermateDaInizio.contains(polit) || inizioB.getFermata().equals(polit.getFermata()) || fineB.getFermata().equals(polit.getFermata()) || inizioA.getFermata().equals(polit.getFermata()) || fineA.getFermata().equals(polit.getFermata()))){
				if(Integer.valueOf(b.getNumeroLinea()) == 106) {
					percorso.setText(a.getNumeroLinea() + ": " + inizioA + ", " + String.valueOf(fermateDaInizio).replace("[", "").replace("]", "") + ", " + fineA + "\n" + b.getNumeroLinea() +  ": " + inizioB + ", " + String.valueOf(fermateDaFine).replace("[", "").replace("]", "") + ", " + fineB + "\nSalire alla fermata " + partenzaSpinner.getValue() + " della linea " + a.getNumeroLinea() + "\nScendere alla fermata " + polit.getFermata() + "\nRisalire alla fermata " + rom.getFermata() + " della linea " + b.getNumeroLinea() + "\nE rimanere fino alla fermata " + arrivoSpinner.getValue());
				} else if(Integer.valueOf(a.getNumeroLinea()) == 106){
					percorso.setText(a.getNumeroLinea() + ": " + inizioA + ", " + String.valueOf(fermateDaInizio).replace("[", "").replace("]", "") + ", " + fineA + "\n" + b.getNumeroLinea() +  ": " + inizioB + ", " + String.valueOf(fermateDaFine).replace("[", "").replace("]", "") + ", " + fineB + "\nSalire alla fermata " + partenzaSpinner.getValue() + " della linea " + a.getNumeroLinea() + "\nScendere alla fermata " + rom.getFermata() + "\nRisalire alla fermata " + polit.getFermata() + " della linea " + b.getNumeroLinea() + "\nE rimanere fino alla fermata " + arrivoSpinner.getValue());
				} else {
					percorso.setText(a.getNumeroLinea() + ": " + inizioA + ", " + String.valueOf(fermateDaInizio).replace("[", "").replace("]", "") + ", " + fineA + "\n" + b.getNumeroLinea() +  ": " + inizioB + ", " + String.valueOf(fermateDaFine).replace("[", "").replace("]", "") + ", " + fineB + "\nSalire alla fermata " + partenzaSpinner.getValue() + " della linea " + a.getNumeroLinea() + "\nScendere alla fermata " + polit.getFermata() + "\nRisalire alla stessa fermata, ma della linea " + b.getNumeroLinea() + "\nE rimanere fino alla fermata " + arrivoSpinner.getValue());
				}
			}
			
			else if(fermateDaFine.contains(rom) || fermateDaInizio.contains(rom) || inizioB.getFermata().equals(rom.getFermata()) || fineB.getFermata().equals(rom.getFermata()) || inizioA.getFermata().equals(rom.getFermata()) || fineA.getFermata().equals(rom.getFermata())){
				percorso.setText(a.getNumeroLinea() + ": " + inizioA + ", " + String.valueOf(fermateDaInizio).replace("[", "").replace("]", "") + ", " + fineA + "\n" + b.getNumeroLinea() +  ": " + inizioB + ", " + String.valueOf(fermateDaFine).replace("[", "").replace("]", "") + ", " + fineB + "\nSalire alla fermata " + partenzaSpinner.getValue() + " della linea " + a.getNumeroLinea() + "\nScendere alla fermata " + rom.getFermata() + "\nRisalire alla stessa fermata, ma della linea " + b.getNumeroLinea() + "\nE rimanere fino alla fermata " + arrivoSpinner.getValue());
			}
			
			else if(fermateDaFine.contains(stad) || fermateDaInizio.contains(stad) || inizioB.getFermata().equals(stad.getFermata()) || fineB.getFermata().equals(stad.getFermata()) || inizioA.getFermata().equals(stad.getFermata()) || fineA.getFermata().equals(stad.getFermata())){
				percorso.setText(a.getNumeroLinea() + ": " + inizioA + ", " + String.valueOf(fermateDaInizio).replace("[", "").replace("]", "") + ", " + fineA + "\n" + b.getNumeroLinea() +  ": " + inizioB + ", " + String.valueOf(fermateDaFine).replace("[", "").replace("]", "") + ", " + fineB + "\nSalire alla fermata " + partenzaSpinner.getValue() + " della linea " + a.getNumeroLinea() + "\nScendere alla fermata " + stad.getFermata() + "\nRisalire alla stessa fermata, ma della linea " + b.getNumeroLinea() + "\nE rimanere fino alla fermata " + arrivoSpinner.getValue());
			}
			
			else {
				System.out.println("Non ci sono punti in comune");
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
