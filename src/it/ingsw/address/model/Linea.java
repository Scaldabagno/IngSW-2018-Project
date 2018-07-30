package it.ingsw.address.model;

public class Linea {
	
	private int numeroLinea;
	private String itinerario;
	
	private int orario;
	
	public Linea() {
	}
	
	public Linea(int i, String string, int j) {
		this.setNumeroLinea(i);
		this.setItinerario(string);
		this.setOrario(j);
	}
	
	public int getNumeroLinea() {
		return numeroLinea;
	}
	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}
	public String getItinerario() {
		return itinerario;
	}
	public void setItinerario(String itinerario) {
		this.itinerario = itinerario;
	}
	public int getOrario() {
		return orario;
	}
	public void setOrario(int orario) {
		this.orario = orario;
	}
	
	
}
