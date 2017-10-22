package com.example.bismillahTP1.service;

import java.util.List;

import com.example.bismillahTP1.model.KeluargaModel;
import com.example.bismillahTP1.model.PendudukModel;


public interface KeluargaService {

	KeluargaModel selectKeluarga(String nkk);
	
	List<PendudukModel> selectDaftarKeluarga(String nkk);
	
	Long cariIdKelurahan(String nama_kelurahan);
	
	String createNKK(Long id_kelurahan);

	void insertKeluarga(KeluargaModel keluarga);

	void updateKeluarga(KeluargaModel keluarga, String nomor_kk_lama);
	
}
