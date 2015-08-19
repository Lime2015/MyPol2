package com.lime.mypol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.models.Comment;

import java.util.List;

public class CommentsAdapter extends ArrayAdapter<Comment> implements OnClickListener {

    private LayoutInflater mInflater;

    public CommentsAdapter(Context context, List<Comment> items) {
        super(context, 0, items);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_comment, parent, false);
            holder = new ViewHolder();
            holder.memberIcon = (ImageView) convertView.findViewById(R.id.comment_member_photo);
            holder.memberId = (TextView) convertView.findViewById(R.id.comment_member_id);
            holder.comment = (TextView) convertView.findViewById(R.id.comment_comment);
//            holder.like = (TextView) convertView.findViewById(R.id.comment_like);
//            holder.follow = (TextView) convertView.findViewById(R.id.comment_dislike);
//            holder.like.setOnClickListener(this);
//            holder.follow.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment item = getItem(position);
        ImageUtil.displayRoundImage(holder.memberIcon, item.getMemberIconUrl(), null);
        holder.memberId.setText(item.getMemberId());
        holder.comment.setText(item.getComment());
//        holder.like.setTag(position);
//        holder.follow.setTag(position);

        return convertView;
    }

    private static class ViewHolder {
        public ImageView memberIcon;
        public TextView memberId;
        public TextView comment;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int possition = (Integer) v.getTag();
//        switch (v.getId()) {
//            case R.id.list_item_google_cards_social_like:
//                // click on like button
//                break;
//            case R.id.list_item_google_cards_social_follow:
//                // click on follow button
//                break;
//        }
    }
}
