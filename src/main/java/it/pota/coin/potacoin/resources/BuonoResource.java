package it.pota.coin.potacoin.resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.pota.coin.potacoin.dto.Buono;

import it.pota.coin.potacoin.service.BuonoService;

@Path("buoni")
public class BuonoResource {
	BuonoService bs = new BuonoService();
	
	//@OPTIONS
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ArrayList<Buono> getAll() {

		try {

			return bs.getAllBuoni();

		} catch (Exception e) {
			return null;
		}
		
	}

}
