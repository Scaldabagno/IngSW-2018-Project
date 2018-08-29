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
				linea.setCapolineaI(this.getCapolinea(linea, true));
				linea.setCapolineaF(this.getCapolinea(linea, false));
				linea.setFermate(this.getFermate(linea));
				linee.add(new DatiLinea (linea));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiLinea> datiLinee = FXCollections.observableArrayList(linee);
		return datiLinee;
	}
	
	public ArrayList<Fermata> getFermate(Linea linea) {
		ArrayList<Fermata> fermate = new ArrayList<>();
		try {
				dbm.executeQuery("SELECT fermate.idFermate, fermate.fermata FROM fermate, linee_has_fermate, linee " +
						"WHERE fermate.idFermate=linee_has_fermate.fermate_idFermate AND linee.numeroLinea='" + linea.getNumeroLinea() +
						"' AND linee.numeroLinea=linee_has_fermate.linee_idLinea AND linee_has_fermate.tipo='INTERMEDIA'");
			
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			int i=-1;
			while(resultSet.next()) {
				Fermata fermata = new Fermata();
				fermata.setFermata(resultSet.getString("fermate.fermata"));
				fermate.add(++i, fermata);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return fermate;
	}
	
	public Fermata getCapolinea(Linea linea, boolean first) {
		Fermata fermata = new Fermata();
		try {
			if(first == true) {
//				TODO: Aggiustare
				dbm.executeQuery("SELECT fermate.idFermate, fermate.fermata FROM fermate, linee_has_fermate, linee " +
						"WHERE fermate.idFermate=linee_has_fermate.fermate_idFermate AND linee.numeroLinea='" + linea.getNumeroLinea() +
						"' AND linee.numeroLinea=linee_has_fermate.linee_idLinea AND linee_has_fermate.tipo='PRIMA'");
			} else {
				dbm.executeQuery("SELECT fermate.idFermate, fermate.fermata FROM fermate, linee_has_fermate, linee " +
						"WHERE fermate.idFermate=linee_has_fermate.fermate_idFermate AND linee.numeroLinea='" + linea.getNumeroLinea() +
						"' AND linee.numeroLinea=linee_has_fermate.linee_idLinea AND linee_has_fermate.tipo='ULTIMA'");
			}
			ResultSet result = dbm.getResultSet();
//			/**************
//			 * CONTROLLO DA FARE
//			 * ATTENZIONE
//			 */
			if(!result.next()) {
				Fermata f = new Fermata();
				f.setFermata("Via vattelappesca 12");
				return f;
			}
//			/**
//			 * AGGIUSTARE
//			 */
			fermata.setFermata(result.getString("fermate.fermata"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return fermata;
	}
}
