package com.example.bismillahTP1.model;

public class KelurahanModel {
	
	private long id;
	private String kode_kelurahan;
	private long id_kecamatan;
	private String nama_kelurahan;
	private String kode_pos;
	
	public KelurahanModel(long id, String kode_kelurahan, long id_kecamatan, String nama_kelurahan, String kode_pos) {
		
		this.id= id;
		this.kode_kelurahan = kode_kelurahan;
		this.id_kecamatan = id_kecamatan;
		this.nama_kelurahan = nama_kelurahan;
		this.kode_pos = kode_pos;
	
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKode_kelurahan() {
		return kode_kelurahan;
	}

	public void setKode_kelurahan(String kode_kelurahan) {
		this.kode_kelurahan = kode_kelurahan;
	}

	public long getId_kecamatan() {
		return id_kecamatan;
	}

	public void setId_kecamatan(char id_kecamatan) {
		this.id_kecamatan = id_kecamatan;
	}

	public String getNama_kelurahan() {
		return nama_kelurahan;
	}

	public void setNama_kecamatan(String nama_kelurahan) {
		this.nama_kelurahan = nama_kelurahan;
	}

	public String getKode_pos() {
		return kode_pos;
	}

	public void setKode_pos(String kode_pos) {
		this.kode_pos = kode_pos;
	}
	
	
	
}
