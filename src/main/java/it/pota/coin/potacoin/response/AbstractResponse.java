package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Errore;

public abstract class AbstractResponse {
	private Errore errore;
	private String token;
	
	public AbstractResponse() {
	}

	public Errore getErrore() {
		return errore;
	}

	public void setErrore(Errore errore) {
		this.errore =errore;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
