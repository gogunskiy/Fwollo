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


public class LoginActivity extends BaseActivity {

    private EditText countryCode;
    private EditText phoneNumber;
    private Button btnLogin;
    private CountryService countryService;

    @Override
    int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    void inflateViews() {

        btnLogin = (Button) findViewById(R.id.btn_login);
        countryCode = (EditText) findViewById(R.id.et_country_code);
        phoneNumber = (EditText) findViewById(R.id.et_phone_number);

        countryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CountryListActivity.class));
            }
        });

        btnLogin.setOnClickListener(onLoginButtonClickListener);
    }

    @Override
    void viewsWereInflated() {
        initiateService();
        initiateTextWatcher();
    }

    @Override
    String title() {
        return "Phone Login";
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    private void initiateService() {
        countryService = DataManager.defaultManager().getCountryService();
        countryService.work(new CountryService.ServiceCallBack() {
            @Override
            public void onSuccess() {
                update();
            }
        });
    }

    private void update() {
        Country selectedCountry = countryService.getSelectedCountry();
        countryCode.setText(selectedCountry.getName() + " (+" + selectedCountry.getPhoneCode() + ")");
        phoneNumber.setText("");
        btnLogin.setVisibility(View.GONE);
    }


    private void initiateTextWatcher() {
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
    }


    View.OnClickListener onLoginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dialog.showConfirmAlert(LoginActivity.this,
                    getString(R.string.phone_number_verification_header),
                    getString(R.string.phone_number_verification_text, phoneNumber.getText().toString()),
                    "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(LoginActivity.this, ActivateAccountActivity.class));
                            finish();
                        }
                    });
        }
    };
}
