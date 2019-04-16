package it.pota.coin.potacoin.dto;

import java.util.Date;

public class News {

	private String titoloNews;
	private String descrizioneNews;
	private Date date;
	private int ID_Esercente;

	public String getTitoloNews() {
		return titoloNews;
	}

	public void setTitoloNews(String titoloNews) {
		this.titoloNews = titoloNews;
	}

	public String getDescrizioneNews() {
		return descrizioneNews;
	}

	public void setDescrizioneNews(String descrizioneNews) {
		this.descrizioneNews = descrizioneNews;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getID_Esercente() {
		return ID_Esercente;
	}

	public void setID_Esercente(int iD_Esercente) {
		ID_Esercente = iD_Esercente;
	}

	@Override
	public String toString() {
		return "News [titoloNews=" + titoloNews + ", descrizioneNews=" + descrizioneNews + ", date=" + date
				+ ", ID_Esercente=" + ID_Esercente + "]";
	}

	
	

}
