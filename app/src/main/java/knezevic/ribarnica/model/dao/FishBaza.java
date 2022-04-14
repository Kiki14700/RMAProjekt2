package knezevic.ribarnica.model.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import knezevic.ribarnica.model.Fish;

@Database(entities = {Fish.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class FishBaza extends RoomDatabase {

    public abstract FishDAO fishDAO();

    private static FishBaza instance;

    public static FishBaza getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    FishBaza.class,
                    "fish-baza"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }



}
