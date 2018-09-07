package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sessione {
	
	private final StringProperty email;
	private final StringProperty password;
	public static Impiegato impiegato;
	
	public Sessione() {
		this(null, null);
	}
	
	public Sessione(String email, String password) {
		 this.email = new SimpleStringProperty(email);
	     this.password = new SimpleStringProperty(password);
	}
	
	public String getEmail() {
	      return email.get();
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public StringProperty getPassword() {
	    return password;
	}
	  
	public void setPassword(String password) {
	    this.password.set(password);
	}

}
