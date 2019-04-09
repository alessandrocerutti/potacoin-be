package it.pota.coin.potacoin.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Credenziali;
import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.util.DBUtil;

public class EsercenteDao {
	
	public ArrayList<Esercente> selectAllEsercenti() throws DBException {
		ArrayList<Esercente> esercenti = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT e.ID_esercente, te.descrizione as descrizione, e.nome_attivita, e.indirizzo, e.coordinate, e.p_iva FROM TB_ESERCENTE as e ");
		sql.append("INNER JOIN TB_TIPO_ESERCENTE as te ON e.ID_tipo_esercente = te.ID_tipo_esercente");
		/*sql.append("WHERE e.cancellato IS FALSE");*/
		System.out.println("Sono in" + this.getClass().getName());

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Esercente e = new Esercente();
				e.setId(rs.getInt("ID_esercente"));
				e.setTipo_esercente(rs.getString("descrizione"));
				e.setNome_attivita(rs.getString("nome_attivita"));
				e.setIndirizzo(rs.getString("indirizzo"));
				e.setCoordinate(rs.getString("coordinate"));
				e.setP_iva(rs.getString("p_iva"));
				esercenti.add(e);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
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
		System.out.println("lista esercenti: "+esercenti);
		return esercenti;
	}

	public int autenticazione(String u, String p) throws DBException {

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_esercenti ");
		sql.append("WHERE username = ? AND pw = ?");

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
				System.out.println(rs.getInt("ID_user"));
				return rs.getInt("ID_user"); // ottengo l'id dell'esercente
			} else {
				System.out.println("Esercente non presente");
				return 0;

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

	}

