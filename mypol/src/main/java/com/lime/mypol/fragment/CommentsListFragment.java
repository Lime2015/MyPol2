package com.lime.mypol.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.mypol.R;
import com.lime.mypol.adapter.CommentsAdapter;
import com.lime.mypol.adapter.NotesAdapter;


/**
 * Created by Gordon Wong on 7/18/2015.
 * <p/>
 * Generic fragment displaying a list of notes.
 */
public abstract class CommentsListFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract int getNumColumns();

    protected abstract int getNumItems();

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);

        // Setup list
        recyclerView = (RecyclerView) view.findViewById(R.id.comments_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(), StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new CommentsAdapter());

        return view;
    }

//    public RecyclerView getRecyclerView(){
//        return recyclerView;
//    }

}
