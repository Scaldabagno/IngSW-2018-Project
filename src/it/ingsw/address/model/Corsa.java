package it.ingsw.address.model;

public class Corsa {
	
	private Impiegato impiegato;
	private Mezzo mezzo;
	private Linea linea;
	
	public Corsa(Impiegato i, Mezzo m, Linea l) {
		this.setImpiegato(i);
		this.setMezzo(m);
		this.setLinea(l);
	}
	
	public Impiegato getImpiegato() {
		return impiegato;
	}
	
	public void setImpiegato(Impiegato impiegato) {
		this.impiegato = impiegato;
	}
	
	public Mezzo getMezzo() {
		return mezzo;
	}
	
	public void setMezzo(Mezzo mezzo) {
		this.mezzo = mezzo;
	}
	
	public Linea getLinea() {
		return linea;
	}
	
	public void setLinea(Linea linea) {
		this.linea = linea;
	}
}
