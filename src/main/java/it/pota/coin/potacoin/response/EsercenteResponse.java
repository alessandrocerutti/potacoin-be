package it.pota.coin.potacoin.response;

import java.util.ArrayList;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Esercente;

public class EsercenteResponse extends AbstractResponse{
	Esercente esercente;
	ArrayList<Buono> buoniInseriti;
	ArrayList<BuonoAssegnato> buoniAssegnati;

	public Esercente getEsercente() {
		return esercente;
	}

	public void setEsercente(Esercente esercente) {
		this.esercente = esercente;
	}

	public ArrayList<Buono> getBuoniInseriti() {
		return buoniInseriti;
	}

	public void setBuoniInseriti(ArrayList<Buono> buoniInseriti) {
		this.buoniInseriti = buoniInseriti;
	}

	public ArrayList<BuonoAssegnato> getBuoniAssegnati() {
		return buoniAssegnati;
	}

	public void setBuoniAssegnati(ArrayList<BuonoAssegnato> buoniAssegnati) {
		this.buoniAssegnati = buoniAssegnati;
	}
	
	
}
