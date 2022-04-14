package knezevic.ribarnica.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import knezevic.ribarnica.model.Fish;

@Dao
public interface FishDAO {

    @Query("select * from fish order by id")
    LiveData<List<Fish>> catchfish();

    @Insert
    void addNewFish(Fish fish);

    @Update
    void changeFish(Fish fish);

    @Delete
    void deleteFish(Fish fish);
}
