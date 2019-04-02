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

import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Errore;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.response.ClienteResponse;
import it.pota.coin.potacoin.response.RegistrazioneClienteRequest;
import it.pota.coin.potacoin.response.RequestCliente;
import it.pota.coin.potacoin.service.BuonoService;
import it.pota.coin.potacoin.service.ClientiService;
import it.pota.coin.potacoin.service.EsercenteService;
import it.pota.coin.potacoin.util.SecurityUtil;

@Path("cliente")
public class ClienteResource {
	private ClientiService cs = new ClientiService();
	BuonoService bs = new BuonoService();
	EsercenteService es = new EsercenteService();
	private String CLIENTE = "c";

	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public /* Cliente */ String signup(RegistrazioneClienteRequest req) {
		Errore er = new Errore();
		try {

			cs.completaRegistrazione(req.getCliente(), req.getCredenziali());

		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
		}
		return null;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ClienteResponse login(@Context HttpServletRequest req, Credenziali cred) {
		Errore er = new Errore();
		ClienteResponse resp = new ClienteResponse();
		HttpSession session = req.getSession(true);
		try {
			int id = cs.isAutenticato(cred);
			if (cs.isAutenticato(cred) != 0) {

				resp.setToken(SecurityUtil.prepareToken(Integer.toString(id), CLIENTE));
				resp.setCliente(cs.getDatiCliente(id));
			} else {
				er.setMsg("email o password errati");
				er.setId(1);
				resp.setErrore(er);
				session.invalidate();
			}
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			resp.setErrore(er);
		}
		return resp;

	}

	@POST
	@Path("/dati")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ClienteResponse getData(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse resp = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(1);
			er.setMsg("forbitten");
			resp.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					System.out.println(tkn);
					resp.setCliente(cs.getDatiCliente(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
					resp.setErrore(er);
				}
			} else {
				er.setId(1);
				er.setMsg("forbitten");
				resp.setErrore(er);
			}
		}

		return resp;
	}

	@POST
	@Path("/mieibuoni")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ClienteResponse getBuoniCliente(RequestCliente rc) {
		Errore er = new Errore();
		ClienteResponse resp = new ClienteResponse();
		String tkn = rc.getToken();
		if ("".equals(tkn)) {
			er.setId(1);
			er.setMsg("forbitten");
			resp.setErrore(er);
		} else {
			if (SecurityUtil.controllaToken(tkn)) {
				int id = SecurityUtil.getTokenBody(tkn);
				try {
					resp.setMieibuoni(cs.getBuoniCliente(id));
				} catch (DBException e) {
					er.setId(2);
					er.setMsg(e.getClass().getName());
					resp.setErrore(er);
				}
			} else {
				er.setId(1);
				er.setMsg("forbitten");
				resp.setErrore(er);
			}
		}

		return resp;
	}

	@GET
	@Path("/esercenti/all")
	@Produces(MediaType.APPLICATION_JSON)
	public ClienteResponse getAllEsercenti() {
		ClienteResponse cr = new ClienteResponse();
		Errore er = new Errore();

		try {
			cr.setEsercenti(es.getAllEsercenti());
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			cr.setErrore(er);
		}
		return cr;
	}

	@GET
	@Path("/buoni/all")
	@Produces(MediaType.APPLICATION_JSON)

	public ClienteResponse getAll() {
		ClienteResponse cr = new ClienteResponse();
		Errore er = new Errore();

		try {
			cr.setBuoni(bs.getAllBuoni());
		} catch (DBException e) {
			er.setId(2);
			er.setMsg(e.getMessage());
			cr.setErrore(er);
		}
		return cr;
	}

}
