package it.pota.coin.potacoin.service;

import java.util.List;

import it.pota.coin.potacoin.dao.EsercenteDao;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;


public class EsercenteService {
	
	EsercenteDao ed = new EsercenteDao();
	
	public List<Esercente> getAllEsercenti(){
		try {
			return ed.selectAllEsercenti();
		} catch (DBException e) {
			return null;
		}
	}
}
