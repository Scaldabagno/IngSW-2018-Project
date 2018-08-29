package it.ingsw.address.view;

import java.io.IOException;
import java.sql.SQLException;
import it.ingsw.address.MainApp;
import it.ingsw.address.database.DBMezzo;
import it.ingsw.address.model.DatiMezzo;
import it.ingsw.address.model.Mezzo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificaMezzoControl {
private MainApp mainApp;
	
	@FXML
	private TextField targaText;
	
	@FXML
	private TextField postoText;
	
	@FXML
	private Button annulla;
	
	@FXML
	private Button modifica;
	
	private DatiMezzo datiMezzo;
	private boolean okClicked = false;
	
	@FXML
	public void initialize() {
	}
	
	public void setMezzo(DatiMezzo datiMezzo) {
        this.datiMezzo = datiMezzo;

        targaText.setText(datiMezzo.getDatiTarga());
        postoText.setText(datiMezzo.getDatiPosto());
        targaText.setDisable(true);
    }
	
	public boolean isOkClicked() {
        return okClicked;
    }
	
	@FXML
    private void handleOk() {
        if (isInputValid()) {
            datiMezzo.setDatiTarga(targaText.getText());
            datiMezzo.setDatiPosto(postoText.getText());
            

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
				DBMezzo.getInstance().modificaMezzo(getModificaMezzo());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento avvenuto con successo!");
				alert.setContentText("Il mezzo stato aggiunto all'elenco dei mezzi");
				alert.showAndWait();

				targaText.setText("");
				postoText.setText("");
				
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(null);
				alert.setTitle("Avviso");
				alert.setHeaderText("Inserimento fallito!");
				alert.setContentText("Il numero di targa inserito � gi� stato assegnato");
				alert.showAndWait();
				e.printStackTrace();
			}
			String result = "Il mezzo con targa " + targaText.getText() + " � stato modificato con successo";
			System.out.println(result);
		}
	}
	
	private Mezzo getModificaMezzo() throws SQLException {
		Mezzo mezzo = new Mezzo();
		mezzo.setTarga(targaText.getText());
		mezzo.setNumeroPosto(Integer.valueOf(postoText.getText()));
		return mezzo;
	}
	
	private Alert check() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Avviso");
		alert.setHeaderText("Inserimento fallito!");

		// Check nome
		if (targaText.getText().equals("")) {
			alert.setContentText("Inserisci una targa");
			return alert;
		}
		// Check cognome
		if (postoText.getText().equals("")) {
			alert.setContentText("Inserisci un posto");
			return alert;
		}
		// Data is ok
		return null;
	}

	
	@FXML
	public void annullaButton() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddettoAiMezziScreen.fxml"));
		AnchorPane addettoAiMezzi = (AnchorPane) loader.load();
		Scene scene = new Scene(addettoAiMezzi);
		System.out.println(scene);
		System.out.println(addettoAiMezzi);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AddettoAiMezziControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}