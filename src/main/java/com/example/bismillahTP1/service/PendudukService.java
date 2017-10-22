package com.example.bismillahTP1.service;

import java.util.Date;
import java.util.List;

import com.example.bismillahTP1.model.PendudukModel;

public interface PendudukService {

	PendudukModel selectPenduduk(String nik);
	
	void insertPenduduk(PendudukModel penduduk);
	
	String createNIK(Date tanggal_lahir, String id_keluarga, int jenis_kelamin);
	
	String updateNIK(String nik_lama, Date tanggal_lahir, String id_keluarga);
	
	void updatePenduduk(PendudukModel penduduk, String nik_lama);
	
	void ubahIsWafat(String nik);
	
	void nonAktifkanNKK(String nik);
	
	List<String> selectAllKota();
	
	Long cariIdKota (String nama_kota);
	
	List<String> selectAllKecamatan(Long id_kota);
	
	Long cariIdKecamatan(String nama_kecamatan);
	
	List<String> selectAllKelurahan(Long id_kecamatan);
	
	Long cariIdKelurahan(String nama_kelurahan);
	
	List<PendudukModel> cariPendudukKelurahan(Long id_kelurahan);
	
}
