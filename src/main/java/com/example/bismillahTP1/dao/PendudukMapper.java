package com.example.bismillahTP1.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.bismillahTP1.model.PendudukModel;
import com.example.bismillahTP1.model.KeluargaModel;
import com.example.bismillahTP1.model.KelurahanModel;
import com.example.bismillahTP1.model.KecamatanModel;
import com.example.bismillahTP1.model.KotaModel;

@Mapper
public interface PendudukMapper {

	@Select("select P.nik, P.nama, P.tempat_lahir, P.tanggal_lahir, P.jenis_kelamin, "
			+ "P.is_wni, P.agama, P.pekerjaan, P.status_perkawinan, P.status_dalam_keluarga, P.golongan_darah, P.is_wafat, P.id_keluarga, "
			+ "K.alamat, K.RT, K.RW, KL.nama_kelurahan, KC.nama_kecamatan, KO.nama_kota FROM PENDUDUK P,"
			+ "KELUARGA K, KELURAHAN KL, KECAMATAN KC, KOTA KO WHERE P.id_keluarga = K.id AND K.id_kelurahan = KL.id AND "
			+ "KL.id_kecamatan = KC.id AND KC.id_kota = KO.id AND P.nik = #{nik}")
	@Results(value = {
			@Result(property="nik", column="nik"),
			@Result(property="nama", column="nama"),
			@Result(property="tempat_lahir", column="tempat_lahir"),
			@Result(property="tanggal_lahir", column="tanggal_lahir"),
			@Result(property="jenis_kelamin", column="jenis_kelamin"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="RT"),
			@Result(property="rw", column="RW"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota"),
			@Result(property="golongan_darah", column="golongan_darah"),
			@Result(property="agama", column="agama"),
			@Result(property="status_perkawinan", column="status_perkawinan"),
			@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="is_wni", column="is_wni"),
			@Result(property="is_wafat", column="is_wafat"),
			@Result(property="id_keluarga", column="id_keluarga")
	})
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("SELECT id_kelurahan FROM keluarga WHERE id = #{id_keluarga}")
	@Results(value = {
			@Result(property="id_kelurahan", column="id_kelurahan")
	})
	Long selectIdKelurahan(String id_keluarga);
	
	@Select("SELECT id_kecamatan FROM kelurahan WHERE id = #{id_kelurahan}")
	@Results(value = {
			@Result(property="id_kecamatan", column="id_kecamatan")
	})
	Long selectIdKecamatan(Long id_kelurahan);
	
	@Select("SELECT kode_kecamatan FROM kecamatan WHERE id = #{id_kecamatan}")
	@Results(value = {
			@Result(property="kode_kecamatan", column="kode_kecamatan")
	})
	String selectKodeKecamatan(Long id_kecamatan);
	
	@Insert("INSERT INTO PENDUDUK (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, "
			+ "id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
			+ "golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, "
			+ "#{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, "
			+ "#{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void insertPenduduk(PendudukModel penduduk);
	
	@Select("SELECT nik FROM penduduk where nik like #{test_nik}")
	List<String> selectNIK(String test_nik);
	
	@Update("UPDATE penduduk SET nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempat_lahir}, "
			+ "tanggal_lahir = #{penduduk.tanggal_lahir}, jenis_kelamin = #{penduduk.jenis_kelamin}, is_wni = #{penduduk.is_wni}, "
			+ "id_keluarga = #{penduduk.id_keluarga}, agama = #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan}, "
			+ "status_perkawinan = #{penduduk.status_perkawinan}, status_dalam_keluarga = #{penduduk.status_dalam_keluarga}, "
			+ "golongan_darah = #{penduduk.golongan_darah}, is_wafat = #{penduduk.is_wafat} WHERE nik = #{nik_lama}")
	void updatePenduduk(@Param("penduduk") PendudukModel penduduk, @Param("nik_lama") String nik_lama);
	
	@Update("UPDATE penduduk SET is_wafat = '1' WHERE nik = #{nik}")
	void ubahIsWafat(String nik);
	
	@Update("UPDATE keluarga SET is_tidak_berlaku = '1' WHERE id = #{id_keluarga_cari}")
	void nonAktifkanNKK(String id_keluarga_cari);
	
	@Select("SELECT id_keluarga FROM penduduk WHERE nik = #{nik}")
	String selectIdKeluarga(String nik);
	
	@Select("SELECT P.is_wafat FROM penduduk P, keluarga K WHERE P.id_keluarga = K.id AND K.id = #{id_keluarga_cari}")
	List<Integer> cekStatusKematian(String id_keluarga_cari);
	
	@Select("SELECT nama_kota FROM kota")
	List<String> selectAllKota();
	
	@Select("SELECT id FROM kota WHERE nama_kota = #{nama_kota_cari}")
	Long selectIdKota(String nama_kota_cari);
	
	@Select("SELECT nama_kecamatan FROM kecamatan WHERE id_kota = #{id_kota_cari}")
	List<String> selectAllKecamatan(Long id_kota_cari);
	
	@Select("SELECT id FROM kecamatan WHERE nama_kecamatan = #{nama_kecamatan_cari}")
	Long selectIdKecamatanByNama(String nama_kecamatan_cari);
	
	@Select("SELECT nama_kelurahan FROM kelurahan WHERE id_kecamatan = #{id_kecamatan_cari}")
	List<String> selectAllKelurahan(Long id_kecamatan_cari);
	
	@Select("SELECT id FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan_cari}")
	Long selectIdKelurahanByNama(String nama_kelurahan_cari);
	
	@Select("select P.nik, P.nama, P.jenis_kelamin "
			+ "FROM PENDUDUK P, KELUARGA K, KELURAHAN KL WHERE P.id_keluarga = K.id AND K.id_kelurahan = KL.id AND KL.id = #{id_kelurahan}")
	List<PendudukModel> cariPendudukKelurahan (Long id_kelurahan);
	
	
}
