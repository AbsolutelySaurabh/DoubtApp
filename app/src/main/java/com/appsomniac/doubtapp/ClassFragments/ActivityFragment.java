package com.appsomniac.doubtapp.ClassFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.appsomniac.doubtapp.R;
import com.github.clans.fab.FloatingActionButton;

/**
 * Created by absolutelysaurabh on 6/8/17.
 */

public class ActivityFragment extends Fragment {

    RecyclerView recyclerView;
    public static int index = 0;
    View listItemView;
    private FloatingActionButton mFAB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listItemView = inflater.inflate(R.layout.item_headline, container, false);

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        return listItemView;
    }
}