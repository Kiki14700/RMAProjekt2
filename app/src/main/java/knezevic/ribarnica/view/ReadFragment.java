package knezevic.ribarnica.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import knezevic.ribarnica.R;
import knezevic.ribarnica.model.Fish;
import knezevic.ribarnica.view.adapter.FishAdapter;
import knezevic.ribarnica.view.adapter.FishClickListener;
import knezevic.ribarnica.viewModel.FishViewModel;

public class ReadFragment extends Fragment{

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private FishViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        defineList ();

        refreshData ();

        return view;
    }

    public void refreshData(){
        model.dohvatiFish ().observe ( getViewLifecycleOwner ( ), new Observer<List<Fish>> ( ) {
            @Override
            public void onChanged(List<Fish> fish) {
                swipeRefreshLayout.setRefreshing(false);
                ((FishAdapter)listView.getAdapter()).setItemList(fish);
                ((FishAdapter) listView.getAdapter()).refreshItem();
            }
        } );
    }


    private void defineSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData ();
            }
        });

    }

    private void defineList() {

        listView.setAdapter ( new FishAdapter ( getActivity ( ), R.layout.red_liste, new FishClickListener ( ) {
            @Override
            public void onItemClick(Fish fish) {
                model.setFish (fish);
                ((MainActivity)getActivity()).cud();
            }
        } ));
    }

    @OnClick(R.id.fab)
    public void novaOsoba(){
        model.setFish (new Fish ());
        ((MainActivity)getActivity()).cud();
    }
}
