package it.pota.coin.potacoin.service;

import java.util.ArrayList;

import it.pota.coin.potacoin.dao.BuonoDao;
import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.exception.DBException;

public class BuonoService {
	BuonoDao bd = new BuonoDao();

	public ArrayList<Buono> getAllBuoni() throws DBException {

		return bd.getAll();

	}
}
