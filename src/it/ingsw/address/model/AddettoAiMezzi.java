package it.ingsw.address.model;

import java.time.LocalDate;

public class AddettoAiMezzi extends Impiegato {
	
public AddettoAiMezzi() {
		
	}
	
	public AddettoAiMezzi(String m, String n, String c, String e, String nT, LocalDate d, Turno t, String r ) {
		super();
		setRuolo("Addetto Ai Mezzi");
	}
}
