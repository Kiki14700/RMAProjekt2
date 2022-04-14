package knezevic.ribarnica.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import knezevic.ribarnica.R;
import knezevic.ribarnica.viewModel.FishViewModel;

public class MainActivity extends AppCompatActivity {

    private FishViewModel model;

    public FishViewModel getModel() {
        return model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
        model = new ViewModelProvider (this).get ( FishViewModel.class );
        read();

    }

    public void read(){
        setFragment( new ReadFragment());
    };

    public void cud(){
        setFragment( new CUDFragment());
    };

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }


}
