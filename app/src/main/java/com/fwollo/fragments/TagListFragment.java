package com.fwollo.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.activities.NavigationDrawerActivity;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Tag;
import com.fwollo.logic.models.User;
import com.fwollo.logic.services.TagService.TagList;
import com.fwollo.logic.services.TagService.TagService;
import com.fwollo.utils.KeyboardUtils;
import com.fwollo.utils.NavigationUtils;
import com.fwollo.widgets.fastScroller.BubbleTextGetter;
import java.util.ArrayList;

import com.fwollo.adapters.AnimatedGridLayoutManager;

public class TagListFragment extends MainActivityFragment {

    private RecyclerView recyclerView;
    private ArrayList <TextView> tabs = new ArrayList<>();
    private TagAdapter adapter;

    private TagList tagList;
    private int selectedTabIndex = 0;
    private OnSearchViewChangeListener onSearchQueryListener;

    public TagListFragment() {

        super();

        initOnSearchListener();
    }

    @Override
    public void createMenu(Menu menu) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getOnSearchQueryListener().onSearchViewClosed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getOnSearchQueryListener().onSearchTextChanged(newText);
                return true;
            }
        });

        final SearchView finalSearchView = searchView;
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    finalSearchView.setIconified(true);
                    finalSearchView.setQuery("", false);
                    KeyboardUtils.hideKeyboard(getActivity());
                    getOnSearchQueryListener().onSearchViewClosed();
                }
            }
        });
    }

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
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
        public void onItemClick(Tag tag) {

        }

        @Override
        public void onItemActionButtonClick(Tag tag) {

            User currentUser = DataManager.defaultManager().getCurrentUser();
            User author = tag.getAuthor();

            if (author.getId().equalsIgnoreCase(currentUser.getId())) {
                OwnTagDetailsFragment fragment = new OwnTagDetailsFragment();
                fragment.setTag(tag);
                NavigationUtils.showChildFragment(getActivity(), fragment, R.id.frame_container);
            } else {
                FollowingTagDetailsFragment fragment = new FollowingTagDetailsFragment();
                fragment.setTag(tag);
                NavigationUtils.showChildFragment(getActivity(), fragment, R.id.frame_container);
            }
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

    private class TagAdapter extends RecyclerView.Adapter <TagAdapter.ViewHolder> implements BubbleTextGetter {
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

            public ViewHolder(View v, final OnItemClickListener onClickListener) {
                super(v);
                this.rootView = v;
                v.setOnClickListener(this);
                this.onClickListener = onClickListener;

                this.tvTitle = (TextView) v.findViewById(R.id.tv_item_title);
                this.tvSubtitle = (TextView) v.findViewById(R.id.tv_item_subtitle);
                this.btnMore = (ImageButton) v.findViewById(R.id.btn_more);

                btnMore.setColorFilter(getResources().getColor(R.color.app_blue_color));
                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onItemActionButtonClick(item);
                    }
                });
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
        void onItemActionButtonClick(Tag tag);
    }

    public interface OnSearchViewChangeListener {
        void onSearchTextChanged(String text);
        void onSearchViewClosed();
    }
}
