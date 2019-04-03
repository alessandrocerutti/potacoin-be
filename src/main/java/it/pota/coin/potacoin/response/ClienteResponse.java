package it.pota.coin.potacoin.response;

import java.util.ArrayList;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.Esercente;

public class ClienteResponse extends AbstractResponse {
	private Cliente cliente;
	private ArrayList<BuonoAssegnato> mieibuoni;
	private ArrayList<Buono> buonipreferiti;
	private ArrayList<Buono> buoni;
	private ArrayList<Esercente> esercenti;

	public ClienteResponse() {
		super();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ArrayList<BuonoAssegnato> getMieibuoni() {
		return mieibuoni;
	}

	public void setMieibuoni(ArrayList<BuonoAssegnato> mieibuoni) {
		this.mieibuoni = mieibuoni;
	}

	public ArrayList<Buono> getBuonipreferiti() {
		return buonipreferiti;
	}

	public void setBuonipreferiti(ArrayList<Buono> buonipreferiti) {
		this.buonipreferiti = buonipreferiti;
	}

	public ArrayList<Buono> getBuoni() {
		return buoni;
	}

	public void setBuoni(ArrayList<Buono> buoni) {
		this.buoni = buoni;
	}

	public ArrayList<Esercente> getEsercenti() {
		return esercenti;
	}

	public void setEsercenti(ArrayList<Esercente> esercenti) {
		this.esercenti = esercenti;
	}

	
}
