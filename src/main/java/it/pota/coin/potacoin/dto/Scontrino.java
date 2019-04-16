package it.pota.coin.potacoin.dto;

public class Scontrino {
	private int ID_scontrino;
	private int ID_esercente;
	private int ID_cliente;
	private String codice_scontrino;
	private double tot_scontrino;
	private int punti_assegnabili;
	private boolean riscossione_avvenuta;
	
	public int getID_scontrino() {
		return ID_scontrino;
	}
	public void setID_scontrino(int iD_scontrino) {
		ID_scontrino = iD_scontrino;
	}
	public int getID_esercente() {
		return ID_esercente;
	}
	public void setID_esercente(int iD_esercente) {
		ID_esercente = iD_esercente;
	}
	public int getID_cliente() {
		return ID_cliente;
	}
	public void setID_cliente(int iD_cliente) {
		ID_cliente = iD_cliente;
	}
	
	public double getTot_scontrino() {
		return tot_scontrino;
	}
	public void setTot_scontrino(double tot_scontrino) {
		this.tot_scontrino = tot_scontrino;
	}
	public int getPunti_assegnabili() {
		return punti_assegnabili;
	}
	public void setPunti_assegnabili(int punti_assegnabili) {
		this.punti_assegnabili = punti_assegnabili;
	}
	public boolean isRiscossione_avvenuta() {
		return riscossione_avvenuta;
	}
	public void setRiscossione_avvenuta(boolean riscossione_avvenuta) {
		this.riscossione_avvenuta = riscossione_avvenuta;
	}
	public String getCodice_scontrino() {
		return codice_scontrino;
	}
	public void setCodice_scontrino(String codice_scontrino) {
		this.codice_scontrino = codice_scontrino;
	}	

}
