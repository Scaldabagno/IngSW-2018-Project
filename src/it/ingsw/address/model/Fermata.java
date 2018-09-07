package it.ingsw.address.model;

public class Fermata {
	private String fermata;
	
	public Fermata() {
		this(null);
	}
	
	public Fermata(String fermata) {
		this.setFermata(fermata);
	}

	public String getFermata() {
		return fermata;
	}

	public void setFermata(String fermata) {
		this.fermata = fermata;
	}
	
	@Override
	public String toString() {
		return fermata;
	}
}
