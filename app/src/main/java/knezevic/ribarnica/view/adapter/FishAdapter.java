package knezevic.ribarnica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import knezevic.ribarnica.R;
import knezevic.ribarnica.model.Fish;

public class FishAdapter extends ArrayAdapter<Fish>{

    private List<Fish> itemList;
    private FishClickListener fishClickListener;
    private int resource;
    private Context context;

    public FishAdapter(@NonNull Context context, int resource, knezevic.ribarnica.view.adapter.FishClickListener fishClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.fishClickListener = fishClickListener;
    }


    private static class ViewHolder {

        private TextView id;
        private TextView name;
        private TextView species;
        private TextView weigth;
        private ImageView imagePath;
        private TextView date;
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

        View view = convertView;
        Fish fish;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.id = view.findViewById(R.id.id);
                viewHolder.name = view.findViewById(R.id.name);
                viewHolder.imagePath = view.findViewById(R.id.imagePath);
                viewHolder.species = view.findViewById(R.id.species);
                viewHolder.weigth = view.findViewById(R.id.weight);
                viewHolder.date = view.findViewById ( R.id.date );

            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            fish = getItem(position);

            if (fish != null) {

                viewHolder.id.setText(String.valueOf(fish.getId()));
                viewHolder.name.setText(String.valueOf (fish.getName()));
                viewHolder.species.setText(context.getResources().getStringArray(R.array.species)[fish.getSpeciesType ()]);
                viewHolder.weigth.setText ( String.valueOf (fish.getWeigth ()) );
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                try {
                    viewHolder.date.setText(simpleDateFormat.format(fish.getDate()));
                }catch (Exception e){
                    viewHolder.date.setText("");
                }

                if (fish.getPutanjaSlika() == null) {
                    Picasso.get().load(R.drawable.riba).fit().centerCrop().into(viewHolder.imagePath);
                } else {
                    Picasso.get().load(fish.getPutanjaSlika()).fit().centerCrop().into(viewHolder.imagePath);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { fishClickListener.onItemClick(fish); }
            });
        }
        return view;



    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public Fish getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<Fish> itemList) {
        this.itemList = itemList;
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }



}
