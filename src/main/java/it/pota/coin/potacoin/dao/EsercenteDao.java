package it.pota.coin.potacoin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.pota.coin.potacoin.dto.Esercente;
import it.pota.coin.potacoin.exception.DBException;
import it.pota.coin.potacoin.util.DBUtil;

public class EsercenteDao {
	
	public List<Esercente> selectAllEsercenti() throws DBException {
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

}
