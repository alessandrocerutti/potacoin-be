package it.pota.coin.potacoin.dto;

import java.util.Date;

public class Buono {
	int ID_buono;
	int ID_esercente;
	String nome_attivita;
	String tipo_buono;
	String descrizione;
	int scadenza;
	int costo_punti;
	int giacenza;
	Date scadenza_assoluta;
	
	public Buono() {
	}

	public int getID_buono() {
		return ID_buono;
	}

	public void setID_buono(int iD_buono) {
		ID_buono = iD_buono;
	}

	public int getID_esercente() {
		return ID_esercente;
	}

	public void setID_esercente(int iD_esercente) {
		ID_esercente = iD_esercente;
	}
	
	

	public String getTipo_buono() {
		return tipo_buono;
	}

	public void setTipo_buono(String tipo_buono) {
		this.tipo_buono = tipo_buono;
	}

	public int getScadenza() {
		return scadenza;
	}

	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}

	public int getCosto_punti() {
		return costo_punti;
	}

	public void setCosto_punti(int costo_punti) {
		this.costo_punti = costo_punti;
	}

	public int getGiacenza() {
		return giacenza;
	}

	public void setGiacenza(int giacenza) {
		this.giacenza = giacenza;
	}

	public Date getScadenza_assoluta() {
		return scadenza_assoluta;
	}

	public void setScadenza_assoluta(Date scadenza_assoluta) {
		this.scadenza_assoluta = scadenza_assoluta;
	}

	public String getNome_attivita() {
		return nome_attivita;
	}

	public void setNome_attivita(String nome_attivita) {
		this.nome_attivita = nome_attivita;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toString() {
		return "Buono [ID_buono=" + ID_buono + ", ID_esercente=" + ID_esercente + ", nome_attivita=" + nome_attivita
				+ ", tipo_buono=" + tipo_buono + ", descrizione=" + descrizione + ", scadenza=" + scadenza
				+ ", costo_punti=" + costo_punti + ", giacenza=" + giacenza + ", scadenza_assoluta=" + scadenza_assoluta
				+ "]";
	}
	
	

}
