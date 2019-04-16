package it.pota.coin.potacoin.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import it.pota.coin.potacoin.dao.ClientiDao;
import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.News;
import it.pota.coin.potacoin.dto.Scontrino;
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
		return dao.autenticazione(u, p, e);
	}

	public ArrayList<BuonoAssegnato> getBuoniCliente(int id) throws DBException {
		return dao.getBuoniCliente(id);
	}

	public void completaRegistrazione(Cliente cliente, Credenziali cred) throws DBException {

		dao.registrazione(cliente, cred);

	}

	public Errore isRegistrato(String email, String username) throws DBException {
		Errore er = new Errore();
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

	public ArrayList<Buono> getPreferiti(int id) throws DBException {
		return dao.buoniPreferiti(id);
	}

	public void setBuonoPreferito(int id, int id_Buono) throws DBException {
		if (isBuonoPreferito(id, id_Buono)) {
			dao.removeBuonoPreferito(id, id_Buono);
		} else {
			dao.setBuonoPreferito(id, id_Buono);
		}

	}

	public boolean isBuonoPreferito(int id, int id_Buono) throws DBException {
		int id_trovato = dao.isBuonoPreferito(id, id_Buono);
		if (id_trovato == 0) {
			return false;
		} else
			return true;
	}

	public ArrayList<News> getAllNews(int id) throws DBException {

		return dao.allNews(id);
	}

	public void riscuotiScontrino(String codice_scontrino, int idcliente) throws Exception {
		if(isScontrinoRiscosso(codice_scontrino).isRiscossione_avvenuta()) {
			System.out.println("scontrino già riscosso");
			throw new Exception("Scontrino già riscosso");
		} else {
			Scontrino scontrino = existScontrino(codice_scontrino);
			if (scontrino.getID_scontrino() != 0) {
				try {
			dao.riscuotiScontrino(codice_scontrino, idcliente);
				} catch (DBException e) {
					throw new Exception(e);
				}
				dao.updatePunti(scontrino.getPunti_assegnabili(), idcliente);
				
			} else
			{
				throw new Exception("Codice scontrino non valido");
			}
		}
		
	}

	private Scontrino existScontrino(String codice_scontrino) throws DBException {
		return dao.getScontrinoByCodiceScontrino(codice_scontrino);
		}

	private Scontrino isScontrinoRiscosso(String codice_scontrino) throws DBException {
		return dao.isScontrinoriscosso(codice_scontrino);
		
	}

	public Errore acquistaBuono(int idBuono, int idCliente) throws DBException {

		boolean isDisponibile = buonoDisponibile(idBuono);
		boolean isAcquistabile = buonoAcquistabile(idCliente, idBuono);
		Errore er = new Errore();

		if (isDisponibile && isAcquistabile) {

			Buono b = dao.getBuono(idBuono);

			BuonoAssegnato ba = new BuonoAssegnato();

			// LocalDate date = LocalDate.now().plusDays(b.getScadenza());

			ba.setID_cliente(idCliente);
			ba.setCodice_buono("a-" + idCliente + "-" + idBuono);
			ba.setID_buono(b.getID_buono());
			ba.setData_assegnazione(new Date());

			ba.setData_scadenza(ottieniExpirationDate(b.getScadenza()));

			// passo l'intero buono assegnato
			dao.acquistoBuono(ba);

			// viene ritornato null SOLO se buono acquistabile e disponibile
			return null;

		} else {

			if (!isDisponibile) {
				er.setMsg("Attenzione buono non disponibile!");

			} else if (!isAcquistabile) {
				er.setMsg("Attenzione saldo portafoglio insufficiente");
			}

			return er;

		}

	}

	private boolean buonoDisponibile(int idBuono) throws DBException {
		Date data = new Date();
		Buono buono = ottieniBuono(idBuono);
		System.out.println(buono);
		if (buono.getGiacenza() > 0
				&& (buono.getScadenza_assoluta().equals(data) || buono.getScadenza_assoluta().after(data))) {
			return true;
		} else {
			return false;
		}
	}

	private boolean buonoAcquistabile(int idCliente, int idBuono) throws DBException {
		Buono buono = ottieniBuono(idBuono);
		Cliente cliente = getDatiCliente(idCliente);

		System.out.println(cliente.getPunti());
		System.out.println(buono.getCosto_punti());
		if (buono.getCosto_punti() <= cliente.getPunti()) {
			System.out.println("sono in true di buonoAcquistabile");
			return true;

		} else {
			System.out.println("sono in false di buonoAcquistabile");
			return false;
		}

	}

	private Buono ottieniBuono(int idBuono) throws DBException {

		return dao.getBuono(idBuono);
	}

	private BuonoAssegnato ottieniBuonoAssegnato(String codiceBuono, int idCliente) throws DBException {

		return dao.findBuonoAssegnatoById(codiceBuono, idCliente);

	}

	private Date ottieniExpirationDate(int scadenza) {

		LocalDate date = LocalDate.now().plusDays(scadenza);

		Date dataScadenza = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		System.out.println(dataScadenza);
		return dataScadenza;
	}

}
