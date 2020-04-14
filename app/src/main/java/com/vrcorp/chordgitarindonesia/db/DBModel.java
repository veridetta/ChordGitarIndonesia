package com.vrcorp.chordgitarindonesia.db;

public class DBModel {
    private String id, id_abjad, id_band;
    private String judul, nama_band, isi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getId_abjad() {
        return id_abjad;
    }
    public void setId_abjad(String id_abjad) {
        this.id_abjad = id_abjad;
    }
    public String getId_band() {
        return id_band;
    }
    public void setId_band(String id_band) {
        this.id_band = id_band;
    }
    public String getNama_band() {
        return nama_band;
    }
    public void setNama_band(String nama_band) {
        this.nama_band = nama_band;
    }
    public String getIsi() {
        return isi;
    }
    public void setIsi(String isi) {
        this.isi = isi;
    }

}