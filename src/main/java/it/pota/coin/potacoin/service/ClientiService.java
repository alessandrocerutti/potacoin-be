package it.pota.coin.potacoin.service;

import java.util.ArrayList;

import it.pota.coin.potacoin.dao.ClientiDao;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.exception.DBException;

public class ClientiService {
private ClientiDao dao = new ClientiDao();

	public Cliente getDatiCliente(int id) throws DBException {
		return dao.getClientefromId(id);
		
	}

	public int isAutenticato(Credenziali cred) throws DBException {
		String u = cred.getUsername();
		String p = cred.getPassword();
		String e = cred.getEmail();
		return dao.autenticazione(u,p,e);
	}

	public ArrayList<BuonoAssegnato> getBuoniCliente(int id) throws DBException {
		return dao.getBuoniCliente(id);
	}

	public void completaRegistrazione(Cliente cliente, Credenziali cred) throws DBException{
		
		dao.registrazione(cliente, cred);
		
	}

	public Errore isRegistrato(String email, String username) throws DBException {
		Errore er = new Errore();
		// 0 --> OK
		// 1 --> account già presente
		// 2 --> username già in uso

		int esitoControllo = dao.controlloRegistrazione(email, username);

		if (esitoControllo == 0) {
			return null;
		} else if (esitoControllo == 1) {
			er.setId(101);
			er.setMsg("email già utilizzata");
			return er;
		} else {
			er.setId(102);
			er.setMsg("username già in uso");
			return er;
		}
	}

}
