package it.ingsw.address.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiMezzo;
import it.ingsw.address.model.Mezzo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBMezzo {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "mydb");
	private static DBMezzo instance;
	
	private DBMezzo() throws SQLException {
		dbm.connect();
	}
	
	public static DBMezzo getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBMezzo();
		return instance;
	}
	
	public Mezzo getMezzoById(String id) throws SQLException {
		return getArrayMezzi("idLinea = '" + id + "'").get(0);
	}

	public ArrayList<Mezzo> getArrayMezzi(String clause) throws SQLException{
		ArrayList<Mezzo> mezzi = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM mezzi WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Mezzo mezzo = new Mezzo();
			mezzo.setTarga(resultSet.getString("targa"));
			mezzo.setNumeroPosto(resultSet.getInt("posto"));
			mezzo.setDisponibilita(resultSet.getBoolean("disponibilitaMezzo"));
			
			mezzi.add(mezzo);
		}
		return mezzi;
	}
	
	public ObservableList<DatiMezzo> getMezzi() {
		ArrayList<DatiMezzo> mezzi = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM mezzi");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Mezzo mezzo = new Mezzo();
				mezzo.setTarga(resultSet.getString("targa"));
				mezzo.setNumeroPosto(resultSet.getInt("deposito_posto"));
				mezzo.setDisponibilita(resultSet.getBoolean("disponibilit‡Mezzo"));
				mezzi.add(new DatiMezzo (mezzo));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiMezzo> datiMezzi = FXCollections.observableArrayList(mezzi);
		return datiMezzi;
	}
	
	public ObservableList<DatiMezzo> getMezziDisponibili() {
		ArrayList<DatiMezzo> mezzi = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM mezzi WHERE disponibilit‡Mezzo=1");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Mezzo mezzo = new Mezzo();
				mezzo.setTarga(resultSet.getString("targa"));
				mezzo.setNumeroPosto(resultSet.getInt("deposito_posto"));
				mezzo.setDisponibilita(resultSet.getBoolean("disponibilit‡Mezzo"));
				mezzi.add(new DatiMezzo (mezzo));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiMezzo> datiMezzi = FXCollections.observableArrayList(mezzi);
		return datiMezzi;
	}
	
	public void aggiungiMezzo(Mezzo m) throws SQLException {
		String query = " INSERT INTO mydb.mezzi ()" + " values (?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, m.getTarga());
		preparedStmt.setString (2, "0");
		preparedStmt.setInt    (3, m.getNumeroPosto());
		
		
		// execute the preparedstatement
		preparedStmt.execute();
	}
	
	public void modificaMezzo(Mezzo m) throws SQLException{
		String query = " UPDATE mydb.mezzi SET deposito_posto=? WHERE targa=?;";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setInt    (1, m.getNumeroPosto());
		preparedStmt.setString (2, m.getTarga());
		
		preparedStmt.execute();
	}

	public void comunicaNonDisponibilita(DatiMezzo m) throws SQLException{
		if(String.valueOf(m.getDatiDisponibilita()) == "true") {
			String query = " UPDATE mydb.mezzi SET " + 
					"disponibilit‡Mezzo = '" + 0 + "' " +
					"WHERE targa = '" + m.getDatiTarga()+"';";
			dbm.executeUpdate(query);
		}else if (m.getDatiDisponibilita() == "false"){
			String query = " UPDATE mydb.mezzi SET " + 
					"disponibilit‡Mezzo = '" + 1 + "' " +
					"WHERE targa = '" + m.getDatiTarga() +"';";
			dbm.executeUpdate(query);
		}
	}

}
