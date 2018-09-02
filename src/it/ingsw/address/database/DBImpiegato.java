package it.ingsw.address.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Ruolo;
import it.ingsw.address.model.Turno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBImpiegato {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "mydb");
	private static DBImpiegato instance;
	
	private DBImpiegato() throws SQLException {
		dbm.connect();
	}
	
	public static DBImpiegato getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBImpiegato();
		return instance;
	}
	
	public Impiegato loginAddettoAlPersonale(String email, String password, Ruolo ruolo) throws SQLException {
		Impiegato impiegato = new Impiegato();
		ruolo=Ruolo.AddettoAlPersonale;
		impiegato = this.getImpiegatoByEmail(email, "ruolo= '" + ruolo + "'");
		if(impiegato == null) {
			return null;
		}
		if( !(impiegato.getPassword().equals(password)) || !(impiegato.getRuolo().equals(ruolo))) {
			return null;
		}
		return impiegato;
	}
	
	public Impiegato loginAddettoAiMezzi(String email, String password, Ruolo ruolo) throws SQLException {
		Impiegato impiegato = new Impiegato();
		ruolo=Ruolo.AddettoAiMezzi;
		impiegato = this.getImpiegatoByEmail(email, "ruolo= '" + ruolo + "'");
		if(impiegato == null) {
			return null;
		}
		if( !(impiegato.getPassword().equals(password)) || !(impiegato.getRuolo().equals(ruolo))) {
			return null;
		}
		return impiegato;
	}
	
	public Impiegato loginAutista(String email, String password, Ruolo ruolo) throws SQLException {
		Impiegato impiegato = new Impiegato();
		ruolo=Ruolo.Autista;
		impiegato = this.getImpiegatoByEmail(email, "ruolo= '" + ruolo + "'");
		if(impiegato == null) {
			return null;
		}
		if( !(impiegato.getPassword().equals(password)) || !(impiegato.getRuolo().equals(ruolo))) {
			return null;
		}
		return impiegato;
	}
	
	public Impiegato getImpiegatoByEmail(String email, String clause) throws SQLException {
		ArrayList<Impiegato> ar = getImpiegati("email = '" + email + "'");
		if(ar.size() == 0) {
			// TODO: come dovremmo comportarci se l'impiegato non esiste?
			return null;
		}
		else return ar.get(0);
	}

	public ArrayList<Impiegato> getArrayImpiegati(String clause) throws SQLException{
		ArrayList<Impiegato> impiegati = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM impiegati WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Impiegato impiegato = new Impiegato();
			impiegato.setNome(resultSet.getString("nome"));
			impiegato.setCognome(resultSet.getString("cognome"));
			impiegato.setMatricola(resultSet.getString("matricola"));
			impiegato.setEmail(resultSet.getString("email"));
			impiegato.setPassword(resultSet.getString("password"));
			impiegato.setRuolo(Ruolo.valueOf(resultSet.getString("ruolo")));
			impiegato.setDataNascita(resultSet.getDate("dataDiNascita").toLocalDate());
			impiegato.setDisponibilita(resultSet.getBoolean("disponibilit‡Impiegato"));
			impiegato.setStipendio(resultSet.getDouble("stipendio"));
			impiegato.setTurno(Turno.valueOf(resultSet.getString("turno")));
			impiegati.add(impiegato);
		}
		return impiegati;
	}
	
	public ObservableList<DatiImpiegato> getImpiegati() {
		ArrayList<DatiImpiegato> impiegati = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM impiegati");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				impiegato.setNome(resultSet.getString("nome"));
				impiegato.setCognome(resultSet.getString("cognome"));
				impiegato.setMatricola(resultSet.getString("matricola"));
				impiegato.setEmail(resultSet.getString("email"));
				impiegato.setPassword(resultSet.getString("password"));
				impiegato.setRuolo(Ruolo.valueOf(resultSet.getString("ruolo")));
				impiegato.setDataNascita(resultSet.getDate("dataDiNascita").toLocalDate());
				impiegato.setDisponibilita(resultSet.getBoolean("disponibilit‡Impiegato"));
				impiegato.setStipendio(resultSet.getDouble("stipendio"));
				if(resultSet.getString("turno") != null) {
					impiegato.setTurno(Turno.valueOf(resultSet.getString("turno")));
				}else {
					impiegato.setTurno(Turno.NonAssegnato);
				}
				
				impiegati.add(new DatiImpiegato(impiegato));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiImpiegato> datiImpiegati = FXCollections.observableArrayList(impiegati);
		return datiImpiegati;
	}
	
	public ObservableList<DatiImpiegato> getAutistiDisponibili() {
		ArrayList<DatiImpiegato> impiegati = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM impiegati WHERE disponibilit‡Impiegato=1 AND ruolo='" + String.valueOf(Ruolo.Autista) + "'");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				impiegato.setNome(resultSet.getString("nome"));
				impiegato.setCognome(resultSet.getString("cognome"));
				impiegato.setMatricola(resultSet.getString("matricola"));
				impiegato.setEmail(resultSet.getString("email"));
				impiegato.setPassword(resultSet.getString("password"));
				impiegato.setRuolo(Ruolo.valueOf(resultSet.getString("ruolo")));
				impiegato.setDataNascita(resultSet.getDate("dataDiNascita").toLocalDate());
				impiegato.setDisponibilita(resultSet.getBoolean("disponibilit‡Impiegato"));
				impiegato.setStipendio(resultSet.getDouble("stipendio"));
				if(resultSet.getString("turno") != null) {
					impiegato.setTurno(Turno.valueOf(resultSet.getString("turno")));
				}else {
					impiegato.setTurno(Turno.NonAssegnato);
				}
				impiegati.add(new DatiImpiegato(impiegato));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiImpiegato> datiImpiegati = FXCollections.observableArrayList(impiegati);
		return datiImpiegati;
	}
	
	public ObservableList<DatiImpiegato> getAutisti() {
		ArrayList<DatiImpiegato> impiegati = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM impiegati WHERE ruolo='" + String.valueOf(Ruolo.Autista) + "'");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				impiegato.setNome(resultSet.getString("nome"));
				impiegato.setCognome(resultSet.getString("cognome"));
				impiegato.setMatricola(resultSet.getString("matricola"));
				impiegato.setEmail(resultSet.getString("email"));
				impiegato.setPassword(resultSet.getString("password"));
				impiegato.setRuolo(Ruolo.valueOf(resultSet.getString("ruolo")));
				impiegato.setDataNascita(resultSet.getDate("dataDiNascita").toLocalDate());
				impiegato.setDisponibilita(resultSet.getBoolean("disponibilit‡Impiegato"));
				impiegato.setStipendio(resultSet.getDouble("stipendio"));
				if(resultSet.getString("turno") != null) {
					impiegato.setTurno(Turno.valueOf(resultSet.getString("turno")));
				}else {
					impiegato.setTurno(Turno.NonAssegnato);
				}
				impiegati.add(new DatiImpiegato(impiegato));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiImpiegato> datiImpiegati = FXCollections.observableArrayList(impiegati);
		return datiImpiegati;
	}
	
	public ArrayList<Impiegato> getImpiegati(String clause){
		ArrayList<Impiegato> impiegati = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM impiegati WHERE " + clause);
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				Impiegato i= new Impiegato();
				i.setNome(result.getString("nome"));
				i.setCognome(result.getString("cognome"));
				i.setMatricola(result.getString("matricola"));
				i.setEmail(result.getString("email"));
				i.setPassword(result.getString("password"));
				i.setRuolo(Ruolo.valueOf(result.getString("ruolo")));
				i.setDataNascita(result.getDate("dataDiNascita").toLocalDate());
				i.setDisponibilita(result.getBoolean("disponibilit‡Impiegato"));
				i.setStipendio(result.getDouble("stipendio"));
				if(result.getString("turno") != null) {
					i.setTurno(Turno.valueOf(result.getString("turno")));
				}else if (result.getString("turno") == null || result.getString("turno") == "NonAssegnato"){
					i.setTurno(Turno.NonAssegnato);
				}
				impiegati.add(i);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return impiegati;
	}
	
	public void aggiungiImpiegato(Impiegato i) throws SQLException {
		String query = " INSERT INTO mydb.impiegati ()" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, i.getMatricola());
		preparedStmt.setString (2, i.getNome());
		preparedStmt.setString (3, i.getCognome());
		preparedStmt.setString (4, i.getEmail());
		preparedStmt.setString (5, i.getPassword());
		preparedStmt.setString (6, i.getDataNascita().getYear()+"-"+i.getDataNascita().getMonthValue()+"-"+i.getDataNascita().getDayOfMonth());
		preparedStmt.setString (7, i.getRuolo().name());
		preparedStmt.setString (8, "0");
		if(i.getRuolo() == Ruolo.AddettoAlPersonale) {
			preparedStmt.setDouble (9, 1500.00);
		}
		if(i.getRuolo() == Ruolo.AddettoAiMezzi) {
			preparedStmt.setDouble (9, 1300.00);
		}
		if(i.getRuolo() == Ruolo.Autista) {
			preparedStmt.setDouble (9, i.getStipendio());
		}
		preparedStmt.setString (10, null);
		// execute the preparedstatement
		preparedStmt.execute();
	}
	
	public void modificaImpiegato(Impiegato i) throws SQLException{
		String query = " UPDATE mydb.impiegati SET nome=?, cognome=?, email=?, password=? WHERE matricola=?;";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, i.getNome());
		preparedStmt.setString (2, i.getCognome());
		preparedStmt.setString (3, i.getEmail());
		preparedStmt.setString (4, i.getPassword());
		preparedStmt.setString (5, i.getMatricola());
		
		preparedStmt.execute();
	}
	
	public void calcolaStipendio(DatiImpiegato i) {
		String query = " UPDATE mydb.impiegati SET " + 
				"stipendio = '" + i.getDatiStipendio() + "' " +
				"WHERE matricola = '" + i.getDatiMatricola()+"';";
		dbm.executeUpdate(query);
	}
	
	public void comunicaNonDisponibilita(Impiegato i) throws SQLException{
		if(String.valueOf(i.getDisponibilita()) == "true") {
			String query = " UPDATE mydb.impiegati SET " + 
					"disponibilit‡Impiegato = '" + 0 + "' " +
					"WHERE matricola = '" + i.getMatricola()+"';";
			dbm.executeUpdate(query);
		}else if (String.valueOf(i.getDisponibilita()) == "false"){
			String query = " UPDATE mydb.impiegati SET " + 
					"disponibilit‡Impiegato = '" + 1 + "' " +
					"WHERE matricola = '" + i.getMatricola() +"';";
			dbm.executeUpdate(query);
		}
	}
	
	public void turnoQuery(Impiegato i) {
		String query = "UPDATE mydb.impiegati SET turno='" + i.getTurno() + "'"
				+ "WHERE matricola='" + i.getMatricola() + "';";
		dbm.executeUpdate(query);
	}
}
	


