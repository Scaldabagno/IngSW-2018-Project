package it.ingsw.address.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Ruolo;
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
		ruolo=Ruolo.Personale;
		impiegato = this.getImpiegatoByEmail(email, "ruolo= '" + ruolo + "'");
		if(impiegato == null) {
			return null;
		}
		System.out.println(impiegato.getPassword());
		System.out.println(password);
		if( !(impiegato.getPassword().equals(password)) || !(impiegato.getRuolo().equals(ruolo))) {
			return null;
		}
		return impiegato;
	}
	
	public void eliminaImpiegatoAP(String matricola) throws SQLException{
		Impiegato i = new Impiegato();
		i.getMatricola();
		dbm.executeQuery("DELETE FROM impiegati WHERE matricola='" + matricola + "'");
	}
	
	public Impiegato loginAddettoAiMezzi(String email, String password, Ruolo ruolo) throws SQLException {
		Impiegato impiegato = new Impiegato();
		ruolo=Ruolo.Mezzi;
		impiegato = this.getImpiegatoByEmail(email, "ruolo= '" + ruolo + "'");
		if(impiegato == null) {
			return null;
		}
		System.out.println(impiegato.getPassword());
		System.out.println(password);
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
		System.out.println(impiegato.getPassword());
		System.out.println(password);
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
			impiegato.setRuolo(Ruolo.getByValue(resultSet.getString("ruolo")));
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
				//impiegato.setRuolo(resultSet.getString("ruolo"));
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
			// verify if the query returned an empty table
			//			if(!dbm.getResultSet().next()) {
			//				return null;
			//			}
			// if the query table returned contains something
			ResultSet result = dbm.getResultSet();
			//			result.beforeFirst();
			while(result.next()) {
				Impiegato i= new Impiegato();
//				e.setId(result.getString("idEmployee"));
				i.setNome(result.getString("nome"));
				i.setCognome(result.getString("cognome"));
				i.setMatricola(result.getString("matricola"));
				i.setEmail(result.getString("email"));
				i.setPassword(result.getString("password"));
				i.setRuolo(Ruolo.getByValue(result.getString("ruolo")));
//				e.setSalary(result.getDouble("Salary"));
//				e.setStatus(StatusEmployee.valueOf(result.getString("Status")));
//				String w = result.getString("Workshift");
//				if(w.equals("MORNING"))
//					e.setWorkshift(Workshift.MATTINA);
//				if(w.equals("AFTERNOON"))
//					e.setWorkshift(Workshift.POMERIGGIO);
//				else
//					e.setWorkshift(Workshift.SERA);
				impiegati.add(i);
			}
		}
		catch(SQLException exc) {
			exc.printStackTrace();
		}
		return impiegati;
	}
	
//	TODO: Da completare e corregere	
	DatiImpiegato autista;
	public DatiImpiegato getAutista(String clause) {
		try {
			dbm.executeQuery("SELECT * FROM impiegati WHERE" + clause);	//TODO: da completare query 
			ResultSet resultSet = dbm.getResultSet();
			Impiegato impiegato = new Impiegato();
			impiegato.setNome(resultSet.getString("nome"));
			impiegato.setCognome(resultSet.getString("cognome"));
			impiegato.setMatricola(resultSet.getString("matricola"));
			impiegato.setEmail(resultSet.getString("email"));
			impiegato.setPassword(resultSet.getString("password"));				
			autista.getImpiegato();
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return autista;
	}
	
//	TODO: Da completare e correggere
	public void aggiungiImpiegato(Impiegato i) throws SQLException {
		String query = " INSERT INTO mydb.impiegati ()" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, i.getNome());
		preparedStmt.setString (2, i.getCognome());
		preparedStmt.setString (3, i.getMatricola());
		preparedStmt.setString (4, i.getEmail());
		preparedStmt.setString (5, i.getPassword());
//		preparedStmt.setString (4, i.getBirthDate().getYear()+"-"+e.getBirthDate().getMonthValue()+"-"+e.getBirthDate().getDayOfMonth());
//		preparedStmt.setDouble (6, i.getSalary());
//		preparedStmt.setString (8, i.getRole().name());
//		preparedStmt.setString (9, "AVAILABLE");
		
		// execute the preparedstatement
		preparedStmt.execute();
	}
	
//	TODO: Da provare
	public void comunicaNonDisponibilita(Impiegato i) throws SQLException{
		String query = " UPDATE mydb.impiegati SET disponibilit‡Impiegato=? WHERE idImpiegato=?";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setBoolean (1, i.getDisponibilita());
		preparedStmt.setInt     (2, i.getIdImpiegato());
	}

}
	


