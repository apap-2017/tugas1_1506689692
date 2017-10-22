package com.example.bismillahTP1.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface KeluargaMapper {

	@Select("select K.nomor_kk, K.alamat, K.rt, K.rw, KL.nama_kelurahan, "
			+ "KC.nama_kecamatan, KO.nama_kota FROM KELUARGA K, KELURAHAN KL, "
			+ "KECAMATAN KC, KOTA KO WHERE K.id_kelurahan = KL.id AND KL.id_kecamatan = KC.id "
			+ "AND KC.id_kota = KO.id AND K.nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nomor_kk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="rt"),
			@Result(property="rw", column="rw"),
			@Result(property="nama_kelurahan", column="nama_kelurahan"),
			@Result(property="nama_kecamatan", column="nama_kecamatan"),
			@Result(property="nama_kota", column="nama_kota")
	})
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select P.nama, P.nik, P.jenis_kelamin, P.tempat_lahir, P.tanggal_lahir, P.agama, "
			+ "P.pekerjaan, P.status_perkawinan, P.status_dalam_keluarga, P.is_wni "
			+ "FROM PENDUDUK P, KELUARGA K WHERE P.id_keluarga = K.id AND K.nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="nama", column="nama"),
			@Result(property="nik", column="nik"),
			@Result(property="jenis_kelamin", column="jenis_kelamin"),
			@Result(property="tempat_lahir", column="tempat_lahir"),
			@Result(property="tanggal_lahir", column="tanggal_lahir"),
			@Result(property="agama", column="agama"),
			@Result(property="pekerjaan", column="pekerjaan"),
			@Result(property="status_perkawinan", column="status_perkawinan"),
			@Result(property="status_dalam_keluarga", column="status_dalam_keluarga"),
			@Result(property="is_wni", column="is_wni")
	})
	List<PendudukModel> selectDaftarKeluarga (@Param("nkk") String nkk);
	
	@Select("SELECT id FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan}")
	@Results(value = {
			@Result(property="id", column="id") 
	})
	Long cariIdKelurahan(String nama_kelurahan);
	
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
	
	@Select("SELECT nomor_kk FROM keluarga where nomor_kk like #{nkk}")
	List<String> selectNKK(String nkk);
	
	@Insert("INSERT INTO keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, "
			+ "is_tidak_berlaku) VALUES (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, "
			+ "#{id_kelurahan}, #{is_tidak_berlaku})")
	void insertKeluarga(KeluargaModel keluarga);
	
	@Update("UPDATE keluarga SET nomor_kk = #{keluarga.nomor_kk}, alamat = #{keluarga.alamat}, "
			+ "rt = #{keluarga.rt}, rw = #{keluarga.rw}, id_kelurahan = #{keluarga.id_kelurahan}, "
			+ "is_tidak_berlaku = #{keluarga.is_tidak_berlaku} WHERE nomor_kk = #{nomor_kk_lama}")
	void updateKeluarga(@Param("keluarga") KeluargaModel keluarga, @Param("nomor_kk_lama") String nomor_kk_lama);
	
}
