package it.pota.coin.potacoin.dto;

public class Esercente {
	private int id;
	private String tipo_esercente;
	private String nome_attivita;
	private String indirizzo;
	private String coordinate;
	private String p_iva;

	public Esercente() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo_esercente() {
		return tipo_esercente;
	}

	public void setTipo_esercente(String tipo_esercente) {
		this.tipo_esercente = tipo_esercente;
	}

	public String getNome_attivita() {
		return nome_attivita;
	}

	public void setNome_attivita(String nome_attivita) {
		this.nome_attivita = nome_attivita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getP_iva() {
		return p_iva;
	}

	public void setP_iva(String p_iva) {
		this.p_iva = p_iva;
	}

	@Override
	public String toString() {
		return "Esercente [id=" + id + ", tipo_esercente=" + tipo_esercente + ", nome_attivita=" + nome_attivita
				+ ", indirizzo=" + indirizzo + ", coordinate=" + coordinate + ", p_iva=" + p_iva + "]";
	}

}
