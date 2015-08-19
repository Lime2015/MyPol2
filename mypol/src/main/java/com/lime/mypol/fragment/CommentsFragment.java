package com.lime.mypol.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lime.mypol.R;
import com.lime.mypol.adapter.CommentsAdapter;
import com.lime.mypol.models.Comment;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gordon Wong on 7/17/2015.
 * <p/>
 * Favorite items fragment.
 */
public class CommentsFragment extends Fragment {

    private static final int INITIAL_DELAY_MILLIS = 300;

    //	private FloatingActionButton fab;
    private ListView listView;
    private CommentsAdapter commentsAdapter;

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

        commentsAdapter = new CommentsAdapter(view.getContext(), makeCommentList());
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(commentsAdapter, new OnDismissCallback() {
            @Override
            public void onDismiss(ViewGroup viewGroup, int[] ints) {

            }
        }));

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

    private List<Comment> makeCommentList() {
        List<Comment> list = new ArrayList<>();
        Comment comment = new Comment(0,"kkk","http://mimgnews2.naver.net/image/108/2015/08/19/2015081908151975669_5_99_20150819092717.jpg?type=w540",1,"001","진보 보수 나눌때가 아닙니다. 대한민국이 침몰하고 있어요 다들 정신좀 차리죠...",50,10);
        for(int i=0;i<20;i++) {
            list.add(comment);
        }
        return list;
    }

}
