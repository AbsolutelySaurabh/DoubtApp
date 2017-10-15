package com.appsomniac.doubtapp.ClassFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.appsomniac.doubtapp.R;

/**
 * Created by absolutelysaurabh on 6/8/17.
 */
public class TrendingFragment extends Fragment{

    RecyclerView recyclerView;
    View listItemView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        listItemView = inflater.inflate(R.layout.item_tech, container, false);
        return listItemView;

    }
}