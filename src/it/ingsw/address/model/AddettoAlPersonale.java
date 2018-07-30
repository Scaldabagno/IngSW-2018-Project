package it.ingsw.address.model;

import java.time.LocalDate;

public class AddettoAlPersonale extends Impiegato {
	
	public AddettoAlPersonale() {
		
	}
	
	public AddettoAlPersonale(String m, String n, String c, String e, String nT, LocalDate d, Turno t, String r ) {
		super();
		setRuolo("Addetto Al Personale");
	}
}
