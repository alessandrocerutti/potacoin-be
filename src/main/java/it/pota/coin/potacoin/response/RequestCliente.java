package it.pota.coin.potacoin.response;

public class RequestCliente {
	private String token;
	private int ID_buono_preferito;

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


}
