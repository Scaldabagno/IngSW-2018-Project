package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBImpiegato;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Ruolo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificaImpiegatoControl {
	private MainApp mainApp;
	
	@FXML
	private TextField nomeText;
	
	@FXML
	private TextField cognomeText;
	
	@FXML
	private TextField emailText;
	
	@FXML
	private TextField passwordText;
	
	@FXML
	private TextField matricolaText;
	
	@FXML
	private DatePicker dataDiNascitaText;
	
	@FXML
	private Spinner<String> ruoloSpinner;
	
	@FXML
	private Button annulla;
	
	@FXML
	private Button modifica;
	
	private DatiImpiegato datiImpiegato;
	private boolean okClicked = false;
	
	@FXML
	public void initialize() {
		String[] variRuoli = {"AddettoAlPersonale" , "AddettoAiMezzi", "Autista"};
		ObservableList<String> ruoli = FXCollections.observableArrayList(variRuoli);
		SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(ruoli);
		ruoloSpinner.setValueFactory(valueFactory);
		
		ruoloSpinner.getValueFactory().setValue(variRuoli[0]);
	}
	
	public void setImpiegato(DatiImpiegato datiImpiegato) {
        this.datiImpiegato = datiImpiegato;

        nomeText.setText(datiImpiegato.getDatiNome());
        cognomeText.setText(datiImpiegato.getDatiCognome());
        emailText.setText(datiImpiegato.getDatiEmail());
        passwordText.setText(datiImpiegato.getDatiPassword());
        matricolaText.setText(datiImpiegato.getDatiMatricola());
        dataDiNascitaText.setValue(datiImpiegato.getDatiNascita());
        ruoloSpinner.setDisable(true);
        matricolaText.setDisable(true);
    }
	
	public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            datiImpiegato.setDatiNome(nomeText.getText());
            datiImpiegato.setDatiCognome(cognomeText.getText());
            datiImpiegato.setDatiEmail(emailText.getText());
            datiImpiegato.setDatiPassword(passwordText.getText());
            datiImpiegato.setDatiMatricola(matricolaText.getText());
            datiImpiegato.setDatiNascita(dataDiNascitaText.getValue());
            

            okClicked = true;
        }
    }
    
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(null);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
    
    @FXML
	private void modifica() {
		Alert error = check();
		if (error != null) {
			error.showAndWait();
		} else {
			try {
				DBImpiegato.getInstance().modificaImpiegato(getModificaImpiegato());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento avvenuto con successo!");
				alert.setContentText("L'impiegato è stato aggiunto all'elenco degli impiegati");
				alert.showAndWait();

				matricolaText.setText("");
				nomeText.setText("");
				cognomeText.setText("");
				emailText.setText("");
				passwordText.setText("");
				dataDiNascitaText.setValue(LocalDate.of(1900, 1, 1));

			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento fallito!");
				alert.setContentText("Il numero di matricola inserito è già stato assegnato");
				alert.showAndWait();
				e.printStackTrace();
			}
			String result = "L'impiegato con matricola " + matricolaText.getText() + " é stato modificato con successo";
			System.out.println(result);
		}
	}
	
	private Impiegato getModificaImpiegato() throws SQLException {
		Impiegato impiegato = new Impiegato();
		impiegato.setMatricola(matricolaText.getText());
		impiegato.setNome(nomeText.getText());
		impiegato.setCognome(cognomeText.getText());
		impiegato.setDataNascita(dataDiNascitaText.getValue());
		impiegato.setEmail(emailText.getText());
		impiegato.setRuolo(Ruolo.valueOf(this.ruoloSpinner.getValue()));
//		e.setWorkshift(Workshift.valueOf(this.turno.getValue().toUpperCase()));
		impiegato.setPassword(passwordText.getText());
		return impiegato;
	}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check nome
		if (nomeText.getText().equals("")) {
			alert.setContentText("Inserisci un nome");
			return alert;
		}
		// Check cognome
		if (cognomeText.getText().equals("")) {
			alert.setContentText("Inserisci un cognome");
			return alert;
		}
		// Check email
		if (emailText.getText().equals("")) {
			alert.setContentText("Inserisci un indirizzo e-mail");
			return alert;
		}

		// Check password
		if (passwordText.getText().equals("")) {
			alert.setContentText("Inserisci una password");
			return alert;
		}
		// Check datanascita
		if (dataDiNascitaText.getValue() == null) {
			alert.setContentText("Inserisci una data di nascita");
			return alert;
		}
//		// Check stipendio
//		if (stipendio.getText().equals("")) {
//			alert.setContentText("Inserisci uno stipendio");
//			return alert;
//		}
		// Check lunghezza matricola
			//Check email pattern
			String regex = "^[\\w\\d\\.]+@[\\w\\.]+\\.\\w+$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(emailText.getText());
			if(! m.matches()) {
				alert.setContentText("Inserisci un indirizzo email valido");
				return alert;
			}
			// Numerical check for stipendio
//			try {
//				double s = Double.parseDouble(stipendio.getText());
//				if(s <= 0) throw new NumberFormatException();
//			}
//			catch(NumberFormatException e) {
//				alert.setContentText("Inserisci un valore numerico positivo per lo stipendio");
//				return alert;
//			}
			// Numerical check for matricola
			if (!matricolaText.getText().equals("")) {
				try {
					int a = Integer.parseInt(matricolaText.getText());
					if(a < 0)	throw new NumberFormatException();
				}
				catch(NumberFormatException e) {
					alert.setContentText("Inserisci un valore numerico positivo per la matricola");
					return alert;
				}
			}
			// Alfabetical check for name
//			if( (Integer.parseInt(nome.getText())) | (Integer.parseInt(cognome.getText())) ) {
//				alert.setContentText("Nome e cognome non possono contenere solo numeri");
//				return alert;
//			}
			// TODO: String check for nome e cognome
			try {
				Integer.parseInt(nomeText.getText());
				alert.setContentText("Inserisci un nome valido");
				return alert;
			}
			catch(NumberFormatException exc) {
				;
			}
			
			try {
				Integer.parseInt(cognomeText.getText());
				alert.setContentText("Inserisci un cognome valido");
				return alert;
			}
			catch(NumberFormatException exc) {
				;
			}

		// Data is ok
		return null;
	}

	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAlPersonaleScreen.fxml"));
		AnchorPane addettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAlPersonale);
		System.out.println(scene);
		System.out.println(addettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
