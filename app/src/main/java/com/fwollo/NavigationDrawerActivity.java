package com.fwollo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.fwollo.utils.Dialog;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

/**
 * Created by neokree on 18/01/15.
 */
public class NavigationDrawerActivity extends MaterialNavigationDrawer {
    @Override
    public void init(Bundle savedInstanceState) {

        // set the header image
        this.setDrawerHeaderImage(R.drawable.header);

         this.addSection(newSection("Fwollo", new HomeFragment()));

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
