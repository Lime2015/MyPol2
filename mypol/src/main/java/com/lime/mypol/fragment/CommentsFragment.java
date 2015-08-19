package com.lime.mypol.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lime.mypol.R;
import com.lime.mypol.adapter.CommentsAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

/**
 * Created by Gordon Wong on 7/17/2015.
 * <p/>
 * Favorite items fragment.
 */
public class CommentsFragment extends Fragment {

    //	private FloatingActionButton fab;
    private ListView listView;
    private CommentsAdapter mGoogleCardsAdapter;

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

        listView = (ListView) view.findViewById(R.id.comments_list);

        mGoogleCardsAdapter = new CommentsAdapter(this, DummyContent.getDummyModelList());
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(mGoogleCardsAdapter, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(swingBottomInAnimationAdapter);

//		fab = (FloatingActionButton) view.findViewById(R.id.fab);
//		fab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 젤 위로가기
////				Log.d("rList:", rList.toString());
//				recyclerView.smoothScrollToPosition(0);
//			}
//		});
        return view;
    }

}
