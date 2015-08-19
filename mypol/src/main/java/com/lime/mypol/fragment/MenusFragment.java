package com.lime.mypol.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.mypol.R;
import com.lime.mypol.adapter.MenusAdapter;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class MenusFragment extends Fragment {

	public static MenusFragment newInstance() {
		return new MenusFragment();
	}

    public int getLayoutResId() {
		return R.layout.fragment_menus;
	}

    public int getNumColumns() {
		return 1;
	}

    public int getNumItems() {
		return 3;
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        // Setup list
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.menus_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(),
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MenusAdapter());

        return view;
    }
}
