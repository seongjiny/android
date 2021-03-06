package com.bitlove.fetlife.view.adapter;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitlove.fetlife.R;
import com.bitlove.fetlife.model.pojos.Friend;
import com.bitlove.fetlife.model.pojos.Friend_Table;
import com.facebook.drawee.view.SimpleDraweeView;
import com.raizlabs.android.dbflow.annotation.Collate;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

public class FriendsRecyclerAdapter extends ResourceListRecyclerAdapter<Friend, FriendViewHolder> {

    private List<Friend> itemList;

    public FriendsRecyclerAdapter() {
        loadItems();
    }

    public void refresh() {
        loadItems();
        //TODO: think of possibility of update only specific items instead of the whole list
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    private void loadItems() {
        //TODO: think of moving to separate thread with specific DB executor
        try {
            itemList = new Select().from(Friend.class).orderBy(OrderBy.fromProperty(Friend_Table.nickname).ascending().collate(Collate.NOCASE)).queryList();
        } catch (Throwable t) {
            itemList = new ArrayList<>();
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public Friend getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_friend, parent, false);
        return new FriendViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder friendViewHolder, int position) {

        final Friend friend = itemList.get(position);

        friendViewHolder.headerText.setText(friend.getNickname());
        friendViewHolder.upperText.setText(friend.getMetaInfo());

//        friendViewHolder.dateText.setText(SimpleDateFormat.getDateTimeInstance().format(new Date(friend.getDate())));

        friendViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onResourceClickListener != null) {
                    onResourceClickListener.onItemClick(friend);
                }
            }
        });

        friendViewHolder.avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onResourceClickListener != null) {
                    onResourceClickListener.onAvatarClick(friend);
                }
            }
        });

        friendViewHolder.avatarImage.setImageResource(R.drawable.dummy_avatar);
        String avatarUrl = friend.getAvatarLink();
        friendViewHolder.avatarImage.setImageURI(avatarUrl);
//        imageLoader.loadImage(friendViewHolder.itemView.getContext(), avatarUrl, friendViewHolder.avatarImage, R.drawable.dummy_avatar);
    }

    @Override
    protected void onItemRemove(FriendViewHolder viewHolder, RecyclerView recyclerView, boolean swipedRight) {

    }
}

class FriendViewHolder extends SwipeableViewHolder {

    SimpleDraweeView avatarImage;
    TextView headerText, upperText, dateText;

    public FriendViewHolder(View itemView) {
        super(itemView);

        headerText = (TextView) itemView.findViewById(R.id.friend_header);
        upperText = (TextView) itemView.findViewById(R.id.friend_upper);
        dateText = (TextView) itemView.findViewById(R.id.friend_right);
        avatarImage = (SimpleDraweeView) itemView.findViewById(R.id.friend_icon);
    }

    @Override
    public View getSwipeableLayout() {
        return null;
    }

    @Override
    public View getSwipeRightBackground() {
        return null;
    }

    @Override
    public View getSwipeLeftBackground() {
        return null;
    }
}