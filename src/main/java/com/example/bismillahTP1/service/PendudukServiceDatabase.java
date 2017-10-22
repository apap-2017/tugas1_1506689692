package com.example.bismillahTP1.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bismillahTP1.model.PendudukModel;
import com.example.bismillahTP1.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

import com.example.bismillahTP1.dao.PendudukMapper;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	PendudukMapper pendudukMapper;
	
	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select penduduk with nik{}",nik);
		return pendudukMapper.selectPenduduk(nik);
	}
	
	@Override
	public void insertPenduduk(PendudukModel penduduk) {
		
		log.info("insert penduduk");
		pendudukMapper.insertPenduduk(penduduk);
	}
	
	@Override
	public String createNIK(Date tanggal_lahir, String id_keluarga, int jenis_kelamin) {
		log.info("create NIK");
		String NIK = "";
		
		Long id_kelurahan = pendudukMapper.selectIdKelurahan(id_keluarga);
		Long id_kecamatan = pendudukMapper.selectIdKecamatan(id_kelurahan);
		String kode_kecamatan = pendudukMapper.selectKodeKecamatan(id_kecamatan);
		
		String kodeKecamatan = kode_kecamatan.substring(0, kode_kecamatan.length()-1);
		
		SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
		String tanggalLahir = df.format(tanggal_lahir);
		
		if(jenis_kelamin == 1) {
			Integer parseTanggal = Integer.parseInt(tanggalLahir) + 40;
			tanggalLahir = "" + parseTanggal;
		}
		
		NIK = kodeKecamatan + tanggalLahir + "%";
		List<String> NIKs = pendudukMapper.selectNIK(NIK);
		NIK = NIK.substring(0,NIK.length()-1);
		
		if(NIKs.size() == 0) {
			NIK += "0001";
		} else {
			Long nikMAX = (long) 0;
			for(int i=0; i<NIKs.size(); i++) {
				Long tmp = Long.parseLong(NIKs.get(i));
				if(nikMAX < tmp) {
					nikMAX = tmp;
				}
			}
			NIK = Long.toString(nikMAX + 1);
		}
		log.info("nik {}", NIK);
		
		return NIK;
	}
	
	@Override
	public String updateNIK(String nik_lama, Date tanggal_lahir, String id_keluarga) {
		
		Long id_kelurahan = pendudukMapper.selectIdKelurahan(id_keluarga);
		Long id_kecamatan = pendudukMapper.selectIdKecamatan(id_kelurahan);
		String kode_kecamatan = pendudukMapper.selectKodeKecamatan(id_kecamatan);
		String kodeKecamatan = kode_kecamatan.substring(0, kode_kecamatan.length()-1);
		SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
		String tanggalLahir = df.format(tanggal_lahir);
		String nik_baru = kodeKecamatan + tanggalLahir;
		
		if(nik_lama.substring(0, 11).equalsIgnoreCase(nik_baru)) {
			return nik_lama;
		} else {
			nik_baru = nik_baru + "%";
			
			List<String> NIKs = pendudukMapper.selectNIK(nik_baru);
			nik_baru = nik_baru.substring(0,nik_baru.length()-1);
			
			if(NIKs.size() == 0) {
				nik_baru += "0001";
			} else {
				Long nikMAX = (long) 0;
				for(int i=0; i<NIKs.size(); i++) {
					Long tmp = Long.parseLong(NIKs.get(i));
					if(nikMAX < tmp) {
						nikMAX = tmp;
					}
				}
				nik_baru = Long.toString(nikMAX + 1);
			}
			return nik_baru;
		}
		
	
	}
	
	@Override
	public void updatePenduduk(PendudukModel penduduk, String nik_lama){
		
		log.info("update penduduk");
		pendudukMapper.updatePenduduk(penduduk, nik_lama);
		
	}
	
	@Override
	public void ubahIsWafat(String nik) {
		pendudukMapper.ubahIsWafat(nik);
	}
	
	@Override
	public void nonAktifkanNKK(String nik) {
		
		String idKeluarga = pendudukMapper.selectIdKeluarga(nik);
		List<Integer> cek = pendudukMapper.cekStatusKematian(idKeluarga);
		
		int hidup = 0;
		
		for(int i = 0; i < cek.size();) {
			if(cek.get(i) == 0) {
				hidup++;
				i++;
			} else {
				i++;
			}
		}
		
		if(hidup == 0) {
			pendudukMapper.nonAktifkanNKK(idKeluarga);
		}
		
	}
	
	@Override
	public List<String> selectAllKota() {
		List<String> semuaKota = pendudukMapper.selectAllKota();
		return semuaKota;
	}
	
	@Override
	public Long cariIdKota(String nama_kota) {
		Long idKota = pendudukMapper.selectIdKota(nama_kota);
		return idKota;
	}
	
	@Override
	public List<String> selectAllKecamatan(Long id_kota) {
		List<String> semuaKecamatan = pendudukMapper.selectAllKecamatan(id_kota);
		return semuaKecamatan;
	}
	
	@Override
	public Long cariIdKecamatan(String nama_kecamatan) {
		Long idKecamatan = pendudukMapper.selectIdKecamatanByNama(nama_kecamatan);
		return idKecamatan;
	}
	
	@Override
	public List<String> selectAllKelurahan(Long id_kecamatan){
		List<String> semuaKelurahan = pendudukMapper.selectAllKelurahan(id_kecamatan);
		return semuaKelurahan;
	}
	
	@Override
	public Long cariIdKelurahan(String nama_kelurahan) {
		Long idKelurahan = pendudukMapper.selectIdKelurahanByNama(nama_kelurahan);
		
		return idKelurahan;
	}
	
	@Override
	public List<PendudukModel> cariPendudukKelurahan (Long id_kelurahan) {
		List<PendudukModel> penduduk = pendudukMapper.cariPendudukKelurahan(id_kelurahan);
		return penduduk;
	
	}
	
}
