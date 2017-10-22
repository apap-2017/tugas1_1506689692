package com.example.bismillahTP1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import com.example.bismillahTP1.model.PendudukModel;
import com.example.bismillahTP1.service.PendudukService;
import com.example.bismillahTP1.model.KeluargaModel;
import com.example.bismillahTP1.service.KeluargaService;

@Controller
public class KeluargaController {
	
	@Autowired
	KeluargaService keluargaService;
	
	@RequestMapping(method = RequestMethod.GET, value="/keluarga")
	public String viewKeluarga (Model model,
			@RequestParam(value="nkk", required = true) String nkk)
	{
		KeluargaModel keluarga = keluargaService.selectKeluarga(nkk);
		List<PendudukModel> daftarKeluarga = keluargaService.selectDaftarKeluarga(nkk);
		
		if(keluarga == null) {
			model.addAttribute("nkk", nkk);
			return "not-found";
		} else {
			model.addAttribute("nkk", keluarga.getNomor_kk());
			model.addAttribute("alamat", keluarga.getAlamat());
			model.addAttribute("rt", keluarga.getRt());
			model.addAttribute("rw", keluarga.getRw());
			model.addAttribute("nama_kelurahan", keluarga.getNama_kelurahan());
			model.addAttribute("nama_kecamatan", keluarga.getNama_kecamatan());
			model.addAttribute("nama_kota", keluarga.getNama_kota());
			
			model.addAttribute("daftarKeluarga", daftarKeluarga);
			
			return "keluarga-found";
			
		}
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/keluarga/tambah")
	public String bukaFormKeluarga (Model model) {
		model.addAttribute("keluarga", new KeluargaModel());
		return "tambah-keluarga";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/keluarga/tambah")
	public String tambahKeluarga (@RequestParam(value="input_alamat", required = true) String alamat, 
			@RequestParam(value="input_rt", required = true) String rt,
			@RequestParam(value="input_rw", required = true) String rw,
			@RequestParam(value="input_kelurahan", required = true) String nama_kelurahan, 
			@RequestParam(value="input_kecamatan", required = true) String nama_kecamatan, 
			@RequestParam(value="input_kota", required = true) String kota, Model model) {
		
			KeluargaModel keluarga = new KeluargaModel();
			
			Long id_kelurahan = keluargaService.cariIdKelurahan(nama_kelurahan);
			String nomor_kk = keluargaService.createNKK(id_kelurahan);
			int is_tidak_berlaku = 0;
			
			keluarga.setNomor_kk(nomor_kk);
			keluarga.setAlamat(alamat);
			keluarga.setRt(rt);
			keluarga.setRw(rw);
			keluarga.setId_kelurahan(id_kelurahan);
			keluarga.setIs_tidak_berlaku(is_tidak_berlaku);
			
			keluargaService.insertKeluarga(keluarga);
		
			model.addAttribute("nomor_kk", nomor_kk);
			return "sukses-tambah-keluarga";
	}
	
	@RequestMapping("/keluarga/ubah")
	public String ubahKeluarga (){
		return "ubah-keluarga";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/keluarga/ubah-submit")
	public String ubahKeluargaSubmit(Model model, @RequestParam(value = "nkk", required = true) String nkk) {
		return "redirect:/keluarga/ubah/" + nkk;
	}
	
	@RequestMapping(value="/keluarga/ubah/{nkk}")
	public String ubahKeluargaSubmitNKK (@PathVariable String nkk, Model model){
		
		KeluargaModel keluarga = keluargaService.selectKeluarga(nkk);
		
		if(keluarga == null) {
			model.addAttribute("nkk", nkk);
			return "not-found";
		} else {
			model.addAttribute("keluarga", keluarga);
			return "ubah-keluarga-form";
		}

	}
	
	@RequestMapping(method = RequestMethod.POST, value="/keluarga/ubah")
	public String ubahKeluargaData (@RequestParam(value="nomor_kk", required = true) String nomor_kk_lama,
			@RequestParam(value="alamat", required = true) String alamat, 
			@RequestParam(value="rt", required = true) String rt,
			@RequestParam(value="rw", required = true) String rw,
			@RequestParam(value="nama_kelurahan", required = true) String nama_kelurahan,
			@RequestParam(value="nama_kecamatan", required = true) String nama_kecamatan,
			@RequestParam(value="nama_kota", required = true) String nama_kota, Model model) {
		
		KeluargaModel keluarga = new KeluargaModel();
		int is_tidak_berlaku = 0;
		Long id_kelurahan = keluargaService.cariIdKelurahan(nama_kelurahan);
		
		String nomor_kk = keluargaService.createNKK(id_kelurahan);
		
		keluarga.setNomor_kk(nomor_kk);
		keluarga.setAlamat(alamat);
		keluarga.setRt(rt);
		keluarga.setRw(rw);
		keluarga.setId_kelurahan(id_kelurahan);
		keluarga.setIs_tidak_berlaku(is_tidak_berlaku);
		
		keluargaService.updateKeluarga(keluarga, nomor_kk_lama);
		model.addAttribute("nomor_kk_lama", nomor_kk_lama);
		return "sukses-ubah-keluarga";
		
	}
	
	
}
