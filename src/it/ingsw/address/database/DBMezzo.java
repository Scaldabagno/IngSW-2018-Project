package it.ingsw.address.database;

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
//				mezzo.setDisponibilita(resultSet.getString("disponibilitaMezzo")); Booleano, devo vedere come "settarlo"
				mezzi.add(new DatiMezzo (mezzo));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiMezzo> datiMezzi = FXCollections.observableArrayList(mezzi);
		return datiMezzi;
	}
	

}
