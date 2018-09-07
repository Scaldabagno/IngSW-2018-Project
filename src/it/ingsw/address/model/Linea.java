package it.ingsw.address.model;

import java.util.ArrayList;


public class Linea {
	
	private String numeroLinea;
	private Fermata capolineaI;
	private Fermata capolineaF;
	private ArrayList<Fermata> fermate;
	
	public Linea() {
		
	}
	
	public Linea(String numeroLinea, Fermata capolineaI, Fermata capolineaF, ArrayList<Fermata> fermate) {
		this.setNumeroLinea(numeroLinea);
		this.setFermate(fermate);
		this.setCapolineaI(capolineaI);
		this.setCapolineaF(capolineaF);
	}
	
	public String getNumeroLinea() {
		return numeroLinea;
	}
	
	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public Fermata getCapolineaI() {
		return capolineaI;
	}

	public void setCapolineaI(Fermata capolineaI) {
		this.capolineaI = capolineaI;
	}

	public Fermata getCapolineaF() {
		return capolineaF;
	}

	public void setCapolineaF(Fermata capolineaF) {
		this.capolineaF = capolineaF;
	}

	public ArrayList<Fermata> getFermate() {
		return fermate;
	}

	public void setFermate(ArrayList<Fermata> fermate) {
		this.fermate = fermate;
	}
}
