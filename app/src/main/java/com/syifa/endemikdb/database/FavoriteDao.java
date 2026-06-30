package com.syifa.endemikdb.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.syifa.endemikdb.entities.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM favorit WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM favorit")
    List<Favorite> getAll();

    @Query("SELECT EXISTS(SELECT 1 FROM favorit WHERE id = :id)")
    boolean isFavorite(String id);
}
