package it.pota.coin.potacoin.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.service.EsercenteService;

@Path("esercenti")
public class EsercenteResource {
	EsercenteService es = new EsercenteService();

	@GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Esercente> getAllEsercenti() {
			return es.getAllEsercenti();
    }

}
