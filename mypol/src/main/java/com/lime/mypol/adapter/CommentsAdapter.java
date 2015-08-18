package com.lime.mypol.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.models.Comment;
import com.lime.mypol.models.Menu;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Adapter for the all items screen.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

	private Comment[] comments;

	public CommentsAdapter() {

		comments = new Comment[30];
		for(int i=0;i<30;i++){
			comments[0] = new Comment();
		}
	}



	@Override
	public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent,
				false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Comment commentModel = comments[position];
		String memberId = commentModel.getMemberId();
		int commentType = commentModel.getCommentType();
		String targetId = commentModel.getTargetId();
		String comment = commentModel.getComment();
		int like = commentModel.getLike();
		int dislike = commentModel.getDislike();

		// Set text
		holder.memberIdText.setText(memberId);
		holder.commentText.setText(comment);

//		// Set Color
//		holder.infoTextView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.text_white_87));
//		holder.infoTextView.setTextColor(holder.itemView.getContext().getResources().getColor(colorId));


//		// Set padding
//		int paddingTop = holder.itemView.getContext().getResources()
//						.getDimensionPixelSize(R.dimen.note_content_spacing);
//		holder.titleTextView.setPadding(holder.titleTextView.getPaddingLeft(), paddingTop,
//				holder.titleTextView.getPaddingRight(), holder.titleTextView.getPaddingBottom());

		// Set background color

		((CardView) holder.itemView).setCardBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.abc_primary_text_material_light));
	}

	@Override
	public int getItemCount() {
		return comments.length;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView memberIdText;
		public TextView commentText;

		public ViewHolder(View itemView) {
			super(itemView);
			memberIdText = (TextView) itemView.findViewById(R.id.comment_memberId);
			commentText = (TextView) itemView.findViewById(R.id.comment_comment);
		}
	}

}
