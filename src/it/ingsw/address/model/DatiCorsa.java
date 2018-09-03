package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiCorsa {
	private final SimpleStringProperty datiMatricolaImpiegato;
	private final SimpleStringProperty datiTargaMezzo;
	private final SimpleStringProperty datiNumeroLineaLinea;
	
	private Corsa corsa;
	
	public DatiCorsa(String matricolaImpiegato, String targaMezzo, String numeroLinea) {
		this.datiMatricolaImpiegato = new SimpleStringProperty(matricolaImpiegato);
		this.datiTargaMezzo = new SimpleStringProperty(targaMezzo);
		this.datiNumeroLineaLinea = new SimpleStringProperty(numeroLinea);
	}
	
	public DatiCorsa(Corsa c) {
		this.datiMatricolaImpiegato = new SimpleStringProperty(c.getImpiegato().getMatricola());
		this.datiTargaMezzo = new SimpleStringProperty(c.getMezzo().getTarga());
		this.datiNumeroLineaLinea = new SimpleStringProperty(c.getLinea().getNumeroLinea());
	}
	
	public String getDatiMatricolaImpiegato() {
		return datiMatricolaImpiegato.get();
	}
	
	public void setDatiMatricolaImpiegato(String matricolaImpiegato) {
		this.datiMatricolaImpiegato.set(matricolaImpiegato);
	}
	
	public StringProperty matricolaImpiegatoProperty() {
	    return datiMatricolaImpiegato;
	}
	
	public String getDatiTargaMezzo() {
		return datiTargaMezzo.get();
	}
	
	public void setDatiTargaMezzo(String targaMezzo) {
		this.datiTargaMezzo.set(targaMezzo);
	}
	
	public StringProperty targaMezzoProperty() {
	    return datiTargaMezzo;
	}
	
	public String getDatiNumeroLineaLinea() {
		return datiNumeroLineaLinea.get();
	}
	
	public void setDatiNumeroLineaLinea(String numeroLinea) {
		this.datiNumeroLineaLinea.set(numeroLinea);
	}
	
	public StringProperty numeroLineaProperty() {
	    return datiNumeroLineaLinea;
	}
	
	public String getEdit() {
		return "edit";
	}

	public String getDelete() {
		return "delete";
	}
	
	public Corsa getCorsa() {
		return corsa;
	}
	
	public void setCorsa(Corsa corsa) {
		this.corsa = corsa;
	}
}
