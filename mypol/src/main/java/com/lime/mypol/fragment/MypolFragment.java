package com.lime.mypol.fragment;

import com.lime.mypol.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class MypolFragment extends MypolListFragment {

	public static MypolFragment newInstance() {
		return new MypolFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_mypol;
	}

	@Override
	protected int getNumColumns() {
		return 1;
	}

	@Override
	protected int getNumItems() {
		return 5;
	}
}
