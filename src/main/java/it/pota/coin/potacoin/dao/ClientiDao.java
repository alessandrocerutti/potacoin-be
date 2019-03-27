package it.pota.coin.potacoin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.dto.BuonoAssegnato;
import it.pota.coin.potacoin.dto.Cliente;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.util.DBUtil;

public class ClientiDao {

	public String inserimento(Cliente cliente) throws DBException {

		String nome = cliente.getNome();
		String cognome = "prova";
		String cf = "";
		String citta = "Milano";
		StringBuilder sql = new StringBuilder();
		System.out.println(nome);
		sql.append("INSERT INTO TB_CLIENTE (NOME, COGNOME, CF, CITTA)");
		sql.append(" VALUES(?,?,?,?)");

		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			pstm.setString(1, nome);
			pstm.setString(2, cognome);
			pstm.setString(3, cf);
			pstm.setString(4, citta);
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
		return "ok";
	}

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
			
			if (rs.next()==true) {
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

	public int autenticazione(String u, String p) throws DBException {

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

	public ArrayList<BuonoAssegnato> getBuoniCliente(int id) throws DBException{
		ArrayList<BuonoAssegnato> buoniAssegnati = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select buono.ID_buono, esr.ID_esercente,nome_attivita, buono.descrizione as descrizione, scadenza,costo_punti, giacenza, scadenza_assoluta, ID_buono_assegnato, ID_cliente, data_assegnazione,data_scadenza from tb_buono as buono Inner join tb_tipo_buono as tbuono on buono.ID_tipo_buono = tbuono.ID_tipo_buono Inner join tb_buono_assegnato as buonoa on buonoa.ID_buono = buono.ID_buono Inner join tb_esercente as esr on esr.ID_esercente = buono.ID_esercente where buonoa.ID_cliente = ?");

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
				b.setID_Cliente(rs.getInt("ID_Cliente"));
				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setData_assegnazione(rs.getDate("data_assegnazione"));
				b.setData_scadenza(rs.getDate("data_scadenza"));
				b.setDescrizione(rs.getString("descrizione"));
				b.setGiacenza(rs.getInt("giacenza"));
				b.setID_buono(rs.getInt("ID_buono"));
				b.setID_buono_assegnato(rs.getInt("ID_buono_assegnato"));
				b.setID_Cliente(rs.getInt("ID_cliente"));
				b.setID_esercente(rs.getInt("ID_esercente"));
				b.setNome_attivita(rs.getString("nome_attivita"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta(rs.getDate("scadenza_assoluta"));
				b.setTipo_buono(rs.getString("tipo_buono"));
				b.setUsato(rs.getBoolean("usato"));
				
				buoniAssegnati.add(b);
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
		return buoniAssegnati;

	}

}
