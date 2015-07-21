package com.fwollo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fwollo.R;

import com.fwollo.adapters.AnimatedGridLayoutManager;
import com.fwollo.logic.models.Tag;

public class OwnTagDetailsFragment extends MainActivityFragment {

    private RecyclerView recyclerView;
    private Tag tag;

    @Override
    public String getTitle() {
        return tag.getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_own_tag_details, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new AnimatedGridLayoutManager(getActivity()));

        update();

        return rootView;
    }

    private void update() {

    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
