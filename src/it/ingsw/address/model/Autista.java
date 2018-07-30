package it.ingsw.address.model;

import java.time.LocalDate;

public class Autista extends Impiegato {
	
	private double stipendio;
	
	
	public Autista(String m, String n, String c, String e, String nT, LocalDate d, Turno t, double s) {
		super();
		setRuolo("Autista");
		this.stipendio=s;
	}
	
	public double getStipendio() {
		return this.stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
}
