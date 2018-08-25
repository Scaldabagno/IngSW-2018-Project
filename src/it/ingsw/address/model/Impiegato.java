package it.ingsw.address.model;

import java.time.LocalDate;

public class Impiegato {
	
	private int idImpiegato;
	private String nome;
	private String cognome;
	private String matricola;
	private String email;
	private String password;
	private LocalDate dataNascita;
	private Ruolo ruolo;
	private double stipendio;
	private Turno ore;
	private boolean disponibilita;
	
	
	public Impiegato() {
	
	}	
	
	public int getIdImpiegato() {
		return idImpiegato;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public Ruolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	public double getStipendio() {
		return stipendio;
	}

	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}

	public Turno getOre() {
		return ore;
	}
	public void setOre(Turno ore) {
		this.ore = ore;
	}

	public boolean getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	
	
}