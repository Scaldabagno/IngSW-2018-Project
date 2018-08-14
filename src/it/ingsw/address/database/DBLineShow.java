package it.ingsw.address.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBLineShow {
	private static DBManager dbm = new DBManager("localhost", "root", "root", "mydb");
	private static DBLineShow instance;
	
	private DBLineShow() throws SQLException {
		dbm.connect();
	}
	
	public static DBLineShow getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBLineShow();
		return instance;
	}
	
	public Linea getLineById(String id) throws SQLException {
		return getAllLinesArray("idLinea = '" + id + "'").get(0);
	}

	public ArrayList<Linea> getAllLinesArray(String clause) throws SQLException{
		ArrayList<Linea> linee = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM linee WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Linea linea = new Linea();
			linea.setNumeroLinea(resultSet.getString("numeroLinea"));
//			line.setStartTerminal(this.getTerminal(line, true));
//			line.setEndTerminal(this.getTerminal(line, false));
//			line.setGoingStops(this.getStops(line, true));
//			line.setReturnStops(this.getStops(line, false));
			linee.add(linea);
		}
		return linee;
	}
	
	public ObservableList<DatiLinea> getAllLines() {
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
//				line.setStartTerminal(this.getTerminal(line, true));
//				line.setEndTerminal(this.getTerminal(line, false));
//				line.setGoingStops(this.getStops(line, true));
//				line.setReturnStops(this.getStops(line, false));
				linee.add(new DatiLinea (linea));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiLinea> datiLinee = FXCollections.observableArrayList(linee);
		return datiLinee;
	}
	
}
