package com.fwollo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Country;
import com.fwollo.logic.services.CountryService;
import com.fwollo.utils.Dialog;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class ActivateAccountActivity extends BaseActivity {

    private EditText tvCode;

    @Override
    int layoutId() {
        return R.layout.activity_activate_account;
    }

    @Override
    void inflateViews() {
        tvCode = (EditText) findViewById(R.id.et_code);

    }

    @Override
    void viewsWereInflated() {
        initiateTextWatcher();
    }

    @Override
    String title() {
        return "Phone Login";
    }


    private void initiateTextWatcher() {
        tvCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 3) {
                    startActivity(new Intent(ActivateAccountActivity.this, UserProfileActivity.class));
                    finish();
                }
            }
        });
    }

}
