package it.ingsw.address.model;

public class Turno {
	private String linea;
	private String matricolaAutista;
	private Orari orario;
	
	
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	public String getMatricolaAutista() {
		return matricolaAutista;
	}
	public void setMatricolaAutista(String matricolaAutista) {
		this.matricolaAutista = matricolaAutista;
	}
	
	public Orari getOrario() {
		return orario;
	}
	public void setOrario(Orari orario) {
		this.orario = orario;
	}
	
}