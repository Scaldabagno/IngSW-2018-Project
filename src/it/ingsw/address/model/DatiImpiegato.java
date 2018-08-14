package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiImpiegato {
	private final SimpleStringProperty datiNome;
	private final SimpleStringProperty datiCognome;
	private final SimpleStringProperty datiMatricola;
	//TODO: Aggiungere altre SimpleStringProperty
	private Impiegato impiegato;
	
	public DatiImpiegato(String nome, String cognome, String matricola) {
		this.datiNome = new SimpleStringProperty(nome);
		this.datiCognome = new SimpleStringProperty(cognome);
		this.datiMatricola = new SimpleStringProperty(matricola);
	}
	
	public DatiImpiegato(Impiegato i) {
		this.datiNome = new SimpleStringProperty(i.getNome());
		this.datiCognome = new SimpleStringProperty(i.getCognome());
		this.datiMatricola = new SimpleStringProperty(i.getMatricola());
	}
	
	public String getDatiNome() {
		return datiNome.get();
	}
	
	public void setDatiNome(String nome) {
		this.datiNome.set(nome);
	}
	
	public StringProperty nomeProperty() {
	    return datiNome;
	}
	
	public String getDatiCognome() {
		return datiCognome.get();
	}
	
	public void setDatiCapolineaI(String cognome) {
		this.datiCognome.set(cognome);
	}
	
	public StringProperty cognomeProperty() {
	    return datiCognome;
	}
	
	public String getDatiMatricola() {
		return datiMatricola.get();
	}
	
	public void setDatiCapolineaF(String matricola) {
		this.datiMatricola.set(matricola);
	}
	
	public StringProperty matricolaProperty() {
	    return datiMatricola;
	}
	
	public String getEdit() {
		return "edit";
	}

	public String getDelete() {
		return "delete";
	}
	
	public Impiegato getImpiegato() {
		return impiegato;
	}
	
	public void setImpiegato(Impiegato impiegato) {
		this.impiegato = impiegato;
	}
}
