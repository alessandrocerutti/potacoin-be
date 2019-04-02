package it.pota.coin.potacoin.response;

import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;

public class RegistrazioneClienteRequest extends AbstractRegistrazioneRequest{
	private Cliente cliente;
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
}
