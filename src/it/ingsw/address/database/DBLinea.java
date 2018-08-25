package it.ingsw.address.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBLinea {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "mydb");
	private static DBLinea instance;
	
	private DBLinea() throws SQLException {
		dbm.connect();
	}
	
	public static DBLinea getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBLinea();
		return instance;
	}
	
	public Linea getLineaById(String id) throws SQLException {
		return getArrayLinee("idLinea = '" + id + "'").get(0);
	}

	public ArrayList<Linea> getArrayLinee(String clause) throws SQLException{
		ArrayList<Linea> linee = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM linee WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Linea linea = new Linea();
			linea.setNumeroLinea(resultSet.getString("numeroLinea"));
			linee.add(linea);
		}
		return linee;
	}
	
	public ObservableList<DatiLinea> getLinee() {
		ArrayList<DatiLinea> linee = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM linee");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Linea linea = new Linea();
				linea.setNumeroLinea(resultSet.getString("numeroLinea"));
				linea.setCapolineaI(resultSet.getString("capolineaI"));
				linea.setCapolineaF(resultSet.getString("capolineaF"));
				linee.add(new DatiLinea (linea));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiLinea> datiLinee = FXCollections.observableArrayList(linee);
		return datiLinee;
	}
	
	public Fermata getTerminal(Linea linea, boolean first) {
		Fermata fermata = new Fermata();
		try {
			if(first == true) {
//				TODO: Aggiustare
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + linea.getNumeroLinea() +
						"' AND l.idLine=ls.Line_idLine AND ls.type='FIRST'");
			} else {
				dbm.executeQuery("SELECT s.idStop, s.Address FROM stop s, line_has_stop ls, line l " +
						"WHERE s.idStop=ls.Stop_idStop AND l.idLine='" + linea.getNumeroLinea() +
						"' AND l.idLine=ls.Line_idLine AND ls.type='END'");
			}
			ResultSet result = dbm.getResultSet();
			/**************
			 * CONTROLLO DA FARE
			 * ATTENZIONE
			 */
			if(!result.next()) {
				Fermata s = new Fermata();
				s.setFermata("Via vattelappesca 12");
				return s;
			}
			/**
			 * AGGIUSTARE
			 */
			fermata.setFermata(result.getString("s.Address"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return fermata;
	}
}
