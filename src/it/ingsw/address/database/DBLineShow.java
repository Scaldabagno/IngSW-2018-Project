package it.ingsw.address.database;

import java.net.ConnectException;
import java.sql.SQLException;

import it.ingsw.address.model.Linea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBLineShow {
	private static DBConnection dbc = new DBConnection("localhost", "root", "root", "ats_2018");
	private static DBLineShow instance;
	
	private DBLineShow() throws SQLException {
		dbc.connect();
	}
	
	public static DBLineShow getInstance() throws SQLException {
		if(instance != null) {
			return instance;
		}
		instance = new DBLineShow();
		return instance;
	}
	
	
}
