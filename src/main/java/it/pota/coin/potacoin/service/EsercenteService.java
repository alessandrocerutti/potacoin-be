package it.pota.coin.potacoin.service;

import java.util.ArrayList;
import java.util.List;

import it.pota.coin.potacoin.dao.EsercenteDao;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;

public class EsercenteService {

	EsercenteDao ed = new EsercenteDao();

	public int isAutenticato(Credenziali cred) throws DBException {
		String u = cred.getUsername();
		String p = cred.getPassword();
		return ed.autenticazione(u, p);
	}

	public Errore isRegistrato(String email, String username) {
		
		Errore er = new Errore();
		// 0 --> OK
		// 1 --> account già presente
		// 2 --> username già in uso

		int esitoControllo = ed.controlloRegistrazione(email, username);

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

	public Esercente getDatiEsercente(int id) throws DBException {

		return ed.getEsercenteFromId(id);
	}

	public ArrayList<Esercente> getAllEsercenti() throws DBException {

		return ed.selectAllEsercenti();
	}

	public void completaRegistrazione(Esercente esercente, Credenziali credenziali)throws DBException {
		ed.registrazione(esercente,credenziali );
		
	}

}
