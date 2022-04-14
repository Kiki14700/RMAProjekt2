package knezevic.ribarnica.view;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import knezevic.ribarnica.R;
import knezevic.ribarnica.model.Fish;
import knezevic.ribarnica.viewModel.FishViewModel;

public class CUDFragment extends Fragment{

    static final int SLIKANJE = 1;

    @BindView(R.id.name)
    EditText name;
    @BindView ( R.id.weight)
    EditText weight;
    @BindView(R.id.species)
    Spinner species;
    @BindView(R.id.slikaCUD)
    ImageView slikaCUD;
    @BindView ( R.id.date )
    DatePicker date;
    @BindView(R.id.newFish)
    Button newFish;
    @BindView(R.id.makeImage)
    Button makeImage;
    @BindView(R.id.makeChange)
    Button makeChange;
    @BindView(R.id.deleteFish)
    Button deleteFish;

    FishViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);
        model = ((MainActivity) getActivity()).getModel();
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        picasso.load(R.drawable.riba).fit().centerCrop().into(slikaCUD);


        if (model.getFish().getId() == 0) {
            defineNewFish();
            return view;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getFish ().getDate ());
        name.setText(model.getFish().getName());
        species.setSelection(model.getFish().getSpeciesType ());
        weight.setText ( model.getFish ().getWeigth ());
        date.updateDate ( calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH) );
        defineChangeDeleteFish ();

        return view;
    }

    private void defineNewFish() {
        makeChange.setVisibility(View.GONE);
        deleteFish.setVisibility(View.GONE);
        makeImage.setVisibility(View.GONE);
        newFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaFish ();
            }
        });
    }

    private void novaFish() {
        model.getFish().setName(name.getText().toString());
        model.getFish().setSpeciesType (species.getSelectedItemPosition());
        model.getFish ().setWeigth (weight.getText ().toString ());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,date.getDayOfMonth());
        c.set(Calendar.MONTH, date.getMonth());
        c.set(Calendar.YEAR, date.getYear());
        model.getFish ().setDate ( c.getTime () );
        model.addNewFish();
        nazad();

    }

    private void defineChangeDeleteFish() {
        Fish o = model.getFish();
        newFish.setVisibility(View.GONE);
        name.setText(o.getName());
        species.setSelection(o.getSpeciesType ());
        weight.setText ( o.getWeigth () );
        Log.d("Putanja slika", "->" + o.getPutanjaSlika());
        if (o.getPutanjaSlika() != null) {
            Picasso.get().load(o.getPutanjaSlika()).fit().centerCrop().into(slikaCUD);
        }

        makeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFish ();
            }
        });

        deleteFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFish ();
            }
        });

        makeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeImage ();
            }
        });
    }

    private void makeImage() {
        Intent uslikajIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(uslikajIntent.resolveActivity(getActivity().getPackageManager())==null){
            //poruke korisniku
            return;
        }

        File slika= null;
        try{
            slika = createImageFile ();
        }catch (IOException e){

            return;
        }

        if(slika==null){

            return;
        }

        Uri slikaUri = FileProvider.getUriForFile(getActivity(),
                "com.kristianKiki.provider",
                slika);
        uslikajIntent.putExtra(MediaStore.EXTRA_OUTPUT,slikaUri);
        startActivityForResult(uslikajIntent,SLIKANJE);

    }

    private File createImageFile() throws IOException {
        String naziv = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_osoba";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File datoteka = File.createTempFile(naziv,".jpg",dir);
        model.getFish().setPutanjaSlika("file:" + datoteka.getAbsolutePath());
        return datoteka;
    }

    private void changeFish(){
        model.getFish().setName(name.getText().toString());
        model.getFish().setSpeciesType (species.getSelectedItemPosition());
        model.getFish ().setWeigth (weight.getText ().toString ());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,date.getDayOfMonth());
        c.set(Calendar.MONTH, date.getMonth());
        c.set(Calendar.YEAR, date.getYear());
        model.getFish ().setDate ( c.getTime () );
        model.changeFish();
        nazad();
    }

    private void deleteFish(){
        model.deleteFish();
        nazad();
    }

    @OnClick(R.id.back)
    public void nazad(){
        ((MainActivity)getActivity()).read();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SLIKANJE && resultCode == Activity.RESULT_OK){
            model.changeFish();
            Picasso.get().load(model.getFish().getPutanjaSlika()).fit().centerCrop().into(slikaCUD);
        }
    }

}
