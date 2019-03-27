package it.pota.coin.potacoin.resources;

import javax.ws.rs.Path;

import it.pota.coin.potacoin.service.EsercenteService;

@Path("esercenti")
public class EsercenteResource {
	EsercenteService es = new EsercenteService();
	
}
