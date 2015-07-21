package com.fwollo.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.fwollo.R;
import com.fwollo.utils.KeyboardUtils;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

/**
 * Created by neokree on 18/01/15.
 */
public class NavigationDrawerActivity extends MaterialNavigationDrawer {

    private  HomeFragment homeFragment;

    @Override
    public void init(Bundle savedInstanceState) {

        // set the header image
        this.setDrawerHeaderImage(R.drawable.header);
        this.disableLearningPattern();
        homeFragment = new HomeFragment();
        this.addSection(newSection("Fwollo", homeFragment));

        this.addBottomSection(newSection("Sign out", new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection materialSection) {
                startActivity(new Intent(NavigationDrawerActivity.this, LaunchActivity.class));
                finish();
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeFragment.getOnSearchQueryListener().onSearchViewClosed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeFragment.getOnSearchQueryListener().onSearchTextChanged(newText);
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
                    KeyboardUtils.hideKeyboard(NavigationDrawerActivity.this);
                    homeFragment.getOnSearchQueryListener().onSearchViewClosed();
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
