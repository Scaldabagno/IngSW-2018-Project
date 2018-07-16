package it.ingsw.address.view;

import java.io.IOException;

import it.ingsw.address.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AreaRiservataControl {
private MainApp mainApp;

	@FXML
	private Button ap;
	
	@FXML
	private Button am;
	
	@FXML
	private Button autista;
	
	@FXML
	private Button annulla;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void accessoAddettoAlPersonale() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AccessoAddettoAlPersonale.fxml"));
		AnchorPane accessoAddettoAlPersonale = (AnchorPane) loader.load();
		Scene scene = new Scene(accessoAddettoAlPersonale);
		System.out.println(scene);
		System.out.println(accessoAddettoAlPersonale);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AccessoAddettoAlPersonaleControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void accessoAddettoAiMezzi() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AccessoAddettoAiMezzi.fxml"));
		AnchorPane accessoAddettoAiMezzi = (AnchorPane) loader.load();
		Scene scene = new Scene(accessoAddettoAiMezzi);
		System.out.println(scene);
		System.out.println(accessoAddettoAiMezzi);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AccessoAddettoAiMezziControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	
	@FXML
	public void accessoAutista() throws IOException{
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AccessoAutista.fxml"));
		AnchorPane accessoAutista = (AnchorPane) loader.load();
		Scene scene = new Scene(accessoAutista);
		System.out.println(scene);
		System.out.println(accessoAutista);
		Stage stage = mainApp.getPrimaryStage();
		stage.setScene(scene);
		AccessoAutistaControl controller = loader.getController();
		controller.setMainApp(mainApp);
	}
	@FXML
	public void annullaButton() throws IOException{
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
}
