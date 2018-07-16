package it.ingsw.address.model;

import java.time.LocalDate;

public class Impiegato {
	
	private String matricola;
	private String nome;
	private String cognome;
	private String email;
	private String numeroTel;
	private LocalDate dataNascita;
	private double stipendio;
	private Turno turno;
	private Ruolo ruolo;
	private double ore;
	
	
	public Impiegato()
	{
	
	}
	public Impiegato(String m, String n, String c, String e, String nT, LocalDate d, double s, Turno t, Ruolo r )
	{
		this.matricola = m;
		this.nome = n;
		this.cognome = c;
		this.email = e;
		this.numeroTel = nT;
		this.dataNascita = d;
		this.stipendio = s;
		this.turno = t;
		this.ruolo = r;
	}
	
	
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
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
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNumeroTel() {
		return numeroTel;
	}
	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}
	
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
	public Ruolo getRuolo() {
		return ruolo;
	}
	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	
	public double getOre() {
		return ore;
	}
	public void setOre(double ore) {
		this.ore = ore;
	}
	
	public double getStipendio()
	{
		return this.stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
	
}