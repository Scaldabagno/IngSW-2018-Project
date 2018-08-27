package it.ingsw.address.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiImpiegato {
	private final SimpleStringProperty datiNome;
	private final SimpleStringProperty datiCognome;
	private final SimpleStringProperty datiMatricola;
	private final SimpleStringProperty datiEmail;
	private final SimpleStringProperty datiRuolo;
	private final SimpleObjectProperty <LocalDate> datiNascita;
	private final SimpleStringProperty datiStipendio;
	
	//TODO: Aggiungere altre SimpleStringProperty
	private Impiegato impiegato;
	
	public DatiImpiegato(String nome, String cognome, String matricola, String email, String ruolo, String nascita, String stipendio) {
		this.datiNome = new SimpleStringProperty(nome);
		this.datiCognome = new SimpleStringProperty(cognome);
		this.datiMatricola = new SimpleStringProperty(matricola);
		this.datiEmail = new SimpleStringProperty(email);
		this.datiRuolo = new SimpleStringProperty(ruolo);
		this.datiNascita = new SimpleObjectProperty(nascita);
		this.datiStipendio = new SimpleStringProperty(stipendio);
	}
	
	public DatiImpiegato(Impiegato i) {
		this.datiNome = new SimpleStringProperty(i.getNome());
		this.datiCognome = new SimpleStringProperty(i.getCognome());
		this.datiMatricola = new SimpleStringProperty(i.getMatricola());
		this.datiEmail = new SimpleStringProperty(i.getEmail());
		this.datiRuolo = new SimpleStringProperty(String.valueOf(i.getRuolo()));
		this.datiNascita = new SimpleObjectProperty(i.getDataNascita());
		this.datiStipendio = new SimpleStringProperty(String.valueOf(i.getStipendio()));
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

	public String getDatiRuolo(){
		return datiRuolo.get();
	}
	
	public LocalDate getDatiNascita() {
		return datiNascita.get();
	}
	
	public void setDatiNascita(LocalDate nascita) {
		this.datiNascita.set(nascita);
	}
	
	public ObjectProperty<LocalDate> nascitaProperty() {
	    return datiNascita;
	}
	
	public String getDatiStipendio() {
		return datiStipendio.get();
	}
	
	public void setDatiStipendio(String stipendio) {
		this.datiStipendio.set(stipendio);
	}
	
	public StringProperty stipendioProperty() {
	    return datiStipendio;
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
