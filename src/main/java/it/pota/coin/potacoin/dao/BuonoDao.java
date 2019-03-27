package it.pota.coin.potacoin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import it.pota.coin.potacoin.dto.Buono;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.util.DBUtil;

public class BuonoDao {

	public ArrayList<Buono> getAll() throws DBException {
		ArrayList<Buono> buoni = new ArrayList<>();

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT b.ID_buono,b.descrizione, b.ID_esercente, b.scadenza, b.costo_punti, b.giacenza, b.scadenza_assoluta, tb.descrizione as tipo_buono, e.nome_attivita FROM tb_buono as b INNER JOIN tb_tipo_buono as tb ON b.ID_tipo_buono = tb.ID_tipo_buono INNER JOIN tb_esercente as e ON b.ID_esercente = e.ID_esercente WHERE b.scadenza_assoluta > NOW()");
		System.out.println("Sono in" + this.getClass().getName());

		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = DBUtil.getConnection();
			pstm = connection.prepareStatement(sql.toString());
			rs = pstm.executeQuery();

			while (rs.next()) {
				Buono b = new Buono();
				b.setID_buono(rs.getInt("ID_buono"));
				b.setCosto_punti(rs.getInt("costo_punti"));
				b.setDescrizione(rs.getString("descrizione"));
				b.setTipo_buono(rs.getString("tipo_buono"));
				b.setGiacenza(rs.getInt("giacenza"));
				b.setID_esercente(rs.getInt("ID_esercente"));
				b.setNome_attivita(rs.getString("nome_attivita"));
				b.setScadenza(rs.getInt("scadenza"));
				b.setScadenza_assoluta((Date) rs.getDate("scadenza_assoluta"));
				buoni.add(b);
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
		System.out.println("lista buoni: " + buoni);
		return buoni;
	}

}
