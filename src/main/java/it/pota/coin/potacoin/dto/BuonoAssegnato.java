package it.pota.coin.potacoin.dto;

import java.util.Date;

public class BuonoAssegnato extends Buono {
	private int ID_buono_assegnato;
	private int ID_cliente;
	private Date data_assegnazione;
	boolean usato;
	private Date data_scadenza;
	private String codice_buono;
	private Date data_riscossione;
	
	public BuonoAssegnato() {
		super();
	}

	public int getID_buono_assegnato() {
		return ID_buono_assegnato;
	}

	public void setID_buono_assegnato(int iD_buono_assegnato) {
		ID_buono_assegnato = iD_buono_assegnato;
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

	public int getID_cliente() {
		return ID_cliente;
	}

	public void setID_cliente(int iD_cliente) {
		ID_cliente = iD_cliente;
	}

	public String getCodice_buono() {
		return codice_buono;
	}

	public void setCodice_buono(String codice_buono) {
		this.codice_buono = codice_buono;
	}

	public Date getData_riscossione() {
		return data_riscossione;
	}

	public void setData_riscossione(Date data_riscossione) {
		this.data_riscossione = data_riscossione;
	}

	@Override
	public String toString() {
		return "BuonoAssegnato [ID_buono_assegnato=" + ID_buono_assegnato + ", ID_cliente=" + ID_cliente
				+ ", data_assegnazione=" + data_assegnazione + ", usato=" + usato + ", data_scadenza=" + data_scadenza
				+ ", codice_buono=" + codice_buono + ", data_riscossione=" + data_riscossione + "]";
	}
	
	
	
	
}
