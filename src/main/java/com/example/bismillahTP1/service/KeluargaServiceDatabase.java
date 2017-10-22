package com.example.bismillahTP1.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bismillahTP1.model.KeluargaModel;
import com.example.bismillahTP1.model.PendudukModel;
import com.example.bismillahTP1.service.KeluargaService;
import com.example.bismillahTP1.dao.KeluargaMapper;
import com.example.bismillahTP1.dao.PendudukMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("select keluarga with nkk{}",nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}
	
	@Override
	public List<PendudukModel> selectDaftarKeluarga(String nkk) {
		log.info("select daftar keluarga with nkk{}",nkk);
		return keluargaMapper.selectDaftarKeluarga(nkk);
	}
	
	@Override
	public Long cariIdKelurahan(String nama_kelurahan) {
		
		Long id_kelurahan = keluargaMapper.cariIdKelurahan(nama_kelurahan); 
	
		return id_kelurahan;
	}
	
	@Override
	public String createNKK(Long id_kelurahan) {
		
		Long id_kecamatan = keluargaMapper.selectIdKecamatan(id_kelurahan);
		String kode_kecamatan = keluargaMapper.selectKodeKecamatan(id_kecamatan);
		
		String kodeKecamatan = kode_kecamatan.substring(0, kode_kecamatan.length()-1);
		
		SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		
		String tanggalKK = df.format(date);
		
		String nkk = kodeKecamatan + tanggalKK + "%";
		List<String> NKKs = keluargaMapper.selectNKK(nkk);
		nkk = nkk.substring(0, nkk.length()-1);
		
		if(NKKs.size() == 0) {
			nkk += "0001";
		} else {
			Long nkkMAX = (long) 0;
			for(int i=0; i<NKKs.size(); i++) {
				Long tmp = Long.parseLong(NKKs.get(i));
				if(nkkMAX < tmp) {
					nkkMAX = tmp;
				}
			}
			nkk = Long.toString(nkkMAX + 1);
		}
		log.info("nkk {}", nkk);
		
		return nkk;
	}
	
	@Override
	public void insertKeluarga(KeluargaModel keluarga) {
		keluargaMapper.insertKeluarga(keluarga);
	}
	
	@Override
	public void updateKeluarga(KeluargaModel keluarga, String nomor_kk_lama) {
		keluargaMapper.updateKeluarga(keluarga, nomor_kk_lama);
	}
	
}
