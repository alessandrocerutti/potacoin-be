package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Esercente;

public class EsercenteResponse extends AbstractResponse{
	Esercente esercente;

	public Esercente getEsercente() {
		return esercente;
	}

	public void setEsercente(Esercente esercente) {
		this.esercente = esercente;
	}

}
