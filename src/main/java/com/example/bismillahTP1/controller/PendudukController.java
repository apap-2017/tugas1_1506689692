package com.example.bismillahTP1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import com.example.bismillahTP1.model.PendudukModel;
import com.example.bismillahTP1.service.PendudukService;
import com.example.bismillahTP1.service.PendudukServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {

	@Autowired
	PendudukService pendudukService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/penduduk")
	public String viewPenduduk(Model model, @RequestParam(value = "nik", required = true) String nik) {
		PendudukModel penduduk = pendudukService.selectPenduduk(nik);
		if (penduduk == null) {
			model.addAttribute("nik", nik);
			return "not-found";
		} else {
			model.addAttribute("nik", nik);
			model.addAttribute("nama", penduduk.getNama());
			model.addAttribute("tempat_lahir", penduduk.getTempat_lahir());
			model.addAttribute("tanggal_lahir", penduduk.getTanggal_lahir());
			model.addAttribute("alamat", penduduk.getAlamat());
			model.addAttribute("rt", penduduk.getRt());
			model.addAttribute("rw", penduduk.getRw());
			model.addAttribute("nama_kelurahan", penduduk.getNama_kelurahan());
			model.addAttribute("nama_kecamatan", penduduk.getNama_kecamatan());
			model.addAttribute("nama_kota", penduduk.getNama_kota());
			model.addAttribute("golongan_darah", penduduk.getGolongan_darah());
			model.addAttribute("agama", penduduk.getAgama());
			model.addAttribute("status_perkawinan", penduduk.getStatus_perkawinan());
			model.addAttribute("pekerjaan", penduduk.getPekerjaan());

			if (penduduk.getIs_wni() == 1) {
				String wni = "WNI";
				model.addAttribute("is_wni", wni);
			} else {
				String wna = "WNA";
				model.addAttribute("is_wni", wna);
			}

			if (penduduk.getIs_wafat() == 0) {
				String hidup = "Hidup";
				model.addAttribute("is_wafat", hidup);
			} else {
				String mati = "Mati";
				model.addAttribute("is_wafat", mati);
			}
			
			if(penduduk.getJenis_kelamin() == 1) {
				String wanita = "Wanita";
				model.addAttribute("jenis_kelamin", wanita);
			} else {
				String pria = "Pria";
				model.addAttribute("jenis_kelamin", pria);
			}

			return "penduduk-found";
		}

	}

	@RequestMapping(method = RequestMethod.GET, value="/penduduk/tambah")
	public String bukaFormPenduduk (Model model){
		model.addAttribute("penduduk", new PendudukModel());
		return "tambah-penduduk";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/penduduk/tambah")
	public String tambahPenduduk (@RequestParam(value="input_nama", required = true) String nama, 
			@RequestParam(value="input_tempat_lahir", required = true) String tempat_lahir,
			@RequestParam(value="input_tanggal_lahir", required = true) String tanggal_lahir_tmp,
			@RequestParam(value="input_jenis_kelamin", required = true) String jenis_kelamin_tmp,
			@RequestParam(value="input_goldar", required = true) String golongan_darah,
			@RequestParam(value="input_agama", required = true) String agama,
			@RequestParam(value="input_stts_kawin", required = true) String status_perkawinan,
			@RequestParam(value="input_stts_keluarga", required = true) String status_dalam_keluarga,
			@RequestParam(value="input_pekerjaan", required = true) String pekerjaan,
			@RequestParam(value="input_is_wni", required = true) String is_wni_tmp,
			@RequestParam(value="input_id_keluarga", required = true) String id_keluarga,
			@RequestParam(value="input_is_wafat", required = true) String is_wafat_tmp,
			Model model) throws Exception {
		
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date tanggal_lahir = df.parse(tanggal_lahir_tmp);
			
			int jenis_kelamin;
			int is_wafat;
			int is_wni;
			
			if(jenis_kelamin_tmp.equalsIgnoreCase("wanita")) {
				jenis_kelamin = 1;
			} else {
				jenis_kelamin = 0;
			}
			
			if(is_wni_tmp.equalsIgnoreCase("wni")) {
				is_wni = 1;
			} else {
				is_wni = 0;
			}
			
			if(is_wafat_tmp.equalsIgnoreCase("mati")) {
				is_wafat = 1;
			} else {
				is_wafat = 0;
			}
			
			String nik = pendudukService.createNIK(tanggal_lahir, id_keluarga, jenis_kelamin);
			
			PendudukModel penduduk = new PendudukModel();
			
			penduduk.setNik(nik);
			penduduk.setNama(nama);
			penduduk.setTempat_lahir(tempat_lahir);
			penduduk.setTanggal_lahir(tanggal_lahir);
			penduduk.setJenis_kelamin(jenis_kelamin);
			penduduk.setIs_wni(is_wni);
			penduduk.setId_keluarga(id_keluarga);
			penduduk.setAgama(agama);
			penduduk.setPekerjaan(pekerjaan);
			penduduk.setStatus_perkawinan(status_perkawinan);
			penduduk.setStatus_dalam_keluarga(status_dalam_keluarga);
			penduduk.setGolongan_darah(golongan_darah);
			penduduk.setIs_wafat(is_wafat);
			penduduk.setIs_wni(is_wni);
			
			pendudukService.insertPenduduk(penduduk);
			
			model.addAttribute("nik", nik);
			return "sukses-tambah";
	}
	
	@RequestMapping("/penduduk/ubah")
	public String ubahPenduduk (){
		return "ubah-penduduk";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/penduduk/ubah-submit")
	public String ubahPendudukSubmit(Model model, @RequestParam(value = "nik", required = true) String nik) {
		return "redirect:/penduduk/ubah/" + nik;
	}
	
	@RequestMapping(value="/penduduk/ubah/{nik}")
	public String ubahPendudukSubmitNIK (@PathVariable String nik, Model model){
		
		PendudukModel penduduk = pendudukService.selectPenduduk(nik);
		
		if(penduduk == null) {
			model.addAttribute("nik", nik);
			return "not-found";
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String tanggal_lahir = df.format(penduduk.getTanggal_lahir());
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("tgl_lahir", tanggal_lahir);
			
			return "ubah-penduduk-form";
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value="/penduduk/ubah")
	public String ubahPendudukData (@RequestParam(value="nik", required = true) String nik_lama, 
			@RequestParam(value="nama", required = true) String nama,
			@RequestParam(value="tempat_lahir", required = true) String tempat_lahir,
			@RequestParam(value="input_tanggal_lahir", required = true) String tanggal_lahir_tmp,
			@RequestParam(value="jenis_kelamin", required = true) String jenis_kelamin_tmp,
			@RequestParam(value="golongan_darah", required = true) String golongan_darah,
			@RequestParam(value="agama", required = true) String agama,
			@RequestParam(value="status_perkawinan", required = true) String status_perkawinan,
			@RequestParam(value="pekerjaan", required = true) String pekerjaan,
			@RequestParam(value="is_wni", required = true) String is_wni_tmp,
			@RequestParam(value="status_dalam_keluarga", required = true) String status_dalam_keluarga,
			@RequestParam(value="is_wafat", required = true) String is_wafat_tmp,
			@RequestParam(value="id_keluarga", required = true) String id_keluarga,
			Model model) throws Exception {
	
			PendudukModel penduduk = new PendudukModel();		
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date tanggal_lahir = df.parse(tanggal_lahir_tmp);
			
			int is_wafat;
			int is_wni;
			int jenis_kelamin = Integer.parseInt(jenis_kelamin_tmp);
			if(is_wni_tmp.equalsIgnoreCase("wni")) {
				is_wni = 1;
			} else {
				is_wni = 0;
			}
			
			if(is_wafat_tmp.equalsIgnoreCase("mati")) {
				is_wafat = 1;
			} else {
				is_wafat = 0;
			}
			
			String nik = pendudukService.updateNIK(nik_lama, tanggal_lahir, id_keluarga);
			
			penduduk.setNik(nik);
			penduduk.setNama(nama);
			penduduk.setTempat_lahir(tempat_lahir);
			penduduk.setTanggal_lahir(tanggal_lahir);
			penduduk.setJenis_kelamin(jenis_kelamin);
			penduduk.setIs_wni(is_wni);
			penduduk.setId_keluarga(id_keluarga);
			penduduk.setAgama(agama);
			penduduk.setPekerjaan(pekerjaan);
			penduduk.setStatus_perkawinan(status_perkawinan);
			penduduk.setGolongan_darah(golongan_darah);
			penduduk.setIs_wafat(is_wafat);
			penduduk.setIs_wni(is_wni);
			penduduk.setStatus_dalam_keluarga(status_dalam_keluarga);
			
			pendudukService.updatePenduduk(penduduk, nik_lama);
			
			model.addAttribute("nik_lama", nik_lama);
			
			return "sukses-ubah-penduduk";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/penduduk/mati")
	public String pendudukMati (@RequestParam(value="nik", required = true) String nik,
			RedirectAttributes redirectAtt) {
		
		pendudukService.ubahIsWafat(nik);
		pendudukService.nonAktifkanNKK(nik);
		
		redirectAtt.addFlashAttribute("wafat", "berhasil");
		return "redirect:/penduduk?nik=" + nik;	
	
	}
	
	@RequestMapping("/penduduk/cari-form")
	public String cariPendudukForm (Model model){
		
		List<String> semuaKota = pendudukService.selectAllKota();
		model.addAttribute("semuaKota", semuaKota);

		return "cari-penduduk";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/penduduk/cari-kecamatan")
	public String cariPendudukKecamatan (Model model, @RequestParam(value = "kt", required = true) String nama_kota) {
		
		Long id_kota = pendudukService.cariIdKota(nama_kota);
		
		List<String> semuaKecamatan = pendudukService.selectAllKecamatan(id_kota);
		model.addAttribute("nama_kota", nama_kota);
		model.addAttribute("semuaKecamatan", semuaKecamatan);
		
		return "cari-penduduk-2";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/penduduk/cari-kelurahan")
	public String cariPendudukKelurahan (Model model, @RequestParam(value = "kc", required = true) String nama_kecamatan,
			@RequestParam(value = "kt", required = true) String nama_kota) {
		
		Long id_kecamatan = pendudukService.cariIdKecamatan(nama_kecamatan);
		
		List<String> semuaKelurahan = pendudukService.selectAllKelurahan(id_kecamatan);
		model.addAttribute("nama_kota", nama_kota);
		model.addAttribute("nama_kecamatan", nama_kecamatan);
		model.addAttribute("semuaKelurahan", semuaKelurahan);
		
		return "cari-penduduk-3";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/penduduk/cari")
	public String cariPenduduk (Model model, @RequestParam(value = "kt", required = true) String nama_kota,
			@RequestParam(value = "kc", required = true) String nama_kecamatan, 
			@RequestParam(value = "kl", required = true) String nama_kelurahan) {
		
		Long id_kelurahan = pendudukService.cariIdKelurahan(nama_kelurahan);
		
		List<PendudukModel> penduduk = pendudukService.cariPendudukKelurahan(id_kelurahan);
		
		model.addAttribute("nama_kota", nama_kota);
		model.addAttribute("nama_kecamatan", nama_kecamatan);
		model.addAttribute("nama_kelurahan", nama_kelurahan);
		model.addAttribute("penduduk", penduduk);
		
		return "cari-penduduk-berhasil";
		
	}
	
		

}
