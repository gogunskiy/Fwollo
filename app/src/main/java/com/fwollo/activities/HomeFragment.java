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
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Tag;
import com.fwollo.logic.services.TagService.TagList;
import com.fwollo.logic.services.TagService.TagService;
import com.fwollo.widgets.fastScroller.BubbleTextGetter;
import com.fwollo.widgets.fastScroller.FastScroller;

import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tag_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FastScroller fastScroller=(FastScroller) rootView.findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(recyclerView);

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
            TextView v = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tag_item, parent, false);
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


            public Tag item;
            private OnItemClickListener onClickListener;

            public ViewHolder(View v, OnItemClickListener onClickListener) {
                super(v);
                this.rootView = v;
                v.setOnClickListener(this);
                this.onClickListener = onClickListener;

                this.tvTitle = (TextView) v.findViewById(R.id.tv_item_title);
                this.tvSubtitle = (TextView) v.findViewById(R.id.tv_item_subtitle);

            }

            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(item);
            }

            public void update() {
                tvTitle.setText(item.getName());
                tvSubtitle.setText(item.getAuthor().getNickName());
            }
        }
    }

    private interface OnItemClickListener {
        void onItemClick(Tag tag);
    }

    public class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
