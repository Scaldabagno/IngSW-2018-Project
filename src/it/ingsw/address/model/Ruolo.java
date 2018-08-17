package it.ingsw.address.model;

import java.util.HashMap;
import java.util.Map;


public enum Ruolo {
	Personale("Addetto Al Personale"), 
	Mezzi("Addetto Ai Mezzi"), 
	Autista("Autista");
	
	private static final Map<String, Ruolo> MY_MAP = new HashMap<String, Ruolo>();
	  static {
	    for (Ruolo ruolo : values()) {
	      MY_MAP.put(ruolo.getValue(), ruolo);
	    }
	  }
	
	private String value;
	
	Ruolo(String ruolo) {
		this.value = ruolo;
	}
	
	public String getValue() {
	    return value;
	 }

	  public static Ruolo getByValue(String value) {
	    return MY_MAP.get(value);
	  }
	
	@Override
	public String toString() {
		return name() + "=" + value;
	}
}
