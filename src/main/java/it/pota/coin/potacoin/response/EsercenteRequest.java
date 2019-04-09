package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Buono;

public class EsercenteRequest {
	private String token;
	private Buono buonoinserito;

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
	
	

}
