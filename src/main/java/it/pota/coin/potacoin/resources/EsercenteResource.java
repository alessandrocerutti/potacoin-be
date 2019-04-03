package it.pota.coin.potacoin.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.response.EsercenteResponse;
import it.pota.coin.potacoin.response.RegistrazioneEsercenteRequest;
import it.pota.coin.potacoin.service.EsercenteService;
import it.pota.coin.potacoin.util.SecurityUtil;

@Path("esercente")
public class EsercenteResource {
	EsercenteService es = new EsercenteService();

	private final String ESERCENTE = "e";

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response iscrizioneEsercente(RegistrazioneEsercenteRequest req) {
		EsercenteResponse er = new EsercenteResponse();
		
		Esercente esercente = req.getEsercente();
		Credenziali credenziali = req.getCredenziali();

		Errore esitoControllo = es.isRegistrato(credenziali.getEmail(), credenziali.getUsername());
		System.out.println(esitoControllo);

		if (esitoControllo == null) {
			System.out.println("ok procedi alla registrazione");
			
			try {
				es.completaRegistrazione(esercente, credenziali);
			} catch (DBException e) {
				
			}
		}

		else if (esitoControllo.getId() == 101) {
			er.setErrore(esitoControllo);
		} else if (esitoControllo.getId() == 102) {
			er.setErrore(esitoControllo);
		}

		return Response.ok(er).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/controlloregistrazione")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response controlloCredenziali(Credenziali cred) {

		EsercenteResponse er = new EsercenteResponse();

		Errore erroreControllo = es.isRegistrato(cred.getEmail(), cred.getUsername());

		er.setErrore(erroreControllo);

		return Response.ok(er).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Credenziali cred) {

		Errore er = new Errore();
		EsercenteResponse resp = new EsercenteResponse();

		try {
			int id = es.isAutenticato(cred);
			if (id != 0) {

				resp.setToken(SecurityUtil.prepareToken(Integer.toString(id), ESERCENTE));
				resp.setEsercente(es.getDatiEsercente(id));
			} else {
				er.setMsg("email o password errati");
				er.setId(1);
				resp.setErrore(er);
			}
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			resp.setErrore(er);
		}

		return Response.ok(resp).header("Access-Control-Allow-Origin", "*").build();
	}

}