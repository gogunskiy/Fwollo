package com.fwollo.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Tag;
import com.fwollo.logic.models.User;
import com.fwollo.logic.services.TagService.TagList;
import com.fwollo.logic.services.TagService.TagService;
import com.fwollo.widgets.fastScroller.BubbleTextGetter;
import com.fwollo.widgets.fastScroller.FastScroller;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tag_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        update();

        return rootView;
    }

    private void update() {
        TagService task = new TagService(DataManager.defaultManager().getApiClient(), "2344");
        DataManager.defaultManager().runTaskInBackground(task, new TagService.GetTagTaskListener() {
            @Override
            public void onRequestSuccess(TagList list) {
                if (list != null) {
                    recyclerView.setAdapter(new TagAdapter(list.getTags(), onClickListener));
                }
            }
        });
    }

    OnItemClickListener onClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Tag country) {
            
        }
    };

    private class TagAdapter extends RecyclerView.Adapter <TagAdapter.ViewHolder> implements BubbleTextGetter {
        private List<Tag> items;
        private OnItemClickListener onClickListener;

        public TagAdapter(List<Tag> items, OnItemClickListener onClickListener) {
            this.items = items;
            this.onClickListener = onClickListener;
        }

        @Override
        public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_item, parent, false);
            ViewHolder vh = new ViewHolder(v, onClickListener);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.item = items.get(position);
            holder.update();

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public String getTextToShowInBubble(int pos) {
            return Character.toString(items.get(pos).getName().charAt(0));
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public View rootView;
            public TextView tvTitle;
            public TextView tvSubtitle;
            public ImageButton btnMore;


            public Tag item;
            private OnItemClickListener onClickListener;

            public ViewHolder(View v, OnItemClickListener onClickListener) {
                super(v);
                this.rootView = v;
                v.setOnClickListener(this);
                this.onClickListener = onClickListener;

                this.tvTitle = (TextView) v.findViewById(R.id.tv_item_title);
                this.tvSubtitle = (TextView) v.findViewById(R.id.tv_item_subtitle);
                this.btnMore = (ImageButton) v.findViewById(R.id.btn_more);

                btnMore.setColorFilter(getResources().getColor(R.color.app_blue_color));
            }

            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(item);
            }

            public void update() {

                User currentUser = DataManager.defaultManager().getCurrentUser();
                User author = item.getAuthor();

                if (author.getId().equalsIgnoreCase(currentUser.getId())) {
                    int size = item.getFollowers().size();
                    String end = size == 1 ? "" : "s";
                    tvSubtitle.setText(size + " follower" + end);
                    tvTitle.setTextColor(getResources().getColor(R.color.app_blue_color));
                } else {
                    tvSubtitle.setText(author.getFirstName() + " " + author.getLastName());
                    tvTitle.setTextColor(getResources().getColor(R.color.app_red_color));

                }
                tvTitle.setText(item.getName());

            }
        }
    }

    private interface OnItemClickListener {
        void onItemClick(Tag tag);
    }
}
