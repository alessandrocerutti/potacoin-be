package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Scontrino;

public class RequestCliente {
	private String token;
	private int ID_buono_preferito;
	private Scontrino scontrino;

	public RequestCliente() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getID_buono_preferito() {
		return ID_buono_preferito;
	}

	public void setID_buono_preferito(int iD_buono_preferito) {
		ID_buono_preferito = iD_buono_preferito;
	}

	public Scontrino getScontrino() {
		return scontrino;
	}

	public void setScontrino(Scontrino scontrino) {
		this.scontrino = scontrino;
	}


}
