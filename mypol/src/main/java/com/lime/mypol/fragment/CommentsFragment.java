package com.lime.mypol.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.mypol.R;
import com.lime.mypol.adapter.CommentsAdapter;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class CommentsFragment extends Fragment {

	private FloatingActionButton fab;
	private RecyclerView recyclerView;

	public static CommentsFragment newInstance() {
		return new CommentsFragment();
	}

	public int getLayoutResId() {
		return R.layout.fragment_comments;
	}

	public int getNumColumns() {
		return 1;
	}

	public int getNumItems() {
		return 30;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutResId(), container, false);

		// Setup list
		recyclerView = (RecyclerView) view.findViewById(R.id.comments_list);
		recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getNumColumns(), StaggeredGridLayoutManager.VERTICAL));
		recyclerView.setAdapter(new CommentsAdapter());


		fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 젤 위로가기
//				Log.d("rList:", rList.toString());
				recyclerView.smoothScrollToPosition(0);
			}
		});
		return view;
	}

}
