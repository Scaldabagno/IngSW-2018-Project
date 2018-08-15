package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiMezzo {
	private final SimpleStringProperty datiTarga;
	
	private Mezzo mezzo;
	
	public DatiMezzo(String targa) {
		this.datiTarga = new SimpleStringProperty(targa);
	}
	
	public DatiMezzo(Mezzo m) {
		this.datiTarga = new SimpleStringProperty(m.getTarga());
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
		
	public String getEdit() {
		return "edit";
	}

	public String getDelete() {
		return "delete";
	}
	
	public Mezzo getMezzo() {
		return mezzo;
	}
	
	public void setLinea(Mezzo mezzo) {
		this.mezzo = mezzo;
	}

}
