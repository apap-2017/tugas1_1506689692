package com.example.bismillahTP1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class KeluargaModel {
	
	private int id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private Long id_kelurahan;
	private int is_tidak_berlaku;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	
}
