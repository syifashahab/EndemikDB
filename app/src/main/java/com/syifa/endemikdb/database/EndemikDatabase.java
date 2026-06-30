package com.syifa.endemikdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.syifa.endemikdb.entities.Endemik;
import com.syifa.endemikdb.entities.Favorite;

@Database(
        entities = {
                Endemik.class,
                Favorite.class
        },
        version = 1,
        exportSchema = false
)
public abstract class EndemikDatabase extends RoomDatabase {

    private static EndemikDatabase instance;

    public abstract EndemikDao endemikDao();

    public abstract FavoriteDao favoriteDao();

    public static synchronized EndemikDatabase getInstance(Context context){

        if(instance == null){

            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            EndemikDatabase.class,
                            "endemikDB"
                    ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
