package com.lime.mypol.fragment;

import com.lime.mypol.R;

/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Favorite items fragment.
 */
public class MenusFragment extends MenusListFragment {

	public static MenusFragment newInstance() {
		return new MenusFragment();
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_menus;
	}

	@Override
	protected int getNumColumns() {
		return 1;
	}

	@Override
	protected int getNumItems() {
		return 7;
	}
}
