package com.fwollo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fwollo.R;
import com.fwollo.adapters.AnimatedGridLayoutManager;
import com.fwollo.logic.models.Tag;
import com.fwollo.utils.MediaUtils;
import com.github.siyamed.shapeimageview.CircularImageView;

public class FollowingTagDetailsFragment extends MainActivityFragment {

    private Tag tag;

    @Override
    public String getTitle() {
        return tag.getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_following_tag_details, container, false);

        TextView tvSharedBy = (TextView) rootView.findViewById(R.id.tv_shared_by);
        tvSharedBy.setText("Shared by " + tag.getAuthor().getFirstName() + " " + tag.getAuthor().getLastName());

        CircularImageView imgUserThumb = (CircularImageView) rootView.findViewById(R.id.image_user);
        MediaUtils.loadUserImage(getActivity(), tag.getAuthor().getImageUrl(), imgUserThumb);

        update();

        return rootView;
    }

    private void update() {

    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
