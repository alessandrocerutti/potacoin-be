package it.pota.coin.potacoin.service;

import java.util.ArrayList;

import it.pota.coin.potacoin.dao.ClientiDao;
import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.exception.DBException;

public class ClientiService {
private ClientiDao dao = new ClientiDao();
	
	public void insert(Cliente cliente) throws DBException {
		
		cliente.setNome("prova");
		
		dao.inserimento(cliente);
	}

	public Cliente getDatiCliente(int id) throws DBException {
		return dao.getClientefromId(id);
		
	}

	public int isAutenticato(Credenziali cred) throws DBException {
		String u = cred.getUsername();
		String p = cred.getPassword();
		return dao.autenticazione(u,p);
	}

	public ArrayList<BuonoAssegnato> getBuoniCliente(int id) throws DBException {
		return dao.getBuoniCliente(id);
	}

}
