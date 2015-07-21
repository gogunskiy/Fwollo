package com.fwollo.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Tag;
import com.fwollo.logic.models.User;
import com.fwollo.logic.services.TagService.TagList;
import com.fwollo.logic.services.TagService.TagService;
import com.fwollo.widgets.NonSwipeableViewPager;
import com.fwollo.widgets.fastScroller.BubbleTextGetter;
import com.fwollo.widgets.fastScroller.FastScroller;
import com.melnykov.fab.FloatingActionButton;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList <TextView> tabs = new ArrayList<>();
    private TagAdapter adapter;

    private TagList tagList;
    private int selectedTabIndex = 0;
    private OnSearchViewChangeListener onSearchQueryListener;

    public HomeFragment() {

        super();

        initOnSearchListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tag_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new AnimatedGridLayoutManager(getActivity()));

        adapter = new TagAdapter(onClickListener);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);

        tabs.add((TextView) rootView.findViewById(R.id.tv_all_tags));
        tabs.add((TextView) rootView.findViewById(R.id.tv_my_tags));
        tabs.add((TextView) rootView.findViewById(R.id.tv_following));

        for (TextView tv : tabs) {
            tv.setOnClickListener(onTagClickListener);
        }

        update();

        return rootView;
    }

    private void setSelectedTab(int tabIndex) {
        for (TextView tv : tabs) {
            tv.setTextColor(getResources().getColor(R.color.app_text_gray_color));
        }

        tabs.get(tabIndex).setTextColor(getResources().getColor(R.color.app_text_dark_gray_color));
        selectedTabIndex = tabIndex;
        onUpdateListener.update();
    }


    private void update() {
        TagService task = new TagService(DataManager.defaultManager().getApiClient(), "2344");
        DataManager.defaultManager().runTaskInBackground(task, new TagService.GetTagTaskListener() {
            @Override
            public void onRequestSuccess(TagList list) {
                if (list != null) {
                    setTagList(list);
                    setSelectedTab(0);
                }
            }
        });
    }

    public TagList getTagList() {
        return tagList;
    }

    public void setTagList(TagList tagList) {
        this.tagList = tagList;
    }

    private void initOnSearchListener() {
        onSearchQueryListener = new OnSearchViewChangeListener() {
            @Override
            public void onSearchTextChanged(String text) {
                ArrayList <Tag> tags = new ArrayList<>();
                for (Tag tag : getTagList().getTags()) {
                    if (tag.getName().toLowerCase().contains(text.toLowerCase())) {
                        tags.add(tag);
                    }
                }

                adapter.setItems(tags);
            }

            @Override
            public void onSearchViewClosed() {
                setSelectedTab(0);
            }
        };
    }

    View.OnClickListener onTagClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setSelectedTab(tabs.indexOf(view));
        }
    };

    OnItemClickListener onClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Tag country) {
            
        }
    };

    OnUpdateListener onUpdateListener = new OnUpdateListener() {
        @Override
        public void update() {
            adapter.setItems(getFilteredItems());
        }
    };

    private ArrayList<Tag> getFilteredItems() {

        switch (selectedTabIndex) {
            case 1: {
                ArrayList <Tag> ownTags = new ArrayList<>();
                for (Tag tag : getTagList().getTags()) {
                    User currentUser = DataManager.defaultManager().getCurrentUser();
                    User author = tag.getAuthor();

                    if (author.getId().equalsIgnoreCase(currentUser.getId())) {
                        ownTags.add(tag);
                    }
                }
                return ownTags;
            }

            case 2: {
                ArrayList <Tag> followingTags = new ArrayList<>();
                for (Tag tag : getTagList().getTags()) {
                    User currentUser = DataManager.defaultManager().getCurrentUser();
                    User author = tag.getAuthor();
                    if (!author.getId().equalsIgnoreCase(currentUser.getId())) {
                        followingTags.add(tag);
                    }
                }
                return followingTags;
            }
        }

        return getTagList().getTags();
    }

    public OnSearchViewChangeListener getOnSearchQueryListener() {
        return onSearchQueryListener;
    }

    private class AnimatedGridLayoutManager extends GridLayoutManager {

        public AnimatedGridLayoutManager(Context context) {
            super(context, 1);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return true;
        }
    }

    private class TagAdapter extends RecyclerView.Adapter <TagAdapter.ViewHolder> implements BubbleTextGetter {
        private OnUpdateListener onUpdateListener;
        private OnItemClickListener onClickListener;

        private ArrayList <Tag> items = new ArrayList<>();

        public TagAdapter(OnItemClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public void setItems(ArrayList<Tag> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return items.get(position).hashCode();
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

            public View getRootView() {
                return rootView;
            }

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
    private interface OnUpdateListener {
        void update();
    }

    private interface OnItemClickListener {
        void onItemClick(Tag tag);
    }

    public interface OnSearchViewChangeListener {
        void onSearchTextChanged(String text);
        void onSearchViewClosed();
    }
}
