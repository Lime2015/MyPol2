package com.lime.mypol.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.models.Menu;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Adapter for the all items screen.
 */
public class MypolAdapter extends RecyclerView.Adapter<MypolAdapter.ViewHolder> {

	private Menu[] menus;
	private final int MYPOL_STEP_ZERO = 0;

	public MypolAdapter(int step) {

		switch (step){
			case MYPOL_STEP_ZERO:
				menus = new Menu[1];
				menus[0] = new Menu("시작하기", "내 정치성향과 비슷한 국회의원을 영입하여 나만의 정당을 만들어 보세요~\n마이폴 시즌19를 시작합니다.", R.color.orange_400);
				break;
		}
	}



	@Override
	public MypolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

//		// Set Color
//		holder.infoTextView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.text_white_87));
//		holder.infoTextView.setTextColor(holder.itemView.getContext().getResources().getColor(colorId));


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
