package it.pota.coin.potacoin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.pota.coin.potacoin.dao.EsercenteDao;
import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.dto.News;
import it.pota.coin.potacoin.dto.Scontrino;
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

	public ArrayList<BuonoAssegnato> trovaBuoniAssegnati(int id) throws DBException {

		return ed.ricercaBuoniAssegnati(id);
	}

	public ArrayList<Buono> trovaAllBuoni(int id) throws DBException {
		return ed.trovaAllBuoni(id);
	}

	public void aggiungiNews(News news) throws DBException {
		
		ed.aggiungiNews(news);
		
	}

	public void inserisciNuovoScontrino(Scontrino scontrino, int idEsercente) throws Exception {
		if(isScontrinoGiaInserito(scontrino, idEsercente)) {
			System.out.println("gia inserito");
		} else {
			ed.inserisciScontrino(scontrino, idEsercente);
		}
		
	}

	private boolean isScontrinoGiaInserito(Scontrino scontrino, int idEsercente) throws DBException {
		if (ed.getScontrinoByCodice(scontrino.getCodice_scontrino(), idEsercente) == 0) {
		return false;
	} else {
		return true;
		}
	}

	public Errore riscuotiBuono(BuonoAssegnato ba) throws DBException {

		Errore er = new Errore();
		int idCliente = ba.getID_cliente();
		
		System.out.println("id buono assegnato "+ba.getID_buono_assegnato() + " id cliente" + idCliente);
		if (isBuonoRiscuotibile(ba.getID_buono_assegnato(), idCliente)) {
			ba.setData_riscossione(new Date());
			ed.riscuotiBuono(ba, idCliente);
			
		} else {
			
			er.setMsg("Attenzione buono NON riscuotibile");
			return er;
		}

		return null;
	}

	private boolean isBuonoRiscuotibile(int idBuonoAssegnato, int idCliente) throws DBException {

		BuonoAssegnato ba = this.ottieniBuonoAssegnato(idBuonoAssegnato, idCliente);
		System.out.println(ba);
		System.out.println(idCliente);
		
		if (ba == null) {
			
			return false;
			
		}

		Date today = new Date();

		System.out.println("sono in isBuonoRiscuotibile");
		if (today.equals(ba.getData_scadenza()) || today.before(ba.getData_scadenza()) && (!ba.isUsato() && ba.getData_riscossione() == null)) {

			return true;
		}

		return false;
	}

	public BuonoAssegnato ottieniBuonoAssegnato(int idBuonoAssegnato, int idCliente) throws DBException {

		return ed.findBuonoAssegnatoById(idBuonoAssegnato, idCliente);

	}

}
