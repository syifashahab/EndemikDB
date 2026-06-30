package com.syifa.endemikdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.syifa.endemikdb.entities.Endemik;

import java.util.List;

@Dao
public interface EndemikDao {

    @Insert
    void insert(Endemik endemik);

    @Insert
    void insertAll(List<Endemik> endemikList);

    @Query("SELECT * FROM endemik")
    List<Endemik> getAll();

    @Query("SELECT * FROM endemik WHERE tipe = 'Hewan'")
    List<Endemik> getHewan();

    @Query("SELECT * FROM endemik WHERE tipe = 'Tumbuhan'")
    List<Endemik> getTumbuhan();

    @Query("SELECT * FROM endemik WHERE tipe='Hewan' LIMIT 20")
    List<Endemik> getHewanHome();

    @Query("SELECT * FROM endemik WHERE tipe='Tumbuhan' LIMIT 20")
    List<Endemik> getTumbuhanHome();

    @Query("SELECT * FROM endemik WHERE tipe='Hewan' AND asal LIKE '%' || :region || '%' LIMIT 20")
    List<Endemik> getHewanHomeByRegion(String region);

    @Query("SELECT * FROM endemik WHERE tipe='Tumbuhan' AND asal LIKE '%' || :region || '%' LIMIT 20")
    List<Endemik> getTumbuhanHomeByRegion(String region);

    @Query("SELECT COUNT(*) FROM endemik")
    int countData();

    @Query("SELECT * FROM endemik WHERE nama LIKE '%' || :keyword || '%'")
    List<Endemik> search(String keyword);

    @Query("SELECT * FROM endemik WHERE id = :id LIMIT 1")
    Endemik getById(String id);

    @Query("SELECT * FROM endemik WHERE tipe='Hewan' AND asal LIKE '%' || :region || '%'")
    List<Endemik> getHewanByRegion(String region);

    @Query("SELECT * FROM endemik WHERE tipe='Tumbuhan' AND asal LIKE '%' || :region || '%'")
    List<Endemik> getTumbuhanByRegion(String region);

    @Query("SELECT DISTINCT tipe FROM endemik")
    List<String> getAllTypes();
}