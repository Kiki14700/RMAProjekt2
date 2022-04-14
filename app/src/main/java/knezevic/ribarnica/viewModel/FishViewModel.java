package knezevic.ribarnica.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import knezevic.ribarnica.model.Fish;
import knezevic.ribarnica.model.dao.FishBaza;
import knezevic.ribarnica.model.dao.FishDAO;

public class FishViewModel extends AndroidViewModel{

    FishDAO fishDAO;
    private Fish fish;

    public FishDAO getFishDAO() {
        return fishDAO;
    }

    public void setFishDAO(FishDAO fishDAO) {
        this.fishDAO = fishDAO;
    }

    private LiveData<List<Fish>> fishes;

    public void setFish(Fish fish) {
        this.fish = fish;
    }

    public Fish getFish() {
        return fish;
    }

    public FishViewModel(@NonNull Application application) {
        super(application);
        fishDAO = FishBaza.getInstance(application.getApplicationContext()).fishDAO();
    }

    public LiveData<List<Fish>> dohvatiFish(){
        fishes = fishDAO.catchfish();
        return fishes;
    }

    public void addNewFish(){ fishDAO.addNewFish(fish); }

    public void changeFish(){ fishDAO.changeFish(fish); }

    public void deleteFish(){
        fishDAO.deleteFish(fish);
    }

}
