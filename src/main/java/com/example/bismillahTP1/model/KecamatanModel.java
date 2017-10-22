package com.example.bismillahTP1.model;

public class KecamatanModel {

	private long id;
	private String kode_kecamatan;
	private char id_kota;
	private String nama_kecamatan;
	
	public KecamatanModel(long id, String kode_kecamatan, char id_kota, String nama_kecamatan) {
		this.id = id;
		this.kode_kecamatan = kode_kecamatan;
		this.id_kota = id_kota;
		this.nama_kecamatan = nama_kecamatan;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKode_kecamatan() {
		return kode_kecamatan;
	}

	public void setKode_kecamatan(String kode_kecamatan) {
		this.kode_kecamatan = kode_kecamatan;
	}

	public char getId_kota() {
		return id_kota;
	}

	public void setId_kota(char id_kota) {
		this.id_kota = id_kota;
	}

	public String getNama_kecamatan() {
		return nama_kecamatan;
	}

	public void setNama_kecamatan(String nama_kecamatan) {
		this.nama_kecamatan = nama_kecamatan;
	}
	
	
	
}
