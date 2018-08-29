package it.ingsw.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DatiLinea {
		private final SimpleStringProperty datiNumeroLinea;
		private final SimpleStringProperty datiCapolineaI;
		private final SimpleStringProperty datiCapolineaF;
		private final SimpleStringProperty datiFermate;
		private Linea linea;
		
		public DatiLinea(String nLinea, String capolineaI, String capolineaF, String fermate) {
			this.datiNumeroLinea = new SimpleStringProperty(nLinea);
			this.datiCapolineaI = new SimpleStringProperty(capolineaI);
			this.datiCapolineaF = new SimpleStringProperty(capolineaF);
			this.datiFermate = new SimpleStringProperty(fermate);
		}
		
		public DatiLinea(Linea l) {
			this.datiNumeroLinea = new SimpleStringProperty(l.getNumeroLinea());
			this.datiCapolineaI = new SimpleStringProperty(l.getCapolineaI().getFermata());
			this.datiCapolineaF = new SimpleStringProperty(l.getCapolineaF().getFermata());
			this.datiFermate = new SimpleStringProperty(String.valueOf(l.getFermate()));
			}
		
		public String getDatiNumeroLinea() {
			return datiNumeroLinea.get();
		}
		
		public void setDatiNumeroLinea(String numeroLinea) {
			this.datiNumeroLinea.set(numeroLinea);
		}
		
		public StringProperty numeroLineaProperty() {
		    return datiNumeroLinea;
		}
		
		public String getDatiCapolineaI() {
			return datiCapolineaI.get();
		}
		
		public void setDatiCapolineaI(String capolineaI) {
			this.datiCapolineaI.set(capolineaI);
		}
		
		public StringProperty capolineaIProperty() {
		    return datiCapolineaI;
		}
		
		public String getDatiCapolineaF() {
			return datiCapolineaF.get();
		}
		
		public void setDatiCapolineaF(String capolineaF) {
			this.datiCapolineaF.set(capolineaF);
		}
		
		public StringProperty capolineaFProperty() {
		    return datiCapolineaF;
		}
		
		public String getDatiFermate() {
			return datiFermate.get();
		}
		
		public void setDatiFermate(String capolineaF) {
			this.datiFermate.set(capolineaF);
		}
		
		public StringProperty fermateProperty() {
		    return datiFermate;
		}
		
		public String getEdit() {
			return "edit";
		}

		public String getDelete() {
			return "delete";
		}
		
		public Linea getLinea() {
			return linea;
		}
		
		public void setLinea(Linea linea) {
			this.linea = linea;
		}
}
