package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiImpiegato {
	private final SimpleStringProperty datiNome;
	private final SimpleStringProperty datiCognome;
	private final SimpleStringProperty datiMatricola;
	private final SimpleStringProperty datiEmail;
	
//	private final SimpleStringProperty datiRuolo;
	//TODO: Aggiungere altre SimpleStringProperty
	private Impiegato impiegato;
	
	public DatiImpiegato(String nome, String cognome, String matricola, String email) {
		this.datiNome = new SimpleStringProperty(nome);
		this.datiCognome = new SimpleStringProperty(cognome);
		this.datiMatricola = new SimpleStringProperty(matricola);
		this.datiEmail = new SimpleStringProperty(email);
//		this.datiRuolo = null;
	}
	
	public DatiImpiegato(Impiegato i) {
		this.datiNome = new SimpleStringProperty(i.getNome());
		this.datiCognome = new SimpleStringProperty(i.getCognome());
		this.datiMatricola = new SimpleStringProperty(i.getMatricola());
		this.datiEmail = new SimpleStringProperty(i.getEmail());
//		this.datiRuolo = new SimpleStringProperty(i.getRuolo);
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
	
	public void setDatiCognome(String cognome) {
		this.datiCognome.set(cognome);
	}
	
	public StringProperty cognomeProperty() {
	    return datiCognome;
	}
	
	public String getDatiMatricola() {
		return datiMatricola.get();
	}
	
	public void setDatiMatricola(String matricola) {
		this.datiMatricola.set(matricola);
	}
	
	public StringProperty matricolaProperty() {
	    return datiMatricola;
	}
	
	public String getDatiEmail() {
		return datiEmail.get();
	}
	
	public void setDatiEmail(String matricola) {
		this.datiEmail.set(matricola);
	}
	
	public StringProperty emailProperty() {
	    return datiEmail;
	}

	
//	public String getDatiRuolo(){
//		return datiRuolo.get();
//	}
	
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
