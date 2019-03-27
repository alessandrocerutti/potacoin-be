package it.pota.coin.potacoin.dto;

import java.util.Date;

public class BuonoAssegnato extends Buono {
	private int ID_buono_assegnato;
	private int ID_Cliente;
	private Date data_assegnazione;
	boolean usato;
	private Date data_scadenza;
	
	public BuonoAssegnato() {
		super();
	}

	public int getID_buono_assegnato() {
		return ID_buono_assegnato;
	}

	public void setID_buono_assegnato(int iD_buono_assegnato) {
		ID_buono_assegnato = iD_buono_assegnato;
	}

	public int getID_Cliente() {
		return ID_Cliente;
	}

	public void setID_Cliente(int iD_Cliente) {
		ID_Cliente = iD_Cliente;
	}

	public Date getData_assegnazione() {
		return data_assegnazione;
	}

	public void setData_assegnazione(Date data_assegnazione) {
		this.data_assegnazione = data_assegnazione;
	}

	public boolean isUsato() {
		return usato;
	}

	public void setUsato(boolean usato) {
		this.usato = usato;
	}

	public Date getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(Date data_scadenza) {
		this.data_scadenza = data_scadenza;
	}
	
}
