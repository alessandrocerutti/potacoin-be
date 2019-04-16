package it.pota.coin.potacoin.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.News;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.response.ClienteResponse;
import it.pota.coin.potacoin.response.EsercenteResponse;
import it.pota.coin.potacoin.response.RegistrazioneClienteRequest;
import it.pota.coin.potacoin.response.RequestCliente;
import it.pota.coin.potacoin.service.BuonoService;
import it.pota.coin.potacoin.service.ClientiService;
import it.pota.coin.potacoin.service.EsercenteService;
import it.pota.coin.potacoin.util.Costanti;
import it.pota.coin.potacoin.util.SecurityUtil;

@Path("cliente")
public class ClienteResource {
	private ClientiService cs = new ClientiService();
	private BuonoService bs = new BuonoService();
	private EsercenteService es = new EsercenteService();
	private String CLIENTE = "c";

	@POST
	@Path("/controlloregistrazione")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response controlloRegistrazione(Credenziali cred) {

		ClienteResponse cr = new ClienteResponse();
		Errore er = new Errore();
		try {

			Errore erroreControllo = cs.isRegistrato(cred.getEmail(), cred.getUsername());
			if (erroreControllo != null) {
				cr.setErrore(er);
			}
		} catch (DBException e) {
			er.setId(Costanti.ID_ERRORE_DBCONNECTION);
			er.setMsg(e.getMessage());
			cr.setErrore(er);

		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(RegistrazioneClienteRequest req) {
		EsercenteResponse er = new EsercenteResponse();

		Cliente cliente = req.getCliente();
		Credenziali credenziali = req.getCredenziali();

		Errore esitoControllo = es.isRegistrato(credenziali.getEmail(), credenziali.getUsername());
		System.out.println(esitoControllo);

		if (esitoControllo == null) {
			System.out.println("ok procedi alla registrazione");

			try {
				cs.completaRegistrazione(cliente, credenziali);
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
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Credenziali cred) {
		System.out.println("chiamata a login cliente effettuata");
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		if (cred == null) {
			er.setMsg(Costanti.CREDENZIALI_ERRATE);
			er.setId(Costanti.ID_ERRORE_UTENTE);
			cr.setErrore(er);
		} else
			try {
				int id = cs.isAutenticato(cred);
				if (cs.isAutenticato(cred) != 0) {

					cr.setToken(SecurityUtil.prepareToken(Integer.toString(id), CLIENTE));
					cr.setCliente(cs.getDatiCliente(id));
				} else {
					er.setMsg(Costanti.CREDENZIALI_ERRATE);
					er.setId(Costanti.ID_ERRORE_UTENTE);
					cr.setErrore(er);
				}
			} catch (DBException e) {
				er.setId(2);
				er.setMsg(e.getMessage());
				cr.setErrore(er);
			}
		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();

	}

	@POST
	@Path("/dati")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getData(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(Costanti.ID_ERRORE_UTENTE);
			er.setMsg(Costanti.FORBITTEN);
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					System.out.println(tkn);
					cr.setCliente(cs.getDatiCliente(id));
				} catch (DBException e) {
					er.setId(Costanti.ID_ERRORE_DBCONNECTION);
					er.setMsg(e.getMessage());
					cr.setErrore(er);
				}
			} else {
				er.setId(Costanti.ID_ERRORE_UTENTE);
				er.setMsg(Costanti.FORBITTEN);
				cr.setErrore(er);
			}
		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/mieibuoni")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBuoniCliente(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(Costanti.ID_ERRORE_UTENTE);
			er.setMsg(Costanti.FORBITTEN);
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					cr.setMieibuoni(cs.getBuoniCliente(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
					cr.setErrore(er);
				}
			} else {
				er.setId(Costanti.ID_ERRORE_UTENTE);
				er.setMsg(Costanti.FORBITTEN);
				cr.setErrore(er);
			}
		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/esercenti/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllEsercenti() {
		ClienteResponse cr = new ClienteResponse();
		Errore er = new Errore();

		try {
			cr.setEsercenti(es.getAllEsercenti());
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			cr.setErrore(er);
		}
		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/buoni/all")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getAll() {
		ClienteResponse cr = new ClienteResponse();
		Errore er = new Errore();

		try {
			cr.setBuoni(bs.getAllBuoni());
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			cr.setErrore(er);
		}
		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/buonipreferiti")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBuoniPrefe(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(Costanti.ID_ERRORE_UTENTE);
			er.setMsg(Costanti.FORBITTEN);
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					cr.setBuonipreferiti(cs.getPreferiti(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
					cr.setErrore(er);
				}
			} else {
				er.setId(Costanti.ID_ERRORE_UTENTE);
				er.setMsg(Costanti.FORBITTEN);
				cr.setErrore(er);
			}
		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/togglebuonopreferito")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addBuonoaPreferito(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(1);
			er.setMsg("forbitten");
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);

				try {
					cs.setBuonoPreferito(id, rc.getID_buono_preferito());

				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getMessage());
					cr.setErrore(er);
				}
			} else {
				er.setId(1);
				er.setMsg("forbitten");
				cr.setErrore(er);
			}
		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/getallnews/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getAllNews(@PathParam("id") int id) {

		Errore er = new Errore();
		ClienteResponse clienteResp = new ClienteResponse();
		ArrayList<News> listaNews = new ArrayList<>();

		try {
			listaNews = cs.getAllNews(id);
			System.out.println(listaNews);
			clienteResp.setNews(listaNews);

		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			clienteResp.setErrore(er);
			e.printStackTrace();
		}

		return Response.ok(clienteResp).header("Access-Control-Allow-Origin", "*").build();

	}

	// TODO riscuoti scontrino
	@POST
	@Path("/riscuotiscontrino")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response riscuotiScontrino(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(Costanti.ID_ERRORE_UTENTE);
			er.setMsg(Costanti.FORBITTEN);
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					cs.riscuotiScontrino(rc.getScontrino().getCodice_scontrino(), id);
				} catch (DBException e) {
					er.setId(2);
					cr.setErrore(er);
				} catch (Exception e1) {
					er.setId(2);
					er.setMsg(e1.getMessage());
					cr.setErrore(er);
				}
			} else {
				er.setId(Costanti.ID_ERRORE_UTENTE);
				er.setMsg(Costanti.FORBITTEN);
				cr.setErrore(er);
			}
		}

		return Response.ok(cr).header("Access-Control-Allow-Origin", "*").build();
	}

	@POST
	@Path("/acquistaBuono/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response acquistaBuono(@PathParam("id") int idBuono, ClienteResponse rc) {

		Errore er = new Errore();
		Errore erroreAcquisto = null;
		ClienteResponse clienteResp = new ClienteResponse();
		String tkn = rc.getToken();
		System.out.println(idBuono);

		if ("".equals(tkn)) {
			er.setId(1);
			er.setMsg("forbitten");
			clienteResp.setErrore(er);
		} else {

			if (SecurityUtil.controllaToken(tkn)) {
				int idCliente = SecurityUtil.getTokenBody(tkn);

				try {
					erroreAcquisto = cs.acquistaBuono(idBuono, idCliente);
				} catch (DBException e) {

					er.setId(2);
					er.setMsg(e.getMessage());
					clienteResp.setErrore(er);
				}
				if (erroreAcquisto != null) {
					clienteResp.setErrore(erroreAcquisto);
				} else {
					 clienteResp.setMessaggio("Buono acquistato correttamente");
				}
			} 
			else {
				er.setId(Costanti.ID_ERRORE_UTENTE);
				er.setMsg(Costanti.FORBITTEN);
				clienteResp.setErrore(er);
			}

		}
		return Response.ok(clienteResp).header("Access-Control-Allow-Origin", "*").build();

	}

}
