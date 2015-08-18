package com.lime.mypol.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.models.Menu;
import com.lime.mypol.models.Note;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Adapter for the all items screen.
 */
public class MenusAdapter extends RecyclerView.Adapter<MenusAdapter.ViewHolder> {

	private Menu[] menus;

	public MenusAdapter() {

		menus = new Menu[3];
		menus[0] = new Menu("국회의원","19대 국회의원의 기본정보 및 전반적인 의정활동을 살펴볼 수 있습니다.", R.color.menu_assemblyman);
		menus[1] = new Menu("의안","19대 국회 개원 이후 국회 본회의에서 처리된 모든 의안의 의원별 투표 결과를 확인하실 수 있습니다.\n <자료출처 : 국회공보, 국회회의록시스템>", R.color.menu_bill);
		menus[2] = new Menu("명예의전당","기간별 가장 인기있는 국회의원을 확인할 수 있습니다.", R.color.menu_hallofhonor);
	}



	@Override
	public MenusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent,
				false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Menu menuModel = menus[position];
		String title = menuModel.getTitle();
		String info = menuModel.getInfo();
		int colorId = menuModel.getColor();

		// Set text
		holder.titleTextView.setText(title);
		holder.infoTextView.setText(info);


//		// Set padding
//		int paddingTop = holder.itemView.getContext().getResources()
//						.getDimensionPixelSize(R.dimen.note_content_spacing);
//		holder.titleTextView.setPadding(holder.titleTextView.getPaddingLeft(), paddingTop,
//				holder.titleTextView.getPaddingRight(), holder.titleTextView.getPaddingBottom());

		// Set background color
		((CardView) holder.itemView).setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(colorId));
	}

	@Override
	public int getItemCount() {
		return menus.length;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView titleTextView;
		public TextView infoTextView;

		public ViewHolder(View itemView) {
			super(itemView);
			titleTextView = (TextView) itemView.findViewById(R.id.menu_title);
			infoTextView = (TextView) itemView.findViewById(R.id.menu_info);
		}
	}

}
