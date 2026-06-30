package com.syifa.endemikdb.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "endemik")
public class Endemik {

    public String getId() {
        return id;
    }

    public String getTipe() {
        return tipe;
    }

    public String getNama_latin() {
        return nama_latin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSumber_vidio() {
        return sumber_vidio;
    }

    public void setSumber_vidio(String sumber_vidio) {
        this.sumber_vidio = sumber_vidio;
    }

    public String getVidio() {
        return vidio;
    }

    public void setVidio(String vidio) {
        this.vidio = vidio;
    }

    public String getSumber_foto() {
        return sumber_foto;
    }

    public void setSumber_foto(String sumber_foto) {
        this.sumber_foto = sumber_foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSebaran() {
        return sebaran;
    }

    public void setSebaran(String sebaran) {
        this.sebaran = sebaran;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFamili() {
        return famili;
    }

    public void setFamili(String famili) {
        this.famili = famili;
    }

    public void setNama_latin(String nama_latin) {
        this.nama_latin = nama_latin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int localId;

    private String id;
    private String tipe;
    private String nama;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    private String nama_latin;
    private String famili;
    private String genus;
    private String deskripsi;
    private String asal;
    private String sebaran;
    private String foto;
    private String sumber_foto;
    private String vidio;
    private String sumber_vidio;
    private String status;

    public Endemik() {
    }
}
