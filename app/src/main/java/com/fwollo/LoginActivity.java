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


public class LoginActivity extends android.support.v7.app.ActionBarActivity {

    private EditText countryCode;
    private EditText phoneNumber;
    private Button btnLogin;
    private CountryService countryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btn_login);
        countryCode = (EditText) findViewById(R.id.et_country_code);
        phoneNumber = (EditText) findViewById(R.id.et_phone_number);

        final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    final String defaultRegion = countryService.getSelectedCountry().getCode().toUpperCase();
                    Phonenumber.PhoneNumber phoneObject = phoneUtil.parse(editable.toString(), defaultRegion);
                    if (phoneUtil.isValidNumber(phoneObject)) {
                        btnLogin.setVisibility(View.VISIBLE);
                    } else {
                       btnLogin.setVisibility(View.GONE);
                    }

                } catch (NumberParseException e) {
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Phone Login");

        countryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CountryListActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.showConfirmAlert(LoginActivity.this,
                        getString(R.string.phone_number_verification_header),
                        getString(R.string.phone_number_verification_text,  phoneNumber.getText().toString()),
                        "OK",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        });
        countryService = DataManager.defaultManager().getCountryService();
        countryService.work(new CountryService.ServiceCallBack() {
            @Override
            public void onSuccess() {
                update();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void update() {
        Country selectedCountry = countryService.getSelectedCountry();
        countryCode.setHint(selectedCountry.getName() + " (+" + selectedCountry.getPhoneCode() + ")");
        phoneNumber.setText("");
    }
}
