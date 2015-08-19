package com.lime.mypol.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lime.mypol.R;
import com.lime.mypol.fragment.CommentsFragment;
import com.lime.mypol.fragment.MenusFragment;
import com.lime.mypol.fragment.MypolFragment;


/**
 * Created by Gordon Wong on 7/17/2015.
 *
 * Pager adapter for main activity.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

	public static final int NUM_ITEMS = 3;
	public static final int MENUS_POS = 0;
	public static final int MYPOL_POS = 1;
	public static final int COMMENT_POS = 2;

	private Context context;

	public MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case MENUS_POS:
			return MenusFragment.newInstance();
		case MYPOL_POS:
			return MypolFragment.newInstance();
		case COMMENT_POS:
			return CommentsFragment.newInstance();
		default:
			return null;
		}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case MENUS_POS:
			return context.getString(R.string.menus);
		case MYPOL_POS:
			return context.getString(R.string.mypol);
		case COMMENT_POS:
			return context.getString(R.string.comment);
		default:
			return "";
		}
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}
}
