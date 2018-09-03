package it.ingsw.address.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.ingsw.address.model.Corsa;
import it.ingsw.address.model.DatiCorsa;
import it.ingsw.address.model.DatiImpiegato;
import it.ingsw.address.model.DatiLinea;
import it.ingsw.address.model.Fermata;
import it.ingsw.address.model.Impiegato;
import it.ingsw.address.model.Linea;
import it.ingsw.address.model.Mezzo;
import it.ingsw.address.model.Turno;
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
	
	public ArrayList<Fermata> getArrayFermate () {
		ArrayList<Fermata> stops = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM mydb.fermate");
			ResultSet result = dbm.getResultSet();
			while(result.next()) {
				Fermata stop = new Fermata();
				stop.setFermata(result.getString("Fermata"));
				stops.add(stop);
			}
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return stops;
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
				dbm.executeQuery("SELECT fermate.idFermate, fermate.fermata FROM fermate, linee_has_fermate, linee " +
						"WHERE fermate.idFermate=linee_has_fermate.fermate_idFermate AND linee.numeroLinea='" + linea.getNumeroLinea() +
						"' AND linee.numeroLinea=linee_has_fermate.linee_idLinea AND linee_has_fermate.tipo='PRIMA'");
			} else {
				dbm.executeQuery("SELECT fermate.idFermate, fermate.fermata FROM fermate, linee_has_fermate, linee " +
						"WHERE fermate.idFermate=linee_has_fermate.fermate_idFermate AND linee.numeroLinea='" + linea.getNumeroLinea() +
						"' AND linee.numeroLinea=linee_has_fermate.linee_idLinea AND linee_has_fermate.tipo='ULTIMA'");
			}
			ResultSet result = dbm.getResultSet();
			if(!result.next()) {
				Fermata f = new Fermata();
				f.setFermata("Fermate non settate, rivolgersi all'amministrazione");
				return f;
			}
			fermata.setFermata(result.getString("fermate.fermata"));
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		return fermata;
	}
	
	public Corsa getCorsaById(String id) throws SQLException {
		return getArrayCorse("idcorse = '" + id + "'").get(0);
	}
	
	public Corsa getCorsaByMatricola(String matricola) throws SQLException {
		return getArrayCorse("impiegati_matricola = '" + matricola + "'").get(0);
	}
	
	public Corsa getCorsaByTarga(String targa) throws SQLException {
		return getArrayCorse("mezzi_targa = '" + targa + "'").get(0);
	}
	
	public ObservableList<DatiCorsa> getCorse() {
		ArrayList<DatiCorsa> corse = new ArrayList<>();
		try {
			dbm.executeQuery("SELECT * FROM corse");
			ResultSet resultSet = dbm.getResultSet();
			resultSet.beforeFirst();
			while(resultSet.next()) {
				Impiegato impiegato = new Impiegato();
				Mezzo mezzo = new Mezzo();
				Linea linea = new Linea();
				Corsa corsa = new Corsa(impiegato, mezzo, linea);
				impiegato.setMatricola(resultSet.getString("impiegati_matricola"));
				mezzo.setTarga(resultSet.getString("mezzi_targa"));
				linea.setNumeroLinea(resultSet.getString("linee_numeroLinea"));
				corsa.setIdCorsa(resultSet.getInt("idcorse"));
				corsa.setImpiegato(impiegato);
				corsa.setMezzo(mezzo);
				corsa.setLinea(linea);;
				
				corse.add(new DatiCorsa(corsa));
			}
		} catch(SQLException exc) {
			exc.printStackTrace();
		}
		ObservableList<DatiCorsa> datiCorse = FXCollections.observableArrayList(corse);
		return datiCorse;
	}

	public ArrayList<Corsa> getArrayCorse(String clause) throws SQLException{
		ArrayList<Corsa> corse = new ArrayList<>();

		clause = (clause == null)? "TRUE":clause;

		dbm.executeQuery("SELECT * FROM corse WHERE " + clause);
		ResultSet resultSet = dbm.getResultSet();
		while(resultSet.next()) {
			Impiegato impiegato = new Impiegato();
			Mezzo mezzo = new Mezzo();
			Linea linea = new Linea();
			Corsa corsa = new Corsa(impiegato, mezzo, linea);
			
			
			corsa.setIdCorsa(Integer.valueOf(resultSet.getString("idcorse")));
			corse.add(corsa);
		}
		return corse;
	}
	
	public void allocaCorsaQuery(Corsa corsa) throws SQLException {
		String query = "INSERT INTO mydb.corse (idcorse, impiegati_matricola, mezzi_targa, linee_numeroLinea) VALUES (?, ?, ?, ?);";
		// create the mysql insert preparedstatement
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		preparedStmt.setString (1, null);
		preparedStmt.setString (2, corsa.getImpiegato().getMatricola());
		preparedStmt.setString (3, corsa.getMezzo().getTarga());
		preparedStmt.setString (4, corsa.getLinea().getNumeroLinea());
		preparedStmt.execute();
	}
	
	public void disallocaCorsaQuery(DatiCorsa corsa) throws SQLException{
		String query = "DELETE FROM mydb.corse WHERE impiegati_matricola=?";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		
		preparedStmt.setString (1, corsa.getDatiMatricolaImpiegato());
		
		preparedStmt.execute();
		
		String query1 = "UPDATE mydb.impiegati SET turno=? WHERE matricola=?;";
		
		PreparedStatement preparedStmt1 = dbm.getConnection().prepareStatement(query1);
		
		preparedStmt1.setString (1,String.valueOf(Turno.NonAssegnato));
		preparedStmt1.setString (2, corsa.getDatiMatricolaImpiegato());
		
		preparedStmt1.execute();
	}
	
	public void disallocaCorseQuery() throws SQLException{
		String query = "DELETE FROM mydb.corse";
		
		PreparedStatement preparedStmt = dbm.getConnection().prepareStatement(query);
		
		preparedStmt.execute();
		
		String query1 = "UPDATE mydb.impiegati SET turno=?;";
		
		PreparedStatement preparedStmt1 = dbm.getConnection().prepareStatement(query1);
		
		preparedStmt1.setString (1, String.valueOf(Turno.NonAssegnato));
		
		preparedStmt1.execute();
	}
}
