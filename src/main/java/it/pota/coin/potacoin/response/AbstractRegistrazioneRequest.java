package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Credenziali;

public abstract class AbstractRegistrazioneRequest {
	private Credenziali credenziali;

	public Credenziali getCredenziali() {
		return credenziali;
	}

	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}

}
