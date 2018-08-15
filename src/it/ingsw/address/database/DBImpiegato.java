package it.ingsw.address.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
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
	
	public Impiegato getImpiegatoById(String id) throws SQLException {
		return getArrayImpiegati("idImpiegato = '" + id + "'").get(0);
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
				//impiegato.setRuolo(resultSet.getString("ruolo"));
				impiegati.add(new DatiImpiegato(impiegato));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiImpiegato> datiImpiegati = FXCollections.observableArrayList(impiegati);
		return datiImpiegati;
	}
	
	DatiImpiegato autista;
	public DatiImpiegato getAutista() {
		try {
			dbm.executeQuery("SELECT * FROM impiegati WHERE");	//TODO: da completare query 
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				impiegato.setNome(resultSet.getString("nome"));
				impiegato.setCognome(resultSet.getString("cognome"));
				impiegato.setMatricola(resultSet.getString("matricola"));
				impiegato.setEmail(resultSet.getString("email"));
				
				autista.getImpiegato();
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return autista;
	}
}
	


