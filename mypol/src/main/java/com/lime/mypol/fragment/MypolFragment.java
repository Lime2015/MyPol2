package com.lime.mypol.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.mypol.R;
import com.lime.mypol.adapter.MypolAdapter;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class MypolFragment extends Fragment {

	public static MypolFragment newInstance() {
		return new MypolFragment();
	}

    public int getLayoutResId() {
		return R.layout.fragment_mypol;
	}

    public int getNumColumns() {
		return 1;
	}

    public int getNumItems() {
		return 5;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        // Setup list
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mypol_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(),
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MypolAdapter(0));

        return view;
    }
}