	public Esercente getEsercenteFromId(int id) throws DBException {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT ID_esercente, tb_tipo_esercente.descrizione, nome_attivita, indirizzo, coordinate, p_iva, nTelefono from tb_esercente inner join tb_tipo_esercente on tb_esercente.ID_tipo_esercente = tb_tipo_esercente.ID_tipo_esercente WHERE ID_esercente = ?");

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		Esercente esercente = new Esercente();

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			rs = pstm.executeQuery();

			if (rs.next() == true) {
				esercente.setId(rs.getInt("ID_esercente"));
				esercente.setTipo_esercente(rs.getString("tb_tipo_esercente.descrizione"));
				esercente.setNome_attivita(rs.getString("nome_attivita"));
				esercente.setIndirizzo(rs.getString("indirizzo"));
				esercente.setCoordinate(rs.getString("coordinate"));
				esercente.setP_iva(rs.getString("p_iva"));
				esercente.setNumero_telefono(rs.getString("nTelefono"));
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

		return esercente;
	}

	public int controlloRegistrazione(String mail, String username) {
		
		//boolean emailTrovata = controlloEmail(mail);

		//boolean usernameTrovata = controlloUsername(username);
		
		if (controlloEmail(mail)) {
			return 1;
		}else if (controlloUsername(username)) {
			return 2;
		}else {
			return 0;
		}
		
		

	}

	private boolean controlloEmail(String mail) {

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		boolean emailTrovata = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_esercenti ");
		sql.append("WHERE email = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, mail);
			rs = pstm.executeQuery();

			if (rs.next() == true) {
				emailTrovata = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private boolean controlloUsername(String username) {

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		boolean usernameTrovata = false;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_user FROM tb_credenziali_esercenti ");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return usernameTrovata;

	}

	public void registrazione(Esercente esercente, Credenziali cred) throws DBException {
		System.out.println("sono in registrazione di DaoClienti");
		String username = cred.getUsername();
		String password = cred.getPassword();
		String email = cred.getEmail();
		System.out.println(esercente.toString() + cred.toString());
		
		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		StringBuilder sql3 = new StringBuilder();
		sql.append("INSERT INTO tb_esercente(ID_tipo_esercente, nome_attivita, indirizzo, coordinate, p_iva, nTelefono) VALUES ( ?, ?, ?, ?,?,?)");
		
		System.out.println("query scritta");
		Connection connection = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		ResultSet rs2 = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, esercente.getId_tipo_esercente());
			pstm.setString(2, esercente.getNome_attivita());
			pstm.setString(3, esercente.getIndirizzo());
			pstm.setString(4, esercente.getCoordinate());
			pstm.setString(5, esercente.getP_iva());
			pstm.setString(6, esercente.getNumero_telefono() );
			pstm.executeUpdate();
			System.out.println("sono in registrazione di DaoClienti -- executeUpdate");
			
			sql2.append("SELECT LAST_INSERT_ID()");
			pstm2 = connection.prepareStatement(sql2.toString());
			rs2 = pstm2.executeQuery();
			int newId = 0;
			while(rs2.next()) {
				newId = rs2.getInt(1);
			}
			
			sql3.append("INSERT INTO tb_credenziali_esercenti (ID_user, ID_ruolo, email, username, pw) VALUES ( ?, ?, ?, ?,?)");
			pstm3 = connection.prepareStatement(sql3.toString());
			pstm3.setInt(1, newId);
			pstm3.setInt(2, 2);
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
	
	
	public void aggiungiBuono(Buono pacchettoBuoni) {

		Connection connection = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;

		System.out.println("Sono in aggiugiBuono esercente");

		StringBuilder sql = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();

		sql.append(
				"INSERT INTO tb_buono(ID_esercente, ID_tipo_buono, descrizione, scadenza, costo_punti, giacenza,scadenza_assoluta) VALUES ( ?, ?, ?, ?,?,?,?)");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());

			System.out.println(pacchettoBuoni);

			pstm.setInt(1, pacchettoBuoni.getID_esercente());
			pstm.setInt(2, pacchettoBuoni.getID_tipo_buono());
			pstm.setString(3, pacchettoBuoni.getDescrizione());
			pstm.setInt(4, pacchettoBuoni.getScadenza());
			pstm.setInt(5, pacchettoBuoni.getCosto_punti());
			pstm.setInt(6, pacchettoBuoni.getGiacenza());
			pstm.setDate(7, (Date) pacchettoBuoni.getScadenza_assoluta());
			pstm.executeUpdate();

			sql2.append("SELECT LAST_INSERT_ID()");
			pstm2 = connection.prepareStatement(sql.toString());
			rs = pstm2.executeQuery();

			int newId = 0;
			if (rs.next()) {
				newId = rs.getInt(1);
				// pacchettoBuoni.setID_buono(newId);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e1) {
					System.out.println(e1);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					
				}
			}
		}

	}

	public ArrayList<BuonoAssegnato> ricercaBuoniAssegnati(int id) throws DBException {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ArrayList<BuonoAssegnato> buoniAssegnati = new ArrayList<>();

		sql.append("SELECT * FROM tb_buono_assegnato as t INNER JOIN tb_buono as t1 ON t.ID_buono = t1.ID_buono");
		sql.append(" WHERE t1.ID_esercente = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			rs = pstm.executeQuery();

			while (rs.next()) {
				BuonoAssegnato buono = new BuonoAssegnato();

				buono.setID_buono_assegnato(rs.getInt("ID_buono_assegnato"));
				//buono.setID_Cliente(rs.getInt("ID_cliente"));
				buono.setData_assegnazione(rs.getDate("data_assegnazione"));

				boolean usato = rs.getInt("usato") == 0 ? false : true;

				buono.setUsato(usato);
				buono.setData_scadenza(rs.getDate("data_scadenza"));
				buono.setID_buono(rs.getInt("ID_buono"));
				buono.setID_tipo_buono(rs.getInt("ID_tipo_buono"));
				buono.setCosto_punti(rs.getInt("costo_punti"));
				buono.setScadenza(rs.getInt("scadenza"));
				buono.setDescrizione(rs.getString("descrizione"));

				buoniAssegnati.add(buono);

				// System.out.println(buono);

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

		return buoniAssegnati;
	}

	public ArrayList<Buono> trovaAllBuoni(int id) throws DBException {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		ArrayList<Buono> buoniInseriti = new ArrayList<>();

		sql.append("SELECT * FROM tb_buono");
		sql.append(" WHERE ID_esercente = ?");

		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setInt(1, id);
			rs = pstm.executeQuery();

			while (rs.next()) {
				
				System.out.println(rs);

				Buono b = new Buono();
				b.setID_buono(rs.getInt("ID_buono"));
				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setDescrizione(rs.getString("descrizione"));
				//b.setTipo_buono(rs.getString("tipo_buono"));
				b.setGiacenza(rs.getInt("giacenza"));
				//b.setID_esercente(rs.getInt("ID_esercente"));
				//b.setNome_attivita(rs.getString("nome_attivita"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta((Date) rs.getDate("scadenza_assoluta"));
				
				buoniInseriti.add(b);

				// System.out.println(buono);

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

		return buoniInseriti;
	}

	
}
