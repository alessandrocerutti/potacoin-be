package it.pota.coin.potacoin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.News;
import it.pota.coin.potacoin.dto.Scontrino;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.util.DBUtil;

public class ClientiDao {

	public Cliente getClientefromId(int id) throws DBException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_cliente, nome, cognome, CF, citta, punti FROM tb_cliente WHERE ID_cliente=?");
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Cliente c = new Cliente();
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, id);

			rs = pstm.executeQuery();

			if (rs.next() == true) {
				c.setId(rs.getInt("ID_cliente"));
				c.setNome(rs.getString("nome"));
				c.setCf(rs.getString("CF"));
				c.setCognome(rs.getString("cognome"));
				c.setPunti(rs.getInt("punti"));
				c.setCitta(rs.getString("citta"));
			}
		} catch (Exception e) {

			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		return c;

	}

	public int autenticazione(String u, String p, String em) throws DBException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_cliente");
		sql.append(" WHERE username=? AND pw=?");

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, u);
			pstm.setString(2, p);
			rs = pstm.executeQuery();

			if (rs.next()) {
				return rs.getInt("ID_user");
			} else
				return 0;

		} catch (Exception e) {

			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	public ArrayList<BuonoAssegnato> getBuoniCliente(int id) throws DBException {
		ArrayList<BuonoAssegnato> buoniAssegnati = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select buono.ID_buono, esr.ID_esercente,nome_attivita, tbuono.descrizione as tipo_buono, buono.descrizione as descrizione, scadenza,costo_punti, giacenza, scadenza_assoluta, ID_buono_assegnato, usato, ID_cliente, data_assegnazione,data_scadenza from tb_buono as buono Inner join tb_tipo_buono as tbuono on buono.ID_tipo_buono = tbuono.ID_tipo_buono Inner join tb_buono_assegnato as buonoa on buonoa.ID_buono = buono.ID_buono Inner join tb_esercente as esr on esr.ID_esercente = buono.ID_esercente where buonoa.ID_cliente = ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				BuonoAssegnato b = new BuonoAssegnato();
				b.setID_cliente(rs.getInt("ID_Cliente"));
				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setData_assegnazione(rs.getDate("data_assegnazione"));
				b.setData_scadenza(rs.getDate("data_scadenza"));
				b.setDescrizione(rs.getString("descrizione"));
				b.setGiacenza(rs.getInt("giacenza"));
				b.setID_buono(rs.getInt("ID_buono"));
				b.setID_buono_assegnato(rs.getInt("ID_buono_assegnato"));
				b.setID_cliente(rs.getInt("ID_cliente"));
				b.setID_esercente(rs.getInt("ID_esercente"));
				b.setNome_attivita(rs.getString("nome_attivita"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta(rs.getDate("scadenza_assoluta"));
				b.setTipo_buono(rs.getString("tipo_buono"));
				b.setUsato(rs.getBoolean("usato"));

				buoniAssegnati.add(b);
			}

		} catch (Exception e) {
			System.out.println(e);
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		return buoniAssegnati;

	}

	public void registrazione(Cliente cliente, Credenziali cred) throws DBException {

		System.out.println(cliente.toString() + cred.toString());

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();
		sql.append("INSERT INTO tb_cliente(nome, cognome, CF, citta) VALUES ( ?, ?, ?, ?)");

		System.out.println("query scritta");
		Connection connection = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		ResultSet rs2 = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getCognome());
			pstm.setString(3, cliente.getCf());
			pstm.setString(4, cliente.getCitta());
			pstm.executeUpdate();

			sql2.append("SELECT LAST_INSERT_ID()");
			pstm2 = connection.prepareStatement(sql2.toString());
			rs2 = pstm2.executeQuery();
			int newId = 0;
			while (rs2.next()) {
				newId = rs2.getInt(1);
			}

			sql3.append(
					"INSERT INTO tb_credenziali_cliente (ID_user, ID_ruolo, email, username, pw) VALUES ( ?, ?, ?, ?,?)");
			pstm3 = connection.prepareStatement(sql3.toString());
			pstm3.setInt(1, newId);
			pstm3.setInt(2, 3);
			pstm3.setString(3, cred.getEmail());
			pstm3.setString(4, cred.getUsername());
			pstm3.setString(5, cred.getPassword());
			pstm3.executeUpdate();

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	public int controlloRegistrazione(String email, String username) throws DBException {
		if (controlloEmail(email)) {
			return 1;
		} else if (controlloUsername(username)) {
			return 2;
		} else {
			return 0;
		}
	}

	private boolean controlloUsername(String username) throws DBException {

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		boolean usernameTrovata = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_cliente ");
		sql.append("WHERE username = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, username);
			rs = pstm.executeQuery();

			if (rs.next() == true) {
				usernameTrovata = true;
			}

		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {

				}
			}
		}

		return usernameTrovata;
	}

	private boolean controlloEmail(String email) throws DBException {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		boolean emailTrovata = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_cliente ");
		sql.append("WHERE email = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, email);
			rs = pstm.executeQuery();

			if (rs.next() == true) {
				emailTrovata = true;
			}

		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

		return emailTrovata;

	}

	public ArrayList<Buono> buoniPreferiti(int id) throws DBException {

		ArrayList<Buono> buoniPrefe = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select buon.costo_punti, buon.descrizione as descrizioneb, buon.giacenza,buon.ID_buono, buon.ID_esercente, buon.ID_buono, eserc.nome_attivita, buon.scadenza, buon.scadenza_assoluta, tbuono.descrizione as tipo_buono from tb_buono buon inner join tb_buoni_preferiti as pref on pref.ID_buono = buon.ID_buono inner join tb_cliente as cli on pref.ID_cliente = cli.ID_cliente inner join tb_tipo_buono as tbuono on tbuono.id_tipo_buono = buon.ID_tipo_buono inner join tb_esercente as eserc on eserc.id_Esercente = buon.id_esercente where pref.ID_cliente = ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				Buono b = new Buono();

				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setDescrizione(rs.getString("descrizioneb"));
				b.setGiacenza(rs.getInt("giacenza"));
				b.setID_buono(rs.getInt("ID_buono"));
				b.setID_esercente(rs.getInt("ID_esercente"));
				b.setNome_attivita(rs.getString("nome_attivita"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta(rs.getDate("scadenza_assoluta"));
				b.setTipo_buono(rs.getString("tipo_buono"));

				buoniPrefe.add(b);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		return buoniPrefe;
	}

	public void setBuonoPreferito(int id, int id_Buono) throws DBException {
		System.out.println("Sono alla fine. il buono ha id= " + id_Buono);

		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO tb_buoni_preferiti(ID_buono, ID_cliente) VALUES ( ?, ?)");

		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, id_Buono);
			pstm.setInt(2, id);

			pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	public int isBuonoPreferito(int id, int id_Buono) throws DBException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ID_Preferiti, ID_cliente, ID_Buono FROM tb_buoni_preferiti WHERE ID_cliente=? AND ID_Buono=?");
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, id);
			pstm.setInt(2, id_Buono);

			rs = pstm.executeQuery();

			if (rs.next()) {

				return rs.getInt("ID_Preferiti");
			}

		} catch (Exception e) {

			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

		return 0;
	}

	public void removeBuonoPreferito(int id, int id_Buono) throws DBException {
		System.out.println("Sono alla fine. il buono ha id= " + id_Buono);

		StringBuilder sql = new StringBuilder();

		sql.append("DELETE FROM `tb_buoni_preferiti` WHERE `ID_cliente`=? AND `ID_Buono` = ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, id_Buono);
			pstm.setInt(2, id);

			pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	public ArrayList<News> allNews(int id) throws DBException {
		StringBuilder sql = new StringBuilder();

		ArrayList<News> listaNews = new ArrayList<>();

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;

		sql.append("SELECT * FROM tb_news WHERE id_news > ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);

			rs = pstm.executeQuery();

			while (rs.next()) {

				News news = new News();

				news.setDate(rs.getDate("data"));
				news.setDescrizioneNews(rs.getString("DescrizioneNews"));
				news.setID_Esercente(rs.getInt("ID_esercente"));
				news.setTitoloNews(rs.getString("titoloNews"));

				listaNews.add(news);

			}
		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

		return listaNews;
	}

	public void riscuotiScontrino(String codice_scontrino, int id) throws DBException {
		
		System.out.println("riscuoti scontrino DAO");
		
		StringBuilder sql = new StringBuilder();

		sql.append("UPDATE tb_scontrino SET ID_Cliente = ?,riscossione_avvenuta = ? WHERE codice_scontrino= ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, id);
			pstm.setBoolean(2, true);
			pstm.setString(3, codice_scontrino);

			pstm.executeUpdate();

		} catch (Exception e) {
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	public Scontrino isScontrinoriscosso(String codice_scontrino) throws DBException {
		Scontrino scontrino = new Scontrino();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_Scontrino, riscossione_avvenuta FROM tb_scontrino WHERE codice_scontrino=?");
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setString(1, codice_scontrino);

			rs = pstm.executeQuery();

			if (rs.next()) {
				scontrino.setID_scontrino(rs.getInt("ID_Scontrino"));
				scontrino.setRiscossione_avvenuta(rs.getBoolean("riscossione_avvenuta"));
			}

		} catch (Exception e) {

			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

		return scontrino;
	}

	public Scontrino getScontrinoByCodiceScontrino(String codice_scontrino) throws DBException {
		Scontrino scontrino = new Scontrino();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_Scontrino,punti_assegnabili  FROM tb_scontrino WHERE codice_scontrino=?");
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setString(1, codice_scontrino);

			rs = pstm.executeQuery();

			if (rs.next()) {
				scontrino.setID_scontrino(rs.getInt("ID_Scontrino"));
				scontrino.setPunti_assegnabili(rs.getInt("punti_assegnabili"));
			}

		} catch (Exception e) {

			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

		return scontrino;
	}
	

	public void updatePunti(int punti_assegnabili, int idcliente) throws DBException {
		System.out.println("updatePunti");
		StringBuilder sql = new StringBuilder();
		Cliente cliente = getClientefromId(idcliente);
		int punti = cliente.getPunti() + punti_assegnabili;
		
		System.out.println("updatePunti cliente = " + cliente + " punti: " + punti);
		sql.append("UPDATE tb_cliente SET punti = ? WHERE ID_cliente= ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setInt(1, punti);
			pstm.setInt(2, idcliente);
			pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		
		
		
	}
	
	public Buono getBuono(int idBuono) throws DBException {

		StringBuilder sql = new StringBuilder();
		Buono b = new Buono();

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;

		sql.append("SELECT * FROM tb_buono WHERE ID_buono = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, idBuono);

			rs = pstm.executeQuery();

			if (rs.next()) {

				b.setID_buono(rs.getInt("ID_buono"));
				b.setGiacenza(rs.getInt("giacenza"));
				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta((Date) rs.getDate("scadenza_assoluta"));

			}

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		return b;
	}

	public void acquistoBuono(BuonoAssegnato ba) throws DBException {

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();

		PreparedStatement pstm = null;
		Connection connection = null;
		PreparedStatement pstm2 = null;

		// diminuisco la giacenza del buono
		sql.append("UPDATE tb_buono SET giacenza = ? WHERE ID_buono = ?");

		// diminuisco i punti dell'utente
		sql2.append("UPDATE tb_cliente SET punti = ? WHERE ID_cliente = ?");

		try {

			System.out.println(ba.getID_buono());
			Buono b = this.getBuono(ba.getID_buono());

			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			int giacenza = b.getGiacenza();
			giacenza -= 1;
			// b.setGiacenza(giacenza-=1);

			pstm.setInt(1, giacenza);
			pstm.setInt(2, ba.getID_buono());

			pstm.executeUpdate();

			Cliente c = this.getClientefromId(ba.getID_cliente());

			int punti = c.getPunti();
			punti -= b.getCosto_punti();

			pstm2 = connection.prepareStatement(sql2.toString());
			pstm2.setInt(1, punti);
			pstm2.setInt(2, ba.getID_cliente());

			pstm2.executeUpdate();

			// aggiungo il buono acquistato alla tb_buoni_assegnati
			this.aggiungiBuonoAssegnato(ba);

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	private void aggiungiBuonoAssegnato(BuonoAssegnato ba) throws DBException {

		StringBuilder sql = new StringBuilder();

		sql.append(
				"INSERT INTO tb_buono_assegnato (codice_buono, ID_buono, ID_cliente , data_assegnazione, data_scadenza)  VALUES (?,?,?,?,?)");
		// MANCA IL CODICE BUONO CHE DEVE ESSERE AGGIUNTO A MANO PERCHE' BUONOASSEGNATO

		PreparedStatement pstm = null;
		Connection connection = null;

		try {

			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			pstm.setString(1, ba.getCodice_buono());
			pstm.setInt(2, ba.getID_buono());
			pstm.setInt(3, ba.getID_cliente());
			System.out.println(ba.getID_cliente());

			java.sql.Date sqlStartDate = new java.sql.Date(ba.getData_assegnazione().getTime());
			pstm.setDate(4, sqlStartDate);

			java.sql.Date sqlStartDate1 = new java.sql.Date(ba.getData_scadenza().getTime());

			pstm.setDate(5, sqlStartDate1);

			pstm.executeUpdate();

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}

	}

	

	public BuonoAssegnato findBuonoAssegnatoById(String codiceBuono,int idBuonoAssegnato) throws DBException {

		StringBuilder sql = new StringBuilder();
		BuonoAssegnato b = new BuonoAssegnato();

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;

		sql.append("SELECT ID_buono_assegnato, codice_buono FROM tb_buono WHERE ID_buono = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, idBuonoAssegnato);

			rs = pstm.executeQuery();

			if (rs.next()) {

				b.setID_buono(rs.getInt("ID_buono"));
				b.setCodice_buono(rs.getString("codice_buono"));

			}

		} catch (Exception e) {
			System.out.println("errore " + e.getMessage());
			throw new DBException(e);

		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					// non faccio nulla
				}
			}
		}
		return b;

	}

	
}
