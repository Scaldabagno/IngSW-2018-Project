package it.ingsw.address.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.Impiegato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBEmployeeShow {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "mydb");
	private static DBEmployeeShow instance;
	
	private DBEmployeeShow() throws SQLException {
		dbm.connect();
	}
	
	public static DBEmployeeShow getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBEmployeeShow();
		return instance;
	}
	
	public Impiegato getEmployeeById(String id) throws SQLException {
		return getAllEmployeesArray("idImpiegato = '" + id + "'").get(0);
	}

	public ArrayList<Impiegato> getAllEmployeesArray(String clause) throws SQLException{
		ArrayList<Impiegato> impiegati = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM impiegati WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Impiegato impiegato = new Impiegato();
			impiegato.setMatricola(resultSet.getString("matricola"));
			impiegato.setCognome(resultSet.getString("cognome"));
//			line.setStartTerminal(this.getTerminal(line, true));
//			line.setEndTerminal(this.getTerminal(line, false));
//			line.setGoingStops(this.getStops(line, true));
//			line.setReturnStops(this.getStops(line, false));
			impiegati.add(impiegato);
		}
		return impiegati;
	}
	
	public ObservableList<DatiImpiegato> getAllEmployees() {
		ArrayList<DatiImpiegato> impiegati = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM impiegati");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				impiegato.setMatricola(resultSet.getString("matricola"));
				impiegato.setCognome(resultSet.getString("cognome"));
//				line.setStartTerminal(this.getTerminal(line, true));
//				line.setEndTerminal(this.getTerminal(line, false));
//				line.setGoingStops(this.getStops(line, true));
//				line.setReturnStops(this.getStops(line, false));
				impiegati.add(new DatiImpiegato(impiegato));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiImpiegato> datiImpiegati = FXCollections.observableArrayList(impiegati);
		return datiImpiegati;
	}
	

}
