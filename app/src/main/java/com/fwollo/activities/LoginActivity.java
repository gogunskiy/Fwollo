package com.fwollo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fwollo.R;
import com.fwollo.logic.datamanager.DataManager;
import com.fwollo.logic.models.Country;
import com.fwollo.logic.services.CountryService;
import com.fwollo.utils.Dialog;
import com.fwollo.utils.PhoneNumberFormattingTextWatcher;
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
        countryService.update(new CountryService.ServiceCallBack() {
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
        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public synchronized void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                checkLoginButtonVisibility();
            }
        });
    }

    private void checkLoginButtonVisibility() {
        Phonenumber.PhoneNumber phoneObject = getPhoneNumberObject();
        if (phoneObject != null && PhoneNumberUtil.getInstance().isValidNumber(phoneObject)) {
            btnLogin.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.GONE);
        }
    }

    private Phonenumber.PhoneNumber getPhoneNumberObject() {
        final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneObject = null;
        try {
            phoneObject = phoneUtil.parse(phoneNumber.getText().toString(), getCountryCode());
        } catch (NumberParseException e) {

        }
        return phoneObject;
    }

    View.OnClickListener onLoginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dialog.showConfirmAlert(LoginActivity.this,
                    getString(R.string.phone_number_verification_header),
                    getString(R.string.phone_number_verification_text, getFormattedPhoneNumber()),
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

    private String getFormattedPhoneNumber() {
        final String code = countryService.getSelectedCountry().getPhoneCode();
        return "+" + code + " "+ phoneNumber.getText().toString();
    }

    private String getCountryCode() {
        return  countryService.getSelectedCountry().getCode().toUpperCase();
    }
}
