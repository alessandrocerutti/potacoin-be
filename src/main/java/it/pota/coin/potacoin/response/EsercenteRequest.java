package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.News;
import it.pota.coin.potacoin.dto.Scontrino;

public class EsercenteRequest {
	private String token;
	private Buono buonoinserito;
	private News news;
	private Scontrino scontrino;
	private BuonoAssegnato buonoassegnato;


	public EsercenteRequest() {
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Buono getBuonoinserito() {
		return buonoinserito;
	}
	public void setBuonoinserito(Buono buonoinserito) {
		this.buonoinserito = buonoinserito;
	}
	@Override
	public String toString() {
		return "EsercenteRequest [token=" + token + ", buonoinserito=" + buonoinserito + "]";
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
	public Scontrino getScontrino() {
		return scontrino;
	}
	public void setScontrino(Scontrino scontrino) {
		this.scontrino = scontrino;
	}
	public BuonoAssegnato getBuonoassegnato() {
		return buonoassegnato;
	}
	public void setBuonoassegnato(BuonoAssegnato buonoassegnato) {
		this.buonoassegnato = buonoassegnato;
	}
	
	

}
