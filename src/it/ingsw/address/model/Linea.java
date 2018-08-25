package it.ingsw.address.model;

import java.util.ArrayList;

public class Linea {
	
	private String numeroLinea;
	private String capolineaI;
	private String capolineaF;
	private ArrayList<Fermata> fermate;
	
	public Linea() {
	}
	
	public String getNumeroLinea() {
		return numeroLinea;
	}
	
	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getCapolineaI() {
		return capolineaI;
	}

	public void setCapolineaI(String capolineaI) {
		this.capolineaI = capolineaI;
	}

	public String getCapolineaF() {
		return capolineaF;
	}

	public void setCapolineaF(String capolineaF) {
		this.capolineaF = capolineaF;
	}

	public ArrayList<Fermata> getFermate() {
		return fermate;
	}

	public void setFermate(ArrayList<Fermata> fermate) {
		this.fermate = fermate;
	}
	
//	public int getOrario() {
//		return orario;
//	}
//	public void setOrario(int orario) {
//		this.orario = orario;
//	}
	
	
}
