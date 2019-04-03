package it.pota.coin.potacoin.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.response.ClienteResponse;
import it.pota.coin.potacoin.response.EsercenteResponse;
import it.pota.coin.potacoin.response.RegistrazioneClienteRequest;
import it.pota.coin.potacoin.response.RegistrazioneEsercenteRequest;
import it.pota.coin.potacoin.response.RequestCliente;
import it.pota.coin.potacoin.service.BuonoService;
import it.pota.coin.potacoin.service.ClientiService;
import it.pota.coin.potacoin.service.EsercenteService;
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
			er.setId(2);
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
	public Response login(@Context HttpServletRequest req, Credenziali cred) {
		Errore er = new Errore();
		ClienteResponse cr = new ClienteResponse();
		HttpSession session = req.getSession(true);
		try {
			int id = cs.isAutenticato(cred);
			if (cs.isAutenticato(cred) != 0) {

				cr.setToken(SecurityUtil.prepareToken(Integer.toString(id), CLIENTE));
				cr.setCliente(cs.getDatiCliente(id));
			} else {
				er.setMsg("email o password errati");
				er.setId(1);
				cr.setErrore(er);
				session.invalidate();
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
			er.setId(1);
			er.setMsg("forbitten");
			cr.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					System.out.println(tkn);
					cr.setCliente(cs.getDatiCliente(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
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

	@POST
	@Path("/mieibuoni")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBuoniCliente(RequestCliente rc) {
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
					cr.setMieibuoni(cs.getBuoniCliente(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
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
	
	

}
