package com.fwollo.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Country;
import com.fwollo.logic.services.CountryService;
import com.fwollo.widgets.fastScroller.BubbleTextGetter;
import com.fwollo.widgets.fastScroller.FastScroller;

import java.util.List;


public class CountryListActivity extends BaseActivity {

    private CountryService service;
    private  RecyclerView recyclerView;

    @Override
    int layoutId() {
        return R.layout.activity_country_list;
    }

    @Override
    void inflateViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FastScroller fastScroller=(FastScroller)findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(recyclerView);
    }

    @Override
    void viewsWereInflated() {
        service = DataManager.defaultManager().getCountryService();

        if (service != null) {
            recyclerView.setAdapter(new CountryAdapter(service.getCountries(), onClickListener));
        }
    }

    @Override
    String title() {
        return "Choose country";
    }

    OnItemClickListener onClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Country country) {
            service.setSelectedCountry(country);
            finish();
        }
    };

    private class CountryAdapter extends RecyclerView.Adapter <CountryAdapter.ViewHolder> implements BubbleTextGetter {
        private List <Country> items;
        private OnItemClickListener onClickListener;

        public CountryAdapter(List <Country> items, OnItemClickListener onClickListener) {
            this.items = items;
            this.onClickListener = onClickListener;
        }

        @Override
        public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            TextView v = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.view_country_item, parent, false);
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
            public TextView tvTitle;
            public Country item;
            private OnItemClickListener onClickListener;

            public ViewHolder(TextView v, OnItemClickListener onClickListener) {
                super(v);
                this.tvTitle = v;
                v.setOnClickListener(this);
                this.onClickListener = onClickListener;
            }

            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(item);
            }

            public void update() {
                tvTitle.setText(item.getName());
            }
        }
    }

    private interface OnItemClickListener {
        void onItemClick(Country country);
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
