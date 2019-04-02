package it.pota.coin.potacoin.dto;

import java.text.SimpleDateFormat;

public class Cliente {
	private int id;
	private String nome;
	private String cognome;
	private String cf;
	private String citta;
	private int punti;
	
	public Cliente() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public  String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public int getPunti() {
		return punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}
	
	@Override
	public String toString() {
		return this.nome + "," + this.cognome + "," + this.cf+ "," +this.citta+ "," +this.punti+ ".";
	}

}
