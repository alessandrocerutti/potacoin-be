package it.pota.coin.potacoin.service;

import java.util.ArrayList;
import java.util.List;

import it.pota.coin.potacoin.dao.EsercenteDao;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;


public class EsercenteService {
	
	EsercenteDao ed = new EsercenteDao();
	
	public ArrayList<Esercente> getAllEsercenti() throws DBException{

			return ed.selectAllEsercenti();
	}

}
