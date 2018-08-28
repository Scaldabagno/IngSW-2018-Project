package it.ingsw.address.model;

public class Mezzo {
	private String targa;
	private int numeroPosto;
	private boolean disponibilita;
	
	public Mezzo() {
		
	}
	
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	
	public int getNumeroPosto() {
		return numeroPosto;
	}

	public void setNumeroPosto(int numeroPosto) {
		this.numeroPosto = numeroPosto;
	}

	public boolean getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}
	
}
