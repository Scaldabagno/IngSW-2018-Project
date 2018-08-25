package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiMezzo {
	private final SimpleStringProperty datiTarga;
	private final SimpleStringProperty datiPosto;
	
	private Mezzo mezzo;
	
	public DatiMezzo(String targa, String posto) {
		this.datiTarga = new SimpleStringProperty(targa);
		this.datiPosto = new SimpleStringProperty(posto);
	}
	
	public DatiMezzo(Mezzo m) {
		this.datiTarga = new SimpleStringProperty(m.getTarga());
		this.datiPosto = new SimpleStringProperty(String.valueOf(m.getNumeroPosto()));
	}
	
	public String getDatiTarga() {
		return datiTarga.get();
	}
	
	public void setDatiTarga(String targa) {
		this.datiTarga.set(targa);
	}
	
	public StringProperty targaProperty() {
	    return datiTarga;
	}
	
	public String getDatiPosto() {
		return datiPosto.get();
	}
	
	public void setDatiPosto(String posto) {
		this.datiPosto.set(posto);
	}
	
	public StringProperty postoProperty() {
	    return datiPosto;
	}
		
	public String getEdit() {
		return "edit";
	}

	public String getDelete() {
		return "delete";
	}
	
	public Mezzo getMezzo() {
		return mezzo;
	}
	
	public void setMezzo(Mezzo mezzo) {
		this.mezzo = mezzo;
	}

}
